package library;

public class Book {
	String title;
	String author;
	int due_date;
	
	/**
	 * the constructor. instance takes in a title, one author, and creates an integer corresponding to the due date
	 * @param title
	 * @param author
	 */
	public Book(String title, String author) {
		this.title = title;
		this.author = author;
		this.due_date = -1;		//due date initializes as -1; -1 denotes a book is not checked out
	}

	/**
	 * getter returning the title of the book (a string)
	 * @return
	 */
	String getTitle() {
		return this.title;
	}
	
	/**
	 * getter returning the author of the book (a string)
	 * @return
	 */
	String getAuthor() {
		return this.author;
	}
	
	/**
	 * getter returning the due date of a book (an int)
	 * @return
	 */
	int getDueDate() {
		return this.due_date;
	}
	
	/**
	 * Sets the due date of this Book to the specified date
	 * @param date
	 */
	void checkOut(int date) {
		this.due_date = date;
	}
	
	/**
	 * Sets the due date of the Book to -1 (ie when it is returned)
	 */
	void checkIn() {
		this.due_date = -1;
	}
	
	/**
	 * returns a string representation of the book in the form "'title', by 'author'"
	 */
	public String toString() {
		return this.title + ", by " + author;
	}
}
