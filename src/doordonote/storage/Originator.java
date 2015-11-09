//@@author A0131716M
package doordonote.storage;

/**
 * @author A0131716M
 *
 */
public class Originator {
	private String state;


	protected void setState(String state){
		this.state = state;
	}

	protected String getState(){
		return state;
	}

	protected Memento saveStateToMemento(){
		return new Memento(state);
	}

	protected void getStateFromMemento(Memento Memento){
		state = Memento.getState();
	}

}