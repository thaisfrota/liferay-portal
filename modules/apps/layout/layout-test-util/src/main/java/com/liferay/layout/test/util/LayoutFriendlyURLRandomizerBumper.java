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

package com.liferay.layout.test.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.LayoutFriendlyURLException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.model.impl.LayoutImpl;

/**
 * @author Shuyang Zhou
 */
public class LayoutFriendlyURLRandomizerBumper
	implements RandomizerBumper<String> {

	public static final LayoutFriendlyURLRandomizerBumper INSTANCE =
		new LayoutFriendlyURLRandomizerBumper();

	@Override
	public boolean accept(String randomValue) {
		if ((randomValue == null) || randomValue.isEmpty()) {
			return false;
		}

		if (randomValue.charAt(0) != CharPool.SLASH) {
			randomValue = StringPool.SLASH.concat(randomValue);
		}

		if (LayoutImpl.validateFriendlyURL(randomValue) != -1) {
			return false;
		}

		try {
			LayoutImpl.validateFriendlyURLKeyword(randomValue);

			return true;
		}
		catch (LayoutFriendlyURLException layoutFriendlyURLException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					layoutFriendlyURLException, layoutFriendlyURLException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutFriendlyURLRandomizerBumper.class);

}