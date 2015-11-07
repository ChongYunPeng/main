package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

//@@author A0131436N

public class PathCommand implements Command {
	protected String pathName = null;
	
	public PathCommand(String commandBody) {
		pathName = commandBody;
	}

	@Override
	public String execute(CommandToController controller) throws IOException, Exception {
		return controller.path(pathName);
	}

}
