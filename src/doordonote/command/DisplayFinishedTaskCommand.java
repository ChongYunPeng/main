//@@author A0131436N

package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

/**
 * @author yunpeng
 *
 * This {@code Command} displays a list of finished {@code Task} to the user
 */
public class DisplayFinishedTaskCommand implements Command {
	
	@Override
	public String execute(CommandToController controller) throws IOException {
		return controller.displayFinished();
	}

}
