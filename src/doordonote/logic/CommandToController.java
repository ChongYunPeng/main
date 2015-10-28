package doordonote.logic;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CommandToController {
	String add(String taskDescription, Date startDate, Date endDate) throws IOException;

	String delete(int taskId) throws Exception;

	String find(List<String> keywords);
	
	String finish(int taskId) throws IOException, Exception;
	
	String help();
	
	String help(String commandType);
	
	String redo() throws IOException;
	
	String undo();
	
	String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception;
	
	String home();

	String restore(int taskId) throws IOException, Exception;

	String displayFinished() throws IOException;

	String displayDeleted() throws IOException;
}
