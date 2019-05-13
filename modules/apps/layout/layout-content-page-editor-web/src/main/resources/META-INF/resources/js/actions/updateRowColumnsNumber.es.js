import {MAX_COLUMNS} from '../utils/rowConstants';
import {setIn, updateIn} from '../utils/FragmentsEditorUpdateUtils.es';
import {UPDATE_ROW_COLUMNS_NUMBER_ERROR, UPDATE_ROW_COLUMNS_NUMBER_LOADING, UPDATE_ROW_COLUMNS_NUMBER_SUCCESS} from './actions.es';

/**
 * @return {object}
 * @review
 */
function updateRowColumnsNumberErrorAction() {
	return {
		type: UPDATE_ROW_COLUMNS_NUMBER_ERROR
	};
}

/**
 * @return {object}
 * @review
 */
function updateRowColumnsNumberLoadingAction() {
	return {
		type: UPDATE_ROW_COLUMNS_NUMBER_LOADING
	};
}

/**
 * @param {Array} fragmentEntryLinkIdsToRemove
 * @param {object} layoutData
 * @return {object}
 * @review
 */
function updateRowColumnsNumberSuccessAction(
	fragmentEntryLinkIdsToRemove,
	layoutData
) {
	return {
		fragmentEntryLinkIdsToRemove,
		layoutData,
		type: UPDATE_ROW_COLUMNS_NUMBER_SUCCESS
	};
}

/**
 * Returns a new layoutData with the given columns inserted in the specified
 * row with the specified size and resizes the rest of columns to the
 * same size.
 *
 * @param {object} layoutData
 * @param {number} rowIndex
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @return {object}
 */
function _addColumns(layoutData, rowIndex, numberOfColumns, columnsSize) {
	let nextColumnId = layoutData.nextColumnId || 0;

	let nextData = updateIn(
		layoutData,
		['structure', rowIndex, 'columns'],
		columns => {
			const newColumns = columns.map(
				(column, index) => setIn(
					column,
					['size'],
					_getColumnSize(numberOfColumns, columnsSize, index)
				)
			);

			const numberOfNewColumns = numberOfColumns - columns.length;
			const numberOfOldColumns = columns.length;

			for (let i = 0; i < numberOfNewColumns; i++) {
				newColumns.push(
					{
						columnId: `${nextColumnId}`,
						fragmentEntryLinkIds: [],
						size: _getColumnSize(numberOfColumns, columnsSize, (i + numberOfOldColumns))
					}
				);

				nextColumnId += 1;
			}

			return newColumns;
		}
	);

	nextData = setIn(nextData, ['nextColumnId'], nextColumnId);

	return nextData;
}

/**
 * Returns the new size of a column based on the total number of columns of a
 * grid, the general size and its position in the grid.
 *
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @param {number} columnIndex
 * @return {string}
 */
function _getColumnSize(numberOfColumns, columnsSize, columnIndex) {
	let newColumnSize = columnsSize;

	const middleColumnPosition = Math.ceil(numberOfColumns / 2) - 1;

	if (middleColumnPosition === columnIndex) {
		newColumnSize = MAX_COLUMNS - ((numberOfColumns - 1) * columnsSize);
	}

	return newColumnSize.toString();
}

/**
 * Returns a new layoutData without the columns out of range in the specified
 * row and resizes the rest of columns to the specified size.
 *
 * @param {object} layoutData
 * @param {number} rowIndex
 * @param {number} numberOfColumns
 * @param {number} columnsSize
 * @return {object}
 */
function _removeColumns(layoutData, rowIndex, numberOfColumns, columnsSize) {
	let nextData = updateIn(
		layoutData,
		['structure', rowIndex, 'columns'],
		columns => {
			let newColumns = columns.slice(0, numberOfColumns);

			newColumns = newColumns.map(
				(column, index) => setIn(
					column,
					['size'],
					_getColumnSize(numberOfColumns, columnsSize, index)
				)
			);

			return newColumns;
		}
	);

	return nextData;
}