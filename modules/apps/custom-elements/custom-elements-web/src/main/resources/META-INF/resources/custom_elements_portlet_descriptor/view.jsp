<%--
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
--%>

<%@ include file="/custom_elements_portlet_descriptor/init.jsp" %>

<%
CustomElementsPortletDescriptorDisplayContext customElementsPortletDescriptorDisplayContext = (CustomElementsPortletDescriptorDisplayContext)renderRequest.getAttribute(CustomElementsWebKeys.CUSTOM_ELEMENTS_PORTLET_DESCRIPTOR_DISPLAY_CONTEXT);
%>

<clay:data-set-display
	actionParameterName="customElementsPortletDescriptorEntryId"
	creationMenu="<%= customElementsPortletDescriptorDisplayContext.getCreationMenu() %>"
	dataProviderKey="<%= CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_PORTLET_DESCRIPTORS %>"
	formId='<%= liferayPortletResponse.getNamespace() + "fm" %>'
	id="<%= CustomElementsClayDataSetDisplayNames.CUSTOM_ELEMENT_PORTLET_DESCRIPTORS %>"
	itemsPerPage="<%= 10 %>"
	namespace="<%= liferayPortletResponse.getNamespace() %>"
	pageNumber="<%= 1 %>"
	portletURL="<%= customElementsPortletDescriptorDisplayContext.getCurrentPortletURL() %>"
	selectedItemsKey="customElementsPortletDescriptorEntryId"
	selectionType="multiple"
	style="fluid"
/>