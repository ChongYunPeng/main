package doordonote.logic;

import java.util.List;

public class UIState {
	public enum ListType {
	    DELETED, FINISHED, NORMAL, OVERDUE
	}
	
	
	protected String inputBox = null;
	protected String helpBox = null;
	protected String title = null;
	protected ListType displayType = null;
	protected int idNewTask = 0;
	protected boolean hasFileError = false;
	protected List<String> filterList = null;
	
	public UIState() {
		inputBox = "";
		helpBox = null;
		displayType = ListType.NORMAL;
		title = "Home";
		hasFileError = false;
		idNewTask = 0;
		filterList = null;
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
			title = "";
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
	public boolean isHasFileError() {
		return hasFileError;
	}
	
	protected void setDefault() {
		inputBox = "";
		helpBox = null;
		title = "Home";
		hasFileError = false;
		filterList = null;
	}
	
	
	
}
