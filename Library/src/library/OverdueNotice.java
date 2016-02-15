package library;

public class OverdueNotice {
	
	Patron patron;
	private int date;
	

	public OverdueNotice(Patron patron, int todaysDate) {
		this.patron = patron;
		this.date = todaysDate; 
		
	}

	public String toString() {
		String bookList = new String("You have checked out: " + "\n");
		String overdueBooks = new String("Your overdue books are: " + "\n");
		String openingGreeting = new String("Hello " + this.patron + ", " + "\n");
		for (Book book: patron.getBooks()) { 
			bookList += book.getTitle() + " due by: " + "day " + book.getDueDate() + "\n";	
		}
		for (Book book: patron.getBooks()) {
			if (book.getDueDate() < this.date) overdueBooks += book.getTitle() + "\n";
		}
		
		return openingGreeting + bookList + overdueBooks;
	}
}
