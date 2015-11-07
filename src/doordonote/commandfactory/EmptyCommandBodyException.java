package doordonote.commandfactory;

//@@author A0131436N

public class EmptyCommandBodyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4789438006018030330L;

	public EmptyCommandBodyException() {
		super("Invalid command format. This command needs arguments");
	}
}
