package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

/**
 * This Command display the path of the storage file to the user
 * 
 * @author yunpeng
 *
 */
public class GetPathCommand implements Command {

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.getCurrentFilePath();
	}

}
