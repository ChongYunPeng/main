package doordonote.commandfactory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;


public class DateParserTest {
	
	private DateParser parser = new DateParser();

	
	@Test
	public void parseAndGetDateListTest() {
		DateTime dt1 = new DateTime(2013, 1, 15, 8, 0, 0, 0);
		DateTime dt2 = new DateTime(2015, 3, 30, 7, 45, 0, 0);
	    Date jdkDate1 = dt1.toDate();
	    Date jdkDate2 = dt2.toDate();
	    List<Date> createdDateList = new ArrayList<Date>();
	    createdDateList.add(jdkDate1);
	    createdDateList.add(jdkDate2);
	    List<Date> parsedDateList = parser.parseAndGetDateList("from 15 jan 2013 to 30 march  7.45");
        
	    // Comparing with dt1 checks that parser knows the default time is 8am
	    // Comparing with dt2 checks that the parser knows that this year is 2015
	    assertEquals("Parser should get list of 2 dates", createdDateList, parsedDateList);
	    
	    createdDateList.remove(1);
	    //check that parser only parse one date when no connecting words like "to" is used
	    assertEquals("Parser should get only one valid date without 'to'", 
	    			 createdDateList, 
	    			 parser.parseAndGetDateList("from 15 jan 2013 30 march  7.45"));
	    assertEquals("Parser should return null if no dates are found", 
	    			 null, 
	    			 parser.parseAndGetDateList("do research on database"));


	}
	
	@Test
	public void parseTest() {
		// Creates DateTime object with information like year, month,
        // day, hour, minute, second and milliseconds
		DateTime dt1 = new DateTime(2015, 11, 24, 8, 0, 0, 0);
	    Date jdkDate1 = dt1.toDate();
	    
		DateTime dt2 = new DateTime(2015, 4, 8, 12, 0, 0, 0);
	    Date jdkDate2 = dt2.toDate();
        assertEquals("Parser should return default time at 8am", jdkDate1, parser.parse("nov 24"));
        assertEquals("Parser should be able to deal with leading/ trailing space", 
        			 jdkDate2, 
        			 parser.parse("april 8   12pm"));
        assertEquals("Parser should be able to take empty strings", null, parser.parse(""));
	}

}
