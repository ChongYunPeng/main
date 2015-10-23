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
	dateInString = "07/06 04:30";
	Date date = formatter.parse(dateInString);
	Calendar cal = Calendar.getInstance();
	cal.setTime(date);
	
	@Test
	public void testGetDay() {
		
	}

}
