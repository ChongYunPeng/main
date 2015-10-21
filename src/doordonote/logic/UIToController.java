package doordonote.logic;

import java.util.List;

import doordonote.common.TaskTest;

public interface UIToController {
	List<TaskTest> getTasks();
	String parseAndExecuteCommand(String cmdString);
}
