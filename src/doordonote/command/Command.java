package doordonote.command;

import doordonote.logic.CommandToController;

public interface Command {
	String execute(CommandToController controller);
}
