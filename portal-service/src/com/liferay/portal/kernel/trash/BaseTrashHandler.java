/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * Represents the base class for basic operations with the Trash.
 *
 * <p>
 * The basic operations are:
 * </p>
 *
 * <ul>
 * <li>
 * Deletion of entries
 * </li>
 * <li>
 * Restore of entries
 * </li>
 * </ul>
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 */
public abstract class BaseTrashHandler implements TrashHandler {

	@SuppressWarnings("unused")
	public void checkDuplicateTrashEntry(TrashEntry trashEntry, String newName)
		throws PortalException, SystemException {
	}

	@SuppressWarnings("unused")
	public void deleteTrashAttachments(Group group, Date date)
		throws PortalException, SystemException {
	}

	public void deleteTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		deleteTrashEntries(classPKs, true);
	}

	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		deleteTrashEntries(new long[] {classPK});
	}

	public void deleteTrashEntry(long classPK, boolean checkPermission)
		throws PortalException, SystemException {

		deleteTrashEntries(new long[] {classPK}, checkPermission);
	}

	public ContainerModel getContainerModel(long containerModelId)
		throws PortalException, SystemException {

		return null;
	}

	public String getContainerModelName() {
		return StringPool.BLANK;
	}

	public List<ContainerModel> getContainerModels(
			long entryId, long parentContainerModelId, int start, int end)
		throws PortalException, SystemException {

		return null;
	}

	public int getContainerModelsCount(
			long entryId, long parentContainerModelId)
		throws PortalException, SystemException {

		return 0;
	}

	public String getDeleteMessage() {
		return "deleted-in-x";
	}

	@SuppressWarnings("unused")
	public String getRestoreLink(PortletRequest PortletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	@SuppressWarnings("unused")
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		return StringPool.BLANK;
	}

	public String getRootContainerModelName() {
		return StringPool.BLANK;
	}

	public String getSubContainerModelName() {
		return StringPool.BLANK;
	}

	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		AssetRendererFactory assetRendererFactory = getAssetRendererFactory();

		if (assetRendererFactory != null) {
			AssetRenderer assetRenderer = assetRendererFactory.getAssetRenderer(
				classPK);

			if (assetRenderer instanceof TrashRenderer) {
				return (TrashRenderer)assetRenderer;
			}
		}

		return null;
	}

	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		return true;
	}

	public void moveTrashEntry(
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (isRestorable(classPK)) {
			restoreTrashEntry(classPK);
		}

		_log.error("moveTrashEntry() is not implemented in " +
			getClass().getName());

		throw new SystemException();
	}

	public void restoreTrashEntry(long classPK)
		throws PortalException, SystemException {

		restoreTrashEntries(new long[] {classPK});
	}

	public void updateTitle(long classPK, String title)
		throws PortalException, SystemException {
	}

	protected AssetRendererFactory getAssetRendererFactory() {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(getClassName());
	}

	private static Log _log = LogFactoryUtil.getLog(BaseTrashHandler.class);

}