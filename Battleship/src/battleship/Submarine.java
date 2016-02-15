package battleship;

public class Submarine extends Ship{

	public Submarine() {
		this.length = 1;
		this.name = "submarine";
	}

	@Override
	String getShipType() {
		return "This ship is a " + this.name;
	}

}
