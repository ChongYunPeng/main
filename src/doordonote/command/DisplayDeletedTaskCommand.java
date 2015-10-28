package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 * 
 * This {@code Command} displays a list of deleted {@code Task} to the user
 *
 */
public class DisplayDeletedTaskCommand implements Command {
	
	@Override
	public String execute(CommandToController controller) {
		return controller.displayDeleted();
	}

}
