package doordonote.logic;

import java.io.IOException;
import java.util.List;

import doordonote.common.Task;

public interface UIToLogic {
	List<Task> getTasks();
	String parseAndExecuteCommand(String userInput) throws Exception;
	UIState getState();
}
