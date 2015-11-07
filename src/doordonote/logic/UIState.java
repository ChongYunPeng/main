//@@author A0131436N

package doordonote.logic;

import java.util.Date;
import java.util.List;

public class UIState {
	public enum ListType {
	    DELETED, FINISHED, NORMAL, OVERDUE
	}
	
	
	protected String inputBox = null;
	protected String helpBox = null;
	protected ListType displayType = null;
	protected int idNewTask = -1;
	protected List<String> filterList = null;
	protected Date startDate = null;
	
	public UIState() {
		setDefault();
	}
	
	public int getIdNewTask() {
		return idNewTask;
	}

	public ListType getDisplayType() {
		return displayType;
	}

	public String getInputBox() {
		return inputBox;
	}
	public String getHelpBox() {
		return helpBox;
	}
	public String getTitle() {
		String title = "";
		switch (displayType) {
		case NORMAL :
			title = "Home";
			break;
		case FINISHED :
			title = "Finished Tasks";
			break;
		case DELETED :
			title = "Deleted Tasks";
			break;
		case OVERDUE :
			title = "Overdue Tasks";
			break;
		default :
			
		}
		if (filterList == null || filterList.isEmpty()) {
			return title;
		} else {
			title += " Filter by: ";
			for (String word : filterList) {
				title += word + ", ";
			}
			return title;
		}
		
		
	}

	protected void clearTempState() {
		inputBox = "";
		helpBox = null;
		idNewTask = -1;
	}
	
	protected void setDefault() {
		inputBox = "";
		displayType = ListType.NORMAL;
		helpBox = null;
		idNewTask = -1;
		filterList = null;
		startDate = null;
	}

	/* 
	 * equals() is used only in testing
	 */
	public boolean equals(Object other) {
	    if (other == null) {
	        return false;
	    }
	    if (other == this) {
	    	return true;
	    }

        if (other instanceof UIState) {
        	UIState otherUIState = (UIState)other;
        	if (this.inputBox == otherUIState.inputBox && 
        		this.helpBox == otherUIState.helpBox &&
        		this.displayType == otherUIState.displayType &&
	        	this.idNewTask == otherUIState.idNewTask &&
	        	this.filterList == otherUIState.filterList &&
	        	this.startDate == otherUIState.startDate) {
        		return true;
        	}
        }
        return false;
	}
	
	/*
	 * hashCode method is implemented only to be consistent with the equals() method
	 * For using UIState class in any hash methods (e.g. storing it in a Hashmap),
	 * a better implementation of hashCode should be used.
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputBox == null) ? 0 : inputBox.hashCode());
		result = prime * result + ((helpBox == null) ? 0 : helpBox.hashCode());
		result = prime * result + idNewTask;
		result = prime * result + ((filterList == null) ? 0 : filterList.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		return result;
	}
}
	
