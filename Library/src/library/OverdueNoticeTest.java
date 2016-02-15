package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OverdueNoticeTest {
	
	Patron testPatron;
	OverdueNotice testNotice;
	Book testBook1;
	Book testBook2;

	@Before
	public void setUp() throws Exception {
		testPatron = new Patron("Jon", null);
		testNotice = new OverdueNotice(testPatron, 8);
		testBook1 = new Book("CIT 590", "arvind");
		testBook2 = new Book("Contact", "Carl Sagan");
		testPatron.take(testBook1);
		testPatron.take(testBook2);
	}
	@Test
	public void testOverdueNotice() {
		assertTrue(testNotice instanceof OverdueNotice);
	}
	
	@Test
	public void testToString() {
		assertEquals("Hello Jon, " +"\n" + "You have checked out: " + "\n" + "CIT 590 due by: day -1" +	"\n" + "Contact due by: day -1" + "\n"	+ "Your overdue books are: " + "\n"	+ "CIT 590" + "\n" + "Contact\n", testNotice.toString());
	}

}
