/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {useIsMounted} from 'frontend-js-react-web';
import PropTypes from 'prop-types';
import React from 'react';
import {Cell, Pie, PieChart} from 'recharts';

import {numberFormat} from '../utils/numberFormat';
import Hint from './Hint';

const {useEffect, useState} = React;

const COLORS_MAP = {
	organic: '#7785FF',
	paid: '#FFB46E',
};

const PIE_CHART_SIZES = {
	height: 80,
	innerRadius: 25,
	paddingAngle: 5,
	radius: 40,
	width: 80,
};

/**
 * Used when the traffic source name is not within the COLORS_MAP
 */
const FALLBACK_COLOR = '#e92563';

const getColorByName = name => COLORS_MAP[name] || FALLBACK_COLOR;

export default function TrafficSources({dataProvider, languageTag}) {
	const isMounted = useIsMounted();
	const [trafficSources, setTrafficSources] = useState([]);

	useEffect(() => {
		dataProvider().then(response => {
			if (isMounted()) {
				setTrafficSources(response.analyticsReportsTrafficSources);
			}
		});
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="pie-chart-wrapper">
			<div className="pie-chart-wrapper--legend">
				<table>
					<tbody>
						{trafficSources.map(entry => {
							return (
								<tr key={entry.name}>
									<td>
										<span
											className="pie-chart-wrapper--legend--dot"
											style={{
												backgroundColor: getColorByName(
													entry.name
												),
											}}
										></span>
									</td>
									<td className="pie-chart-wrapper--legend--title pr-1 text-secondary">
										{entry.title}
										<Hint
											message={entry.helpMessage}
											title={entry.title}
										/>
									</td>
									<td className="font-weight-bold">
										{numberFormat(languageTag, entry.value)}
									</td>
								</tr>
							);
						})}
					</tbody>
				</table>
			</div>

			<div className="pie-chart-wrapper--chart">
				<PieChart
					height={PIE_CHART_SIZES.height}
					width={PIE_CHART_SIZES.width}
				>
					<Pie
						cx="50%"
						cy="50%"
						data={trafficSources}
						dataKey="value"
						innerRadius={PIE_CHART_SIZES.innerRadius}
						nameKey={'name'}
						outerRadius={PIE_CHART_SIZES.radius}
						paddingAngle={PIE_CHART_SIZES.paddingAngle}
					>
						{trafficSources.map((entry, i) => {
							const fillColor = getColorByName(entry.name);

							return <Cell fill={fillColor} key={i} />;
						})}
					</Pie>
				</PieChart>
			</div>
		</div>
	);
}

TrafficSources.propTypes = {
	dataProvider: PropTypes.func.isRequired,
	languageTag: PropTypes.string.isRequired,
};
