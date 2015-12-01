package student;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {

	Student arvind;
	Student jon;
	Student s;
	Student empty;
	
	@Before
	public void setUp() {
		arvind = new Student("Arvind");
		jon = new Student("Jon");
		s = new Student("S");
		empty = new Student("");
	}

	@Test
	public void testGetName() {
		assertEquals(arvind.getName(),"Arvind");
		assertEquals(jon.getName(),"Jon");
		assertEquals(s.getName(),"S");
		assertEquals(empty.getName(),"");
	}

	@Test
	public void testAddHWScore() {
		arvind.addHWScore(75);
		assertEquals(arvind.total_hw_score, 75);
		arvind.addHWScore(50);
		assertEquals(arvind.total_hw_score, 125);
		arvind.addHWScore(100);
		assertEquals(arvind.total_hw_score, 225);
		arvind.addHWScore(0);
		assertEquals(arvind.total_hw_score, 225);
		arvind.addHWScore(1);
		assertEquals(arvind.total_hw_score, 226);
	}

	@Test
	public void testGetTotalScore() {
		assertEquals(arvind.getTotalScore(),0);
		arvind.addHWScore(50);
		assertEquals(arvind.getTotalScore(), 50);
		arvind.addHWScore(25);
		assertEquals(arvind.getTotalScore(), 75);
	}

	@Test
	public void testGetAverageScore() {
		assertEquals(arvind.getAverageScore(),-1, .00001);
		arvind.addHWScore(0);
		assertEquals(arvind.getAverageScore(), 0, .00001);
		arvind.addHWScore(100);
		assertEquals(arvind.getAverageScore(), 50, .00001);
		arvind.addHWScore(0);
		assertEquals(arvind.getAverageScore(), 33.33333333333333333333, .000001);
		arvind.addHWScore(20);
		assertEquals(arvind.getAverageScore(), 30, .000001);
	}

	@Test
	public void testToString() {
		assertEquals(arvind.toString(), "Arvind : 0");
		assertEquals(jon.toString(), "Jon : 0");
		assertEquals(s.toString(), "S : 0");
		assertEquals(empty.toString(), " : 0");
		
		arvind.addHWScore(0);
		assertEquals(arvind.toString(), "Arvind : 0");
		arvind.addHWScore(50);
		assertEquals(arvind.toString(), "Arvind : 50");
		arvind.addHWScore(83);
		assertEquals(arvind.toString(), "Arvind : 133");
		
		empty.addHWScore(97);
		assertEquals(empty.toString(), " : 97");
		empty.addHWScore(4);
		assertEquals(empty.toString(), " : 101");	
	}

}
