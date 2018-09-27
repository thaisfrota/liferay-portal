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

package com.liferay.bean.portlet.cdi.extension.internal.xml;

import java.util.Map;
import java.util.Set;

/**
 * @author Neil Griffin
 */
public class BeanFilterDescriptorImpl extends BaseBeanFilterImpl {

	public BeanFilterDescriptorImpl(
		String filterName, Class<?> filterClass, int ordinal,
		Set<String> portletNames, Set<String> lifecycles,
		Map<String, String> initParams) {

		_filterName = filterName;
		_filterClass = filterClass;
		_ordinal = ordinal;
		_portletNames = portletNames;
		_lifecycles = lifecycles;
		_initParams = initParams;
	}

	@Override
	public Class<?> getFilterClass() {
		return _filterClass;
	}

	@Override
	public String getFilterName() {
		return _filterName;
	}

	@Override
	public Map<String, String> getInitParams() {
		return _initParams;
	}

	@Override
	public Set<String> getLifecycles() {
		return _lifecycles;
	}

	@Override
	public int getOrdinal() {
		return _ordinal;
	}

	@Override
	public Set<String> getPortletNames() {
		return _portletNames;
	}

	private final Class<?> _filterClass;
	private final String _filterName;
	private final Map<String, String> _initParams;
	private final Set<String> _lifecycles;
	private final int _ordinal;
	private final Set<String> _portletNames;

}