package doordonote.command;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 * This {@code Command} displays a list of finished {@code Task} to the user
 */
public class DisplayFinishedTaskCommand implements Command {
	
	@Override
	public String execute(CommandToController controller) {
		return controller.displayFinished();
	}

}
