package fr.yacini.exceltosql.builder;

import java.util.List;

import fr.yacini.exceltosql.model.CellModel;
import fr.yacini.exceltosql.model.RowModel;
import fr.yacini.exceltosql.util.QueryBuilderUtils;
import fr.yacini.exceltosql.util.SQLSyntax;
import fr.yacini.exceltosql.util.WorkbookUtils;
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

	public void comments(final String tableName) {
		final StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("line.separator"));
		this.buildCommentLine(sb, tableName, false);
		this.buildCommentLine(sb, tableName, true);
		this.buildCommentLine(sb, tableName, false);
		sb.append(System.getProperty("line.separator"));
		this.sql = sb.toString();
	}

	private void buildCommentLine(final StringBuilder sb, final String tableName, final boolean withText) {
		final String commentText = tableName == null ? "" : this.getCommentText(tableName);
		sb.append(SQLSyntax.COMMENT_BEGIN);
		if (withText) {
			sb.append(SQLSyntax.STAR_LINE).append(SQLSyntax.SPACE).append(commentText).append(SQLSyntax.SPACE)
					.append(SQLSyntax.STAR_LINE);
		} else {
			final int lineWidth = SQLSyntax.STAR_LINE.length() * 2 + commentText.length() + 2;
			for (int i = 0; i < lineWidth; i++) {
				sb.append(SQLSyntax.STAR);
			}
		}
		sb.append(SQLSyntax.COMMENT_END).append(System.getProperty("line.separator"));
	}

	private String getCommentText(final String table) {
		final StringBuilder sb = new StringBuilder();
		final String[] sequence = table.split(WorkbookUtils.UNDERSCORE);
		for (int i = 0; i < sequence.length; i++) {
			if (!sequence[i].equals(WorkbookUtils.DATABASE_PREFIX)) {
				sb.append(this.getWordWithFirstUpperCase(sequence[i]));
				if (i != sequence.length - 1) {
					sb.append(SQLSyntax.SPACE);
				} else if (i == sequence.length - 1 && !sequence[i].toLowerCase().equals("s")) {
					sb.append("s");
				}
			}
		}
		return sb.toString();
	}

	private String getWordWithFirstUpperCase(String s) {
		if (s.length() == 0) {
			return s;
		}
		s = s.toUpperCase();
		final char[] sequence = s.toCharArray();
		if (s.length() > 1) {
			for (int i = 1; i < sequence.length; i++) {
				sequence[i] = Character.toLowerCase(sequence[i]);
			}
		}
		return String.valueOf(sequence);
	}

	public void reset() {
		this.sql = null;
	}

	public void setDefineOff() {
		final StringBuilder sb = new StringBuilder();
		sb.append(SQLSyntax.SET_DEFINE_OFF).append(SQLSyntax.SEMICOLON).append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		this.sql = sb.toString();
	}

	public void commit() {
		final StringBuilder sb = new StringBuilder();
		sb.append(System.getProperty("line.separator"));
		sb.append(SQLSyntax.COMMIT).append(SQLSyntax.SEMICOLON);
		sb.append(System.getProperty("line.separator"));
		this.sql = sb.toString();
	}
}
