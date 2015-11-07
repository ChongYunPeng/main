package doordonote.logic;

import java.util.List;

import doordonote.common.Task;

//@@author A0131436N

public interface UIToLogic {
	List<Task> getTasks();
	String parseAndExecuteCommand(String userInput) throws Exception;
	UIState getState();
}
