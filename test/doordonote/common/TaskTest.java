//@@author A0131716M
package doordonote.common;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author A0131716M
 *
 */
public class TaskTest {

	
	@Test
	public void testSameFloatingTask() {
		Task task1 = new FloatingTask("test");
		Task task2 = new FloatingTask("test");
		assertTrue(task1.hashCode() == task2.hashCode());
		assertTrue(task1.equals(task2));
	}
	
	
	
	@Test
	public void testSameDeadlineTask() {
		Date date1 = new Date(2015, 1, 1, 1, 1);
		Date date2 = new Date(2015, 1, 1, 1, 1);
		Task task1 = new DeadlineTask("test", date1);
		Task task2 = new DeadlineTask("test", date2);
		assertTrue(task1.hashCode() == task2.hashCode());
		assertTrue(task1.equals(task2));
	}
	
	
	@Test
	public void testSameEventTask() {
		Date date1 = new Date(2015, 1, 1, 1, 1);
		Date date2 = new Date(2015, 10, 3);
		Task task1 = new EventTask("test", date1, date2);
		Task task2 = new EventTask("test", date1, date2);
		assertTrue(task1.hashCode() == task2.hashCode());
		assertTrue(task1.equals(task2));
	}
	
	
	
	@Test
	public void testDifferentEventTask() {
		Date date1 = new Date(2015, 1, 1, 1, 1);
		Date date2 = new Date(2015, 10, 3);
		Date date3 = new Date(2015, 1, 1, 1, 2);
		Task task1 = new EventTask("test", date1, date2);
		Task task2 = new EventTask("test", date1, date3);
		assertFalse(task1.hashCode() == task2.hashCode());
		assertFalse(task1.equals(task2));
	}
	
	@Test
	public void testDifferentTask() {
		Date date1 = new Date(2015, 1, 1, 1, 1);
		Date date3 = new Date(2015, 1, 1, 1, 2);
		Task task1 = new DeadlineTask("test", date1);
		Task task2 = new EventTask("test", date1, date3);
		assertFalse(task1.hashCode() == task2.hashCode());
		assertFalse(task1.equals(task2));
	}
	
	@Test
	public void testDateSorting(){
		Date date1 = new Date(2015, 1, 1, 1, 1);
		Date date2 = new Date(1999, 3, 3, 3, 3);
		Date date3 = new Date(2015, 1, 1, 1, 2);
		Date date4 = new Date(2015, 1, 1, 3, 3);
		Date date5 = new Date(2016, 5, 5, 5, 5);
		Task task1 = new DeadlineTask("test", date1);
		Task task2 = new EventTask("swim", date1, date3);
		Task task3 = new DeadlineTask("run", date2);
		Task task4 = new FloatingTask("float");
		Task task5 = new FloatingTask("soccer");
		Task task6 = new EventTask("Exams", date4, date5);
		List<Task> expected = new ArrayList<Task>();
		expected.add(task3);
		expected.add(task1);
		expected.add(task2);
		expected.add(task6);
		expected.add(task4);
		expected.add(task5);
		List<Task> arrlist = new ArrayList<Task>();
		arrlist.add(task4);
		arrlist.add(task5);
		arrlist.add(task1);
		arrlist.add(task2);
		arrlist.add(task3);
		arrlist.add(task6);
		assertFalse(expected.equals(arrlist));
		Collections.sort(arrlist);
		assertEquals(expected, arrlist);
	}
	

}
