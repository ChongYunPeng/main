package doordonote.logic;

import java.util.List;

import doordonote.common.Task;
import doordonote.common.TaskTest;

public interface UIToController {
	List<Task> getTasks();
	String parseAndExecuteCommand(String cmdString) throws Exception;
	String getState();
}
