//@@author A0132785Y
package doordonote.ui;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.FloatingTask;
import doordonote.common.Task;
import doordonote.ui.DateUtil;
import doordonote.ui.UI;

import org.junit.Before;
import org.junit.Test;

public class UITest {
	
	/** initialising a UI object for setting count value */
	private UI ui = new UI();
	
	/** SimpleDateFormat objects for parsing String objects of following formats into Dates */
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private SimpleDateFormat formatter2 = new SimpleDateFormat("MM/dd/yyyy");

	/** String objects for dates to be parsed into Date Objects */
	private static final String dateInString1 = "31/10/2015 04:30";
	private static final String dateInString2 = "02/03/2016 12:05";
	private static final String dateInString3 = "13/12/2015 00:00";
	private static final String dateInString4 = "19/06/2016 23:45";
	private static final String dateInString5 = "31/10/2015 06:30";
    
	/** Date objects to be initialised after parsing of date strings */
	private Date dt1;
	private Date dt2;
	private Date dt3;
	private Date dt4;
	private Date dt5;
	
	@Before
	public void setup() throws ParseException{
		dt1 = formatter.parse(dateInString1);
		dt2 = formatter.parse(dateInString2);
		dt3 = formatter.parse(dateInString3);
		dt4 = formatter.parse(dateInString4);
		dt5 = formatter.parse(dateInString5);
	}

	/**
	 * Runs the String getDay(Calendar) method test for DateUtil class
	 * 
	 */
	@Test
	public void testGetDay() {
		
			Calendar cl1 = DateUtil.dateToCalendar(dt1);
			Calendar cl2 = DateUtil.dateToCalendar(dt2);

			String day1 = DateUtil.getDay(cl1);
			String day2 = DateUtil.getDay(cl2);

			assertEquals("Saturday", day1);
			assertEquals("Wednesday", day2);
	}

	/**
	 * Runs the String getMonth(Calendar) method test for DateUtil class
	 * 
	 */
	@Test
	public void testGetMonth() {

			Calendar cl1 = DateUtil.dateToCalendar(dt1);
			Calendar cl3 = DateUtil.dateToCalendar(dt3);

			String month1 = DateUtil.getMonth(cl1);
			String month3 = DateUtil.getMonth(cl3);

			assertEquals("Oct", month1);
			assertEquals("Dec", month3);

	}
    
	/**
	 * Runs the String getMinutes(Calendar) method test for DateUtil class
	 * 
	 */
	@Test
	public void testGetMinutes() {

			Calendar cl1 = DateUtil.dateToCalendar(dt1);
			Calendar cl3 = DateUtil.dateToCalendar(dt3);

			String minutes1 = DateUtil.getMinutes(cl1);
			String minutes3 = DateUtil.getMinutes(cl3);

			assertEquals("30", minutes1);
			assertEquals(null, minutes3);

	}

	/**
	 * Runs the String getTime(Calendar) method test for DateUtil class
	 * 
	 */
	@Test
	public void testGetTime() {

			Calendar cl1 = DateUtil.dateToCalendar(dt1);
			Calendar cl2 = DateUtil.dateToCalendar(dt2);
			Calendar cl3 = DateUtil.dateToCalendar(dt3);
			Calendar cl4 = DateUtil.dateToCalendar(dt4);

			String time1 = DateUtil.getTime(cl1);
			String time2 = DateUtil.getTime(cl2);
			String time3 = DateUtil.getTime(cl3);
			String time4 = DateUtil.getTime(cl4);

			assertEquals("4:30am", time1);
			assertEquals("12:05pm", time2);
			assertEquals("12am", time3);
			assertEquals("11:45pm", time4);

	}

	/**
	 * Runs the boolean checkForOverdue(Date) method test for DateUtil class
	 * 
	 */
	@Test
	public void testCheckForOverdue() {

			boolean overdue1 = DateUtil.checkForOverdue(dt1);
			boolean overdue2 = DateUtil.checkForOverdue(dt2);
			boolean overdue3 = DateUtil.checkForOverdue(dt3);
			boolean overdue4 = DateUtil.checkForOverdue(dt4);

			assertEquals(overdue1, true);
			assertEquals(overdue2, false);
			assertEquals(overdue3, false);
			assertEquals(overdue4, false);
	}
	
	/**
	 * Runs the boolean checkForOngoing(Date, Date) method test for DateUtil class
	 * 
	 */
	@Test
	public void testCheckForOngoing() {

			boolean ongoing1 = DateUtil.checkForOngoing(dt1, dt3);
			boolean ongoing2 = DateUtil.checkForOngoing(dt2, dt4);
			boolean ongoing3 = DateUtil.checkForOngoing(dt1, dt2);

			assertEquals(ongoing1, true);
			assertEquals(ongoing2, false);
			assertEquals(ongoing3, true);
	}
    
