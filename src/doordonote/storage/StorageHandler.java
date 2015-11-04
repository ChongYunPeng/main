package doordonote.storage;

import java.util.Date;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;

import java.util.ArrayList;
import java.io.IOException;
import java.io.FileNotFoundException;

public class StorageHandler implements Storage {
	
	private static final String FILE_TYPE = ".json";
	private final String MESSAGE_ADD = "Task %1$s added";
	private final String MESSAGE_UPDATE = "Task updated to %1$s";
	private final String MESSAGE_DELETE = "Task %1$s deleted";
	private final String MESSAGE_NO_TASK_TO_DELETE = "No Tasks to delete";
	private final String MESSAGE_NO_TASK_TO_UPDATE = "No Tasks to update";
	private final String MESSAGE_REMOVE = "Task %1$s removed from file";
	private final String MESSAGE_CLEAR = "All data deleted from file";
	private final String MESSAGE_UNDO_SUCCESS = "Undo executed";
	private final String MESSAGE_UNDO_FAIL = "Undo not executed";
	private final String MESSAGE_REDO_SUCCESS = "Redo executed";
	private final String MESSAGE_REDO_FAIL = "Redo not executed";
	private final String MESSAGE_RESTORE = "Task %1$s restored";
	private final String MESSAGE_FINISH = "Marked Task %1$s as done";
	private final String MESSAGE_NOT_FINISH = "Marked Task %1$s as not done";
	private final String MESSAGE_NOT_FOUND = "File \"%1$s\" is not found.";
	private final String MESSAGE_READ = "Reading from file \"%1$s\"";
	private final String MESSAGE_PATH_CREATE = "Creating file \"%1$s\"";
	private final String MESSAGE_PATH_EXISTS = "File \"%1$s\" exists. Reading from \"%1$s\"";

	
	protected TaskWriter writer;
	protected TaskReader reader;
	private static StorageHandler storageHandler;

	public StorageHandler(){
		writer = new TaskWriter();
		reader = new TaskReader();
	}

	public StorageHandler(String fileName){
		writer = new TaskWriter(fileName);
		reader = new TaskReader(fileName);
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
		int create = writer.path(fileName);
		if(create==0){
			return String.format(MESSAGE_PATH_CREATE, fileName);
		} else if(create==1){
			return String.format(MESSAGE_PATH_EXISTS, fileName);
		}
		return null;
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

	public String add(Task task){
		try{
			writer.add(task);
			return String.format(MESSAGE_ADD, task);
		}
		catch(IOException e){
			e.printStackTrace();
			return "";
		}
	}

	public String add(String description, Date startDate, Date endDate){
		Task task = createTask(description, startDate, endDate);
		return add(task);
	}


	public String update(Task taskToUpdate, String description, Date startDate, Date endDate) {
		Task updatedTask = createTask(description, startDate, endDate);
		return update(taskToUpdate, updatedTask);
	}

	public String update(Task taskToUpdate, Task updatedTask){
		try{
			writer.update(taskToUpdate, updatedTask);
			return String.format(MESSAGE_UPDATE, updatedTask);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_UPDATE;
		}
		catch(IOException e){
			e.printStackTrace();
			return "";
		}
	}

	public String delete(Task taskToDelete){
		try{
			writer.delete(taskToDelete);
			return String.format(MESSAGE_DELETE, taskToDelete);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_DELETE;
		}
		catch(IOException e){
			e.printStackTrace();
			return "";
		}
	}

	public String remove(Task taskToRemove) throws IOException{
		writer.remove(taskToRemove);
		return String.format(MESSAGE_REMOVE, taskToRemove);
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
	
	public String restore(Task task) throws IOException{
		writer.restore(task);
		return String.format(MESSAGE_RESTORE, task);
	}
	
	public String finish(Task task) throws IOException{
		writer.setDone(task);
		return String.format(MESSAGE_FINISH, task);
	}

	public String notFinish(Task task) throws IOException{
		return String.format(MESSAGE_NOT_FINISH,task);
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
			assert (task != null):"Invalid Task parameters";
		}

		return task;
	}

}
