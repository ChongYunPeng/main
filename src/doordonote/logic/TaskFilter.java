package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.Storage;

//@@author A0131436N

/**
 * {@code TaskFilter} 
 * 
 * @author yunpeng
 *
 */
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
		case NORMAL :
			// fallthrough
		default :
			userTaskList = getUnfinishedTasks();
		}
		
		if (userTaskList.isEmpty()) {
			return userTaskList;
		}
		
		if (stateObj.filterDate != null) {
			return filter(userTaskList, stateObj.filterDate);
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

	private List<Task> getUnfinishedTasks() throws IOException {
		return storage.readTasks();
	}
	

	//@@author A013
	private List<Task> getFinishedTasks() throws IOException {
		return storage.readDoneTasks();
	}

	private List<Task> getDeletedTasks() throws IOException {
		return storage.readDeletedTasks();
	}
	
}
	




