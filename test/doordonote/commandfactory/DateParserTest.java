package doordonote.commandfactory;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;


public class DateParserTest {
	
	private DateParser parser = new DateParser();

	@Test
	public void test() {
		// Creates DateTime object with information like year, month,
        // day, hour, minute, second and milliseconds
		DateTime dt1 = new DateTime(2015, 11, 24, 8, 0, 0, 0);
	    Date jdkDate1 = dt1.toDate();
	    
		DateTime dt2 = new DateTime(2015, 4, 8, 12, 0, 0, 0);
	    Date jdkDate2 = dt2.toDate();
        assertEquals("Parser should return default time at 8am", jdkDate1, parser.parse("nov 24"));
        assertEquals("Parser should be able to deal with leading/ trailing space", 
        			 jdkDate2, 
        			 parser.parse("april 8 12pm"));
        assertEquals("Parser should be able to take empty strings", null, parser.parse(""));
	}

}
