package doordonote.ui;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.*;


import org.junit.Test;

public class UITest {
    
	UI ui = new UI();
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM hh:mm"); 
	String dateInString1 = "31/10 04:30";
	String dateInString2 = "02/03 12:05";
	String dateInString3 = "13/12 00:00";
	String sample = "Invalid command format";
	Date dt1 = new Date();
	Date dt2 = new Date();
	Date dt3 = new Date();

	//cl.setTime(dt);
	
	@Test
	public void testGetDay() {
		try {
			dt1 = formatter.parse(dateInString1);
			dt2 = formatter.parse(dateInString2);
			Calendar cl1 = ui.DateToCalendar(dt1);
			Calendar cl2 = ui.DateToCalendar(dt2);
			
			String day1 = ui.getDay(cl1);
			String day2 = ui.getDay(cl2);
			
			assertEquals("Sat", day1);
			assertEquals("Mon", day2);
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	public void testGetMonth() {
		try {
			dt1 = formatter.parse(dateInString1);
			dt3 = formatter.parse(dateInString3);
			Calendar cl1 = ui.DateToCalendar(dt1);
			Calendar cl3 = ui.DateToCalendar(dt3);
			
			String month1 = ui.getMonth(cl1);
			String month3 = ui.getMonth(cl3);
			
			assertEquals("Oct", month1);
			assertEquals("Dec", month3);
	
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}


	}
	
	@Test
	public void testGetMinutes() {
		try {
			dt1 = formatter.parse(dateInString1);
			dt3 = formatter.parse(dateInString3);
			Calendar cl1 = ui.DateToCalendar(dt1);
			Calendar cl3 = ui.DateToCalendar(dt3);
			
			String minutes1 = ui.getMinutes(cl1);
			String minutes3 = ui.getMinutes(cl3);
			
			assertEquals("30", minutes1);
			assertEquals(null, minutes3);
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}

	}
	
	@Test
	public void testGetTime() {
		try {
			dt1 = formatter.parse(dateInString1);
			dt3 = formatter.parse(dateInString3);
			Calendar cl1 = ui.DateToCalendar(dt1);
			Calendar cl3 = ui.DateToCalendar(dt3);
			
			String time1 = ui.getTime(cl1);
			String time3 = ui.getTime(cl3);
			
			assertEquals("4:30am", time1);
			assertEquals("12am", time3);
		}
		catch(ParseException e) {
			System.out.println(e.getMessage());
		}
	
	}
	
	@Test
	public void testGetFirstWord() {
		String firstWord = ui.getFirstWord(sample);
		assertEquals("invalid", firstWord);
		
	}

}
