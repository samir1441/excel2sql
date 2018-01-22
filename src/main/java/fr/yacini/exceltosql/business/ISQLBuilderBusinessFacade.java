package fr.yacini.exceltosql.business;

import fr.yacini.exceltosql.model.WorkbookModel;

public interface ISQLBuilderBusinessFacade {

	String buildQuery(WorkbookModel workbookModel);

}
