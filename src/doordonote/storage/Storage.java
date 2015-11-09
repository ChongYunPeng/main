//@@author A0131716M
package doordonote.storage;


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
	public String add(Task task)throws DuplicateTaskException, IOException;
	public String update(Task oldTask, Task newTask)throws DuplicateTaskException, IOException;
	public String delete(Task taskToDelete) throws IOException;
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
