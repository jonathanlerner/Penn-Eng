package library;

import java.util.ArrayList;

public class Patron {
	String name;
	Library library;
	ArrayList<Book> checked_books;

	/**
	 * constructor
	 * @param name
	 * @param library
	 * @param  
	 */
	public Patron(String name, Library library) {
		this.name = name;
		this.library = library;
		this.checked_books = new ArrayList<Book>();	//list of books this Patron has checked out
		
	}

	/**
	 * getter for the Patron's name
	 * @return
	 */
	String getName() {
		return this.name;
	}
	
	/**
	 * Adds this book to the list of books check out by this Patron
	 * @param book
	 */
	void take(Book book) {
		this.checked_books.add(book);	
	}
	
	/**
	 * Removes this Book object from the list of books checked out by this Patron
	 * @param book
	 */
	void giveBack(Book book) {
		this.checked_books.remove(this.checked_books.indexOf(book));
	}
	
	/**
	 * Returns the list of Book objects checked out to this Patron (may be an empty list)
	 * @return
	 */
	ArrayList<Book> getBooks() {
		return this.checked_books;
	}
	
	/**
	 * Returns this patron's name
	 */
	public String toString() {
		return this.name;
	}
}
