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
	
	public abstract String getType();
	
	public abstract int compareTo(Task t);
	
	public abstract int hashCode();
	
	public abstract boolean equals(Object obj);


	public String toString(){
		String str = "\"";
		str += getDescription();
		str += "\"";
		return str;
	}

	
}
