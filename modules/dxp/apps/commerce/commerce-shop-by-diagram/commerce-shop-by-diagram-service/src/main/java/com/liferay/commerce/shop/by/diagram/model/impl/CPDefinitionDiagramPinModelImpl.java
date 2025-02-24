/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.model.impl;

import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPin;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPinModel;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramPinSoap;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * The base model implementation for the CPDefinitionDiagramPin service. Represents a row in the &quot;CPDefinitionDiagramPin&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface <code>CPDefinitionDiagramPinModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CPDefinitionDiagramPinImpl}.
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPinImpl
 * @generated
 */
@JSON(strict = true)
public class CPDefinitionDiagramPinModelImpl
	extends BaseModelImpl<CPDefinitionDiagramPin>
	implements CPDefinitionDiagramPinModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cp definition diagram pin model instance should use the <code>CPDefinitionDiagramPin</code> interface instead.
	 */
	public static final String TABLE_NAME = "CPDefinitionDiagramPin";

	public static final Object[][] TABLE_COLUMNS = {
		{"CPDefinitionDiagramPinId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"CPDefinitionId", Types.BIGINT}, {"positionX", Types.DOUBLE},
		{"positionY", Types.DOUBLE}, {"sequence", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("CPDefinitionDiagramPinId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("CPDefinitionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("positionX", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("positionY", Types.DOUBLE);
		TABLE_COLUMNS_MAP.put("sequence", Types.VARCHAR);
	}

	public static final String TABLE_SQL_CREATE =
		"create table CPDefinitionDiagramPin (CPDefinitionDiagramPinId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,CPDefinitionId LONG,positionX DOUBLE,positionY DOUBLE,sequence VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
		"drop table CPDefinitionDiagramPin";

	public static final String ORDER_BY_JPQL =
		" ORDER BY cpDefinitionDiagramPin.sequence ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY CPDefinitionDiagramPin.sequence ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long CPDEFINITIONID_COLUMN_BITMASK = 1L;

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *		#getColumnBitmask(String)}
	 */
	@Deprecated
	public static final long SEQUENCE_COLUMN_BITMASK = 2L;

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setEntityCacheEnabled(boolean entityCacheEnabled) {
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static void setFinderCacheEnabled(boolean finderCacheEnabled) {
	}

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static CPDefinitionDiagramPin toModel(
		CPDefinitionDiagramPinSoap soapModel) {

		if (soapModel == null) {
			return null;
		}

		CPDefinitionDiagramPin model = new CPDefinitionDiagramPinImpl();

		model.setCPDefinitionDiagramPinId(
			soapModel.getCPDefinitionDiagramPinId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCPDefinitionId(soapModel.getCPDefinitionId());
		model.setPositionX(soapModel.getPositionX());
		model.setPositionY(soapModel.getPositionY());
		model.setSequence(soapModel.getSequence());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public static List<CPDefinitionDiagramPin> toModels(
		CPDefinitionDiagramPinSoap[] soapModels) {

		if (soapModels == null) {
			return null;
		}

		List<CPDefinitionDiagramPin> models =
			new ArrayList<CPDefinitionDiagramPin>(soapModels.length);

		for (CPDefinitionDiagramPinSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public CPDefinitionDiagramPinModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _CPDefinitionDiagramPinId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCPDefinitionDiagramPinId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _CPDefinitionDiagramPinId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinitionDiagramPin.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinitionDiagramPin.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<CPDefinitionDiagramPin, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<CPDefinitionDiagramPin, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramPin, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((CPDefinitionDiagramPin)this));
		}

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<CPDefinitionDiagramPin, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<CPDefinitionDiagramPin, Object>
				attributeSetterBiConsumer = attributeSetterBiConsumers.get(
					attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(CPDefinitionDiagramPin)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<CPDefinitionDiagramPin, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<CPDefinitionDiagramPin, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, CPDefinitionDiagramPin>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			CPDefinitionDiagramPin.class.getClassLoader(),
			CPDefinitionDiagramPin.class, ModelWrapper.class);

		try {
			Constructor<CPDefinitionDiagramPin> constructor =
				(Constructor<CPDefinitionDiagramPin>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new InternalError(reflectiveOperationException);
				}
			};
		}
		catch (NoSuchMethodException noSuchMethodException) {
			throw new InternalError(noSuchMethodException);
		}
	}

	private static final Map<String, Function<CPDefinitionDiagramPin, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<CPDefinitionDiagramPin, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<CPDefinitionDiagramPin, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<CPDefinitionDiagramPin, Object>>();
		Map<String, BiConsumer<CPDefinitionDiagramPin, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<CPDefinitionDiagramPin, ?>>();

		attributeGetterFunctions.put(
			"CPDefinitionDiagramPinId",
			CPDefinitionDiagramPin::getCPDefinitionDiagramPinId);
		attributeSetterBiConsumers.put(
			"CPDefinitionDiagramPinId",
			(BiConsumer<CPDefinitionDiagramPin, Long>)
				CPDefinitionDiagramPin::setCPDefinitionDiagramPinId);
		attributeGetterFunctions.put(
			"companyId", CPDefinitionDiagramPin::getCompanyId);
		attributeSetterBiConsumers.put(
			"companyId",
			(BiConsumer<CPDefinitionDiagramPin, Long>)
				CPDefinitionDiagramPin::setCompanyId);
		attributeGetterFunctions.put(
			"userId", CPDefinitionDiagramPin::getUserId);
		attributeSetterBiConsumers.put(
			"userId",
			(BiConsumer<CPDefinitionDiagramPin, Long>)
				CPDefinitionDiagramPin::setUserId);
		attributeGetterFunctions.put(
			"userName", CPDefinitionDiagramPin::getUserName);
		attributeSetterBiConsumers.put(
			"userName",
			(BiConsumer<CPDefinitionDiagramPin, String>)
				CPDefinitionDiagramPin::setUserName);
		attributeGetterFunctions.put(
			"createDate", CPDefinitionDiagramPin::getCreateDate);
		attributeSetterBiConsumers.put(
			"createDate",
			(BiConsumer<CPDefinitionDiagramPin, Date>)
				CPDefinitionDiagramPin::setCreateDate);
		attributeGetterFunctions.put(
			"modifiedDate", CPDefinitionDiagramPin::getModifiedDate);
		attributeSetterBiConsumers.put(
			"modifiedDate",
			(BiConsumer<CPDefinitionDiagramPin, Date>)
				CPDefinitionDiagramPin::setModifiedDate);
		attributeGetterFunctions.put(
			"CPDefinitionId", CPDefinitionDiagramPin::getCPDefinitionId);
		attributeSetterBiConsumers.put(
			"CPDefinitionId",
			(BiConsumer<CPDefinitionDiagramPin, Long>)
				CPDefinitionDiagramPin::setCPDefinitionId);
		attributeGetterFunctions.put(
			"positionX", CPDefinitionDiagramPin::getPositionX);
		attributeSetterBiConsumers.put(
			"positionX",
			(BiConsumer<CPDefinitionDiagramPin, Double>)
				CPDefinitionDiagramPin::setPositionX);
		attributeGetterFunctions.put(
			"positionY", CPDefinitionDiagramPin::getPositionY);
		attributeSetterBiConsumers.put(
			"positionY",
			(BiConsumer<CPDefinitionDiagramPin, Double>)
				CPDefinitionDiagramPin::setPositionY);
		attributeGetterFunctions.put(
			"sequence", CPDefinitionDiagramPin::getSequence);
		attributeSetterBiConsumers.put(
			"sequence",
			(BiConsumer<CPDefinitionDiagramPin, String>)
				CPDefinitionDiagramPin::setSequence);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@JSON
	@Override
	public long getCPDefinitionDiagramPinId() {
		return _CPDefinitionDiagramPinId;
	}

	@Override
	public void setCPDefinitionDiagramPinId(long CPDefinitionDiagramPinId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPDefinitionDiagramPinId = CPDefinitionDiagramPinId;
	}

	@JSON
	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_companyId = companyId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException portalException) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@JSON
	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_userName = userName;
	}

	@JSON
	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_createDate = createDate;
	}

	@JSON
	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_modifiedDate = modifiedDate;
	}

	@JSON
	@Override
	public long getCPDefinitionId() {
		return _CPDefinitionId;
	}

	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_CPDefinitionId = CPDefinitionId;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getColumnOriginalValue(String)}
	 */
	@Deprecated
	public long getOriginalCPDefinitionId() {
		return GetterUtil.getLong(
			this.<Long>getColumnOriginalValue("CPDefinitionId"));
	}

	@JSON
	@Override
	public double getPositionX() {
		return _positionX;
	}

	@Override
	public void setPositionX(double positionX) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_positionX = positionX;
	}

	@JSON
	@Override
	public double getPositionY() {
		return _positionY;
	}

	@Override
	public void setPositionY(double positionY) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_positionY = positionY;
	}

	@JSON
	@Override
	public String getSequence() {
		if (_sequence == null) {
			return "";
		}
		else {
			return _sequence;
		}
	}

	@Override
	public void setSequence(String sequence) {
		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		_sequence = sequence;
	}

	public long getColumnBitmask() {
		if (_columnBitmask > 0) {
			return _columnBitmask;
		}

		if ((_columnOriginalValues == null) ||
			(_columnOriginalValues == Collections.EMPTY_MAP)) {

			return 0;
		}

		for (Map.Entry<String, Object> entry :
				_columnOriginalValues.entrySet()) {

			if (!Objects.equals(
					entry.getValue(), getColumnValue(entry.getKey()))) {

				_columnBitmask |= _columnBitmasks.get(entry.getKey());
			}
		}

		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(
			getCompanyId(), CPDefinitionDiagramPin.class.getName(),
			getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public CPDefinitionDiagramPin toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, CPDefinitionDiagramPin>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		CPDefinitionDiagramPinImpl cpDefinitionDiagramPinImpl =
			new CPDefinitionDiagramPinImpl();

		cpDefinitionDiagramPinImpl.setCPDefinitionDiagramPinId(
			getCPDefinitionDiagramPinId());
		cpDefinitionDiagramPinImpl.setCompanyId(getCompanyId());
		cpDefinitionDiagramPinImpl.setUserId(getUserId());
		cpDefinitionDiagramPinImpl.setUserName(getUserName());
		cpDefinitionDiagramPinImpl.setCreateDate(getCreateDate());
		cpDefinitionDiagramPinImpl.setModifiedDate(getModifiedDate());
		cpDefinitionDiagramPinImpl.setCPDefinitionId(getCPDefinitionId());
		cpDefinitionDiagramPinImpl.setPositionX(getPositionX());
		cpDefinitionDiagramPinImpl.setPositionY(getPositionY());
		cpDefinitionDiagramPinImpl.setSequence(getSequence());

		cpDefinitionDiagramPinImpl.resetOriginalValues();

		return cpDefinitionDiagramPinImpl;
	}

	@Override
	public int compareTo(CPDefinitionDiagramPin cpDefinitionDiagramPin) {
		int value = 0;

		value = getSequence().compareTo(cpDefinitionDiagramPin.getSequence());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CPDefinitionDiagramPin)) {
			return false;
		}

		CPDefinitionDiagramPin cpDefinitionDiagramPin =
			(CPDefinitionDiagramPin)object;

		long primaryKey = cpDefinitionDiagramPin.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isEntityCacheEnabled() {
		return true;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public boolean isFinderCacheEnabled() {
		return true;
	}

	@Override
	public void resetOriginalValues() {
		_columnOriginalValues = Collections.emptyMap();

		_setModifiedDate = false;

		_columnBitmask = 0;
	}

	@Override
	public CacheModel<CPDefinitionDiagramPin> toCacheModel() {
		CPDefinitionDiagramPinCacheModel cpDefinitionDiagramPinCacheModel =
			new CPDefinitionDiagramPinCacheModel();

		cpDefinitionDiagramPinCacheModel.CPDefinitionDiagramPinId =
			getCPDefinitionDiagramPinId();

		cpDefinitionDiagramPinCacheModel.companyId = getCompanyId();

		cpDefinitionDiagramPinCacheModel.userId = getUserId();

		cpDefinitionDiagramPinCacheModel.userName = getUserName();

		String userName = cpDefinitionDiagramPinCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			cpDefinitionDiagramPinCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			cpDefinitionDiagramPinCacheModel.createDate = createDate.getTime();
		}
		else {
			cpDefinitionDiagramPinCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			cpDefinitionDiagramPinCacheModel.modifiedDate =
				modifiedDate.getTime();
		}
		else {
			cpDefinitionDiagramPinCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		cpDefinitionDiagramPinCacheModel.CPDefinitionId = getCPDefinitionId();

		cpDefinitionDiagramPinCacheModel.positionX = getPositionX();

		cpDefinitionDiagramPinCacheModel.positionY = getPositionY();

		cpDefinitionDiagramPinCacheModel.sequence = getSequence();

		String sequence = cpDefinitionDiagramPinCacheModel.sequence;

		if ((sequence != null) && (sequence.length() == 0)) {
			cpDefinitionDiagramPinCacheModel.sequence = null;
		}

		return cpDefinitionDiagramPinCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<CPDefinitionDiagramPin, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(4 * attributeGetterFunctions.size()) + 2);

		sb.append("{");

		for (Map.Entry<String, Function<CPDefinitionDiagramPin, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramPin, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((CPDefinitionDiagramPin)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<CPDefinitionDiagramPin, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			(5 * attributeGetterFunctions.size()) + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<CPDefinitionDiagramPin, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<CPDefinitionDiagramPin, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((CPDefinitionDiagramPin)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, CPDefinitionDiagramPin>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _CPDefinitionDiagramPinId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private long _CPDefinitionId;
	private double _positionX;
	private double _positionY;
	private String _sequence;

	public <T> T getColumnValue(String columnName) {
		Function<CPDefinitionDiagramPin, Object> function =
			_attributeGetterFunctions.get(columnName);

		if (function == null) {
			throw new IllegalArgumentException(
				"No attribute getter function found for " + columnName);
		}

		return (T)function.apply((CPDefinitionDiagramPin)this);
	}

	public <T> T getColumnOriginalValue(String columnName) {
		if (_columnOriginalValues == null) {
			return null;
		}

		if (_columnOriginalValues == Collections.EMPTY_MAP) {
			_setColumnOriginalValues();
		}

		return (T)_columnOriginalValues.get(columnName);
	}

	private void _setColumnOriginalValues() {
		_columnOriginalValues = new HashMap<String, Object>();

		_columnOriginalValues.put(
			"CPDefinitionDiagramPinId", _CPDefinitionDiagramPinId);
		_columnOriginalValues.put("companyId", _companyId);
		_columnOriginalValues.put("userId", _userId);
		_columnOriginalValues.put("userName", _userName);
		_columnOriginalValues.put("createDate", _createDate);
		_columnOriginalValues.put("modifiedDate", _modifiedDate);
		_columnOriginalValues.put("CPDefinitionId", _CPDefinitionId);
		_columnOriginalValues.put("positionX", _positionX);
		_columnOriginalValues.put("positionY", _positionY);
		_columnOriginalValues.put("sequence", _sequence);
	}

	private transient Map<String, Object> _columnOriginalValues;

	public static long getColumnBitmask(String columnName) {
		return _columnBitmasks.get(columnName);
	}

	private static final Map<String, Long> _columnBitmasks;

	static {
		Map<String, Long> columnBitmasks = new HashMap<>();

		columnBitmasks.put("CPDefinitionDiagramPinId", 1L);

		columnBitmasks.put("companyId", 2L);

		columnBitmasks.put("userId", 4L);

		columnBitmasks.put("userName", 8L);

		columnBitmasks.put("createDate", 16L);

		columnBitmasks.put("modifiedDate", 32L);

		columnBitmasks.put("CPDefinitionId", 64L);

		columnBitmasks.put("positionX", 128L);

		columnBitmasks.put("positionY", 256L);

		columnBitmasks.put("sequence", 512L);

		_columnBitmasks = Collections.unmodifiableMap(columnBitmasks);
	}

	private long _columnBitmask;
	private CPDefinitionDiagramPin _escapedModel;

}