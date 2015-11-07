//@@author A0131436N

package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.Storage;

public class TaskFilter {
	
	protected Storage storage = null;
	protected ArrayList<Task> fullTaskList;
	
	protected TaskFilter(Storage storage) throws IOException {
		this.storage = storage;
		fullTaskList = storage.readTasks();
	}
	
	protected void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	public List<Task> getUserTaskList(UIState stateObj) throws IOException {
		fullTaskList = storage.readTasks();
		assert (fullTaskList != null);
		List<Task> userTaskList = null;
		switch (stateObj.displayType) {
		case DELETED :
			userTaskList = getDeletedTasks();
			break;
		case FINISHED :
			userTaskList = getFinishedTasks();
			break;
		case OVERDUE :
			userTaskList = getOverdueTasks();
			break;
		case NORMAL :
			// fallthrough
		default :
			userTaskList = getUnfinishedTasks();
		}
		
		if (userTaskList.isEmpty()) {
			return userTaskList;
		}
		
		if (stateObj.startDate != null) {
			return filter(userTaskList, stateObj.startDate);
		} else if (stateObj.filterList == null || stateObj.filterList.isEmpty()) {
			return userTaskList;
		} else {
			return filter(userTaskList, stateObj.filterList);
		}
	}
	

	protected List<Task> filter(List<Task> unfilteredUserTaskList, Date startDate) {
		assert(startDate != null);
		List<Task> userTaskList = new ArrayList<Task>();
		for (Task task : unfilteredUserTaskList) {
			if (task.getEndDate() != null && task.getEndDate().after(startDate)) {
				userTaskList.add(task);
			}
		}

		return userTaskList;		
	}

	protected List<Task> filter(List<Task> unfilteredUserTaskList, List<String> filterList) {
		List<Task> tempList = null;
		List<Task> userTaskList = unfilteredUserTaskList;
		for (String keyword : filterList) {
			tempList = new ArrayList<Task>();
			for (Task task : userTaskList) {
				if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
					tempList.add(task);
				}
			}
			userTaskList = tempList;
		}
		return userTaskList;
	}

		private List<Task> getUnfinishedTasks() {
		List<Task> unfinishedTaskList = new ArrayList<Task>();
		for(Task task : fullTaskList) {
			if (!task.isDone() && !task.isDeleted()) {
				unfinishedTaskList.add(task);
			}
		}
		return unfinishedTaskList;
	}
	
	private List<Task> getOverdueTasks() {
//		List<Task> overDueTasks = new ArrayList<Task>();
//		for(Task task : fullTaskList) {
//			if (task.getEndDate()) {
//				unfinishedTaskList.add(task);
//			}
//		}
//		return unfinishedTaskList;
		return null;
	}

	//@@author A013
	private List<Task> getFinishedTasks() {
		List<Task> finishedTasks = new ArrayList<Task>();
		for(Task task : fullTaskList) {
			if (task.isDone() && !task.isDeleted()) {
				finishedTasks.add(task);
			}
		}
		return finishedTasks;
	}

	private List<Task> getDeletedTasks() {
		List<Task> deletedTasks = new ArrayList<Task>();
		for(Task task : deletedTasks) {
			if (task.isDeleted()) {
				deletedTasks.add(task);
			}
		}
		return deletedTasks;
	}
	
}
	




