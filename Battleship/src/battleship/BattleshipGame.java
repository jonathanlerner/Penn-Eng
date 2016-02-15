package battleship;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleshipGame {
	Ocean ocean;
	Scanner scanner;
	ArrayList<Ship> ships_sunk;

	//1. setup the game
	//2. accept 'shots' from the user
	//3. display the results
	//4. print final score
	//ask the user if he/she wants to play again
	
	public BattleshipGame() {
		this.ocean = new Ocean();
		this.ships_sunk = new ArrayList<Ship>();
	}
	
	/**
	 * setup. just places the ships randomly
	 */
	void setup() {
		this.ocean.placeAllShipsRandomly();
	}
	
	/**
	 * prints the current score
	 */
	void printscore() {
		String s = "";
		s += "Shots fired: " + this.ocean.getShotsFired() + "\n";
		s += "Hit count: " + this.ocean.getHitCount() + "\n";
		s += "Ships sunk: " + this.ocean.getShipsSunk() + " (out of 10 total).\n";
		println(s);
	}
	
	/**
	 * takes in a row and column, and shoots at that row and column (updating the ocean). prints appropriate message
	 * note: assumes valid row and column input
	 * @param row
	 * @param column
	 */
	void shoot(int row, int column) {
		int prev_hitcount = this.ocean.getHitCount();
		int prev_sunkcount = this.ocean.getShipsSunk();
		boolean shot_result = this.ocean.shootAt(row, column);
		
		print("\n");
		if (shot_result) {
			if (this.ocean.getHitCount() == prev_hitcount + 1) {
				//we shot at a ship that hasn't been sunk, and hit a new place
				
				//check if we sunk a ship. if we did, what did we sink?
				if (this.ocean.getShipsSunk() == prev_sunkcount + 1) {
					//we sunk a ship!
					for (Ship ship : this.ocean.ship_collection) {
						if (ship.isSunk()) {
							//check if this is a new sunk ship
							if (!this.ships_sunk.contains(ship)) {
								//we sank a new ship!
								println("Hit! You sank a " + ship.name + "!");
								this.ships_sunk.add(ship);
								return;
							}
						}
					}
				}
				println("Hit!");
			}
			else {
				//we shot at a ship that hasn't been sunk, but in a place we've shot before
				println("You've already shot and hit there before.");
			}
		}
		else {
			//we shot at either a ship that's already been sunk, or an empty location
			println("Miss.");
		}
	}
	
	
	public void play() {
		//variable setup
		int [] shot_coords;
		
		//game setup
		this.setup();
		this.scanner = new Scanner(System.in);
				
		
		//game-play
		
		//main loop
		while (true) {
			println("The board is:\n");
			this.ocean.print();
			
			println("Where would you like to shoot? Please enter in the form [row number] [column number], separated by a space:");
			shot_coords = ReadShotInput();
			
			this.shoot(shot_coords[0], shot_coords[1]);
			
			
			//Print the results of this round of play
			if (this.ocean.isGameOver()) {
				println("\nCongratulations! You won!");
				println("The final score is:");
				printscore();
				break;
			}
			else {
				println("Current score:");
				printscore();
			}
			
		}
	}
	
	
	/**
	 * this method reads input from the user and returns a 2-element integer array, each number in which is between 0-9
	 * does all the error checking around this. calls ReadInput
	 */
	public int[] ReadShotInput() {
		boolean valid_input = false;
		int[] shot_coords = new int[2];
		
		while (!valid_input) {
			ArrayList<String> cmd = readInput();
			
			if (cmd.size() != 2) {
				println("Please enter a valid command.");	
			}
			else {
				//check that both the first and second tokens are integers between 0-9
				
				//first make sure both are just single characters
				if (cmd.get(0).length() != 1 || cmd.get(1).length() != 1) {
					println("Please enter a valid command.");		
				}
				else {
					//we have single chars for both tokens. try to convert them to integers
					try {
						shot_coords[0] = Integer.parseInt(cmd.get(0));
						try {
							shot_coords[1] = Integer.parseInt(cmd.get(1));
							valid_input = true;
						}
						catch (Exception NumberFormatException){
							println("Please enter a valid command.");		
						}
					}
					catch (Exception NumberFormatException) {
						println("Please enter a valid command.");	
					}
				}
			}
		
		}	

		
		return shot_coords;
	}
	
	/**
	 * this method helps read input. returns an Array list of command tokens. a scanner must be created prior to its use
	 * @return
	 */
	private ArrayList<String> readInput() {
	    ArrayList<String> commandTokens = new ArrayList<String>();
	    String inputString = "";
	    inputString = scanner.nextLine();
	    for (int i = 0; i < inputString.split(" ").length; i++) {
    		commandTokens.add(inputString.split(" ")[i]);
	    }
	    if (commandTokens.size() == 0) {
	    	scanner.close();
	        throw new InputMismatchException("Please enter a command");
	    } 
	            
	    return commandTokens;
	}
	
	/**
	 * quick helper function for System.out.print
	 * @param message
	 */
	void print(String message) {
		System.out.print(message);
	}
	
	/**
	 * quick helper function for System.out.println
	 * @param message
	 */
	void println(String message) {
		System.out.println(message);
	}

	/**
	 * the main, just create a new BattelshipGame and play it
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("Welcome to Battleship! Created by Jonathan Lerner and Zaks Lubin.\n");
		//main loop
		while (true) {
				BattleshipGame b = new BattleshipGame();
				b.play();
		
				//ask user if they want to play again
				b.println("Would you like to play again? (y/n)");
				ArrayList<String> cmd = b.readInput();
				if (!cmd.get(0).toLowerCase().equals("y")) {
					b.scanner.close();
					break;
				}
		}
		System.out.println("Thanks for playing!");
	}

}
