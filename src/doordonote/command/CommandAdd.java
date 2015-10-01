package doordonote.command;

import java.util.ArrayList;

import doordonote.storage.Storage;

public class CommandAdd extends Command {
	
	public CommandAdd() {
		cmdType = "add";
		cmdArguments = new ArrayList<String>();
	}
	
	@Override
	public String run(Storage storageName) {
		String output = add(storageName);
		return output;
	}
	
	private String add(Storage storageObj) {
		String output = storageObj.add(cmdArguments.get(0));
		return output;
	};

	@Override
	public void setCmdArguments(String cmdBody) {
		cmdArguments.add(cmdBody);		
	}


}
