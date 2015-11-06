package doordonote.common;

import java.util.Date;
import org.joda.time.DateTimeComparator;

/**
 * @@author A0131716M
 *
 */

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
		/*if(getEndDate() == null && task.getEndDate() == null){
			return getDescription().compareTo(task.getDescription());
		} else if(getEndDate() != null && getStartDate() != null && !(getStartDate().equals(getEndDate())) &&
				task.getEndDate() != null && task.getStartDate() != null && !(task.getStartDate().equals(task.getEndDate()))){ 
			return getStartDate().compareTo(task.getStartDate());
		} else if(getEndDate() == null){
			return 1;
		} else if(task.getEndDate() == null){
			return -1;
		} else{
			return getEndDate().compareTo(task.getEndDate());
		}*/
		if (task.getEndDate() == null && getEndDate() == null) {
			return getDescription().compareTo(task.getDescription());
		} else if (task.getEndDate() != null && getEndDate() == null) {
			return 1;
		} else if (task.getEndDate() == null && getEndDate() != null) {
			return -1;
		}
		else if (task.getStartDate() != null && getStartDate() == null) {
			if(task.getStartDate().getDate() == task.getEndDate().getDate()) {
				return getEndDate().compareTo(task.getEndDate());
			}
			else{
				return -1;
			}
		} else if (task.getStartDate() == null && getStartDate() != null) {
			if(getStartDate().getDate() == getEndDate().getDate()) {
				return getEndDate().compareTo(task.getEndDate());
			}
			else{
				return 1;
			}
		} else if (task.getStartDate() == null && getStartDate() == null) {
			return getEndDate().compareTo(task.getEndDate());
		} else if (task.getStartDate().getDate() == task.getEndDate().getDate()
				 && getStartDate().getDate() != getEndDate().getDate()) {
			return 1;
		} else if (task.getStartDate().getDate() != task.getEndDate().getDate()
				 && getStartDate().getDate() == getEndDate().getDate()) {
			return -1;
		} else if (task.getStartDate().getDate() == task.getEndDate().getDate()
				 && getStartDate().getDate() == getEndDate().getDate()) {
			return getStartDate().compareTo(task.getStartDate());
		} else if (task.getStartDate().getDate() != task.getEndDate().getDate()
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
