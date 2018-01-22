package fr.yacini.exceltosql.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

import fr.yacini.exceltosql.business.IWorkbookBusinessFacade;
import fr.yacini.exceltosql.exception.CommonException;
import fr.yacini.exceltosql.exception.TechnicalException;
import fr.yacini.exceltosql.model.WorkbookModel;
import lombok.Data;

/**
 * @author Samir
 *
 */
@ManagedBean
@ViewScoped
@Data
public class FileUploadController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 252053416348823628L;

	@ManagedProperty("#{workbookBusinessFacade}")
	private IWorkbookBusinessFacade workbookBusinessFacade;

	private WorkbookModel workbookModel;

	/**
	 * @param event
	 */
	public void handleFileUpload(final FileUploadEvent event) {
		try {
			this.workbookModel = this.workbookBusinessFacade.buildWorkbook(event);
		} catch (final IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur de lecture", null));
		} catch (final CommonException e) {
			e.printStackTrace();
			if (e instanceof TechnicalException) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur technique", e.getMessage()));
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur", e.getMessage()));
			}
		}
	}
}
