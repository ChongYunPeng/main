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
	protected Date endDate = null;
	
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

	protected void setDefault() {
		inputBox = "";
		displayType = ListType.NORMAL;
		helpBox = null;
		displayType = ListType.NORMAL;
		idNewTask = -1;
		filterList = null;
		startDate = null;
		endDate = null;
	}
	
	
	
}
