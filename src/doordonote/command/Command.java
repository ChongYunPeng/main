package doordonote.command;

import java.util.List;

import doordonote.storage.Storage;

public abstract class Command {	
	protected String cmdType = null;
	protected List<String> cmdArguments = null;
	
	
	public abstract String run(Storage storageName);
	public abstract void setCmdArguments(String cmdBody);
}
