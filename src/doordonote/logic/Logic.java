//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.List;

import doordonote.command.Command;
import doordonote.commandfactory.CommandFactory;
import doordonote.common.Task;

//@@author A0131436N

/**
 * Entry to the Logic component.
 * 
 * @author yunpeng
 *
 */
public class Logic implements UIToLogic {
	protected static final String EXCEPTION_CORRUPTED_FILE = "Storage file is corrupted. Use 'readfrom' or 'save' to read from or create new file";

	protected CommandFactory cmdFactory = null;
	protected CommandToController controller = null;

	public Logic() {
		cmdFactory = new CommandFactory();
		controller = new Controller();
	}

	@Override
	public String parseAndExecuteCommand(String userInput) throws Exception {
		Command cmd = cmdFactory.parse(userInput);
		String feedback;
		try {
			feedback = cmd.execute(controller);
		} catch (IOException e) {
			throw new IOException(EXCEPTION_CORRUPTED_FILE);
		}
		return feedback;
	}

	@Override
	public UIState getState() {
		return controller.getState();
	}

	@Override
	public List<Task> getTasks() throws IOException {
		List<Task> taskList = null;
		try {
			taskList = controller.getTaskList();
		} catch (IOException e) {
			 throw new IOException(EXCEPTION_CORRUPTED_FILE);
		}
		return taskList;
	}

}