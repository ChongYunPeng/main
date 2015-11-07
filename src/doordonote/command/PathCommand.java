package doordonote.command;

import java.io.IOException;

import doordonote.logic.CommandToController;

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
