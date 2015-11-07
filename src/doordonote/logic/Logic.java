//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.List;

import doordonote.command.Command;
import doordonote.commandfactory.CommandFactory;
import doordonote.common.Task;

//@@author A0131436N

public class Logic implements UIToLogic {
	protected CommandFactory cmdFactory = null;
	protected CommandToController controller = null;
	
	public Logic() throws IOException {
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
		return controller.getState();
	}

	@Override
	public List<Task> getTasks() {
		List<Task> taskList = null;
		try {
			taskList = controller.getTaskList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskList;
	}
	

}