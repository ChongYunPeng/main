//@@author A0131716M
package doordonote.storage;

/**
 * @author A0131716M
 *
 */

public class Memento {
	private String state;

	protected Memento(String state){
		this.state = state;
	}

	protected String getState(){
		return state;
	}	
}
