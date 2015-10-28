package doordonote.logic;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CommandToController {
	String add(String taskDescription, Date startDate, Date endDate);

	String delete(int taskID);

	String find(List<String> keywords);
	
	String finish(int taskID) throws IOException;
	
	String help();
	
	String help(String commandType);
	
	String redo();
	
	String undo();
	
	String update(int taskID, String taskDescription, Date startDate, Date endDate);
	
	String home();

	String restore(int taskID) throws IOException;

	String displayFinished() throws IOException;

	String displayDeleted() throws IOException;
}
