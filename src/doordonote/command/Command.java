package doordonote.command;

import doordonote.logic.CommandToController;

public interface Command {
	/**
     * @return  {@code true} if the command can be undone
     * 			{@code false} otherwise
	 */
	boolean isUndoable();
	
	String execute(CommandToController controller);
}
