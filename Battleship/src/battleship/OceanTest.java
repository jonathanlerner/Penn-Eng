package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OceanTest {
	Ocean testOcean;
	
	Cruiser testCruiser;
	Battleship testBattleship;
	

	@Before
	public void setUp() throws Exception {
		this.testOcean = new Ocean();
		this.testCruiser = new Cruiser();
		this.testBattleship = new Battleship();
		
	}

	//constructor and placeAllShipsRandomly are not tested per instructor confirmation on piazza

	@Test
	public void testIsOccupied() {
		this.testCruiser.placeShipAt(0, 0, false, testOcean);
		for (int i = 0; i < this.testCruiser.getLength(); i++) {
			assertEquals(true, this.testOcean.isOccupied(i, 0));
		}
		for (int i = this.testCruiser.getLength(); i < 10; i++) {
			for (int j = 1; j < 10; j++){
				assertEquals(false, this.testOcean.isOccupied(i,j));
			}
		}
	}

	@Test
	public void testShootAt() {
		//test the shots fired
		for (int i = 0; i < 10; i++) {
			this.testOcean.shootAt(0, 0);
		}
		assertEquals(this.testOcean.shotsFired, 10);
		assertEquals(this.testOcean.getShotsFired(), 10);
		
		//test the hit count
		this.testBattleship.placeShipAt(0, 0, true, testOcean);
		for (int i = 0; i < this.testBattleship.getLength(); i++) {
			this.testOcean.shootAt(0, i);
		}
		assertEquals(this.testOcean.getHitCount(), 4);
		
		//test a hit on a ship
		this.testCruiser.placeShipAt(0, 0, true, testOcean);
		for (int i = 0; i < this.testCruiser.getLength(); i++) {
			assertTrue(this.testOcean.shootAt(0, i));
		}
		
		//test a miss
		assertFalse(this.testOcean.shootAt(9, 9));
		
		//test a hit on a ship that has already been sunk.  This works because the ship was sunk earlier.
		this.testBattleship.placeShipAt(0, 0, true, testOcean);
		for (int i = 0; i < this.testBattleship.getLength(); i++) {
			assertFalse(this.testOcean.shootAt(0, i));
		}
		assertEquals(this.testOcean.getHitCount(), 7);
	}

	@Test
	public void testGetShotsFired() {
		assertEquals(this.testOcean.shotsFired, this.testOcean.getShotsFired());
		this.testOcean.shotsFired = 5;
		assertEquals(5, this.testOcean.getShotsFired());
	}

	@Test
	public void testGetHitCount() {
		assertEquals(this.testOcean.hitCount, this.testOcean.getHitCount());
		this.testOcean.hitCount = 8;
		assertEquals(8, this.testOcean.getHitCount());
	}

	@Test
	public void testGetShipsSunk() {
		assertEquals(this.testOcean.shipsSunk, this.testOcean.getShipsSunk());
		this.testOcean.shipsSunk = 6;
		assertEquals(6, this.testOcean.getShipsSunk());
	}

	@Test
	public void testIsGameOver() {
		//place all the ships on the board.  Doesn't matter that they are placed illegally
		for (Ship s : this.testOcean.ship_collection) {
			for (int i = 0; i < 10; i++)
			s.placeShipAt(i, 0, true, testOcean);
			
			//fire on all ships until they are sunk and test isGameOver after every shot
			for (int j = 0; j < s.getLength(); j++) {
				assertFalse(this.testOcean.isGameOver());
				this.testOcean.shootAt(s.getBowRow(), j);
			}
		}
		assertTrue(this.testOcean.isGameOver());
	}

	
	@SuppressWarnings("deprecation")
	@Test
	public void testGetShipArray() {
		Ship[][] s = this.testOcean.getShipArray();
		assertEquals(this.testOcean.ships, s);
		assertEquals(10, s.length);
	}

	@Test
	public void testtoString() {
		this.testOcean.placeAllShipsRandomly();
		System.out.println(this.testOcean.toString());
		System.out.println(this.testOcean.total_board());
	}

}
