package doordonote.commandfactory;

//@@author A0131436N

public class NoTaskFoundException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5240083176979243708L;

	public NoTaskFoundException() {
		super("No task found");
	}

}
