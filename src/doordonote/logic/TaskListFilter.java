package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.Storage;

public class TaskListFilter {
	
	protected Storage storage = null;
	protected List<Task> fullTaskList = null;
	
	protected TaskListFilter(Storage storage) {
		this.storage = storage;
	}
	
	protected void setStorage(Storage storage) {
		this.storage = storage;
	}
	
	
	
	protected List<Task> getUserTaskList(UIState stateObj) throws IOException {
		fullTaskList = storage.readTasks();
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
		
		if (stateObj.filterList == null || stateObj.filterList.isEmpty()) {
			return userTaskList;
		} else {
			return filter(userTaskList, stateObj.filterList);
		}
	}
	
	
	

	private List<Task> filter(List<Task> userTaskList, List<String> filterList) {
		List<Task> tempList = fullTaskList;
		userTaskList = tempList;
		for (String keyword : filterList) {
			tempList = new ArrayList<Task>();
			for (Task task : userTaskList) {
				if (task.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
					tempList.add(task);
				}
			}
			userTaskList = tempList;
		}
		return null;
	}

	
	//@@author yongrui
	private List<Task> getUnfinishedTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Task> getOverdueTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Task> getFinishedTasks() {
		// TODO Auto-generated method stub
		return null;
	}

	private List<Task> getDeletedTasks() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
	




