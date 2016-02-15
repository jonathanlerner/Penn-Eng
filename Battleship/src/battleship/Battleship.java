package battleship;

public class Battleship extends Ship {

	public Battleship() {
		this.length = 4;
		this.name = "battleship";
		// TODO Auto-generated constructor stub
	}

	@Override
	String getShipType() {
		// TODO Auto-generated method stub
		return "This ship is a " + this.name;
	}

}
