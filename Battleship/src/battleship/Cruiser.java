package battleship;

public class Cruiser extends Ship {

	public Cruiser() {
		this.length = 3;
		this.name = "cruiser";
	}

	@Override
	String getShipType() {
		return "This ship is a " + this.name;
	}

}
