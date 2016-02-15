package battleship;
	import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("unused")


public class Ocean {
	Ship[][] ships = new Ship[10][10];
	int shotsFired;
	int hitCount;
	int shipsSunk;
	
	int num_battle;
	int num_cruiser;
	int num_destroy;
	int num_sub;
	ArrayList<Ship> ship_collection;
	
	public Ocean() {
		this.ships = new Ship[10][10];
		this.shotsFired = 0;
		this.hitCount = 0;
		this.shipsSunk = 0;
		
		//Change this if you want to change the number of each ship in the game
		this.num_battle = 1;
		this.num_cruiser = 2;
		this.num_destroy = 3;
		this.num_sub = 4;
		
		//create our collection of ships from biggest to smallest
		this.ship_collection = new ArrayList<Ship>();
		for (int i = 0; i < num_battle; i++) {
			Ship s = new Battleship();
			ship_collection.add(s);
		}
		for (int i = 0; i < num_cruiser; i++) {
			Ship s = new Cruiser();
			ship_collection.add(s);
		}
		for (int i = 0; i < num_destroy; i++) {
			Ship s = new Destroyer();
			ship_collection.add(s);
		}
		for (int i = 0; i < num_sub; i++) {
			Ship s = new Submarine();
			ship_collection.add(s);
		}
		
		//put EmptySea in all the spots in the ocean array
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++){
				Ship s = new EmptySea();
				s.placeShipAt(i, j, true, this);
			}
		}
	}
	
	/*Place all 10 ships randomly on the (initially empty) ocean
	 * Place larger ships before smaller ones
	 */
	void placeAllShipsRandomly() {
		int row_random;
		int col_random;
		boolean horz_random;
		boolean ship_placed;
		
		//now place our ships
		for (Ship s : this.ship_collection) {
			ship_placed = false;
			
			while (!ship_placed) {
				//get our random selection for this potential placement
				row_random = (int) (Math.random() * 10);
				col_random = (int) (Math.random() * 10);
				if (Math.random() < .5) {
					horz_random = false;
				}
				else {
					horz_random = true;
				}
			
				//try to place this ship in this configuration. if not try again (ie loop again)
				if (s.okToPlaceShipAt(row_random, col_random, horz_random, this)) {
					//it's ok to place, so go ahead and place it
					s.placeShipAt(row_random, col_random, horz_random, this);
					ship_placed = true;
				}
			}	
		
			
		}

	}
	
	//return true if the location has a ship type anything other than EmptySea, false if not
	boolean isOccupied(int row, int column) {
		if (this.ships[row][column] instanceof EmptySea){
			return false;
		}
		return true;
	}
	
	/*return true if the given location contains a still afloat ship
	 * false if it does not.
	 * method updates number of shots fired, and number of hits.
	 * return "true" for additional shots at a previously hit location.
	 * return "false" for additional shots at a hit location once the ship is sunk.
	 */
	boolean shootAt(int row, int column) {
		this.shotsFired++;
		
		Ship s = this.ships[row][column];
		if (isOccupied(row, column)) {
			if (!s.isSunk()){
				if (s.shootAt(row, column)) {
					//we shot at a ship in a location that hasn't been hit before
					this.hitCount++;
					//now see how many ships are now sunk and update shipsSunk
					int curr_sunk_count = 0;
					//loop through all our ships
					for (Ship one_of_our_ships : this.ship_collection) {
						if (one_of_our_ships.isSunk()) {
							curr_sunk_count++;
						}
					}
					this.shipsSunk = curr_sunk_count;
				}
				//still return true, as spec'd, for additional shots at a previously hit location
				return true;
			}
			
		}
		else {
			//still shoot at EmptySeas, just to know where we've shot before (to update the board)
			//see notes on it in the shootAt method within the Ship class
			s.shootAt(row,column);
		}
		//here we have all cases except when you shoot at a ship that hasn't been sunk already
		return false;
	}
	
	int getShotsFired() { 
		return this.shotsFired;
	}
	
	int getHitCount() {
		return this.hitCount;
	}
	
	int getShipsSunk() {
		return this.shipsSunk;
	}
	
	//return true if all ships are sunk, false otherwise.
	boolean isGameOver() {
		for (Ship s : this.ship_collection) {
			if (!s.isSunk()) {
				return false;
			}
		}
		return true;
	}
	
	//return the 10x10 array of ships
	Ship[][] getShipArray() {
		return this.ships;
	}


	@Override
	/**
	 * create our own toString()
	 */
	public String toString() {
		String s = " ";
		Ship [][] ship_array = this.getShipArray();
		//first print the row across the top
		for (int i = 0; i < 10; i++) {
			s += "    " + i;
		}
		s += "\n\n";
		
		//now print every subsequent row
		for (int i = 0; i < 10; i++) {
			s += "" + i;
			for (int j = 0; j < 10; j++) {
				s += "    ";
				Ship curr_ship = ship_array[i][j];

				//either the row or column of curr_ship should be equal to where we are
				if (curr_ship.getBowRow() == i) {
					//our row matches the bow
					if (curr_ship.hit[j - curr_ship.getBowColumn()]) {
						//we've fired here before, so print what it is
						s += curr_ship.toString();
					}
					else {
						//we haven't fired here before
						s += ".";
					}
				}
				else {
					//our column matches the bow
					if (curr_ship.hit[i - curr_ship.getBowRow()]) {
						//we've fired here before, so print what it is
						s += curr_ship.toString();
					}
					else {
						//we haven't fired here before
						s += ".";
					}
				}
			}
			//got to the end of the row
			s += "\n\n";
		}
		
		//now return our string
		return s;
	}
	
	/**
	 * this method returns a string of the total board. very useful for testing
	 */
	public String total_board() {
		String s = " ";
		Ship [][] ship_array = this.getShipArray();
		//first print the row across the top
		for (int i = 0; i < 10; i++) {
			s += "    " + i;
		}
		s += "\n\n";
		
		//now print every subsequent row
		for (int i = 0; i < 10; i++) {
			s += "" + i;
			for (int j = 0; j < 10; j++) {
				s += "    ";
				Ship curr_ship = ship_array[i][j];
				s += curr_ship.toString();
			}
			//got to the end of the row
			s += "\n\n";
		}
		
		//now return our string
		return s;
	}
	
	//prints the ocean
	void print() {
		System.out.println(this.toString());
	}

}
