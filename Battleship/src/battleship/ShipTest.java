package battleship;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;



public class ShipTest {
	Battleship testBattle;
	Cruiser testCruiser;
	Submarine testSub;
	Destroyer testDestroyer;
	EmptySea testEmptySea;
	Ocean testOcean;
	
	
	@Before
	public void setUp() throws Exception {
		this.testBattle = new Battleship();
		this.testCruiser = new Cruiser();
		this.testSub = new Submarine();
		this.testEmptySea = new EmptySea();
		this.testOcean = new Ocean();
		this.testDestroyer = new Destroyer();
	}

	@Test
	public void testGetLength() {
		assertEquals(4, this.testBattle.getLength());
		assertEquals(3, this.testCruiser.getLength());
		assertEquals(1, this.testSub.getLength());
		assertEquals(1, this.testEmptySea.getLength());
	}
	
	@Test
	public void testGetBowRow() {
		this.testBattle.setBowRow(4);
		assertEquals(4, this.testBattle.getBowRow());
		this.testCruiser.bowRow = 3;
		assertEquals(3, this.testCruiser.getBowRow());
	}
	
	@Test
	public void testGetBowColumn() {
		this.testCruiser.setBowColumn(5);
		assertEquals(5, this.testCruiser.getBowColumn());
	}
	
	@Test
	public void testIsHorizontal() {
		this.testBattle.setHorizontal(false);
		assertFalse(this.testBattle.isHorizontal()); 
	}
	
	@Test
	public void testSetBowRow() {
		this.testBattle.setBowRow(4);
		assertEquals(4, this.testBattle.bowRow);
	}
	
	@Test
	public void testSetBowColumn() {
		this.testSub.setBowColumn(4);
		assertEquals(4, this.testSub.bowColumn);
	}
	
	@Test	
	public void testSetHorizontal() {
		boolean test_horizontal = true;
		this.testSub.setHorizontal(test_horizontal);
		assertTrue(this.testSub.horizontal);
	}
	
	@Test
	public void testGetShipType() {
		assertEquals("This ship is a battleship", this.testBattle.getShipType());
		
		//test the specific case of EmptySea
		assertEquals("empty", this.testEmptySea.getShipType());
	}
	
	@Test
	public void testOkToPlaceShipAt() {
		//test every spot on an empty board to see if it correctly places a ship
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++) { 
				assertEquals(true, this.testBattle.okToPlaceShipAt(i, j, true, testOcean));
			}
			for (int j = 7; j < 10; j++) { 
				assertEquals(false, this.testBattle.okToPlaceShipAt(i, j, true, testOcean));
			}
		}
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 10; j++) { 
				assertEquals(true, this.testBattle.okToPlaceShipAt(i, j, false, testOcean));
			}
		}
		for (int i = 7; i < 10; i++) {
			for (int j = 0; j < 10; j++) { 
				assertEquals(false, this.testBattle.okToPlaceShipAt(i, j, false, testOcean));
			}
		}
		//place a test ship
		this.testCruiser.placeShipAt(4, 4, true, testOcean);
		
		//if a ship is already placed, make sure that another one can't be placed at the same spot
		for (int i = 4; i < this.testCruiser.getLength(); i++) {
			assertFalse(this.testSub.okToPlaceShipAt(4, i, true, testOcean));
		}
		
		//test diagonal, test adjacent
		for (int i = this.testCruiser.getBowColumn() - 1; i < this.testCruiser.getLength() + 1; i++) {
			for (int j = this.testCruiser.getBowRow() - 1; i < this.testCruiser.getBowRow() + 1; i++) {
				assertFalse(this.testSub.okToPlaceShipAt(i, j, true, testOcean));
			}
		}
		
		//test overlapping ships specifically
		for (int i = this.testCruiser.getBowRow(); i < this.testBattle.getLength(); i++) {
			assertFalse(this.testBattle.okToPlaceShipAt(4, i, false, testOcean));
		}
	}
	
	@Test
	public void testPlaceShipAt() {
		//test the basics
		this.testBattle.placeShipAt(4, 3, true, testOcean);
		assertEquals(4, this.testBattle.getBowRow());
		assertEquals(3, this.testBattle.getBowColumn());
		assertEquals(true, this.testBattle.isHorizontal());
		for (int i = 3; i < this.testBattle.getLength() + 3; i++) {
			assertEquals(this.testOcean.ships[4][i], this.testBattle);
		}
		//test the vertical direction
		this.testBattle.placeShipAt(4, 3, false, testOcean);
		for (int i = 4; i < this.testBattle.getLength() + 4; i++) {
			assertEquals(this.testOcean.ships[i][3], this.testBattle);
		}
		
		//test something that isn't a battleship
		this.testEmptySea.placeShipAt(6, 7, false, testOcean);
		assertEquals(6, this.testEmptySea.getBowRow());
		assertEquals(7, this.testEmptySea.getBowColumn());
		assertEquals(false, this.testEmptySea.isHorizontal());
	}
	
	@Test
	public void testShootAt() {
		this.testBattle.placeShipAt(4, 3, true, testOcean);
		for ( int i = this.testBattle.getBowColumn(); i < this.testBattle.getLength(); i++) {
			assertEquals(this.testBattle.shootAt(4, i), true);
		}
		//test a ship that's already sunk
		assertFalse(this.testBattle.shootAt(4, 3));
		
		//test spots that have already been shot at
		this.testDestroyer.placeShipAt(8, 6, false, testOcean);
		this.testDestroyer.shootAt(this.testDestroyer.getBowRow(), this.testDestroyer.getBowColumn());
		assertFalse(this.testDestroyer.shootAt(this.testDestroyer.getBowRow(), this.testDestroyer.getBowColumn()));
		
		//test the EmptySea specific case
		this.testEmptySea.placeShipAt(0, 0, true, testOcean);
		assertFalse(this.testEmptySea.shootAt(0, 0));
		this.testEmptySea.shootAt(0, 0);
		assertTrue(this.testEmptySea.hit[0]);
	}
	
	@Test	
	public void testIsSunk() {
		this.testBattle.placeShipAt(4, 3, true, testOcean);
		for (int i = 3; i < this.testBattle.getLength() + 3; i++) {
			this.testBattle.shootAt(4, i);
		}
		assertEquals(true, this.testBattle.isSunk());
		
		//test the EmptySea specific case
		assertFalse(this.testEmptySea.isSunk());
	}
}
