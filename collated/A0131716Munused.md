# A0131716Munused
###### test\doordonote\storage\StorageHandlerTest.java
``` java
package doordonote.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


//This test is not used in production code
public class StorageHandlerTest {
	
	private static final String NAME_DEFAULT = "test.json";
	private static final String NAME_SETTINGS = "settings.dodn";
	
	Storage str = null;
	String settingsStr;
	
	@Before
	public void setup() throws IOException{
		settingsStr = TaskReader.getFileString(NAME_SETTINGS).trim();
		File testFile = new File(NAME_DEFAULT);
		if(!testFile.exists()){
			testFile.createNewFile();
		}
		str.get(NAME_DEFAULT);
		str.path(NAME_DEFAULT);
		str.clear();
	}
	
	@After
	public void tearDown() throws IOException{
		File file = new File(NAME_DEFAULT);
		str.path(settingsStr);
		file.delete();
	}
	
	@Test
	//This test if Storage can be instantiated if Json file is invalid.
	public void testInValidJsonFile() throws IOException {
		assertTrue(null == str);
		writeInvalidJsonFile();
		str = StorageHandler.getInstance();
		assertFalse(null == str);
		System.out.println(str.getCurrentFilePath());
	}
	
	private void writeInvalidJsonFile() throws IOException{
		File file = new File(NAME_DEFAULT);
		FileWriter writer = new FileWriter(file);
		writer.write("[{{}]");
		writer.close();
		
	}

}
```
