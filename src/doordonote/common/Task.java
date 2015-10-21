package doordonote.common;

import java.util.Date;

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

	public int compareTo(Task task){
		if(getEndDate() == null){
			return 1;
		} else if(task.getEndDate() == null){
			return -1;
		} else{
			return getEndDate().compareTo(task.getEndDate());

		}
	}

	public String toString(){
		String str = "\"";
		str += getDescription();
		str += "\"";
		return str;
	}


}
