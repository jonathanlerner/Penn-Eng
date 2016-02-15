package garage;

import java.util.HashMap;

public class Garage {
	HashMap<Integer, Car> spots;
	
	public Garage() {
		HashMap<Integer, Car> car_hash = new HashMap<Integer, Car>();
		
		for (int i = 1; i <= 500; i++) {
			car_hash.put(i, null);
		}
		this.spots = car_hash;
	}
	
	public int park(Car car) {
		for (int i = 1; i <= 500; i++) {
			if (this.spots.get(i) == null) {
				this.spots.put(i, car);
				return i;
			}
		}
		System.out.println("Sorry. Garage is full");
		return 0;
	}
	
	Car retrieve(int spot_number) {
		if (spot_number < 1 || spot_number > 500){
			System.out.println("No car in that spot");
			return null;
		}
		else if (this.spots.get(spot_number) == null) {
			System.out.println("No car in that spot");
			return null;
		}
		else {
			Car returnCar = this.spots.get(spot_number);
			this.spots.remove(spot_number);
			return returnCar;
		}
		
	}

	public static void main(String[] args) {
		Car pink_caddy = new Car("pink", "cadillac");
		Car red_corv = new Car("red", "corvette");
		Car black_merc = new Car("black", "mercedes");
		Garage MyGarage = new Garage();
		int pink_caddy_num = MyGarage.park(pink_caddy);
		MyGarage.park(red_corv);
		MyGarage.retrieve(pink_caddy_num);
		int black_merc_num = MyGarage.park(black_merc);
		System.out.println(black_merc_num);
	}

}
