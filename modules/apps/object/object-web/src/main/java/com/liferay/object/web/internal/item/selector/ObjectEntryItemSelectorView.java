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

package com.liferay.object.web.internal.item.selector;

import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.item.selector.ItemSelectorViewDescriptorRenderer;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Guilherme Camacho
 */
@Component(
	property = "item.selector.view.order:Integer=500",
	service = ItemSelectorView.class
)
public class ObjectEntryItemSelectorView
	implements InfoItemSelectorView,
			   ItemSelectorView<InfoItemItemSelectorCriterion> {

	@Override
	public String getClassName() {
		return ObjectEntry.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, ObjectEntryItemSelectorView.class);

		return ResourceBundleUtil.getString(resourceBundle, "objects");
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			InfoItemItemSelectorCriterion infoItemItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		_itemSelectorViewDescriptorRenderer.renderHTML(
			servletRequest, servletResponse, infoItemItemSelectorCriterion,
			portletURL, itemSelectedEventName, search,
			new ObjectItemSelectorViewDescriptor(
				(HttpServletRequest)servletRequest, portletURL));
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

	@Reference
	private ItemSelectorViewDescriptorRenderer<InfoItemItemSelectorCriterion>
		_itemSelectorViewDescriptorRenderer;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Portal _portal;

	private class ObjectEntryItemDescriptor
		implements ItemSelectorViewDescriptor.ItemDescriptor {

		public ObjectEntryItemDescriptor(
			ObjectEntry objectEntry, HttpServletRequest httpServletRequest) {

			_objectEntry = objectEntry;

			try {
				_objectDefinition =
					_objectDefinitionLocalService.getObjectDefinition(
						objectEntry.getObjectDefinitionId());
			}
			catch (PortalException portalException) {
				throw new RuntimeException(portalException);
			}
		}

		@Override
		public String getIcon() {
			return null;
		}

		@Override
		public String getImageURL() {
			return null;
		}

		@Override
		public Date getModifiedDate() {
			return _objectEntry.getModifiedDate();
		}

		@Override
		public String getPayload() {
			return JSONUtil.put(
				"className", ObjectEntry.class.getName()
			).put(
				"classNameId",
				_portal.getClassNameId(ObjectEntry.class.getName())
			).put(
				"classPK", _objectEntry.getObjectEntryId()
			).put(
				"classTypeId", _objectEntry.getObjectDefinitionId()
			).put(
				"subtype", _objectDefinition.getName()
			).put(
				"title",
				StringBundler.concat(
					_objectDefinition.getName(), StringPool.SPACE,
					_objectEntry.getObjectEntryId())
			).toString();
		}

		@Override
		public String getSubtitle(Locale locale) {
			return _objectDefinition.getName();
		}

		@Override
		public String getTitle(Locale locale) {
			return String.valueOf(_objectEntry.getObjectEntryId());
		}

		@Override
		public long getUserId() {
			return _objectEntry.getUserId();
		}

		@Override
		public String getUserName() {
			return _objectEntry.getUserName();
		}

		private final ObjectDefinition _objectDefinition;
		private final ObjectEntry _objectEntry;

	}

	private class ObjectItemSelectorViewDescriptor
		implements ItemSelectorViewDescriptor<ObjectEntry> {

		public ObjectItemSelectorViewDescriptor(
			HttpServletRequest httpServletRequest, PortletURL portletURL) {

			_httpServletRequest = httpServletRequest;
			_portletURL = portletURL;

			_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		}

		@Override
		public String getDefaultDisplayStyle() {
			return "descriptive";
		}

		@Override
		public ItemDescriptor getItemDescriptor(ObjectEntry objectEntry) {
			return new ObjectEntryItemDescriptor(
				objectEntry, _httpServletRequest);
		}

		@Override
		public ItemSelectorReturnType getItemSelectorReturnType() {
			return new InfoItemItemSelectorReturnType();
		}

		@Override
		public SearchContainer<ObjectEntry> getSearchContainer() {
			SearchContainer<ObjectEntry> searchContainer =
				new SearchContainer<>(
					_portletRequest, _portletURL, null,
					"no-entries-were-found");

			List<ObjectEntry> objectEntries =
				_objectEntryLocalService.getObjectEntries(
					searchContainer.getStart(), searchContainer.getEnd());

			searchContainer.setResults(objectEntries);

			searchContainer.setTotal(
				_objectEntryLocalService.getObjectEntriesCount());

			return searchContainer;
		}

		private final HttpServletRequest _httpServletRequest;
		private final PortletRequest _portletRequest;
		private final PortletURL _portletURL;

	}

}