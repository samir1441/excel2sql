package fr.yacini.exceltosql.model;

import java.util.ArrayList;
import java.util.List;

import fr.yacini.exceltosql.model.CellModel.DataType;
import lombok.Data;

/**
 * @author Samir
 *
 */
@Data
public class RowModel {

	protected final int rowNumber;

	protected List<CellModel> cells;

	/**
	 * @param rowNumber
	 *
	 */
	public RowModel(final int rowNumber) {
		this.rowNumber = rowNumber;
		this.cells = new ArrayList<CellModel>();
	}

	/**
	 * @param cellIndex
	 * @param header
	 * @param data
	 * @param dataType
	 */
	public void addCell(final int cellIndex, final String data, final DataType dataType) {
		this.cells.add(new CellModel(cellIndex, data, dataType));
	}
}
