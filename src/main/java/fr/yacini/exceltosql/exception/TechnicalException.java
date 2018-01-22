package fr.yacini.exceltosql.exception;

/**
 * @author Samir
 *
 */
public class TechnicalException extends CommonException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5678403396273326698L;

	/**
	 *
	 */
	public TechnicalException() {
		super();
	}

	/**
	 * @param message
	 */
	public TechnicalException(final String message) {
		super(message);
	}
}
