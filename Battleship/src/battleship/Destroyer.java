package battleship;

public class Destroyer extends Ship {

	public Destroyer() {
		this.length = 2;
		this.name = "destroyer";
	}

	@Override
	String getShipType() {
		return "This ship is a " + this.name;
	}

}
