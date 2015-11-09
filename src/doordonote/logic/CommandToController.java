package doordonote.logic;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.DuplicateTaskException;

//@@author A0131436N

public interface CommandToController {
	
	/**
	 * Adds a {@code Task} to storage.
	 * 
	 * @param taskDescription
	 * @param startDate
	 * @param endDate
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 * @throws DuplicateTaskException
	 */
	String add(String taskDescription, Date startDate, Date endDate) throws IOException, DuplicateTaskException;

	/**
	 * Marks a task as deleted.
	 * If task is already marked as deleted, delete it from the file.
	 * 
	 * @param 	taskId
	 * 			ID of task to be deleted.
	 * @return feedback message to be displayed to users.
	 * @throws Exception
	 */
	String delete(int taskId) throws Exception;

	/**
	 * Filters the list of task user can see based on the list of key words.
	 * Only tasks with task description that contains all the words in keywords will be
	 * displayed to the user.
	 * 
	 * @param 	keywords
	 * 			keywords used to filter the task list
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 */
	String find(List<String> keywords) throws IOException;
	
	/**
	 * Marks a task as done.
	 * 
	 * @param 	taskId
	 * 			ID of task to be mark as done.
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 * @throws Exception
	 */
	String finish(int taskId) throws IOException, Exception;
	
	/**
	 * Displays a help image
	 * 
	 * @return feedback message to be displayed to users.
	 */
	String help();
	
	/**
	 * Displays a help image of a specific commandType
	 * 
	 * @param 	commandType
	 * 			The specific command user needs help for.
	 * @return feedback message to be displayed to users.
	 */
	String help(String commandType);
	
	/**
	 * Redo the last undo.
	 * 
	 * @return feedback message to be displayed to users.
	 */
	String redo();
	
	/**
	 * Undo the last changes made to the file (e.g. add/ delete/ update/ finish/ restore).
	 * 
	 * @return feedback message to be displayed to users.
	 */
	String undo();
	
	/**
	 * Replaces the task specified with a completely new task.
	 * 
	 * @param 	taskId
	 * 			ID of task to be updated.
	 * @param taskDescription
	 * @param startDate
	 * @param endDate
	 * @return feedback message to be displayed to users.
	 * @throws Exception
	 */
	String update(int taskId, String taskDescription, Date startDate, Date endDate) throws Exception;
	
	/**
	 * Displays all undone and undeleted task to user.
	 * 
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 */
	String home() throws IOException;

	/**
	 * Marks a deleted/ done task as undeleted/ undone.
	 * 
	 * @param taskId
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 * @throws Exception
	 */
	String restore(int taskId) throws IOException, Exception;

	/**
	 * Displays all done tasks to user.
	 * 
	 * @return feedback message to be displayed to users.
	 */
	String viewFinished();

	/**
	 * Displays all deleted tasks to user.
	 * 
	 * @return feedback message to be displayed to users.
	 */
	String viewDeleted();

	/**
	 * Used by update to create a String from a Task. This method will
	 * allow users to easily edit a task by filling the user input area
	 * with the task that should be updated.
	 * 
	 * @param 	taskID
	 * 			ID of the task that will be converted to String.
	 * @return feedback message to be displayed to users.
	 * @throws Exception
	 */
	String getTaskStringToUserInputBoxById(int taskID) throws Exception;

	/**
	 * @return UIState object for UI to determine what kind of data should be displayed.
	 */
	UIState getState();

	/**
	 * Displays all tasks that ends after filterDate.
	 * 
	 * @param filterDate
	 * @return feedback message to be displayed to users.
	 * @throws IOException
	 */
	String find(Date filterDate) throws IOException;

	/**
	 * Reads a storage file from the specified path.
	 * 
	 * @param 	pathName
	 * 			File path to read storage file from. 
	 * @return feedback message to be displayed to users.
	 */
	String readFromFilePath(String pathName);

	/**
	 * Saves storage file in specified path.
	 * If a storage file with the same path already exists, read from the existing file.
	 * 
	 * @param pathName
	 * @return
	 */
	String saveFileAt(String pathName);

	/**
	 * @return List of sorted {@code Task}.
	 * @throws IOException
	 */
	List<Task> getTaskList() throws IOException;

	/**
	 * @return Path from which the current storage file is being read from.
	 */
	String getCurrentFilePath();

}
