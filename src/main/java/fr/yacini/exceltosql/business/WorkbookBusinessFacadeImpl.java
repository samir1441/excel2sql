package fr.yacini.exceltosql.business;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.yacini.exceltosql.exception.BusinessException;
import fr.yacini.exceltosql.exception.CommonException;
import fr.yacini.exceltosql.model.CellModel.DataType;
import fr.yacini.exceltosql.model.RowModel;
import fr.yacini.exceltosql.model.SheetModel;
import fr.yacini.exceltosql.model.WorkbookModel;
import fr.yacini.exceltosql.service.FileUploadService;
import fr.yacini.exceltosql.service.IWorkbookService;
import fr.yacini.exceltosql.util.WorkbookUtils;

/**
 * @author Samir
 *
 */
@Service("workbookBusinessFacade")
public class WorkbookBusinessFacadeImpl implements IWorkbookBusinessFacade {

	@Autowired
	private IWorkbookService workbookService;

	@Autowired
	private FileUploadService fileUploadService;

	@Override
	public WorkbookModel buildWorkbook(final FileUploadEvent event) throws IOException, CommonException {
		final InputStream inputStream = this.fileUploadService.handleFileUpload(event);
		final Workbook workbook = this.workbookService.getWorkbook(inputStream);
		final WorkbookModel workbookModel = this.mapWorkbookToModel(workbook);
		return workbookModel;
	}

	private WorkbookModel mapWorkbookToModel(final Workbook workbook) throws BusinessException {
		final WorkbookModel workbookModel = new WorkbookModel();
		final int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			final Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getSheetName().startsWith("DB_")) {
				final SheetModel sheetModel = this.mapSheetToModel(sheet);
				workbookModel.addSheet(sheetModel);
			}
		}
		return workbookModel;
	}

	private SheetModel mapSheetToModel(final Sheet sheet) throws BusinessException {
		final SheetModel sheetModel = new SheetModel();
		sheetModel.setLabel(sheet.getSheetName());
		sheetModel.setHeader(WorkbookUtils.getHeader(sheet));
		sheetModel.setRows(this.getRows(sheet, sheetModel.getHeader()));
		return sheetModel;
	}

	private List<RowModel> getRows(final Sheet sheet, final List<String> header) throws BusinessException {
		final List<RowModel> rows = new ArrayList<RowModel>();
		final int numberOfRows = sheet.getLastRowNum() + 1;
		final int numberOfColumns = header.size();
		for (int rowNumber = 1; rowNumber < numberOfRows; rowNumber++) {
			final RowModel rowModel = new RowModel(rowNumber);
			for (int colNumber = 0; colNumber < numberOfColumns; colNumber++) {
				final Cell cell = sheet.getRow(rowNumber).getCell(colNumber);
				DataType dataType = DataType.NULL;
				String data = null;
				if (cell != null) {
					if ((dataType = WorkbookUtils.getPrefixedDataType(header.get(colNumber))) != DataType.NULL) {
						data = WorkbookUtils.getData(cell, dataType);
					} else {
						switch (cell.getCellTypeEnum()) {
							case NUMERIC:
								try {
									data = WorkbookUtils.numberToPlainString(cell.getNumericCellValue());
									dataType = DataType.NUMBER;
								} catch (final NumberFormatException nfe) {
									dataType = DataType.DATE;
								}
								break;
							case BOOLEAN:
								data = String.valueOf(cell.getBooleanCellValue());
								dataType = DataType.BOOLEAN;
								break;
							case BLANK:
							case _NONE:
								dataType = DataType.NULL;
								break;
							case ERROR:
							case FORMULA:
								throw new BusinessException();
							case STRING:
							default:
								data = cell.getStringCellValue();
								if (WorkbookUtils.isDateType(data)) {
									dataType = DataType.DATE;
								} else {
									dataType = DataType.STRING;
								}
								break;
						}
					}
				}
				rowModel.addCell(colNumber, data, dataType);
			}
			if (rowNumber == 1 && WorkbookUtils.isEmptySheet(rowModel)) {
				break;
			}

			rows.add(rowModel);
		}
		return rows;
	}

}
