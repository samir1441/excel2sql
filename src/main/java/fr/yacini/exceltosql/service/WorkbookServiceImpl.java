package fr.yacini.exceltosql.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import fr.yacini.exceltosql.exception.TechnicalException;

/**
 * @author Samir
 *
 */
@Service("workbookService")
public class WorkbookServiceImpl implements IWorkbookService {

	@Override
	public Workbook getWorkbook(final InputStream inputStream) throws IOException, TechnicalException {
		Workbook workbook = null;
		try {
			workbook = WorkbookFactory.create(inputStream);
		} catch (final EncryptedDocumentException e) {
			throw new TechnicalException("Le fichier est vérouillé par un mot de passe.");
		} catch (final InvalidFormatException e) {
			throw new TechnicalException("Le format du fichier est invalide.");
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw new IOException("Un problème est survenu lors de la lecture du fichier.");
			} else {
				throw new TechnicalException(e.getMessage());
			}
		}
		return workbook;
	}

}
