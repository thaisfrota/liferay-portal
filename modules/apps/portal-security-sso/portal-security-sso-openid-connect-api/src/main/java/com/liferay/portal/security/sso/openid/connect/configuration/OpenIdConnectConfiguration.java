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

package com.liferay.portal.security.sso.openid.connect.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Defines the configuration property keys and default values.
 *
 * <p>
 * This class also defines the identity of the configuration schema which, among
 * other things, defines the filename (minus the <code>.cfg</code> extension)
 * for setting values via a file.
 * </p>
 *
 * @author Thuong Dinh
 */
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	localization = "content/Language",
	name = "open-id-connect-configuration-name"
)
@ProviderType
public interface OpenIdConnectConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "set-this-to-true-to-enable-open-id-connect-authentication",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "30", description = "token-refresh-offset-description",
		name = "token-refresh-offset", required = false
	)
	public int tokenRefreshOffset();

}