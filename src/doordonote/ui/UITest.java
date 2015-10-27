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
	String dateInString = "07/06 04:30";
	//Date dt = formatter.parse(dateInString);
	Calendar cl = Calendar.getInstance();
	//cl.setTime(dt);
	
	@Test
	public void testGetDay() {
		String day = ui.getDay(cl);
		assertEquals("Wed", day);	
	}
	
	@Test
	public void testGetMonth() {
		String month = ui.getMonth(cl);
		assertEquals("Oct", month);
	}
	
	@Test
	public void testGetMinutes() {
		String minutes = ui.getMinutes(cl);
		assertEquals("01", minutes);
	}
	
	@Test
	public void testGetTime() {
		String time = ui.getTime(cl);
		assertEquals("3:01am", time);
	}

}
