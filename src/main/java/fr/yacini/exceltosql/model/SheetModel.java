package fr.yacini.exceltosql.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Samir
 *
 */
@Data
public class SheetModel {

	private String label;

	private List<RowModel> rows;

	private List<String> header;

	/**
	 *
	 */
	public SheetModel() {
		this.rows = new ArrayList<RowModel>();
	}

	/**
	 * @param rowModel
	 */
	public void addRow(final RowModel rowModel) {
		this.rows.add(rowModel);
	}

}
