package battleship;

public abstract class Ship {
	
	int bowRow;
	int bowColumn;
	int length;
	boolean horizontal;
	boolean [] hit = new boolean[4];
	String name;

	public Ship() {
		for (int i = 0; i < 4; i++){
			hit[i] = false;
		}
	}
	
	int getLength() {
		return this.length;
	}
	
	int getBowRow() {
		return this.bowRow;
	}
	
	int getBowColumn() {
		return this.bowColumn;
	}
	
	boolean isHorizontal() {
		return horizontal;
	}
	
	void setBowRow(int row) {
		this.bowRow = row;
	}
	
	void setBowColumn(int column) {
		this.bowColumn = column;
	}
	
	void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
	abstract String getShipType();
	
	/*Returns true if it is okay to put a ship of this length with its bow in this location
	 * with the given orientation, and returns false otherwise.
	 * The ship must not overlap another ship, or touch another ship.
	 * it must not "stick out" beyond the array.
	 */
	boolean okToPlaceShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		if (horizontal) {
			//make sure the ship can fit on the grid
			if (column + this.getLength() - 1 >= 10) {
				return false;
			}
			//now search all grid around and including the ship
			for (int i = row - 1 ; i <= row + 1; i ++) {
				for (int j = column - 1; j <= column + this.getLength(); j++)
					if (0 <= i && i < 10 && 0 <= j && j < 10) {
						if (ocean.isOccupied(i, j)) {
							return false;						
					}

				}
			}
		}
		else {
			//make sure the ship can fit on the grid
			if (row + this.getLength() - 1 >= 10) {
				return false;
			}
			//now search all grid around and including the ship
			for (int i = row - 1 ; i <= row + this.getLength(); i ++) {
				for (int j = column - 1; j <= column + 1; j++)
					if (0 <= i && i < 10 && 0 <= j && j < 10) {
						if (ocean.isOccupied(i, j)) {
							return false;						
					}

				}
			}
			
		}
		return true;
		
	}
	
	/*puts the ship in the ocean.
	 * gives the values to the bowRow, bowColumn, and horizontal instance variables in the ship
	 * it also involves putting a reference to the ship in each of 1 or more locations in the ships array
	 * in the Ocean object.
	 */
	void placeShipAt(int row, int column, boolean horizontal, Ocean ocean) {
		//initialize ship side
		setBowRow(row);
		setBowColumn(column);
		setHorizontal(horizontal);
		
		//initialize ocean side
		Ship[][] ship_array = ocean.getShipArray();
		if (horizontal) {
			for (int i = 0; i < this.getLength(); i ++) {
				ship_array[row][column + i] = this;
			}
		}
		else {
			for (int i = 0; i < this.getLength(); i ++) {
				ship_array[row + i][column] = this;
			}
		}
	}
	
	/* if a part of the ship occupies the given row and column, and ship hasn't been sunk,
	 * mark that part of the ship as "hit" and return true. Note that in this version we only
	 * return true if this is a new hit -- which we are not sure if it may be slightly off-spec
	 * We decided to interpret the spec this way because it seemed to make the most sense, and allows
	 * our Ocean and BattleshipGame classes to distinguish between whether this new shot caused a new
	 * hit or just shot an an already-hit place (which is a much more interesting distinction from the 
	 * gameplay perspective)
	 * 
	 * Returns false if this is not a new hit on a ship that hasn't been sunk yet
	 */
	boolean shootAt(int row, int column) {
		if (this.isSunk()) {
			//as spec'd, if it's already sunk, return false.
			return false;
		}
		//is our ship in this row and column?
		if (this.isHorizontal()){
			if (row == this.getBowRow()) {
				if (column >= this.getBowColumn() && column - this.getBowColumn() + 1 <= this.getLength()) {
					if (!this.hit[column - this.getBowColumn()]) {
						//this section of the ship has not yet been hit, so mark it hit
						/**
						 * Note that we are allowing for changing the hit array for an EmptySea object (in the first
						 * hit array slot). we have explicitly chosen to make this design decision (which is potentially
						 * slightly off-spec) and checked with Arvind about it. We decided to do this because it allows
						 * us to keep track of where we have shot and missed on previous terms,  and conveniently
						 * print the board properly--displaying where we have shot and missed on any previous turn
						 */
						this.hit[column - this.getBowColumn()] = true;
						return true;
					}
				}
			}
			return false;
		}
		else {
			//vertically oriented ship
			if (column == this.getBowColumn()) {
				if (row >= this.getBowRow() && row - this.getBowRow() + 1 <= this.getLength()) {
					if (!this.hit[row - this.getBowRow()]) {
						//this section of the ship has not yet been hit already been hit
						this.hit[row - this.getBowRow()] = true;
						return true;
					}

				}
			}
			return false;
		}
	}
	
	/*return true if every part of the ship has been hit
	 * false otherwise.
	 */
	boolean isSunk() {
		for (int i = 0; i < this.getLength(); i++){
			if (!hit[i]) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	/* Return 'x' if ship is sunk
	 * "S" if it is hit and not sunk
	 * print locations that have been shot at.
	 */
	public String toString() {
		if (this.isSunk()) {
			return "x";
		}
		else {
			return "S";
		}
	}

}
