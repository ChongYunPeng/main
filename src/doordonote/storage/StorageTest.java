package doordonote.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;

import org.junit.Before;
import org.junit.After;

public class StorageTest {
	
	private static final String NAME_TEST = "test.json";
	private static final String NAME_CUSTOM = "custom";
	
	JsonFileIO str = new JsonFileIO(NAME_TEST);
	
//	@Before
	public void setup(){
	}
	
	@After
	public void tearDown(){
		File test = new File(NAME_CUSTOM+".json");
		File file = new File(NAME_TEST);
		test.delete();
		file.delete();
	}
	
	@Test
	public void testDefaultFileName() {
		assertEquals(str.getFileName(), NAME_TEST);
	}
	
//	@Test
	public void testCustomFileName(){
		JsonFileIO str = new JsonFileIO(NAME_CUSTOM);
		assertEquals(str.getFileName(), "custom.json");
	}
	
	@Test
	public void testStorageClear() throws IOException{
		addTaskToStorage();
		str.clear();
		assertEquals("{}", JsonFileIO.getFileString(NAME_TEST));
	}
	
	@Test
	public void testStorageWrite() throws IOException{
		addTaskToStorage();
		assertEquals(JsonFileIO.getFileString("expected.json"), JsonFileIO.getFileString(NAME_TEST));
	}
	
	@Test
	public void testStorageRead() throws IOException{
		ArrayList<Task> expected = addTaskToStorage();
		Collections.sort(expected);
		Task task1 = new FloatingTask("Do CS homework");
		expected.add(task1);
		assertEquals(expected, str.read());
		
	}
	
	private ArrayList<Task> addTaskToStorage(){
		ArrayList<Task> arrlist = new ArrayList<Task>();
		Date date0 = new Date(2000, 10, 6, 13,27);
		Date date1 = new Date(2015, 8, 7, 2, 13);
		Date date2 = new Date(2099, 9, 9, 9, 9);
		Date date3 = new Date(3000, 1, 1, 1, 1);
		Task task0 = new EventTask("Flying Pig", date0, date1);
		Task task1 = new FloatingTask("Do CS homework");
		Task task2 = new EventTask("Swimming", date1, date2);
		Task task3 = new DeadlineTask("Running", date3);
		str.write(task0);
		str.write(task1);
		str.write(task2);
		str.write(task3);
		arrlist.add(task0);
		arrlist.add(task2);
		arrlist.add(task3);
		return arrlist;
	}
	

}
