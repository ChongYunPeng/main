package doordonote.logic;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.DuplicateTaskException;

//@@author A0131436N

public interface CommandToController {
	String add(String taskDescription, Date startDate, Date endDate) throws IOException, DuplicateTaskException;

	String delete(int taskId) throws Exception;

	String find(List<String> keywords) throws IOException;
	
	String finish(int taskId) throws IOException, Exception;
	
	String help();
	
	String help(String commandType);
	
	String redo() throws IOException;
	
	String undo() throws IOException;
	
	String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception;
	
	String home() throws IOException;

	String restore(int taskId) throws IOException, Exception;

	String viewFinished() throws IOException;

	String viewDeleted() throws IOException;

	String getTaskStringById(int taskID) throws Exception;

	UIState getState();

	String find(Date startDate) throws IOException;

	String readFromFilePath(String pathName);

	String saveFileAt(String pathName);

	List<Task> getTaskList() throws IOException;

	String getCurrentFilePath();

}
