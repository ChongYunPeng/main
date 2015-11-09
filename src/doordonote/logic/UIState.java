package doordonote.logic;

import java.util.Date;
import java.util.List;

import doordonote.common.Util;

//@@author A0131436N

public class UIState {
	
	// Type of display
	public enum ListType {
	    DELETED, FINISHED, NORMAL
	}
	
	
	// Fills the user inputBox with this String.
	// Only used by the update method in Controller
	protected String inputBox = null;
	
	// Determines the type of helpBox to display
	protected String helpBox = null;
	
	// Determines the type of Tasks to display
	protected ListType displayType = null;
	
	// Stores the id of a new task. UI will use this id 
	// to highlight newly added tasks
	protected int idNewTask = -1;
	
	// A list of words used to filter the task. Set by the find
	// find method in controller
	protected List<String> filterList = null;
	
	// Filters and displays the list of tasks that ends after
	// this Date
	protected Date filterDate = null;
	
	public UIState() {
		setDefault();
	}
	
	/**
	 * Used to clone a UIState object
	 * 
	 * @param 	other
	 * 			The UIState object to be cloned.
	 */
	public UIState(UIState other) {
		inputBox = other.inputBox;
		displayType = other.displayType;
		helpBox = other.helpBox;
		idNewTask = other.idNewTask;
		filterList = other.filterList;
		filterDate = other.filterDate;
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
	
	/**
	 * Gets a suitable title based on the current state
	 * of the UIState 
	 * 
	 * @return the title that should be displayed
	 */
	public String getTitle() {
		String title = "";
		title = getTitleBasedOnDisplayType();
		if (filterDate != null) {
			return getTitleForFilterDate(title);
		} else if (filterList != null && !filterList.isEmpty()) {
			return getTitleForFilterList(title);
		} else {
			return title;
		}
	}

	private String getTitleBasedOnDisplayType() {
		String title;
		switch (displayType) {
		case FINISHED :
			title = "Finished Tasks";
			break;
		case DELETED :
			title = "Deleted Tasks";
			break;
		case NORMAL :
			// fall through
		default :
			title = "Home";
		}
		return title;
	}

	private String getTitleForFilterDate(String title) {
		title += "- Tasks that ends after: ";
		title += Util.getDateString(filterDate);
		return title;
	}

	private String getTitleForFilterList(String title) {
		title += "- Filter by: ";
		for (String word : filterList) {
			title += word + ", ";
		}
		return title.substring(0, title.length()-2);
	}

	
	/**
	 * Removes states that should not persist
	 */
	protected void clearTempState() {
		inputBox = "";
		helpBox = null;
		idNewTask = -1;
	}
	
	/**
	 * Sets the default state
	 */
	protected void setDefault() {
		inputBox = "";
		displayType = ListType.NORMAL;
		helpBox = null;
		idNewTask = -1;
		filterList = null;
		filterDate = null;
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
	        	this.filterDate == otherUIState.filterDate) {
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
		result = prime * result + ((filterDate == null) ? 0 : filterDate.hashCode());
		return result;
	}
}
	
