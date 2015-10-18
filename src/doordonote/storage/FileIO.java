package doordonote.storage;

import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;

public interface FileIO {
	public String add(Task task);
	public String add(String description, Date startDate, Date endDate);
	public String update(Task taskToUpdate, String descirption,
			Date startDate, Date endDate);
	public String update(Task oldTask, Task newTask);
	public String delete(Task taskToDelete);
	public ArrayList<Task> read() throws IOException;
}
