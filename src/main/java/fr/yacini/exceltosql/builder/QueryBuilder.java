package fr.yacini.exceltosql.builder;

import java.util.List;

import fr.yacini.exceltosql.model.CellModel;
import fr.yacini.exceltosql.model.RowModel;
import fr.yacini.exceltosql.util.QueryBuilderUtils;
import fr.yacini.exceltosql.util.SQLSyntax;
import lombok.Data;

@Data
public class QueryBuilder {

	private String sql;

	public QueryBuilder insertInto(final String tableName) {
		this.sql = this.buildInsertStatement(tableName);
		return this;
	}

	public QueryBuilder insertInto(final String tableName, final List<String> columns) {
		final String sql = this.buildInsertStatement(tableName);
		final StringBuilder sb = new StringBuilder(sql);
		sb.append(SQLSyntax.SPACE).append(SQLSyntax.OPENING_BRACKET);
		for (final String columnName : columns) {
			sb.append(columnName);
			if (columns.indexOf(columnName) != columns.size() - 1) {
				sb.append(SQLSyntax.COMMA);
			}
		}
		sb.append(SQLSyntax.CLOSING_BRACKET);
		this.sql = sb.toString();
		return this;
	}

	public QueryBuilder values(final RowModel row) {
		final StringBuilder sb = new StringBuilder(this.sql);
		sb.append(SQLSyntax.SPACE).append(SQLSyntax.VALUES).append(SQLSyntax.SPACE).append(SQLSyntax.OPENING_BRACKET);
		sb.append(this.parseSingleRow(row));
		sb.append(SQLSyntax.CLOSING_BRACKET).append(SQLSyntax.SEMICOLON).append(System.getProperty("line.separator"));
		this.sql = sb.toString();
		return this;
	}

	private String parseSingleRow(final RowModel row) {
		final StringBuilder sb = new StringBuilder();
		for (final CellModel cell : row.getCells()) {
			switch (cell.getDataType()) {
				case BOOLEAN:
				case NUMBER:
					sb.append(cell.getData());
					break;
				case DATE:
					sb.append(QueryBuilderUtils.parseToDate(cell.getData(), "DD/MM/YY"));
					break;
				case STRING:
					sb.append(QueryBuilderUtils.parseToString(cell.getData()));
					break;
				case NULL:
					sb.append(SQLSyntax.NULL);
					break;
				default:
					// TODO throw an Business exception
					break;
			}
			if (row.getCells().indexOf(cell) != row.getCells().size() - 1) {
				sb.append(SQLSyntax.COMMA);
			}
		}
		return sb.toString();
	}

	private String buildInsertStatement(final String tableName) {
		final StringBuilder sb = new StringBuilder();
		sb.append(SQLSyntax.INSERT_INTO).append(SQLSyntax.SPACE).append(tableName);
		return sb.toString();
	}
}
