//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import doordonote.common.Task;
import doordonote.common.Util;
import doordonote.storage.Storage;

public class StorageStub implements Storage {
	private ArrayList<Task> taskList = null;
	
	public StorageStub() {
		 taskList = new ArrayList<Task>();
	}
	
	@Override
	public String path(String location) {
		return "Path is called";
	}

	@Override
	public String get(String location) {
		return "Get is called";
	}

	@Override
	public String add(Task task) {
		taskList.add(task);
		return "Add is called";
	}

	@Override
	public String add(String description, Date startDate, Date endDate) {
		Task task = Util.createTask(description, startDate, endDate);
		return add(task);
	}

	@Override
	public String update(Task taskToUpdate, String description, Date startDate, Date endDate) {
		Task task = Util.createTask(description, startDate, endDate);
		return update(taskToUpdate,task);
	}

	@Override
	public String update(Task oldTask, Task newTask) {
		taskList.remove(oldTask);
		taskList.add(newTask);
		return "Update is called";
	}

	@Override
	public String delete(Task taskToDelete) {
		taskList.remove(taskToDelete);
;		return "Delete is called";
	}

	@Override
	public String remove(Task taskToRemove) throws IOException {
		taskList.remove(taskToRemove);
;		return "Remove is called";
	}

	@Override
	public ArrayList<Task> readTasks() throws IOException {
		return taskList;
	}

	@Override
	public String undo() {
		return "Undo is called";
	}

	@Override
	public String redo() {
		return "Redo is called";
	}

	@Override
	public String restore(Task task) throws IOException {
		return "Restore is called";
	}

	@Override
	public String finish(Task task) throws IOException {
		return "Finish is called";
	}

	
	// Not used
	@Override
	public String notFinish(Task task) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public ArrayList<Task> readDeletedTasks() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Task> readDoneTasks() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String clear() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return null;
	}

}
