package doordonote.commandfactory;

public class NegativeIndexException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5240083176979243708L;

	public NegativeIndexException() {
		super("Invalid index value");
	}
}
