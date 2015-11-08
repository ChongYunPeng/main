//@@author A0131716M
package doordonote.common;

import java.util.Date;
import org.joda.time.DateTimeComparator;



public abstract class Task implements Comparable<Task>{


	protected String description;
	protected boolean isDone;
	protected boolean isDeleted;


	public Task(String description){
		this.description = description;
		this.isDone = false;
		this.isDeleted = false;
	}

	public String getDescription(){
		return description;
	}

	public boolean isDeleted(){
		return isDeleted;
	}

	public boolean isDone(){
		return isDone;
	}

	public void setDeleted(){
		this.isDeleted = true;
	}

	public void setNotDeleted(){
		this.isDeleted = false;
	}

	public void setDone(){
		this.isDone = true;
	}

	public void setNotDone(){
		this.isDone = false;
	}

	public abstract Date getEndDate();

	public abstract Date getStartDate();

	public abstract String getType();

	public abstract int hashCode();

	public abstract boolean equals(Object obj);
    
	//@@author A0132785Y
	public int compareTo(Task task){
		
        // if both tasks are floating tasks
		if (task.getEndDate() == null && getEndDate() == null) {
			return getDescription().compareTo(task.getDescription());
		} 
		// if task is a deadline or event, and the other is floating
		else if (task.getEndDate() != null && getEndDate() == null) {
			return 1;
		} 
		// if task is floating and the other is a deadline or event
		else if (task.getEndDate() == null && getEndDate() != null) {
			return -1;
		}
		// if task is an event and the other is a deadline
		else if (task.getStartDate() != null && getStartDate() == null) {
			// if task is a single day event
			if (task.getStartDate().getDate() == task.getEndDate().getDate()) {
				return getEndDate().compareTo(task.getEndDate());
			}
			// if task is a multiple day event
			else {
				return -1;
			}
		} 
		// if task is a deadline and the other is an event
		else if (task.getStartDate() == null && getStartDate() != null) {
			// if other task is a single day event
			if (getStartDate().getDate() == getEndDate().getDate()) {
				return getEndDate().compareTo(task.getEndDate());
			}
			// if other task is a multiple day event
			else {
				return 1;
			}
		} 
		// if both tasks are deadlines
		else if (task.getStartDate() == null && getStartDate() == null) {
			return getEndDate().compareTo(task.getEndDate());
		} 
		// both tasks are events: if task is a single day event and other
		// is a multiple day event
		else if (task.getStartDate().getDate() == task.getEndDate().getDate()
				 && getStartDate().getDate() != getEndDate().getDate()) {
			return 1;
		} 
		// if task is a multiple day event and other is a single day event
		else if (task.getStartDate().getDate() != task.getEndDate().getDate()
				 && getStartDate().getDate() == getEndDate().getDate()) {
			return -1;
		} 
		// if both tasks are single day events
		else if (task.getStartDate().getDate() == task.getEndDate().getDate()
				 && getStartDate().getDate() == getEndDate().getDate()) {
			return getStartDate().compareTo(task.getStartDate());
		} 
		// if both tasks are multiple day events
		else if (task.getStartDate().getDate() != task.getEndDate().getDate()
				 && getStartDate().getDate() != getEndDate().getDate()) {
			return getStartDate().compareTo(task.getStartDate());
		} else {
			return 0;
		}
	}
	
	/**
	 * @@author A0131716M
	 *
	 */

	public String toString(){
		String str = "\"";
		str += getDescription();
		str += "\"";
		return str.trim();
	}
	
	


}
