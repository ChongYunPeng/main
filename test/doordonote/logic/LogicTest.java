package doordonote.logic;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;
import doordonote.storage.Storage;
import doordonote.storage.StorageHandler;

//@@author A0131436N

public class LogicTest {
	private static String currentPath = null;
	private static Storage testStorage = null;
	private UIToLogic logic = null;
	private List<Task> testUnfinishedTaskList = null;
	private List<Task> testFinishedTaskList = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		testStorage = StorageHandler.getInstance();
		currentPath = testStorage.getCurrentFilePath();
	}

	@Before
	public void setUp() throws Exception {
		DateTime dt1 = new DateTime(2015, 11, 30, 8, 0, 0, 0);

		Task floatingTask1 = new FloatingTask("Watch LOTR");
		Task floatingTask2 = new FloatingTask("Read Starwars");
		Task deadlineTask = new DeadlineTask("Finish Report", dt1.toDate());

		
		// Initialize the storage path
		testStorage.path("test");
		logic = new Logic();
		testStorage.clear();
		testStorage.add(floatingTask1);
		testStorage.add(floatingTask2);
		testStorage.add(deadlineTask);
		testStorage.finish(floatingTask2);
		logic.getTasks();
		
		testUnfinishedTaskList = new ArrayList<Task>();
		testFinishedTaskList = new ArrayList<Task>();

		testUnfinishedTaskList.add(deadlineTask);
		testUnfinishedTaskList.add(floatingTask1);
		// Tasklist:
		// Finish report
		// watch LOTR
		
		testFinishedTaskList.add(floatingTask2);

	}

	@After
	public void deleteTestFiles() {
		boolean success = new File("test.json").delete();
		System.out.println("File deleted = " + success);
	}

	@AfterClass
	public static void restorePath() {
		testStorage.get(currentPath);
	}

	@Test
	public void addTest() throws Exception {
		String feedback = logic.parseAndExecuteCommand("add attend meeting from 16 Dec 4pm to 6pm");
		assertEquals("Check that feedback for adding a task is correct", "Task \"attend meeting\" added", feedback);
		UIState actualState = logic.getState();
		UIState testState = new UIState();
		testState.idNewTask = 1; // Newly added Task should have a TaskId of 2
									// (index of 1 in List)
		assertEquals("Check that UIState is correct", testState, actualState);

		DateTime dt1 = new DateTime(2015, 12, 16, 16, 0, 0, 0);
		DateTime dt2 = new DateTime(2015, 12, 16, 18, 0, 0, 0);

		Task newlyAddedTask = new EventTask("attend meeting", dt1.toDate(), dt2.toDate());

		testUnfinishedTaskList.add(1, newlyAddedTask);

		assertEquals("Check that task list is correct", testUnfinishedTaskList, logic.getTasks());

	}

	@Test
	public void addExceptionTest() {
		try {
			logic.parseAndExecuteCommand("add Watch LOTR");
		} catch (Exception e) {
			assertEquals("Check that exception message is correct for repeated task", "Duplicate Task!",
					e.getMessage());
		}

		try {
			logic.parseAndExecuteCommand("   ");
		} catch (Exception e) {
			assertEquals("Check that exception message is correct for blank inputs task",
					"add command requires arguments. Type 'help add' for more details.", e.getMessage());
		}
	}

	@Test
	public void deleteTest() throws Exception {
		String feedback = logic.parseAndExecuteCommand("del 2");
		
		assertEquals("Check that feedback is correct", "Task \"Watch LOTR\" deleted", feedback);
		
		assertEquals("Check that UIState is correct", new UIState(), logic.getState());
		assertEquals("Check that feedback is correct", "Task \"Watch LOTR\" deleted", feedback);
		
		Task deletedTask = new FloatingTask("Watch LOTR");
		testUnfinishedTaskList.remove(deletedTask);
		assertEquals("Check that unfinished Task list is correct", testUnfinishedTaskList, logic.getTasks());

		
		logic.parseAndExecuteCommand("view deleted");
		List<Task> deletedTaskList = new ArrayList<Task>();
		deletedTask.setDeleted();
		deletedTaskList.add(deletedTask);
		assertEquals("Check that deleted Task list is correct", deletedTaskList, logic.getTasks());
	}
	
	@Test
	public void finishTest() throws Exception {
		String feedback = logic.parseAndExecuteCommand("fin 2");
		
		assertEquals("Check that UIState is correct", new UIState(), logic.getState());
		assertEquals("Check that feedback is correct", "Marked Task \"Watch LOTR\" as done", feedback);
		
		Task deletedTask = new FloatingTask("Watch LOTR");
		testUnfinishedTaskList.remove(deletedTask);
		assertEquals("Check that unfinished Task list is correct", testUnfinishedTaskList, logic.getTasks());

		
		logic.parseAndExecuteCommand("view finished");
		assertEquals("Check that finished Task list has 2 Tasks", 2, logic.getTasks().size());
	}
	
}
