package doordonote.commandfactory;

public class ExcessArgumentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4517966650474754315L;
	
	public ExcessArgumentException() {
		super("Invalid command format. Too many arguments used in command");
	}
}
