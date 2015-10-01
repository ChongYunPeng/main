package doordonote.parser;

import doordonote.command.*;

public class Parser {

	public Command parse(String cmd) {
		String cmdType = getCommandType(cmd);
		switch (cmdType) {
		case "add":
			String cmdBody = removeFirstWord(cmd);
			Command cmdAddObj = new CommandAdd();
			cmdAddObj.setCmdArguments(cmdBody);
			return cmdAddObj;
		default:
			System.out.println("Error");
			return null;
		}	
	}

	private static String getCommandType(String cmd) {
		String cmdType = getFirstWord(cmd).toLowerCase();
		return cmdType;

	}
	private static String getFirstWord(String cmd) {
		String firstWord = cmd.trim().split("\\s+")[0];
		return firstWord;
	}
	
	private static String removeFirstWord(String cmd) {
		return cmd.replace(getFirstWord(cmd), "").trim();
	}
	
}

