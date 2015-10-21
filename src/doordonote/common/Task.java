package doordonote.common;

import java.util.Date;

public abstract class Task implements Comparable<Task>{


	protected String description;


	public Task(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
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
