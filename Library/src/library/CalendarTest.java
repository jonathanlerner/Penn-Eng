/**
 * 
 */
package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Matuszek
 */
@SuppressWarnings("unused")
public class CalendarTest {
    Calendar cal;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        cal = new Calendar();
    }

    /**
     * Test method for {@link library.Calendar#getDate()}
     * and {@link library.Calendar#advance()}.
     */
    @Test
    public void testCalendar() {
        assertEquals(0, cal.getDate());	//implicitly also tests the constructor
        cal.advance();
        assertEquals(1, cal.getDate());
        cal.advance();
        assertEquals(2, cal.getDate());
    }
    
   
}
