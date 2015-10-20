package doordonote.storage;

import java.io.IOException;
import java.util.*;

import doordonote.common.DeadlineTask;
import doordonote.common.EventTask;
import doordonote.common.Task;

public class main {
	
	
	public static void main(String[] args){
		TaskFileIO str = TaskFileIO.getInstance();
		Date date0 = new Date(2015, 10, 6, 13,27);
		Date date1 = new Date(1999, 3, 3);
		Date date2 = new Date(2013, 10, 3);
		Task task = new DeadlineTask("appendix", date0);
		Task task1 = new DeadlineTask("fishing", date0);
		Task task2 = new DeadlineTask("Do CS homework", date2);
		Task task3 = new EventTask("Swimming", date1, date2);
		Task task4 = new EventTask("Eating", date1, date2);
		
	//	String str1 = str.add(task);
	//	str.add(task2);
	//	str.add(task3);
	//	str.delete(task3);
		str.update(task, task4);
	//	str.add("fishing", date0, date2);
	//	str.add("running", date0, date2);
	//	str.add("hello", date1, date2);
//		String str2 = str.update(0, "Do CS homework", date1, date0);
	//	str.delete(0);
	//	System.out.println(str1);

	}
}
