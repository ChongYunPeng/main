//@@author A0131716M
package doordonote.storage;

/**
 * @author A0131716M
 *
 */

public class DuplicateTaskException extends Exception {
	
	private int value;
	
	public DuplicateTaskException() {
		super("Duplicate Task!");
		this.value = -1;
	}
	
	public DuplicateTaskException(String msg) {
		super(msg);
		this.value = -1;
	}
	
	public DuplicateTaskException(String msg, int value) {
		super(msg);
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
