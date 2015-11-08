//@@author A0131436N

package doordonote.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import doordonote.logic.UIState.ListType;
import doordonote.storage.DuplicateTaskException;
import doordonote.storage.Storage;

public class ControllerTest {
	private Controller controller = null;
	private Storage storage = null;
	
	@Before
	public void setUp() throws Exception {
		
		// Adds 3 tasks to StorageStub obj
		String description1 = "Task 1";
		DateTime startDt1 = new DateTime(2015, 11, 30, 8, 0, 0, 0);
		DateTime endDt1 = new DateTime(2015, 11, 30, 8, 0, 0, 0);

		String description2 = "Task 2";
		DateTime endDt2 = new DateTime(2015, 11, 30, 8, 0, 0, 0);
		
		String description3 = "Task 3";
		
		storage = new StorageStub();
		storage.add(description1, startDt1.toDate(), endDt1.toDate());
		storage.add(description2, null, endDt2.toDate());
		storage.add(description3, null, null);
		
		controller = new Controller(storage);
	}
	
	@Test
	public void addTest() throws IOException, DuplicateTaskException {
		String description = "task to be added";
		// Creates DateTime object with information like year, month,
        // day, hour, minute, second and milliseconds
		DateTime startDt = new DateTime(2015, 11, 30, 8, 0, 0, 0);
		DateTime endDt = new DateTime(2015, 11, 30, 8, 0, 0, 0);
		String feedBack = null;

		controller.stateObj.displayType = ListType.DELETED;
		UIState testStateObj1 = new UIState();
		testStateObj1.idNewTask = 3;
		
		feedBack = controller.add(description, 
								  startDt.toDate(), 
								  endDt.toDate());

		
		// Testing if add is called and if ListType is set to Normal
		assertEquals("Test if storage.add() is called", "Add is called", feedBack);
		assertEquals("Test if UISTATE is updated correctly", testStateObj1, controller.getState());
		
		UIState testStateObj2 = new UIState();
		List<String> filterList = new ArrayList<String>();
		filterList.add("3");
		testStateObj2.filterList = filterList;
		testStateObj2.idNewTask = 1;
		controller.stateObj.filterList = filterList;
		controller.add("test task 3", null, null);
		
		
		// Testing if add maintains the same filterList if task added
		// can be seen from current list
		assertEquals("Test if UISTATE maintains the filter criteria", testStateObj2, controller.getState());
	}
	
	@Test
	public void deleteTest() throws Exception {
		String feedBack1 = controller.delete(1);
		UIState testStateObj1 = new UIState();
		assertEquals("Test if storage.delete() is called", "Delete is called", feedBack1);
		assertEquals("Test if UISTATE is updated correctly", testStateObj1, controller.getState());

		controller.stateObj.displayType = ListType.DELETED;
		String feedBack2 = controller.delete(1);
		UIState testStateObj2 = new UIState();
		testStateObj2.displayType = ListType.DELETED;
		assertEquals("Test if storage.remove() is called", "Remove is called", feedBack2);
		assertEquals("Test if UISTATE is updated correctly", testStateObj2, controller.getState());
		
		
	}
	
	@Test
	public void displayDeletedTest() throws IOException {
		UIState testStateObj = new UIState();
		testStateObj.displayType = ListType.DELETED;
		controller.viewDeleted();
		assertEquals("Test if UIState is updated correctly", 
			 	 	 testStateObj, 
			 	 	 controller.getState());
	}
	
	@Test
	public void displayFinishedTest() throws IOException {
		UIState testStateObj = new UIState();
		testStateObj.displayType = ListType.FINISHED;
		controller.viewFinished();
		assertEquals("Test if UIState is updated correctly", 
			 	 	 testStateObj, 
			 	 	 controller.getState());
	}
	
	@Test
	public void findDateTest() throws IOException {
		DateTime startDt = new DateTime(2015, 9, 30, 0, 0, 0, 0);
		Date startDate = startDt.toDate();
		controller.find(startDate);
		UIState testStateObj = new UIState();
		testStateObj.filterDate = startDate;
		
		
		assertEquals("Test if UIState is updated correctly", 
				 	 testStateObj, 
				 	 controller.getState());
	}
	
	@Test
	public void finishTest() throws Exception {
		String feedBack1 = controller.finish(1);
		UIState testStateObj1 = new UIState();
		
		assertEquals("Test if storage.finish() is called", "Finish is called", feedBack1);
		assertEquals("Test if UISTATE is updated correctly", testStateObj1, controller.getState());
		
		controller.stateObj.displayType = ListType.FINISHED;
		try {
			controller.finish(1);
			fail("Exception should have been thrown");
		} catch (Exception e) {
			assertEquals("Test if exception displays the right msg", 
						 "Task is already finished!", 
						 e.getMessage());
		}

	}
	
	@Test
	public void helpTest() {
		controller.help();
		
		controller.help("test");
		controller.help("add");
	}
	
	@Test
	public void homeTest() throws IOException {
		controller.viewDeleted(); // Change the display type
		String homeMsg = controller.home();
		UIState testStateObj = new UIState();
		
		assertEquals("Test if home() return msg is correct", 
					 "Displaying all unfinished task(s)", 
					 homeMsg);
		assertEquals("Test if UIState is updated correctly", 
					 testStateObj, 
					 controller.getState());
	}
	
//	@Test
//	public void redoTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void restoreTest() {
//		fail("Not yet implemented");
//	}
//
//	
//	@Test
//	public void undoTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void updateTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void pathTest() {
//		fail("Not yet implemented");
//	}
//	
//	@Test
//	public void getTest() {
//		fail("Not yet implemented");
//	}

}
