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
	
	Date date0 = new Date(2000, 10, 6);
	Date date1 = new Date(2015, 8, 7, 2, 13);
	Date date2 = new Date(2099, 9, 9, 9, 9);
	Date date3 = new Date(3000, 1, 1, 1, 1);
	Task task0 = new EventTask("Flying Pig", date0, date1);
	Task task1 = new FloatingTask("Do CS homework");
	Task task2 = new EventTask("Swimming", date1, date2);
	Task task3 = new DeadlineTask("Running", date3);
	
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
	
	@Test
	public void testCustomFileName(){
		JsonFileIO str = new JsonFileIO(NAME_CUSTOM);
		assertEquals(str.getFileName(), "custom.json");
	}
	
	@Test
	public void testStorageClear() throws IOException{
		addTaskToStorage();
		str.clear();
		assertEquals("[]", JsonFileIO.getFileString(NAME_TEST));
	}
	
	@Test
	public void testCurrentFileStringWithVariousMethods() throws IOException, EmptyTaskListException{
		str.add(task0);
		assertEquals(str.getCurrentFileString(), JsonFileIO.getFileString(NAME_TEST));
		str.add(task3);
		assertEquals(str.getCurrentFileString(), JsonFileIO.getFileString(NAME_TEST));
		str.delete(task0);
		assertEquals(str.getCurrentFileString(), JsonFileIO.getFileString(NAME_TEST));
		str.undo();
		assertEquals(str.getCurrentFileString(), JsonFileIO.getFileString(NAME_TEST));
		str.add(task2);
		str.add(task1);
		ArrayList<Task> arrlist = addTaskToStorage();
		Collections.sort(arrlist);
		assertEquals(arrlist, str.readTasks());
	}
	
	
	@Test
	public void testStorageReadAndWrite() throws IOException{
		ArrayList<Task> expected = addTaskToStorage();
		assertFalse(expected.equals(str.readTasks()));
		Collections.sort(expected);
		assertEquals(expected, str.readTasks());		
	}
	
	@Test
	public void testStorageDelete() throws EmptyTaskListException, IOException{		
		ArrayList<Task> arrlist = addTaskToStorage();
		ArrayList<Task> dellist = new ArrayList<Task>();
		arrlist.remove(task0);
		arrlist.remove(task1);
		str.delete(task0);
		str.delete(task1);
		dellist.add(task0);
		dellist.add(task1);
		System.out.println(JsonFileIO.getFileString(NAME_TEST));
		assertEquals(arrlist, str.readTasks());
		assertEquals(dellist, str.readDeletedTasks());
	}
	
	@Test
	public void testStorageUndoAndRedo() throws IOException, EmptyTaskListException{
		ArrayList<Task> arrlist = new ArrayList<Task>();
		str.add(task0);
		str.add(task3);
		str.undo();
		arrlist.add(task0);
		assertEquals(arrlist, str.readTasks());
		str.add(task1);
		str.add(task2);
		str.undo();
		str.undo();
		assertEquals(arrlist, str.readTasks());
		str.redo();
		str.redo();
		arrlist.add(task1);
		arrlist.add(task2);
		assertEquals(arrlist, str.readTasks());
	}
	
	//Returns unsorted ArrayList of Tasks added to Storage
	private ArrayList<Task> addTaskToStorage(){
		ArrayList<Task> arrlist = new ArrayList<Task>();
		str.add(task0);
		str.add(task1);
		str.add(task2);
		str.add(task3);
		arrlist.add(task0);
		arrlist.add(task1);
		arrlist.add(task2);
		arrlist.add(task3);
		return arrlist;
	}
	

}