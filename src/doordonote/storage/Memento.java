package doordonote.storage;

/**
 * @@author A0131716M
 *
 */

public class Memento {
	private String state;

	public Memento(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}	
}
