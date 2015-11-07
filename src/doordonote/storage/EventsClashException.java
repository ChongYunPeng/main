//@@author A0131716M
package doordonote.storage;

import doordonote.common.Task;

/**
 * @author A0131716M
 *
 */
public class EventsClashException extends Exception {
	
	Task originalTask;
	Task clashedTask;
	
	
	public EventsClashException(Task originalTask, Task clashedTask) {
		this.originalTask = originalTask;
		this.clashedTask = clashedTask;
	}
	
	public Task getOriginalTask(){
		return originalTask;
	}
	
	public Task getClashedTask(){
		return clashedTask;
	}
	
}