	/**
	 * Runs the String getMultipleDayEventString(Task) method test for UI class
	 * 
	 */
	@Test
	public void testGetMultipleDayEventString() {

			Task task1 = new EventTask("Flying Pig", dt1, dt3);
			Task task2 = new EventTask("Finish Project", dt3, dt2);

			String eventString1 = UI.getMultipleDayEventString(task1);
			String eventString2 = UI.getMultipleDayEventString(task2);

			assertEquals("1. [Saturday, 31 Oct, 4:30am - Sunday, 13 Dec, 12am] Flying Pig", eventString1);
			assertEquals("2. [Sunday, 13 Dec, 12am - Wednesday, 2 Mar, 12:05pm] Finish Project", eventString2);

	}
    
	/**
	 * Runs the boolean checkForEventsSpanningDays(List<Task>, SimpleDateFormat) 
	 * method test for UI class
	 * 
	 */
	@Test
	public void testCheckForEventsSpanningDays() {

			Task task1 = new EventTask("Flying Pig", dt1, dt3);
			Task task2 = new FloatingTask("Do CS homework");
			Task task3 = new EventTask("Swimming", dt1, dt5);
			Task task4 = new DeadlineTask("Running", dt4);

			ArrayList<Task> list1 = new ArrayList<Task>();
			ArrayList<Task> list2 = new ArrayList<Task>();

			list1.add(task1);
			list1.add(task2);
			list1.add(task3);
			list1.add(task4);

			list2.add(task2);
			list2.add(task3);
			list2.add(task4);

			boolean check1 = UI.checkForEventsSpanningDays(list1, formatter2);
			boolean check2 = UI.checkForEventsSpanningDays(list2, formatter2);

			assertEquals(check1, true);
			assertEquals(check2, false);
	}
    
	/**
	 * Runs the String getDateString(Task) method test for UI class
	 * 
	 */
	@Test
	public void testGetDateString() {

			Task task1 = new EventTask("Swimming", dt1, dt5);
			Task task2 = new DeadlineTask("Running", dt4);

			String date1 = UI.getDateToBeDisplayedString(task1);
			String date2 = UI.getDateToBeDisplayedString(task2);

			assertEquals(date1, "Saturday, 31 Oct");
			assertEquals(date2, "Sunday, 19 Jun");
	}

	/**
	 * Runs the String getDeadlineString(Task) method test for UI class
	 * 
	 */
	@Test
	public void testGetDeadlineString() {

			Task task1 = new DeadlineTask("Swimming", dt1);
			Task task2 = new DeadlineTask("Running", dt4);

			String deadlineTask1 = UI.getDeadlineString(task1);
			String deadlineTask2 = UI.getDeadlineString(task2);

			assertEquals(deadlineTask1, "1. [by 4:30am] Swimming");
			assertEquals(deadlineTask2, "2. [by 11:45pm] Running");

	}
    
	/**
	 * Runs the String getSingleDayEventString(Task) method test for UI class
	 * 
	 */
	@Test
	public void testGetSingleDayEventString() {

			Task task = new EventTask("Swimming", dt1, dt5);

			String eventTask = UI.getSingleDayEventString(task);

			assertEquals(eventTask, "1. [4:30am-6:30am] Swimming");

	}
    
	/**
	 * Runs the boolean checkForSameDay(Task, Task) method test for DateUtil class
	 * 
	 */
	@Test
	public void testCheckForSameDay() {

			Task task1 = new EventTask("Swimming", dt1, dt5);
			Task task2 = new DeadlineTask("Running", dt5);
			Task task3 = new DeadlineTask("assignment", dt1);
			Task task4 = new DeadlineTask("HW", dt2);
			Task task5 = new EventTask("Ball", dt4, dt4);

			boolean check1 = DateUtil.checkForSameDay(task1, task2);
			boolean check2 = DateUtil.checkForSameDay(task2, task3);
			boolean check3 = DateUtil.checkForSameDay(task1, task3);
			boolean check4 = DateUtil.checkForSameDay(task1, task5);
			boolean check5 = DateUtil.checkForSameDay(task1, task4);
			boolean check6 = DateUtil.checkForSameDay(task3, task4);
			boolean check7 = DateUtil.checkForSameDay(task2, task5);

			assertEquals(check1, false);
			assertEquals(check2, false);
			assertEquals(check3, false);
			assertEquals(check4, true);
			assertEquals(check5, true);
			assertEquals(check6, true);
			assertEquals(check7, true);

	}

	/**
	 * Runs the boolean checkIfMultipleDayEvent(Task, SimpleDateFormat) method 
	 * test for DateUtil class
	 * 
	 */
	@Test
	public void testCheckIfMultipleDayEvent() {

			Task task1 = new EventTask("Flying Pig", dt1, dt3);
			Task task2 = new EventTask("Swimming", dt1, dt5);

			boolean check1 = DateUtil.checkIfMultipleDayEvent(task1, formatter2);
			boolean check2 = DateUtil.checkIfMultipleDayEvent(task2, formatter2);

			assertEquals(check1, true);
			assertEquals(check2, false);
	}
}
