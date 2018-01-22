package fr.yacini.exceltosql.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * @author Samir
 *
 */
@Data
public class WorkbookModel {

	private List<SheetModel> sheets;

	/**
	 *
	 */
	public WorkbookModel() {
		this.sheets = new ArrayList<SheetModel>();
	}

	/**
	 * @param sheet
	 */
	public void addSheet(final SheetModel sheet) {
		this.sheets.add(sheet);
	}
}
