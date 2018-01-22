package fr.yacini.exceltosql.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.util.StringUtils;

import fr.yacini.exceltosql.exception.BusinessException;
import fr.yacini.exceltosql.model.CellModel;
import fr.yacini.exceltosql.model.CellModel.DataType;
import fr.yacini.exceltosql.model.RowModel;

/**
 * @author Samir
 *
 */
public class WorkbookUtils {

	public static final String DATABASE_PREFIX = "DB";
	public static final String NUMERIC_PREFIX = "IS";
	public static final String DATE_PREFIX = "D";
	public static final String BOOLEAN_PREFIX = "B";
	public static final String CODE_PREFIX = "CD";
	public static final String LONG_STRING_PREFIX = "LB";
	public static final String SHORT_STRING_PREFIX = "LP";
	public static final String UNDERSCORE = "_";

	/**
	 * @param numericCellValue
	 * @return
	 */
	public static String numberToPlainString(final double numericCellValue) {
		final DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
		df.setMaximumFractionDigits(340); // 340 = DecimalFormat.DOUBLE_FRACTION_DIGITS
		return df.format(numericCellValue);
	}

	/**
	 * @param sheet
	 * @return
	 * @throws BusinessException
	 */
	public static List<String> getHeader(final Sheet sheet) throws BusinessException {
		final List<String> header = new ArrayList<String>();
		final Row row = sheet.getRow(0);
		int i = 0;
		while (row.getCell(i) != null
				&& !StringUtils.isEmpty(StringUtils.trimAllWhitespace(row.getCell(i).getStringCellValue()))) {
			if (!row.getCell(i).getCellTypeEnum().equals(CellType.STRING)) {
				throw new BusinessException();
			}
			header.add(row.getCell(i).getStringCellValue());
			i++;
		}
		return header;
	}

	/**
	 * @param data
	 * @return
	 */
	public static boolean isDateType(final String data) {
		final Pattern pattern = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");
		return pattern.matcher(data).matches();
	}

	/**
	 * @param rowModel
	 * @return
	 */
	public static boolean isEmptySheet(final RowModel rowModel) {
		boolean isEmptySheet = true;
		for (final CellModel cell : rowModel.getCells()) {
			if (DataType.NULL.equals(cell.getDataType())) {
				continue;
			} else {
				isEmptySheet = false;
				break;
			}
		}
		return isEmptySheet;
	}

	public static DataType getPrefixedDataType(final String data) {
		final String prefix = data.substring(0, data.indexOf('_'));
		switch (prefix) {
			case NUMERIC_PREFIX:
				return DataType.NUMBER;
			case DATE_PREFIX:
				return DataType.DATE;
			case BOOLEAN_PREFIX:
				return DataType.BOOLEAN;
			case CODE_PREFIX:
			case LONG_STRING_PREFIX:
			case SHORT_STRING_PREFIX:
				return DataType.STRING;
			default:
				return DataType.NULL;
		}
	}

	public static String getData(final Cell cell, final DataType dataType) {
		String data = null;
		switch (dataType) {
			case NUMBER:
				data = WorkbookUtils.numberToPlainString(cell.getNumericCellValue());
				break;
			case STRING:
			case DATE:
			case BOOLEAN:
			default:
				try {
					data = cell.getStringCellValue();
				} catch (final IllegalStateException e) {
					data = WorkbookUtils.numberToPlainString(cell.getNumericCellValue());
				}
				break;
		}
		return data;
	}

}
