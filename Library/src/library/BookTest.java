package library;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BookTest {
	Book arvind;
	Book dickens;
	Book empty;

	@Before
	public void setUp() throws Exception {
		arvind = new Book("CIT 590", "Arvind");
		
		dickens = new Book("A Tale of Two Cities", "Charles Dickens");
		dickens.due_date = 5;
		
		empty = new Book("","");
	}

	@Test
	public void testBook() {
		
		assertTrue(arvind instanceof Book);
		
		assertEquals(dickens.title, "A Tale of Two Cities");
		assertEquals(dickens.author, "Charles Dickens");
		assertEquals(dickens.due_date, 5);
		
		assertEquals(arvind.due_date, -1);
		assertEquals(empty.title, "");
		assertEquals(empty.author, "");
		assertEquals(empty.due_date, -1);
	}

	@Test
	public void testGetTitle() {
		assertEquals(dickens.getTitle(), "A Tale of Two Cities");
		assertEquals(empty.getTitle(), "");
	}

	@Test
	public void testGetAuthor() {
		assertEquals(dickens.getAuthor(), "Charles Dickens");
		assertEquals(empty.getAuthor(), "");
	}

	@Test
	public void testGetDueDate() {
		assertEquals(arvind.getDueDate(), -1);
		assertEquals(dickens.getDueDate(), 5);
	}

	@Test
	public void testCheckOut() {
		arvind.checkOut(77);	//tests what happens when you check out a book not yet checked out
		dickens.checkOut(3);	//tests what happens when you check out an already-checked out book
	
		assertEquals(arvind.getDueDate(), 77);
		assertEquals(dickens.getDueDate(), 3);
	}

	@Test
	public void testCheckIn() {
		arvind.checkIn();	//arvind was already checked in
		dickens.checkIn();	//dickens was previously checked out
		
		assertEquals(arvind.getDueDate(), -1);
		assertEquals(dickens.getDueDate(), -1);
	}

	@Test
	public void testToString() {
		assertEquals(dickens.toString(), "A Tale of Two Cities, by Charles Dickens");
		assertEquals(arvind.toString(), "CIT 590, by Arvind");
		assertEquals(empty.toString(), ", by ");
	}

}
