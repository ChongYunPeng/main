package doordonote.logic;

public class UIState {
	public enum ListType {
	    DELETED, FINISHED, NORMAL
	}
	
	
	protected String inputBox = null;
	protected String helpBox = null;
	protected String title = null;
	protected ListType displayType = null;
	protected boolean hasFileError = false;
	
	public UIState() {
		inputBox = "";
		helpBox = null;
		displayType = ListType.NORMAL;
		title = "Home";
		hasFileError = false;
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
		return title;
	}
	public boolean isHasFileError() {
		return hasFileError;
	}
	
	protected void setDefault() {
		inputBox = "";
		helpBox = null;
		title = "Home";
		hasFileError = false;
	}
	
	
	
}
