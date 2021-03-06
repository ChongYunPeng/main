package doordonote.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import doordonote.common.Task;
import doordonote.storage.Storage;

//@@author A0131436N

/**
 * {@code TaskFilter} filters the tasks visible to users based on 
 * the UIState object.
 * 
 * @author yunpeng
 *
 */
public class TaskFilter {
	
	protected Storage storage = null;
	protected ArrayList<Task> fullTaskList = null;
	
	protected TaskFilter(Storage storage) {
		this.storage = storage;
		try {
			fullTaskList = storage.readTasks();
		} catch (IOException e) {
			fullTaskList = new ArrayList<Task>();
		}
	}
	
	
	/**
	 * Filters the full task list based on the stateObj
	 * 
	 * @param stateObj
	 * @return user task list based on the UIState object.
	 * @throws IOException
	 */
	public List<Task> getUserTaskList(UIState stateObj) throws IOException {
		fullTaskList = storage.readTasks();
		assert (fullTaskList != null);
		List<Task> userTaskList = filterTaskByDisplayType(stateObj);
		return filterByDateAndKeywords(stateObj, userTaskList);
	}

	private List<Task> filterByDateAndKeywords(UIState stateObj, List<Task> userTaskList) {
		if (stateObj.filterDate != null) {
			return filterByDate(userTaskList, stateObj.filterDate);
		} else if (stateObj.filterList != null) {
			return filterByKeywords(userTaskList, stateObj.filterList);
		} else {
			return userTaskList;
		}
	}
	
	private List<Task> filterTaskByDisplayType(UIState stateObj) throws IOException {
		List<Task> userTaskList;
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
		return userTaskList;
	}
	
	protected List<Task> filterByDate(List<Task> unfilteredUserTaskList, Date filterDate) {
		List<Task> userTaskList = new ArrayList<Task>();
		for (Task task : unfilteredUserTaskList) {
			if (task.getEndDate() != null && task.getEndDate().after(filterDate)) {
				userTaskList.add(task);
			}
		}
		return userTaskList;		
	}

	protected List<Task> filterByKeywords(List<Task> unfilteredUserTaskList, List<String> filterList) {
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

	private List<Task> getFinishedTasks() throws IOException {
		return storage.readDoneTasks();
	}

	private List<Task> getDeletedTasks() throws IOException {
		return storage.readDeletedTasks();
	}
	
}
	




