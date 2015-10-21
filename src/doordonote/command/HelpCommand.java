package doordonote.command;

import doordonote.logic.CommandToController;

public class HelpCommand implements Command {
	protected String commandType = null;
	
	public HelpCommand(String commandType) {
		this.commandType = commandType; 
	}
	
	public HelpCommand() {}

	@Override
	public boolean isUndoable() {
		return false;
	}

	@Override
	public String execute(CommandToController controller) {
		if (commandType == null) {
			return controller.help();
		} else {
			return controller.help(commandType);
		}
	}

}