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

<%@ include file="/sharing/init.jsp" %>

<%
String randomNamespace = PortalUtil.generateRandomKey(request, "taglib_sharing_button_page") + StringPool.UNDERLINE;

String buttonComponentId = randomNamespace + "shareButton";
%>

<button class="btn btn-secondary btn-sm" id="<%= buttonComponentId %>" title="<%= LanguageUtil.get(request, "share") %>" type="button">
	<liferay-ui:message key="share" />
</button>

<aui:script>
	var button = document.getElementById('<%= buttonComponentId %>');

	button.addEventListener(
		'click',
		function() {
			<%= request.getAttribute("liferay-sharing:button:onClick") %>
		}
	);

	<%= request.getAttribute("liferay-sharing:button:javaScript") %>
</aui:script>