package garage;

public class Car {
	String color;
	String manufacturer;
	
	public Car(String manufacturer) {
		this.color = "white";
		this.manufacturer = manufacturer;
	}
	
	public Car(String color, String manufacturer) {
		this.color = color;
		this.manufacturer = manufacturer;
	}
	
	public boolean equals(Car c) {
		return (this.color.equals(c.color) && this.manufacturer.equals(c.manufacturer));
	}
	
	public String toString() {
		return this.color + " " + this.manufacturer;
	}

}
