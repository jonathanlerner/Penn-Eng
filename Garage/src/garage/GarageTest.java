package garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GarageTest {
	Garage g;
	Car c1;
	Car c2;
	
	@Before
	public void setUp() throws Exception {
		g = new Garage();
		c1 = new Car("Volvo");
		c2 = new Car("Merc");
	}

	@Test
	public void testGarage() {
		fail("Not yet implemented");
	}

	@Test
	public void testPark() {
		int num = g.park(c1);
		assertEquals(num, 1);
		int num2 = g.park(c2);
		
	}

	@Test
	public void testRetrieve() {
		
		Car c = new Car("Volvo");
		int num = g.park(c1);
		assertEquals(1, g.retrieve(num));
	}

}
