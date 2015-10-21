package doordonote.command;

import doordonote.logic.Controller;

public interface Command {
	/**
     * @return  {@code true} if the command can be undone
     * 			{@code false} otherwise
	 */
	boolean isUndoable();
	
	String execute(Controller controller);
}
