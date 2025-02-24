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

import classnames from 'classnames';
import React from 'react';
import {Handle} from 'react-flow-renderer';

import '../../../../css/main.scss';

export default function BorderStateNode({data: {initial = true, label}}) {
	return (
		<>
			{initial ? (
				<Handle position="right" style={{top: '50%'}} type="source" />
			) : (
				<Handle position="left" style={{top: '50%'}} type="target" />
			)}

			<div
				className={classnames(
					'border-state-node node text-white',
					initial ? 'start-state' : 'end-state'
				)}
			>
				<span>{Liferay.Language.get(label)}</span>
			</div>
		</>
	);
}
