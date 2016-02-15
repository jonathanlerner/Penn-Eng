package battleship;

public class EmptySea extends Ship {

	public EmptySea() {
		this.length = 1;
		this.name ="empty sea";
	}

	@Override
	String getShipType() {
		return "empty";
	}
	
	@Override
	boolean shootAt(int row, int column) {
		//change the hit array to indicate that this spot has been shot at
		//this will be useful for keeping track of where we've shot at and
		//when printing the board
		if (row == this.getBowRow() && column == this.getBowColumn()){
			this.hit[0] = true;
		}
		return false;
	}
	
	@Override
	boolean isSunk() {
		return false;
	}
	
	@Override
	public String toString() {
		return "-";
	}
	
	

}
