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

package com.liferay.batch.engine.internal.order.comparator;

import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskIdComparator
	extends OrderByComparator<BatchEngineTask> {

	public static final String ORDER_BY_ASC = "batchEngineTaskId ASC";

	public static final String ORDER_BY_DESC = "batchEngineTaskId DESC";

	public static final String[] ORDER_BY_FIELDS = {"batchEngineTaskId"};

	public BatchEngineTaskIdComparator() {
		this(false);
	}

	public BatchEngineTaskIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		BatchEngineTask batchEngineTask1, BatchEngineTask batchEngineTask2) {

		int value = 0;

		if (batchEngineTask1.getBatchEngineTaskId() <
				batchEngineTask2.getBatchEngineTaskId()) {

			value = -1;
		}
		else if (batchEngineTask1.getBatchEngineTaskId() >
					batchEngineTask2.getBatchEngineTaskId()) {

			value = 1;
		}

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private final boolean _ascending;

}