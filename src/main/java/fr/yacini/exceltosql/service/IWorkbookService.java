package fr.yacini.exceltosql.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Workbook;

import fr.yacini.exceltosql.exception.TechnicalException;

/**
 * @author Samir
 *
 */
public interface IWorkbookService {

	/**
	 * @param inputStream
	 * @param uploadedFile
	 * @return
	 * @throws IOException
	 * @throws TechnicalException
	 * @throws Exception
	 */
	public Workbook getWorkbook(InputStream inputStream) throws IOException, TechnicalException;
}
