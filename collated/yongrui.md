# yongrui
###### src\doordonote\logic\TaskListFilter.java
``` java
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

	private List<Task> getFinishedTasks() throws IOException {
		return storage.readDoneTasks();
	}

	private List<Task> getDeletedTasks() throws IOException {
		// TODO Auto-generated method stub
		return storage.readDeletedTasks();
	}
	
}
	




```
