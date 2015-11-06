package doordonote.common;

import java.util.Date;

/**
 * @@author A0131716M
 *
 */

public class FloatingTask extends Task {
	
	
	public FloatingTask(String description){
		super(description);	
	}
	
	public Date getEndDate(){
		return null;
	}
	
	public Date getStartDate(){
		return null;
	}
	
	public String getType(){
		return "FLOATING_TASK";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Task))
			return false;
		Task other = (Task) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		return true;
	}
}
