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

package com.liferay.analytics.reports.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.portal.kernel.exception.NestableRuntimeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayResourceResponse;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class GetAnalyticsReportsTotalViewsMVCResourceCommandTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addLayout(_group);
	}

	@Test
	public void testServeResponse() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_mvcResourceCommand, "_http", _geMocktHttp(() -> "12345"));

		try {
			MockLiferayResourceResponse mockLiferayResourceResponse =
				new MockLiferayResourceResponse();

			_mvcResourceCommand.serveResource(
				new MockResourceRequest(), mockLiferayResourceResponse);

			ByteArrayOutputStream byteArrayOutputStream =
				(ByteArrayOutputStream)
					mockLiferayResourceResponse.getPortletOutputStream();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				new String(byteArrayOutputStream.toByteArray()));

			Assert.assertEquals(
				12345L, jsonObject.getLong("analyticsReportsTotalViews"));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_mvcResourceCommand, "_http", _http);
		}
	}

	@Test
	public void testServeResponseWithError() throws Exception {
		ReflectionTestUtil.setFieldValue(
			_mvcResourceCommand, "_http",
			_geMocktHttp(
				() -> {
					throw new NestableRuntimeException();
				}));

		try {
			MockLiferayResourceResponse mockLiferayResourceResponse =
				new MockLiferayResourceResponse();

			_mvcResourceCommand.serveResource(
				new MockResourceRequest(), mockLiferayResourceResponse);

			ByteArrayOutputStream byteArrayOutputStream =
				(ByteArrayOutputStream)
					mockLiferayResourceResponse.getPortletOutputStream();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				new String(byteArrayOutputStream.toByteArray()));

			Assert.assertEquals(
				"An unexpected error occurred.", jsonObject.getString("error"));
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				_mvcResourceCommand, "_http", _http);
		}
	}

	private Http _geMocktHttp(
		UnsafeSupplier<String, Exception> unsafeSupplier) {

		return (Http)ProxyUtil.newProxyInstance(
			Http.class.getClassLoader(), new Class<?>[] {Http.class},
			(proxy, method, args) -> {
				if (!Objects.equals(method.getName(), "URLtoString")) {
					throw new UnsupportedOperationException();
				}

				try {
					String response = unsafeSupplier.get();

					Http.Options options = (Http.Options)args[0];

					Http.Response httpResponse = new Http.Response();

					httpResponse.setResponseCode(200);

					options.setResponse(httpResponse);

					return response;
				}
				catch (Throwable throwable) {
					Http.Options options = (Http.Options)args[0];

					Http.Response httpResponse = new Http.Response();

					httpResponse.setResponseCode(400);

					options.setResponse(httpResponse);

					throw throwable;
				}
			});
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Http _http;

	private Layout _layout;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(filter = "mvc.command.name=/analytics_reports/get_total_views")
	private MVCResourceCommand _mvcResourceCommand;

	private class MockResourceRequest extends MockLiferayResourceRequest {

		public MockResourceRequest() {
			HttpServletRequest httpServletRequest = getHttpServletRequest();

			try {
				httpServletRequest.setAttribute(
					WebKeys.THEME_DISPLAY, _getThemeDisplay());

				httpServletRequest.setAttribute(
					JavaConstants.JAVAX_PORTLET_CONFIG,
					ProxyUtil.newProxyInstance(
						LiferayPortletConfig.class.getClassLoader(),
						new Class<?>[] {LiferayPortletConfig.class},
						(proxy, method, args) -> {
							if (Objects.equals(
									method.getName(), "getPortletId")) {

								return "testPortlet";
							}

							return null;
						}));
			}
			catch (PortalException portalException) {
				throw new AssertionError(portalException);
			}
		}

		private ThemeDisplay _getThemeDisplay() throws PortalException {
			ThemeDisplay themeDisplay = new ThemeDisplay();

			Company company = _companyLocalService.getCompany(
				TestPropsValues.getCompanyId());

			themeDisplay.setCompany(company);

			themeDisplay.setLanguageId(_group.getDefaultLanguageId());
			themeDisplay.setLocale(
				LocaleUtil.fromLanguageId(_group.getDefaultLanguageId()));
			themeDisplay.setLayout(_layout);
			themeDisplay.setLayoutSet(
				_layoutSetLocalService.getLayoutSet(
					_group.getGroupId(), false));
			themeDisplay.setPortalURL(
				company.getPortalURL(_group.getGroupId()));
			themeDisplay.setPortalDomain("localhost");
			themeDisplay.setSecure(true);
			themeDisplay.setServerName("localhost");
			themeDisplay.setServerPort(8080);
			themeDisplay.setSiteGroupId(_group.getGroupId());

			return themeDisplay;
		}

	}

}