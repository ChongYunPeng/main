//@@author A0131436N

package doordonote.commandfactory;

public class NoTaskFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5240083176979243708L;

	public NoTaskFoundException() {
		super("No task found");
	}

}
