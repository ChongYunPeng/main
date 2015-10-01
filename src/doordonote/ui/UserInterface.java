package doordonote.ui;

import java.util.Scanner;

import doordonote.logic.Logic;

public class UserInterface {
	private Scanner scanner = new Scanner(System.in);
	private Logic logic = new Logic();
	
	public String getCommand() {
		System.out.print("command: ");
		String command = scanner.nextLine();
		return command;
	}
	
	public String executeCommand(String cmd) {
		String output = logic.executeCmd(cmd);
		if (output != null) {
			System.out.println(output);			
		}
		return output;
	}
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
		
		while (true) {
			String command = ui.getCommand();
			ui.executeCommand(command);
		}
	}


}
