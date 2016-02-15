//This is the submission of Jonathan Lerner and Zaks Lubin

package library;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

public class Library {

	//constructor variables
	private Boolean okToPrint;
	ArrayList<Book> collection;
	HashMap<String, Patron> library;
	Calendar lib_cal;
	boolean lib_open;	
	boolean quit;
	Patron currentPatron;
	HashMap<Integer, Book> patronBookList;
	HashMap<Integer, Book> search_results;
	ArrayList<Book> outstanding_overdue_books;
	Scanner scanner;


	
	public Library() {
		this.okToPrint = true;
		this.collection = readBookCollection();
		this.library = new HashMap<String, Patron>();
		this.lib_cal = new Calendar();
		this.lib_open = false;	//this represents whether the library is currently open. initializes to false
		this.quit = false;		//represents if the library has been permanently shut down
		
		this.patronBookList = new HashMap<Integer, Book>();
		this.search_results = new HashMap<Integer, Book>();
		this.outstanding_overdue_books = new ArrayList<Book>();
		this.currentPatron = new Patron("", this);
		
	}
	
	public Library(ArrayList<Book> collection) {
		this.okToPrint = false;
		this.collection = collection;
		this.library = new HashMap<String, Patron>();
		this.lib_cal = new Calendar();
		this.lib_open = false;
		this.quit = false;
		
		this.patronBookList = new HashMap<Integer, Book>();
		this.search_results = new HashMap<Integer, Book>();
		this.outstanding_overdue_books = new ArrayList<Book>();
	}

