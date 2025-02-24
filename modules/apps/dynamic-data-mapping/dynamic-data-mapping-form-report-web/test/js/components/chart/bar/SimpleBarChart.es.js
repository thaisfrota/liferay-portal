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

import {cleanup, render} from '@testing-library/react';
import React from 'react';

import SimpleBarChart from '../../../../../src/main/resources/META-INF/resources/js/components/chart/bar/SimpleBarChart.es';

const props = {
	data: [
		{count: 2, label: 'Option 1'},
		{count: 2, label: 'Option 2'},
		{count: 1, label: 'Option 3'},
	],
	height: 300,
	totalEntries: 5,
	width: 700,
};

describe('SimpleBarChart', () => {
	afterEach(cleanup);

	it('renders', () => {
		const {queryAllByText, queryByText} = render(
			<SimpleBarChart {...props} />
		);

		expect(queryAllByText('Option 1')).toBeTruthy();
		expect(queryByText('Option 2')).toBeTruthy();
		expect(queryByText('Option 3')).toBeTruthy();
	});
});
