//@@author A0131716M
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


/**
 * @author A0131716M
 *
 */

public class StorageTest {
	
	private static final String NAME_DEFAULT = "test.json";
	private static final String NAME_TEST = "test2.json";
	private static final String NAME_SETTINGS = "settings.dodn";
	
	TaskWriter str = new TaskWriter();
	TaskReader reader = new TaskReader();
	String settingStr;
	
	
	
	Date date0 = new Date(2000, 10, 6);
	Date date1 = new Date(2015, 8, 7, 2, 13);
	Date date2 = new Date(2099, 9, 9, 9, 9);
	Date date3 = new Date(3000, 1, 1, 1, 1);
	Task task0 = new EventTask("Flying Pig", date0, date1);
	Task task1 = new FloatingTask("Do CS homework");
	Task task2 = new EventTask("Swimming", date1, date2);
	Task task3 = new DeadlineTask("Running", date3);
	
	@Before
	public void setup() throws IOException{
		settingStr = TaskReader.getFileString(NAME_SETTINGS).trim();
		File testFile = new File(NAME_DEFAULT);
		if(!testFile.exists()){
			testFile.createNewFile();
		}
		reader.read(NAME_DEFAULT);
		str.path(NAME_DEFAULT);
		str.clear();
	}
	
	@After
	public void tearDown(){
		File file = new File(NAME_DEFAULT);
		File test = new File(NAME_TEST);
		str.path(settingStr);
		file.delete();
		test.delete();
	}
	
	@Test
	public void testDefaultFileName() {
		assertEquals(str.getFileName(), NAME_DEFAULT);
	}
	
	@Test
	public void testPathAndGet() throws IOException, EmptyTaskListException, DuplicateTaskException{
		str.add(task0);
		ArrayList<Task> arrlist = new ArrayList<Task>();
		arrlist.add(task0);
		str.path(NAME_TEST);
		str.add(task1);
		ArrayList<Task> testlist = new ArrayList<Task>();
		testlist.add(task1);
		str.delete(task0);
		assertEquals(testlist, reader.readTasks());
		reader.read(NAME_DEFAULT);
		assertEquals(arrlist, reader.readTasks());
	}
	
	@Test
	public void testStorageClear() throws IOException, DuplicateTaskException{
		addTasksToStorage();
		str.clear();
		assertEquals("[]", reader.getFileString(NAME_DEFAULT));
	}
	
	@Test
	public void testCurrentFileStringWithVariousMethods() throws IOException, EmptyTaskListException, DuplicateTaskException{
		str.add(task0);
		assertEquals(str.getCurrentFileString(), reader.getFileString(NAME_DEFAULT));
		str.add(task3);
		assertEquals(str.getCurrentFileString(), reader.getFileString(NAME_DEFAULT));
		str.delete(task0);
		assertEquals(str.getCurrentFileString(), reader.getFileString(NAME_DEFAULT));
		str.undo();
		assertEquals(str.getCurrentFileString(), reader.getFileString(NAME_DEFAULT));
		str.add(task2);
		str.add(task1);
	}
	
	
	@Test
	public void testStorageReadAndWrite() throws IOException, DuplicateTaskException{
		ArrayList<Task> expected = addTasksToStorage();
		assertFalse(expected.equals(reader.readTasks()));
		Collections.sort(expected);
		assertEquals(expected, reader.readTasks());		
	}
	
	@Test
	public void testStorageDelete() throws EmptyTaskListException, IOException, DuplicateTaskException{		
		addTasksToStorage();
		ArrayList<Task> arrlist = new ArrayList<Task>();
		ArrayList<Task> dellist = new ArrayList<Task>();
		arrlist.add(task3);
		arrlist.add(task2);
		str.delete(task0);
		str.delete(task1);
		dellist.add(task0);
		dellist.add(task1);
		assertEquals(arrlist, reader.readTasks());
		assertEquals(dellist, reader.readDeletedTasks());
	}
	
	@Test
	public void testStorageUndoAndRedo() throws IOException, EmptyTaskListException, DuplicateTaskException{
		ArrayList<Task> arrlist = new ArrayList<Task>();
		str.add(task0);
		str.add(task3);
		str.undo();
		arrlist.add(task0);
		assertEquals(arrlist, reader.readTasks());
		str.add(task1);
		str.add(task2);
		str.undo();
		str.undo();
		assertEquals(arrlist, reader.readTasks());
		str.redo();
		str.redo();
		arrlist.add(task2);
		arrlist.add(task1);
		assertEquals(arrlist, reader.readTasks());
	}
	
	@Test
	public void testReadDeleteAndDone() throws IOException, EmptyTaskListException, DuplicateTaskException{
		ArrayList<Task> arrlist = addTasksToStorage();
		ArrayList<Task> dellist = new ArrayList<Task>();
		ArrayList<Task> donelist = new ArrayList<Task>();
		str.delete(task0);
		arrlist.remove(task0);
		dellist.add(task0);
		assertEquals(dellist, reader.readDeletedTasks());
		assertEquals(dellist, reader.readDeletedTasks());
		str.restore(task0);
		arrlist.add(task0);
		Collections.sort(arrlist);
		assertEquals(arrlist, reader.readTasks());
		str.setDone(task2);
		donelist.add(task2);
		arrlist.remove(task2);
		Collections.sort(arrlist);
		assertEquals(arrlist, reader.readTasks());
		assertEquals(donelist, reader.readDoneTasks());
		str.setNotDone(task2);
		donelist.remove(task2);
		assertEquals(donelist, reader.readDoneTasks());
		arrlist.add(task2);
		Collections.sort(arrlist);
		str.setDone(task1);
		str.restore(task1);
		assertEquals(arrlist, reader.readTasks());
		
	}
	
	//Returns unsorted ArrayList of Tasks added to Storage
	private ArrayList<Task> addTasksToStorage() throws DuplicateTaskException{
		ArrayList<Task> arrlist = new ArrayList<Task>();
		try{
		str.add(task0);
		str.add(task1);
		str.add(task2);
		str.add(task3);
		}
		catch(IOException e){
			e.printStackTrace();
		}
		arrlist.add(task0);
		arrlist.add(task1);
		arrlist.add(task2);
		arrlist.add(task3);
		return arrlist;
	}
	

}
