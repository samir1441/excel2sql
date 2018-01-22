package fr.yacini.exceltosql.util;

public class QueryBuilderUtils {

	private static final String SPECIAL_CHARS = "'";
	private static final char ESCAPE_CHAR = '\'';

	public static String parseToString(final String data) {
		final StringBuilder sb = new StringBuilder();
		final char[] dataChars = data.toCharArray();
		sb.append(SQLSyntax.SINGLE_QUOTE);
		for (final char character : dataChars) {
			if (QueryBuilderUtils.SPECIAL_CHARS.indexOf(character) != -1) {
				sb.append(QueryBuilderUtils.ESCAPE_CHAR);
			}
			sb.append(character);
		}
		sb.append(SQLSyntax.SINGLE_QUOTE);
		return sb.toString();
	}

	public static String parseToDate(final String data, final String pattern) {
		final StringBuilder sb = new StringBuilder();
		// @formatter:off
		sb.append(SQLSyntax.TO_DATE)
			.append(SQLSyntax.OPENING_BRACKET)
				.append(SQLSyntax.SINGLE_QUOTE)
				.append(data)
				.append(SQLSyntax.SINGLE_QUOTE)
					.append(SQLSyntax.COMMA)
				.append(SQLSyntax.SINGLE_QUOTE)
				.append(pattern)
				.append(SQLSyntax.SINGLE_QUOTE)
			.append(SQLSyntax.CLOSING_BRACKET);
		// @formatter:on

		return sb.toString();
	}

}
