package fr.yacini.exceltosql.business;

import java.io.IOException;

import org.primefaces.event.FileUploadEvent;

import fr.yacini.exceltosql.exception.CommonException;
import fr.yacini.exceltosql.model.WorkbookModel;

/**
 * @author Samir
 *
 */
public interface IWorkbookBusinessFacade {

	/**
	 * @param event
	 * @return
	 * @throws IOException
	 * @throws CommonException
	 */
	public WorkbookModel buildWorkbook(final FileUploadEvent event) throws IOException, CommonException;
}