	//this reads books from a books.txt file into our library.
	private ArrayList<Book> readBookCollection() {
		File file = new File("books.txt");
		ArrayList<Book> collection = new ArrayList<Book>();
		try {
			FileReader fileReader = new FileReader(file);
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(fileReader);
			
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				line = line.trim();
				if (line.equals("")) continue; //ignore possible blank lines
				String[] bookInfo = line.split(" :: ");
				collection.add(new Book(bookInfo[0], bookInfo[1]));
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return collection;
	}
	
	public static void main(String[] args) {
		Library lib = new Library();
		lib.start();
	}
	/**
	 * This method reads commands from the user.
	 * open--open the library in the morning (advance the date)
	 * issueCard Patron's name--issue a library card
	 * serve Patron's name--prepare to check books in or out.  print a numbered list of books currently checked out to this Patron.
	 * checkIn Book numbers--Check in books (by number) held by the user.
	 * search search string--search for books, and print a numbered list of books that are found.
	 * checkOUt Book numbers--check out books (by number) found by search.
	 * close--Close the library in the evening.
	 * quit--exit this program. 
	 */
	void start() {
		this.scanner = new Scanner(System.in);
		ArrayList<String> cmd = new ArrayList<String>();
		println("Hello Librarian!");
		while(!this.quit) {
			println("The list of all commands is:");
			println(command_print());
			cmd = readInput();
			while (!this.commandList().contains(cmd.get(0))) {
				println("Please enter a valid command.\n");
				cmd = readInput();
			}
			String cmd_lead = cmd.get(0);
			if (cmd_lead.toString().equals("open") && (cmd.size()==1)) {
				if(this.lib_open) {
					println("The library is already open.");
				}
				else {
					this.open();
					println("The library is now open, the day is " + this.lib_cal.getDate() + ".");
				}
			}
			else if (cmd_lead.toString().equals("issueCard") && (cmd.size()==2)) {
				if (!this.lib_open){
					println("The library must be open");
				}
				else {
					this.issueCard(cmd.get(1));
					println("A library card was issued to " + cmd.get(1) + ".");
				}
			}
			else if (cmd_lead.toString().equals("serve") && (cmd.size()==2)) {
				if (!this.lib_open){
					println("The library must be open.");
				}
				else {
					this.serve(cmd.get(1));
					if (!(this.library.get(cmd.get(1)) instanceof Patron)) {
						println("You may serve " + cmd.get(1) + " when they are issued a card.");
					}
					else println("You are ready to serve" + " "+ cmd.get(1) + ".");
				}
			}
			else if (cmd_lead.toString().equals("checkIn") && (cmd.size()>=2)) {
				if (!this.lib_open){
					println("The library must be open");
				}
				else {
					if(this.currentPatron.getName().equals("")) {
						println("You must serve a patron first");
					}
					else {
						int [] checkedInBooks = new int [cmd.size() - 1];
						for(int i = 1; i < cmd.size(); i++)
							 checkedInBooks[i-1]=(Integer.parseInt(cmd.get(i)));
						ArrayList<Book> reportedBooks = this.checkIn(checkedInBooks);
						println("You have checked in:");
						for (Book b : reportedBooks) {
							println(b.toString());
						}
					}
				}
			}
			else if (cmd_lead.toString().equals("checkOut") && (cmd.size()>=2)) {
				if (!this.lib_open){
					println("The library must be open");
				}
				else {
					if(this.currentPatron.getName().equals("")) {
						println("You must serve a patron first");
					}
					else {
						int [] checkedOutBooks = new int [cmd.size() - 1];
						for(int i = 1; i < cmd.size(); i++) {
							 checkedOutBooks[i-1]=(Integer.parseInt(cmd.get(i)));
						}
						ArrayList<Book> reportedBooks = this.checkOut(checkedOutBooks);
						println("You have checked out:");
						for (Book b : reportedBooks) {
							println(b.toString());
						}
					}
				}
			}			
			else if (cmd_lead.toString().equals("search") && (cmd.size()>=2)) {
				if (!this.lib_open){
					println("The library must be open");
				}
				else {
					String searchTerm = new String();
					//put the search terms back together
					for (int i = 1; i < cmd.size() - 1; i++) { 
						searchTerm += cmd.get(i) + " ";
					}
					searchTerm += cmd.get(cmd.size() - 1);
					
					ArrayList<Book> results = search(searchTerm);
					if (results.size() == 0) {
						println("There are no search results.");
					}
					else {
						println("Here are your search results: ");
						for (int i = 0; i < results.size(); i++) {
							println(i+1 + " : " + results.get(i));
						}
					}
				}
			}
			else if (cmd_lead.toString().equals("close") && (cmd.size()==1)) {
				if(!this.lib_open) {
					println("The library is already closed.");
				}
				else {
					close();
					println("The library is now closed for the day.");
				}
			}
			else if (cmd_lead.toString().equals("quit") && (cmd.size()==1)) {
				quit();
			}
			else {
				//should never get here, but just in case
				println("Please enter a valid command.");
			}
			print("\n");
		}
		println("The mayor, citing a budget crisis, has stopped all funding for the library.");
		println("The library is permanently closed.");
		this.scanner.close();
	}
	
	/**
	 * Helper function, just returns a String representing the menu of commands for the program
	 * and what those commands do
	 * @return
	 */
	String command_print() {
		String s = "";
		s += "open                          --	Open the library in the morning\n";
		s += "issueCard [Patron's name]     --	Issue the Patron a libray card\n";
		s += "serve [Patron's name]         --	Prepare to check books in or out\n";
		s += "checkIn [Book numbers]        --	Check in books (by number) held by Patron\n";
		s += "search [String]               --	Search for books containing this String in title or author\n";
		s += "checkOut [Book numbers]       --	Check out books (by number) found by the search\n";
		s += "close                         --	Close the library at the end of the day\n";
		s += "quit                          --	Quit (Shut down the library)\n";
		
		return s;
	}
	
	ArrayList<String> commandList() {
		ArrayList<String> listCommands = new ArrayList<String>();
		listCommands.add("open");
		listCommands.add("issueCard");
		listCommands.add("serve");
		listCommands.add("checkIn");
		listCommands.add("search");
		listCommands.add("checkOut");
		listCommands.add("close");
		listCommands.add("quit");
		return listCommands;
	}
	
	private ArrayList<String> readInput() {
	    ArrayList<String> commandTokens = new ArrayList<String>();
		//Scanner scanner = new Scanner(System.in);
	    System.out.println("Command me please!");
	    String inputString = "";
	    //while(scanner.hasNextLine()){
	    inputString = scanner.nextLine();
	    	//scanner.close();
	    //}
	    for (int i = 0; i < inputString.split(" ").length; i++) {
    		commandTokens.add(inputString.split(" ")[i]);
	    }
	    if (commandTokens.size() == 0) {
	    	scanner.close();
	        throw new InputMismatchException("please enter a command");
	    } 
	            
	    //scanner.close();
	    return commandTokens;
	}
	
	// if okToPrint is true, prints the message using System.out.print(message), otherwise nothing. 
	void print(String message) {
		if (this.okToPrint) {
			System.out.print(message);
		}
	}
	
	//if okToPrint is true, prints the message using System.out.println(message), otherwise nothing.
	void println(String message) {
		if (this.okToPrint) {
			System.out.println(message);
		}
	}
	
	ArrayList<OverdueNotice> open() {
		this.lib_open = true;
		this.lib_cal.advance();
		ArrayList<OverdueNotice> overdues_today = createOverdueNotices();
		for ( OverdueNotice o : overdues_today) {
			println(o.toString());
			
		}
		return overdues_today;
	}
	
	ArrayList<OverdueNotice> createOverdueNotices() {
		ArrayList<OverdueNotice> overdues_today = new ArrayList<OverdueNotice>();
		Set<String> names = library.keySet();	//all the names of Patrons
		for (String name : names) {
			//for each Patron, look through the books he/she has checked out
			for (Book b : library.get(name).getBooks()) {
				//for each book he/she has checked out, check if it is overdue. if any are overdue, create an OverDueNotice for that Patron
				if (b.getDueDate() < this.lib_cal.getDate()) {
					OverdueNotice o = new OverdueNotice(library.get(name), lib_cal.getDate());
					if (!this.outstanding_overdue_books.contains(b)) {
						overdues_today.add(o);					
						outstanding_overdue_books.add(b);
					}
					break;
				}
			}
		}
		return overdues_today;
	}
	
	Patron issueCard(String nameOfPatron) {
		Patron newPatron = new Patron(nameOfPatron, this);
		if (this.library.containsKey(nameOfPatron)) {
			println("This patron is already in the system");
			return library.get(nameOfPatron); 
		}
		this.library.put(nameOfPatron, newPatron);
		return newPatron;
	}
	
	Patron serve(String nameOfPatron) {
		if (!(this.library.get(nameOfPatron) instanceof Patron)) {
			println("This person is not yet a library patron, please issue them a library card.");
			return this.currentPatron;
		}
		this.currentPatron = this.library.get(nameOfPatron);
		this.patronBookList.clear();
		for (int i = 1; i - 1 < this.currentPatron.getBooks().size(); i++) {
			this.patronBookList.put(i, this.currentPatron.getBooks().get(i - 1));
		}
		this.search_results.clear();
		
		if (this.patronBookList.size()==0) println("This Patron has no books checked out.");
		else {
			println("Here is a list of books checked out to " + currentPatron.getName() +": ");
			for (int i = 1; i - 1 < this.patronBookList.size(); i++){
				println(i + ": " + this.patronBookList.get(i).getTitle() + ", by " + this.patronBookList.get(i).getAuthor());
			}
		}
		return currentPatron;
	}
	
	/**
	 * checkIn returns the numbered books from a Patron and returns a list of the books returned
	 * Assumes that bookNumbers is a valid array consisting of some combo of the numbers 1, 2, 3
	 * @param bookNumbers
	 * @return
	 */
	ArrayList<Book> checkIn(int... bookNumbers) {
		ArrayList<Book> checkedInBooks = new ArrayList<Book>();
		for (int i : bookNumbers) {
			Book book1 = patronBookList.get(i);
			outstanding_overdue_books.remove(book1);
			this.currentPatron.giveBack(book1);
			
			book1.checkIn();
			this.patronBookList.remove(i);

			
			checkedInBooks.add(book1);
		}
		return checkedInBooks;
	}
	
	ArrayList<Book> search(String part) {
		//must search for a string at least 4 characters long
		
		ArrayList<Book> found_books = new ArrayList<Book>();
		
		if (part.length() < 4) {
			return found_books;
		}

		part = part.toLowerCase();	//convert our search string to lowercase for comparison purposes
		
		for (Book b : this.collection) {
			//first search the book's title
			String title = new String(b.getTitle());
			if (title.toLowerCase().contains(part)) {
				//we found a book whose title contains the string we're searching for  
				if (b.getDueDate() == -1) {
					//this book isn't checked out, so try to add to our list
					if (!(found_books.toString().contains(b.toString()))) {
						//this book isn't already in our list, so add it
						found_books.add(b);
					}
				}
			}
			//do the same for the book's author
			String author = new String(b.getAuthor());
			if (author.toLowerCase().contains(part)) {
				//we found a book whose author contains the string we're searching for
				if (b.getDueDate() == -1) {
					//book isn't checked out
					if (!(found_books.toString().contains(b.toString()))) {
						//this book isn't already in our list, so add it
						found_books.add(b);
					}
				}
			}
		}
		
		//create search_results HashMap
		for (int i = 1; i - 1 < found_books.size(); i++) {
			//System.out.println(i);
			//System.out.println(found_books);
			this.search_results.put(i, found_books.get(i - 1));
		}
		return found_books;
	}
	
	ArrayList<Book> checkOut(int... bookNumbers) {
		
		ArrayList<Book> checkedOutBooks = new ArrayList<Book>();
		
		for (int i : bookNumbers) {
			if (currentPatron.getBooks().size() < 3) {
				//Patron has space to check out a book
				Book book1 = this.search_results.get(i);
				if (book1 != null){
					book1.checkOut(this.lib_cal.getDate() + 7);
					this.currentPatron.take(book1);
					this.search_results.remove(i);
					checkedOutBooks.add(book1);	
				}	
				else {
					println("There is no book corresponding to number " + i + ".");
				}
			}
		}
		return checkedOutBooks;
	}
	
	void close() {
		this.lib_open = false;
	}
	
	void quit() {
		this.quit = true;
	}
	
}
