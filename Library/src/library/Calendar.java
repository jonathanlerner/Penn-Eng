package library;

public class Calendar {

	private int date;	//holds the passage of time for the class
	
	/**
	 * the constructor
	 */
	public Calendar() {
		this.date = 0;	//start from 0
	}
	
	/**
	 * Returns (as an integer) the current date
	 * @return
	 */
	int getDate() {
		return this.date;
	}
	
	/**
	 * Increment the date (move ahead to the next day)
	 */
	void advance() {
		this.date += 1;
	}
}
