//@@author A0131436N

package doordonote.commandfactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CommandFactoryTest {
//	private CommandFactory cmdFactory = new CommandFactory();

	@Test
	public void getCommandTypeTest() {
		assertEquals("getCommandType should be case insensitive", 
					 "add", 
					 CommandFactory.getCommandType("AdD list of statements"));
		
		assertEquals("getCommandType should be robust to leading spaces",
				 	 "test", 
				 	 CommandFactory.getCommandType("  test  list of "));
		assertEquals("getCommandType should be return empty string for blank strings",
			 	 	 "", 
			 	 	 CommandFactory.getCommandType("   "));	
	}
	
	@Test
	public void parseTest() {
		
	}

}
