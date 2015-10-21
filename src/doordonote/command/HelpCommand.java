package doordonote.command;

import doordonote.logic.Controller;

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
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String execute(Controller controller) {
		if (commandType == null) {
			return controller.help();
		} else {
			return controller.help(commandType);
		}
	}

}
