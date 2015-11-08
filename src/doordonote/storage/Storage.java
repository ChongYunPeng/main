//@@author A0131716M
package doordonote.storage;

import java.util.Date;

/**
 * @@author A0131716M
 *
 */

import doordonote.common.Task;

import java.util.ArrayList;
import java.io.IOException;

/**
 * @author A0131716M
 *
 */
public interface Storage {
	public String path(String location);
	public String get(String location);
	public String add(Task task)throws DuplicateTaskException;
	public String add(String description, Date startDate, Date endDate)throws DuplicateTaskException;
	public String update(Task taskToUpdate, String descirption,
			Date startDate, Date endDate)throws DuplicateTaskException;
	public String update(Task oldTask, Task newTask)throws DuplicateTaskException;
	public String delete(Task taskToDelete);
	public String remove(Task taskToRemove) throws IOException;
	public ArrayList<Task> readTasks() throws IOException;
	public ArrayList<Task> readDeletedTasks() throws IOException;
	public ArrayList<Task> readDoneTasks() throws IOException;
	public String clear();
	public String undo();
	public String redo();
	public String restore(Task task) throws IOException, DuplicateTaskException;
	public String finish(Task task) throws IOException, DuplicateTaskException;
	public String notFinish(Task task) throws IOException;
	public String getCurrentFilePath();
}
