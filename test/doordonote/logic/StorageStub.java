//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import doordonote.common.Task;
import doordonote.storage.Storage;

public class StorageStub implements Storage {

	@Override
	public String path(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String get(String location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(Task task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String add(String description, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Task taskToUpdate, String descirption, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Task oldTask, Task newTask) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Task taskToDelete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String remove(Task taskToRemove) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Task> readTasks() throws IOException {
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
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String redo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String restore(Task task) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String finish(Task task) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notFinish(Task task) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
