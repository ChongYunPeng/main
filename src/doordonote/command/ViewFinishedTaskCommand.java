package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * @author yunpeng
 *
 * This {@code Command} displays a list of finished {@code Task} to the user
 */
public class ViewFinishedTaskCommand implements Command {
	
	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.viewFinished();
	}

}
