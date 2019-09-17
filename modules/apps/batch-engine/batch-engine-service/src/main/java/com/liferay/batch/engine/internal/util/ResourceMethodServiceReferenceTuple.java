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

package com.liferay.batch.engine.internal.util;

import java.lang.reflect.Method;

import org.osgi.framework.ServiceObjects;

/**
 * @author Ivica Cardic
 */
public class ResourceMethodServiceReferenceTuple {

	public ResourceMethodServiceReferenceTuple(
		Method method, String[] itemClassFieldNames,
		ServiceObjects<Object> serviceObjects) {

		_method = method;
		_itemClassFieldNames = itemClassFieldNames;
		_serviceObjects = serviceObjects;
	}

	public String[] getItemClassFieldNames() {
		return _itemClassFieldNames;
	}

	public Method getMethod() {
		return _method;
	}

	public ServiceObjects<Object> getServiceObjects() {
		return _serviceObjects;
	}

	private final String[] _itemClassFieldNames;
	private final Method _method;
	private final ServiceObjects<Object> _serviceObjects;

}