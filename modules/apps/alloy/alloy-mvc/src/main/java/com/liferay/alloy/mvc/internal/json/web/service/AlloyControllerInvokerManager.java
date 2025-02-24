/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.alloy.mvc.internal.json.web.service;

import com.liferay.alloy.mvc.AlloyController;
import com.liferay.alloy.mvc.AlloyPortlet;
import com.liferay.alloy.mvc.json.web.service.AlloyControllerInvoker;
import com.liferay.alloy.mvc.json.web.service.BaseAlloyControllerInvokerImpl;
import com.liferay.alloy.mvc.json.web.service.JSONWebServiceMethod;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceActionsManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.portlet.PortletContext;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Ethan Bustad
 */
public class AlloyControllerInvokerManager {

	public AlloyControllerInvokerManager(
		LiferayPortletConfig liferayPortletConfig) {

		PortletContext portletContext =
			liferayPortletConfig.getPortletContext();

		_contextName = portletContext.getPortletContextName();
		_contextPath =
			StringPool.SLASH + portletContext.getPortletContextName();
	}

	public JSONSerializable invokeAlloyController(
			String controller, String lifecycle, String action,
			Object[] parameters)
		throws Exception {

		AlloyControllerInvoker alloyControllerInvoker =
			_alloyControllerInvokers.get(controller);

		parameters = ArrayUtil.append(
			parameters,
			new Object[] {
				"controller", controller, "action", action, "format", "json"
			});

		return alloyControllerInvoker.invokeAlloyController(
			lifecycle, parameters);
	}

	public void registerController(
		ThemeDisplay themeDisplay, AlloyPortlet alloyPortlet, Portlet portlet,
		String controller, Class<? extends AlloyController> controllerClass) {

		if (_locked) {
			return;
		}

		if (_alloyControllerInvokers.containsKey(controller)) {
			AlloyControllerInvoker alloyControllerInvoker =
				_alloyControllerInvokers.get(controller);

			JSONWebServiceActionsManagerUtil.unregisterJSONWebServiceActions(
				alloyControllerInvoker);
		}

		Class<? extends AlloyControllerInvoker> alloyControllerInvokerClass =
			null;

		AlloyControllerInvoker alloyControllerInvoker = null;

		try {
			alloyControllerInvokerClass = createAlloyControllerInvokerClass(
				controllerClass);

			Constructor<? extends AlloyControllerInvoker> constructor =
				alloyControllerInvokerClass.getConstructor();

			alloyControllerInvoker = constructor.newInstance();

			alloyControllerInvoker.setProperties(
				themeDisplay, alloyPortlet, portlet, controller,
				controllerClass);

			_alloyControllerInvokers.put(controller, alloyControllerInvoker);
		}
		catch (NoClassNecessaryException noClassNecessaryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					noClassNecessaryException, noClassNecessaryException);
			}

