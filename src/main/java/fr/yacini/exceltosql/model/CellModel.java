package fr.yacini.exceltosql.model;

import lombok.Data;

/**
 * @author Samir
 *
 */
@Data
public class CellModel {

	private int cellIndex;

	private String data;

	private DataType dataType;

	/**
	 * @param cellIndex
	 * @param header
	 * @param data
	 * @param dataType
	 */
	public CellModel(final int cellIndex, final String data, final DataType dataType) {
		this.cellIndex = cellIndex;
		this.data = data;
		this.dataType = dataType;
	}

	/**
	 * @author Samir
	 *
	 */
	public static enum DataType {

		/**
		 * Type numérique.
		 */
		NUMBER,

		/**
		 * Type date.
		 */
		DATE,

		/**
		 * Type Chaine de caractères.
		 */
		STRING,

		/**
		 * Type booléen.
		 */
		BOOLEAN,

		/**
		 * Type indéfini.
		 *
		 */
		NULL;
	}
}
