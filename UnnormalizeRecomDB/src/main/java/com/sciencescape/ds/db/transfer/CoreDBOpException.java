package main.java.com.sciencescape.ds.db.transfer;

/**
 * @class CoreDBOpException CoreDBOpException.java
 * @brief exception for Core DB operation errors 
 * @author akshay
 *
 * Exception for CoreDB operation errors.
 */
public class CoreDBOpException extends Exception {
	/**
	 * just to satisfy the interface
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @brief constructor for {@link CoreDBOpException} class
	 * @param message error message in string format
	 * 
	 * Constructor for {@link CoreDBOpException} class.
	 */
	public CoreDBOpException(String message) {
		super(message);
	}
}