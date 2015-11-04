//@@author A0131436N

package doordonote.commandfactory;

public class EmptyCommandBodyException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4789438006018030330L;

	public EmptyCommandBodyException() {
		super("Invalid command format. This command needs arguments");
	}
}
