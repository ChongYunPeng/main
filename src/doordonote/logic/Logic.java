package doordonote.logic;

import doordonote.parser.*;
import doordonote.storage.Storage;

import java.util.List;

import doordonote.command.*;


public class Logic {
	private Storage dataFile = null;
	private Parser cmdParser = null;

	
	public Logic() {
		dataFile = new Storage();
		cmdParser = new Parser();
	}
	
	public String executeCmd(String cmd) {
		Command cmdObject = cmdParser.parse(cmd);
		if (cmdObject == null) {
			return "Error encountered when parsing.";
		}
		return cmdObject.run(dataFile);
	}
	
	
	
	// TODO: Change return type to List<Task>
	public List<String> getTasks() {
		return null;
	}
	
}
