package fr.yacini.exceltosql.exception;

/**
 * @author Samir
 *
 */
public abstract class CommonException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = 293956257459605333L;

	/**
	 *
	 */
	public CommonException() {
		super();
	}

	/**
	 * @param message
	 */
	public CommonException(final String message) {
		super(message);
	}
}
