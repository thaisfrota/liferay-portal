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

package com.liferay.layout.list.retriever;

import com.liferay.info.pagination.Pagination;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class DefaultLayoutListRetrieverContext
	implements LayoutListRetrieverContext {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Optional<long[][]> getAssetCategoryIdsOptional() {
		return Optional.ofNullable(_assetCategoryIds);
	}

	@Override
	public Optional<Map<String, String[]>> getConfigurationOptional() {
		return Optional.ofNullable(_configuration);
	}

	@Override
	public Optional<Object> getContextObjectOptional() {
		return Optional.ofNullable(_contextObject);
	}

	@Override
	public Optional<Map<String, String[]>> getFilterValues() {
		return Optional.ofNullable(_filterValues);
	}

	@Override
	public Optional<HttpServletRequest> getHttpServletRequestOptional() {
		return Optional.ofNullable(_httpServletRequest);
	}

	@Override
	public Optional<Pagination> getPaginationOptional() {
		return Optional.ofNullable(_pagination);
	}

	@Override
	public Optional<long[]> getSegmentsEntryIdsOptional() {
		return Optional.ofNullable(_segmentsEntryIds);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Optional<long[]> getSegmentsExperienceIdsOptional() {
		return Optional.ofNullable(_segmentsExperienceIds);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setAssetCategoryIds(long[][] assetCategoryIds) {
		_assetCategoryIds = assetCategoryIds;
	}

	public void setConfiguration(Map<String, String[]> configuration) {
		_configuration = configuration;
	}

	public void setContextObject(Object contextObject) {
		_contextObject = contextObject;
	}

	public void setFilterValues(Map<String, String[]> filterValues) {
		_filterValues = filterValues;
	}

	public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;
	}

	public void setPagination(Pagination pagination) {
		_pagination = pagination;
	}

	public void setSegmentsEntryIds(long[] segmentsEntryIds) {
		_segmentsEntryIds = segmentsEntryIds;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setSegmentsExperienceIds(long[] segmentsExperienceIds) {
		_segmentsExperienceIds = segmentsExperienceIds;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #setSegmentsExperienceIds(long[])}
	 */
	@Deprecated
	public void setSegmentsExperienceIdsOptional(long[] segmentsExperienceIds) {
		_segmentsExperienceIds = segmentsExperienceIds;
	}

	private long[][] _assetCategoryIds;
	private Map<String, String[]> _configuration;
	private Object _contextObject;
	private Map<String, String[]> _filterValues;
	private HttpServletRequest _httpServletRequest;
	private Pagination _pagination;
	private long[] _segmentsEntryIds;
	private long[] _segmentsExperienceIds;

}