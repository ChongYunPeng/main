package doordonote.logic;

import java.io.IOException;
import java.util.List;

import doordonote.command.Command;
import doordonote.parser.Parser;
import doordonote.storage.Storage;
import doordonote.common.Task;

public class Logic {
	protected Parser parser = null;
	protected Storage storage = null;
	protected TempStorage tempStorage = null;
	protected ServiceHandler service = null;
	
	public Logic() {
		parser = new Parser();
		storage = new Storage();
		tempStorage = new TempStorage();
		service = new ServiceHandler();
	}
	
	public String parseAndExecuteCommand(String cmdString) {
		Command cmdObj = parser.parse(cmdString);
		if (cmdObj == null) {
			return "Invalid command";
		}
		String outputMsg = null;
		if (cmdObj.isRedoable()) {
			String cmdOutcome = cmdObj.run(storage);
			if (cmdOutcome == null) {
				outputMsg = "Error when running command";
			} else {
				tempStorage.addCmd(cmdObj);
				outputMsg = "Command executed";
			}
		} else {
			
		}
		return outputMsg;
	}
	
	
	// Error should be handled in storage component
	public List<Task> getTasks() {
		List<Task> taskList = null;
		try {
			taskList = storage.read();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error while accessing storage");
			return null;
		}
		return taskList;
	}
}
