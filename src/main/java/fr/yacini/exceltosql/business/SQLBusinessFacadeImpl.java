package fr.yacini.exceltosql.business;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.yacini.exceltosql.builder.QueryBuilder;
import fr.yacini.exceltosql.model.RowModel;
import fr.yacini.exceltosql.model.SheetModel;
import fr.yacini.exceltosql.model.WorkbookModel;

/**
 * @author Samir
 *
 */
@Service("sqlBuilderBusinessFacade")
public class SQLBusinessFacadeImpl implements ISQLBuilderBusinessFacade {

	@Override
	public String buildQuery(final WorkbookModel workbookModel) {
		if (workbookModel == null) {
			return "";
		}
		final StringBuilder sql = new StringBuilder();

		for (final SheetModel sheet : workbookModel.getSheets()) {
			if (sheet.getRows().size() > 0) {
				final String table = sheet.getLabel();
				final List<String> columns = sheet.getHeader();
				final List<RowModel> values = sheet.getRows();
				sql.append(this.generateSql(table, columns, values));
			}
		}

		return sql.toString();
	}

	private String generateSql(final String table, final List<String> columns, final List<RowModel> values) {
		final StringBuilder sb = new StringBuilder();
		for (final RowModel row : values) {
			final QueryBuilder builder = new QueryBuilder();
			// @formatter:off
			builder
				.insertInto(table, columns)
				.values(row);
			// @formatter:on
			sb.append(builder.getSql());
		}
		return sb.toString();
	}

}
