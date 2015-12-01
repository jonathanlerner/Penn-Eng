package sets;
import java.util.*;

public class SetOfNames {
	ArrayList<String> nameSet;

	/**
	 * Constructor
	 */
	public SetOfNames() {
		this.nameSet = new ArrayList<String>();
	}
	
	/**
	 * adds a single string to our set of names. returns nothing
	 * @param s
	 */
	public void add(String s) {
		if (!this.nameSet.contains(s)) {
			this.nameSet.add(s);	
		}
		
	}
	
	/**
	 * Adds an array of strings to the set
	 * @param string_array
	 */
	public void add(ArrayList<String> string_array) {
		for (String s : string_array) {
			if (!this.nameSet.contains(s)) {
				this.nameSet.add(s);
			}
		}
	}
	
	/**
	 * delete a particular string from the array. only deletes the first instance of that string
	 * @param s
	 */
	public void delete(String s) {
		this.nameSet.remove(s);
	}
	
	/**
	 * checks whether a given string is an element of the set. returns a boolean
	 * @param s
	 */
	public boolean isElementOf(String s) {
		return this.nameSet.contains(s);
	}
	
	
	/**
	 * returns a string representation of our set
	 */
	public String toString() {
		if (this.nameSet.size() == 0) {
			return "emptySet";
		}
		else {
			String s = "";
			for (String name : this.nameSet) {
				s += name + ", ";
			}
			return "{" + s.substring(0, s.length() - 2) + "}";
		}

		
	}

	/**
	 * returns a SetOfNames that corresponds to the intersection of the two sets
	 * in the usual Set-theory definition
	 * @param otherSet
	 * @return
	 */
	SetOfNames intersect(SetOfNames otherSet) {
		SetOfNames intersectionSet = new SetOfNames();
		for (String name : this.nameSet) {
			if (otherSet.isElementOf(name)) {
				intersectionSet.add(name);
			}
		}
		return intersectionSet;
	}
	
	/**
	 * returns a SetOfNames that corresponds to the union of the two sets
	 * in the usual Set-theory definition
	 * @param otherSet
	 * @return
	 */
	SetOfNames union(SetOfNames otherSet) {
		SetOfNames unionSet = new SetOfNames();
		for (String name : this.nameSet) {
			unionSet.add(name);
		}
		//use the property of the add method that it only adds elements not already in the set
		for (String name : otherSet.nameSet) {
			unionSet.add(name);
		}
		return unionSet;
		
	}
	
	/**
	 * returns a SetOfNames that corresponds to the set that consists of elements
	 * in this set that cannot be found in the otherSet
	 * @param otherSet
	 * @return
	 */
	SetOfNames difference(SetOfNames otherSet) {
		SetOfNames differenceSet = new SetOfNames();
		for (String name : this.nameSet) {
			if (!otherSet.isElementOf(name)) {
				differenceSet.add(name);
			}
		}
		return differenceSet;
		
	}
	
	/**
	 * This method returns true if all the elements of otherSet can be found in this set
	 * and false otherwise
	 * @param otherSet
	 * @return
	 */
	boolean isSubset(SetOfNames otherSet) {
		for (String name : otherSet.nameSet) {
			if (!this.nameSet.contains(name)) {
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * This method returns the number of elements in this set
	 * @return
	 */
	int cardinality() {
		return this.nameSet.size();
	}
	
	public static void main(String[] args) {
		SetOfNames vanHalen = new SetOfNames();
		
		//construct list of rocker names
		ArrayList<String> rocker_names = new ArrayList<String>();
		rocker_names.add("Eddie Van Halen");
		rocker_names.add("David Lee Roth");
		rocker_names.add("Alex Van Halen");
		rocker_names.add("Michael Anthony");
		
		//add that arraylist of rockernames to vanHalen
		vanHalen.add(rocker_names);
		System.out.println(vanHalen.toString());
		
		//delete David Lee Roth :(
		vanHalen.delete("David Lee Roth");
		System.out.println(vanHalen.toString());
		
		//add Sammy Hagar!!!
		vanHalen.add("Sammy Hagar");
		System.out.println(vanHalen.toString());
	}

}
