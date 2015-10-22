package doordonote.logic;

import java.util.Date;
import java.util.List;

public interface CommandToController {
	String add(String taskDescription, Date startDate, Date endDate);

	String delete(int taskID);

	String find(List<String> keywords);
	
	String find(String taskType);
	
	String finish(int taskID);
	
	String help();
	
	String help(String commandType);
	
	String redo();
	
	String undo();
	
	String update(int taskID, String taskDescription, Date startDate, Date endDate);
	
	String home();
}
