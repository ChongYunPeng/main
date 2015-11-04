package doordonote.logic;

import java.util.List;

import doordonote.command.Command;
import doordonote.commandfactory.CommandFactory;
import doordonote.common.Task;

public class Logic implements UIToLogic {
	protected CommandFactory cmdFactory = null;
	protected CommandToController controller = null;
	
	public Logic() {
		cmdFactory = new CommandFactory();
		controller = new Controller();
	}
	
	@Override
	public String parseAndExecuteCommand(String userInput) throws Exception {
		Command cmd = cmdFactory.parse(userInput);
		return cmd.execute(controller);
	}
	
	@Override
	public UIState getState() {
		return null;
//		return stateObj;
	}

	@Override
	public List<Task> getTasks() {
		return null;
//		return userTaskList;
	}

}