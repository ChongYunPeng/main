//@@author A0131716M
package doordonote.storage;

import java.util.Date;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;



/**
 * @author A0131716M
 *
 */
public class StorageHandler implements Storage {

	private static final int TASK_CUTOFF_LENGTH = 30;
	private static final String FILE_TYPE = ".json";
	private static final String TASK_STRING_TAIL = "...\"";
	private static final String MESSAGE_ADD = "Task %1$s added";
	private static final String MESSAGE_UPDATE = "Task updated to %1$s";
	private static final String MESSAGE_DELETE = "Task %1$s deleted";
	private static final String MESSAGE_NO_TASK_TO_UPDATE = "No Tasks to update";
	private static final String MESSAGE_REMOVE = "Task %1$s removed from file";
	private static final String MESSAGE_CLEAR = "All data deleted from file";
	private static final String MESSAGE_UNDO_SUCCESS = "Undo executed";
	private static final String MESSAGE_UNDO_FAIL = "Undo not executed";
	private static final String MESSAGE_REDO_SUCCESS = "Redo executed";
	private static final String MESSAGE_REDO_FAIL = "Redo not executed";
	private static final String MESSAGE_RESTORE = "Task %1$s restored";
	private static final String MESSAGE_FINISH = "Marked Task %1$s as done";
	private static final String MESSAGE_NOT_FINISH = "Marked Task %1$s as not done";
	private static final String MESSAGE_NOT_FOUND = "File \"%1$s\" is not found.";
	private static final String MESSAGE_EVENTS_CLASHED = "Task %1$s added but clashes with %2$s";
	private static final String MESSAGE_READ = "Reading from file \"%1$s\"";
	private static final String MESSAGE_PATH_CREATE = "Creating file \"%1$s\". Reading from this file.";
	private static final String MESSAGE_PATH_EXISTS = "File exists. Reading from \"%1$s\"";
	private static final String MESSAGE_INVALID_PATH = "Path is invalid or DoOrDoNote does not have access to directory!";
	private static final String MESSAGE_ERROR = "Opps! Something went wrong!";
	private static final String MESSAGE_INVALID_TASK = "Invalid tasks parameters!";

	protected TaskWriter writer;
	protected TaskReader reader;
	private static StorageHandler storageHandler;

	private StorageHandler(){
		writer = new TaskWriter();
		reader = new TaskReader();
	}

	public static Storage getInstance(){
		if(storageHandler == null){
			storageHandler = new StorageHandler();
		}
		return storageHandler;
	}

	public String path(String fileName){
		if((fileName.length() < 5) ||
				!(fileName.substring(fileName.length()-4)).contains(FILE_TYPE)){
			fileName += FILE_TYPE;
		}
		try{
			int create = writer.path(fileName);
			if(create==0){
				return String.format(MESSAGE_PATH_CREATE, fileName);
			} else if(create==1){
				return String.format(MESSAGE_PATH_EXISTS, fileName);
			}
		}
		catch (IOException e){
			return MESSAGE_INVALID_PATH;
		}
		return MESSAGE_INVALID_PATH;
	}

	public String get(String fileName){
		try{
			String currentFile = reader.read(fileName);
			return String.format(MESSAGE_READ, currentFile);		 
		}
		catch (FileNotFoundException e){
			return String.format(MESSAGE_NOT_FOUND, fileName);
		}
	}

	public String add(Task task) throws DuplicateTaskException, IOException{
		try{
			writer.add(task);
			String taskStr = shortenTaskName(task);
			return String.format(MESSAGE_ADD, taskStr);
		}
		catch(IOException e){
			throw e;
	//		return MESSAGE_ERROR;
		}
		catch(EventsClashException e){
			String originalTaskStr = shortenTaskName(e.getOriginalTask());
			String clashedTaskStr = shortenTaskName(e.getClashedTask());
			return String.format(MESSAGE_EVENTS_CLASHED, clashedTaskStr, originalTaskStr);
		}
		catch(DuplicateTaskException e){
			throw e;
			//return e.getMessage();
		}
	}

