package garage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CarTest {
	Car yellowMerc1;
	Car yellowMerc2;
	Car yellowmerc;
	Car YellowMerc;
	Car Yellowmerc;
	
	Car whiteVolvo;
	
	@Before
	public void setUp() throws Exception {
		yellowMerc1 = new Car("yellow", "Merc");
		yellowMerc2 = new Car("yellow", "Merc");
		yellowmerc = new Car("yellow", "merc");
		YellowMerc = new Car("Yellow", "Merc");
		Yellowmerc = new Car("Yellow", "merc");
		
		whiteVolvo = new Car("Volvo");
	}

	@Test
	public void testCarString() {
		Car c = new Car("Volvo");
		assertTrue(c.color.equals("white"));
		assertTrue(c.manufacturer.equals("Volvo"));
		assertTrue(c instanceof Car);
		
	}

	@Test
	public void testCarStringString() {
		Car c = new Car("blue", "Volvo");
		assertTrue(c instanceof Car);
		assertTrue(c.color.equals("blue"));
		assertTrue(c.manufacturer.equals("Volvo"));
	}

	@Test
	public void testEqualsCar() {
		assertTrue(yellowMerc1.equals(yellowMerc2));
		assertFalse(yellowMerc1.equals(yellowmerc));
		assertFalse(yellowMerc1.equals(YellowMerc));
		assertFalse(yellowMerc1.equals(Yellowmerc));
		assertFalse(yellowMerc1.equals(whiteVolvo));
	}

	@Test
	public void testToString() {
		assertTrue(yellowMerc1.toString().equals("yellow Merc"));
		assertTrue(whiteVolvo.toString().equals("white Volvo"));
	}

}
