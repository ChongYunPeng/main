package doordonote.storage;

import java.util.Date;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;

import java.util.ArrayList;
import java.io.IOException;

public class StorageHandler implements Storage {

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

	protected JsonFileIO jsonFileIO;
	private static StorageHandler taskStorage;

	private StorageHandler(){
		jsonFileIO = new JsonFileIO();
	}

	private StorageHandler(String fileName){
		jsonFileIO = new JsonFileIO(fileName);
	}

	public static Storage getInstance(){
		if(taskStorage == null){
			taskStorage = new StorageHandler();
		}
		return taskStorage;
	}
	
	public void setFile(String fileName){
		jsonFileIO.setFile(fileName);
	}
	
	public String add(Task task){
		jsonFileIO.add(task);
		return String.format(MESSAGE_ADD, task);
	}

	public String add(String description, Date startDate, Date endDate) {
		Task task = createTask(description, startDate, endDate);
		return add(task);
	}


	public String update(Task taskToUpdate, String description, Date startDate, Date endDate) {
		Task updatedTask = createTask(description, startDate, endDate);
		return update(taskToUpdate, updatedTask);
	}
	
	public String update(Task taskToUpdate, Task updatedTask){
		try{
			jsonFileIO.update(taskToUpdate, updatedTask);
			return String.format(MESSAGE_UPDATE, updatedTask);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_UPDATE;
		}
	}

	public String delete(Task taskToDelete){
		try{
			jsonFileIO.delete(taskToDelete);
			return String.format(MESSAGE_DELETE, taskToDelete);
		}
		catch(EmptyTaskListException e){
			return MESSAGE_NO_TASK_TO_DELETE;
		}
	}
	
	public String remove(Task taskToRemove){
		jsonFileIO.remove(taskToRemove);
		return String.format(MESSAGE_REMOVE, taskToRemove);
	}
	
	public ArrayList<Task> readTasks() throws IOException{
		ArrayList<Task> listTask = null;
		try{
			listTask = jsonFileIO.readTasks();
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
			listTask = jsonFileIO.readDeletedTasks();
		}
		catch (IOException e){
			throw e;
		}		
		assert(listTask!=null);
		return listTask;
	}
	
	public String clear(){
		jsonFileIO.clear();
		return MESSAGE_CLEAR;
	}
	
	public String undo(){
		if(jsonFileIO.undo()){
			return MESSAGE_UNDO_SUCCESS;
		} else{
			return MESSAGE_UNDO_FAIL;
		}
	}
	
	public String redo(){
		if(jsonFileIO.redo()){
			return MESSAGE_REDO_SUCCESS;
		} else{
			return MESSAGE_REDO_FAIL;
		}
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