			return;
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}

		for (Method method : alloyControllerInvokerClass.getDeclaredMethods()) {
			JSONWebServiceActionsManagerUtil.registerJSONWebServiceAction(
				_contextName, _contextPath, alloyControllerInvoker,
				alloyControllerInvokerClass, method,
				getAPIPath(controller, method), "GET");
		}
	}

	public void unregisterControllers() {
		_locked = true;

		for (Map.Entry<String, AlloyControllerInvoker> entry :
				_alloyControllerInvokers.entrySet()) {

			String controller = entry.getKey();

			synchronized (controller.intern()) {
				AlloyControllerInvoker alloyControllerInvoker =
					entry.getValue();

				JSONWebServiceActionsManagerUtil.
					unregisterJSONWebServiceActions(alloyControllerInvoker);
			}
		}

		_alloyControllerInvokers.clear();
	}

	protected Class<? extends AlloyControllerInvoker>
			createAlloyControllerInvokerClass(
				Class<? extends AlloyController> controllerClass)
		throws NoClassNecessaryException {

		ClassLoader classLoader = controllerClass.getClassLoader();

		String alloyControllerInvokerClassName =
			getAlloyControllerInvokerClassName(controllerClass);

		synchronized (classLoader) {
			try {
				Method defineClassMethod = ReflectionUtil.getDeclaredMethod(
					ClassLoader.class, "defineClass", String.class,
					byte[].class, int.class, int.class);

				final byte[] classData =
					generateAlloyControllerInvokerClassData(
						controllerClass, alloyControllerInvokerClassName);

				final String fileName = StringBundler.concat(
					PropsUtil.get(PropsKeys.LIFERAY_HOME), "/data/alloy/",
					getClassBinaryName(alloyControllerInvokerClassName),
					".class");

				ClassLoader customClassLoader = new ClassLoader(classLoader) {

					@Override
					public URL getResource(String name) {
						if (fileName.contains(name)) {
							File file = new File(fileName);

							try {
								FileUtil.write(file, classData);

								URI uri = file.toURI();

								return uri.toURL();
							}
							catch (Exception exception) {
								throw new RuntimeException(exception);
							}
						}

						return super.getResource(name);
					}

				};

				return (Class<? extends AlloyControllerInvoker>)
					defineClassMethod.invoke(
						customClassLoader, alloyControllerInvokerClassName,
						classData, 0, classData.length);
			}
			catch (NoClassNecessaryException noClassNecessaryException) {
				throw noClassNecessaryException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}
	}

	protected byte[] generateAlloyControllerInvokerClassData(
			Class<?> controllerClass, String alloyControllerInvokerClassName)
		throws NoClassNecessaryException {

		boolean jsonWebServiceMethodsPresent = false;

		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

		String alloyControllerInvokerClassBinaryName = getClassBinaryName(
			alloyControllerInvokerClassName);

		String baseAlloyControllerInvokerClassBinaryName = getClassBinaryName(
			BaseAlloyControllerInvokerImpl.class.getName());

		classWriter.visit(
			Opcodes.V1_5, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER,
			alloyControllerInvokerClassBinaryName, null,
			baseAlloyControllerInvokerClassBinaryName, null);

		MethodVisitor methodVisitor = classWriter.visitMethod(
			Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);

		methodVisitor.visitCode();
		methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
		methodVisitor.visitMethodInsn(
			Opcodes.INVOKESPECIAL, baseAlloyControllerInvokerClassBinaryName,
			"<init>", "()V");
		methodVisitor.visitInsn(Opcodes.RETURN);
		methodVisitor.visitMaxs(1, 1);
		methodVisitor.visitEnd();

		Method[] methods = controllerClass.getDeclaredMethods();

		for (Method method : methods) {
			if (!Modifier.isPublic(method.getModifiers())) {
				continue;
			}

			JSONWebServiceMethod jsonWebServiceMethod = method.getAnnotation(
				JSONWebServiceMethod.class);

			if (jsonWebServiceMethod == null) {
				continue;
			}

			jsonWebServiceMethodsPresent = true;

			String methodName = jsonWebServiceMethod.methodName();

			if (Validator.isNull(methodName)) {
				methodName = method.getName();
			}

			Class<?>[] parameterTypes = jsonWebServiceMethod.parameterTypes();

			StringBundler sb = new StringBundler(parameterTypes.length + 3);

			sb.append(StringPool.OPEN_PARENTHESIS);

			for (Class<?> parameterType : parameterTypes) {
				sb.append(Type.getDescriptor(parameterType));
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(Type.getDescriptor(JSONSerializable.class));

			String methodDescriptor = sb.toString();

			methodVisitor = classWriter.visitMethod(
				Opcodes.ACC_PUBLIC, methodName, methodDescriptor, null,
				new String[] {getClassBinaryName(Exception.class.getName())});

			methodVisitor.visitCode();

			for (int i = 0; i < parameterTypes.length; i++) {
				String parameterName = jsonWebServiceMethod.parameterNames()[i];
				Class<?> parameterType = parameterTypes[i];

				methodVisitor.visitLocalVariable(
					parameterName, Type.getDescriptor(parameterType), null,
					new Label(), new Label(), i + 1);
			}

			methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
			methodVisitor.visitLdcInsn(jsonWebServiceMethod.lifecycle());

			methodVisitor.visitIntInsn(
				Opcodes.BIPUSH, (parameterTypes.length * 2) + 2);
			methodVisitor.visitTypeInsn(
				Opcodes.ANEWARRAY, getClassBinaryName(Object.class.getName()));

			methodVisitor.visitInsn(Opcodes.DUP);
			methodVisitor.visitInsn(Opcodes.ICONST_0);
			methodVisitor.visitLdcInsn("action");
			methodVisitor.visitInsn(Opcodes.AASTORE);

			methodVisitor.visitInsn(Opcodes.DUP);
			methodVisitor.visitInsn(Opcodes.ICONST_1);
			methodVisitor.visitLdcInsn(method.getName());
			methodVisitor.visitInsn(Opcodes.AASTORE);

			for (int i = 0; i < parameterTypes.length; i++) {
				String parameterName = jsonWebServiceMethod.parameterNames()[i];

				methodVisitor.visitInsn(Opcodes.DUP);
				methodVisitor.visitIntInsn(Opcodes.BIPUSH, (i + 1) * 2);
				methodVisitor.visitLdcInsn(parameterName);
				methodVisitor.visitInsn(Opcodes.AASTORE);

				methodVisitor.visitInsn(Opcodes.DUP);
				methodVisitor.visitIntInsn(Opcodes.BIPUSH, ((i + 1) * 2) + 1);
				methodVisitor.visitVarInsn(Opcodes.ALOAD, i + 1);
				methodVisitor.visitInsn(Opcodes.AASTORE);
			}

			sb = new StringBundler(5);

			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(Type.getDescriptor(String.class));
			sb.append(Type.getDescriptor(Object[].class));
			sb.append(StringPool.CLOSE_PARENTHESIS);
			sb.append(Type.getDescriptor(JSONSerializable.class));

			methodVisitor.visitMethodInsn(
				Opcodes.INVOKEVIRTUAL, alloyControllerInvokerClassBinaryName,
				"invokeAlloyController", sb.toString());

			methodVisitor.visitInsn(Opcodes.ARETURN);

			methodVisitor.visitMaxs(-1, -1);
			methodVisitor.visitEnd();
		}

		classWriter.visitEnd();

		if (!jsonWebServiceMethodsPresent) {
			throw new NoClassNecessaryException();
		}

		return classWriter.toByteArray();
	}

	protected String getAlloyControllerInvokerClassName(
		Class<? extends AlloyController> controllerClass) {

		String prefix = StringPool.BLANK;
		String simpleName = StringPool.BLANK;

		Class<?> enclosingClass = controllerClass.getEnclosingClass();

		if (enclosingClass != null) {
			prefix = enclosingClass.getName();

			String name = StringUtil.removeSubstring(
				enclosingClass.getSimpleName(), "005f");

			int trimIndex = name.indexOf("_controller");

			String[] wordElements = StringUtil.split(
				name.substring(0, trimIndex), CharPool.UNDERLINE);

			for (String wordElement : wordElements) {
				simpleName = simpleName.concat(
					StringUtil.upperCaseFirstLetter(wordElement));
			}

			simpleName = simpleName.concat(_BASE_CLASS_NAME);
		}
		else if (StringUtil.endsWith(controllerClass.getName(), "Controller")) {
			Package pkg = controllerClass.getPackage();

			prefix = pkg.getName();

			simpleName = controllerClass.getSimpleName();

			simpleName =
				simpleName.substring(0, simpleName.indexOf("Controller")) +
					_BASE_CLASS_NAME;
		}
		else {
			prefix = controllerClass.getName();

			simpleName = _BASE_CLASS_NAME + _counter.getAndIncrement();
		}

		return prefix + StringPool.PERIOD + simpleName;
	}

	protected String getAPIPath(String controller, Method method) {
		return StringBundler.concat(
			StringPool.SLASH, controller, StringPool.SLASH, method.getName());
	}

	protected String getClassBinaryName(String className) {
		return StringUtil.replace(className, '.', '/');
	}

	protected class NoClassNecessaryException extends Exception {

		public NoClassNecessaryException() {
		}

		public NoClassNecessaryException(String message) {
			super(message);
		}

	}

	private static final String _BASE_CLASS_NAME = "AlloyControllerInvokerImpl";

	private static final Log _log = LogFactoryUtil.getLog(
		AlloyControllerInvokerManager.class);

	private final Map<String, AlloyControllerInvoker> _alloyControllerInvokers =
		new ConcurrentHashMap<>();
	private final String _contextName;
	private final String _contextPath;
	private final AtomicInteger _counter = new AtomicInteger(0);
	private boolean _locked;

}