	public String add(String description, Date startDate, Date endDate) throws DuplicateTaskException, IOException{
		Task task = createTask(description, startDate, endDate);
		return add(task);
	}


	public String update(Task taskToUpdate, String description, Date startDate, Date endDate) throws DuplicateTaskException, IOException {
		Task updatedTask = createTask(description, startDate, endDate);
		return update(taskToUpdate, updatedTask);
	}

	public String update(Task taskToUpdate, Task updatedTask) throws DuplicateTaskException, IOException {
		try{
			writer.update(taskToUpdate, updatedTask);
			String taskStr = shortenTaskName(updatedTask);
			return String.format(MESSAGE_UPDATE, taskStr);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_UPDATE;
		}
		catch(IOException e){
			throw e;
	//		return MESSAGE_ERROR;
		}
		catch(DuplicateTaskException e){
			throw e;
			//return e.getMessage();
		}
	}

	public String delete(Task taskToDelete) throws IOException{
		try{
			writer.delete(taskToDelete);
			return String.format(MESSAGE_DELETE, taskToDelete);
		}
		catch(IOException e){
			throw e;
	//		return MESSAGE_ERROR;
		}
	}

	public String remove(Task task) throws IOException{
		writer.remove(task);
		String taskStr = shortenTaskName(task);
		return String.format(MESSAGE_REMOVE, taskStr);
	}

	public ArrayList<Task> readTasks() throws IOException{
		ArrayList<Task> listTask = null;
		try{
			listTask = reader.readTasks();
		}
		catch (IOException e){
			throw e;
		}		
		assert(listTask!=null);
		return listTask;
	}

	public ArrayList<Task> readDeletedTasks() throws IOException{
		ArrayList<Task> listTask = null;
		try{
			listTask = reader.readDeletedTasks();
		}
		catch (IOException e){
			throw e;
		}		
		assert(listTask!=null);
		return listTask;
	}

	public ArrayList<Task> readDoneTasks() throws IOException{
		ArrayList<Task> listTask = null;
		try{
			listTask = reader.readDoneTasks();
		}
		catch (IOException e){
			throw e;
		}		
		assert(listTask!=null);
		return listTask;
	}

	public String clear(){
		writer.clear();
		return MESSAGE_CLEAR;
	}

	public String undo(){
		if(writer.undo()){
			return MESSAGE_UNDO_SUCCESS;
		} else{
			return MESSAGE_UNDO_FAIL;
		}
	}

	public String redo(){
		if(writer.redo()){
			return MESSAGE_REDO_SUCCESS;
		} else{
			return MESSAGE_REDO_FAIL;
		}
	}

	public String restore(Task task) throws IOException, DuplicateTaskException{
		writer.restore(task);
		String taskStr = shortenTaskName(task);
		return String.format(MESSAGE_RESTORE, taskStr);
	}

	public String finish(Task task) throws IOException, DuplicateTaskException{
		writer.setDone(task);
		String taskStr = shortenTaskName(task);
		return String.format(MESSAGE_FINISH, taskStr);
	}

	public String notFinish(Task task) throws IOException{
		String taskStr = shortenTaskName(task);
		return String.format(MESSAGE_NOT_FINISH,taskStr);
	}

	public String getCurrentFilePath(){
		return reader.getFileName();
	}

	private Task createTask(String description, Date startDate,
			Date endDate){
		Task task = null;
		if(description!=null && startDate==null && endDate==null){
			task = new FloatingTask(description);
		} else if(description!=null && startDate==null && endDate!=null){
			task = new DeadlineTask(description, endDate);
		} else if(description!=null && startDate!=null && endDate!=null){
			task = new EventTask(description, startDate, endDate);
		} else {
			assert (task != null): MESSAGE_INVALID_TASK;
		}

		return task;
	}

	private String shortenTaskName(Task task){
		String taskStr = task.toString();
		if(taskStr.length()>TASK_CUTOFF_LENGTH+2){
			taskStr = taskStr.substring(0, TASK_CUTOFF_LENGTH+1);
			taskStr += TASK_STRING_TAIL;
		}
		return taskStr;
	}

}
