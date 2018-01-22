package fr.yacini.exceltosql.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;

import fr.yacini.exceltosql.business.ISQLBuilderBusinessFacade;
import lombok.Data;

/**
 * @author Samir
 *
 */
@ManagedBean(name = "sqlOutputController")
@ViewScoped
final @Data public class SQLOutputController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 991802335415804036L;

	@ManagedProperty("#{sqlBuilderBusinessFacade}")
	private ISQLBuilderBusinessFacade sqlBuilderBusinessFacade;

	@ManagedProperty("#{fileUploadController}")
	private FileUploadController fileUploadController;

	private String sql;

	public boolean isOutputRendered() {
		return this.fileUploadController.getWorkbookModel() != null;
	}

	public void buildQuery() {
		this.setSql(this.sqlBuilderBusinessFacade.buildQuery(this.fileUploadController.getWorkbookModel()));
	}

	public String getSql() {
		return this.sql;
	}

	public void successListener(final ClipboardSuccessEvent successEvent) {
		this.addMessage(FacesMessage.SEVERITY_INFO, "Copié dans le presse-papier.", null);
	}

	public void errorListener(final ClipboardErrorEvent errorEvent) {
		this.addMessage(FacesMessage.SEVERITY_ERROR, "Une erreur s'est produite.", null);
	}

	public void addMessage(final Severity severity, final String summary, final String detail) {
		final FacesMessage message = new FacesMessage(severity, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
