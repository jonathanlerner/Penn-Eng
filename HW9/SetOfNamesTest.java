package sets;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SetOfNamesTest {

	SetOfNames s1;
	SetOfNames s2;
	SetOfNames s3;
	
	@Before
	public void setUp() {
		s1 = new SetOfNames();
		s1.add("a");
		s1.add("b");
		
		s2 = new SetOfNames();
		s2.add("a");
		
		s3 = new SetOfNames();
	}


	@Test
	public void testAddString() {
		assertEquals(s3.toString(),"emptySet");
		s3.add("alpha");
		assertEquals(s3.toString(),"{alpha}");
		s3.add("beta");
		assertEquals(s3.toString(),"{alpha, beta}");
		s3.add("alpha2");
		assertEquals(s3.toString(),"{alpha, beta, alpha2}");
		s3.add("alpha");
		assertEquals(s3.toString(),"{alpha, beta, alpha2}"); //doesn't change
		s3.add("beta");
		assertEquals(s3.toString(),"{alpha, beta, alpha2}"); //doesn't change
		s3.add("alpha2");
		assertEquals(s3.toString(),"{alpha, beta, alpha2}"); //doesn't change
	}

	@Test
	public void testAddArrayListOfString() {
		ArrayList<String> mystrings = new ArrayList<String>();
		mystrings.add("alpha");
		mystrings.add("beta");
		mystrings.add("gamma");
		
		s3.add(mystrings);
		assertEquals(s3.toString(),"{alpha, beta, gamma}");
		
		s1.add(mystrings);
		assertEquals(s1.toString(),"{a, b, alpha, beta, gamma}");
		
		s1.add(mystrings);
		assertEquals(s1.toString(),"{a, b, alpha, beta, gamma}");	//doesn't change
		
		ArrayList<String> mystrings2 = new ArrayList<String>();
		mystrings2.add("a");
		mystrings2.add("c");
		s1.add(mystrings2);		//s1 should now be {a, b, alpha, beta, gamma, c} -- i.e. only c is added (not a)
		assertEquals(s1.toString(),"{a, b, alpha, beta, gamma, c}");
	}

	@Test
	public void testDelete() {
		s1.delete("c");
		assertEquals(s1.toString(),"{a, b}");
		s1.delete("a");
		assertEquals(s1.toString(),"{b}");
		s1.delete("a");		//try to delete again, make sure it works properly
		assertEquals(s1.toString(),"{b}");
		s1.delete("c");
		assertEquals(s1.toString(),"{b}");
		s1.delete("b");
		assertEquals(s1.toString(),"emptySet");
		s1.delete("trytodelete_from_empty");
		assertEquals(s1.toString(),"emptySet");	
	}

	@Test
	public void testIsElementOf() {
		assertTrue(s1.isElementOf("a"));
		assertTrue(s1.isElementOf("b"));
		assertTrue(s2.isElementOf("a"));
	
		assertFalse(s1.isElementOf("c"));
		assertFalse(s2.isElementOf("c"));
		
		assertFalse(s3.isElementOf("a"));	//test when the set is empty
	}
	
	@Test
	public void testIntersection() {
		assertEquals(s1.intersect(s2).toString(), "{a}");
		assertEquals(s2.intersect(s1).toString(), "{a}");	//test that order of intersection doesn't matter
		assertEquals(s1.intersect(s3).toString(), "emptySet");
		assertEquals(s3.intersect(s1).toString(), "emptySet");	//test that order of intersection doesn't matter
		assertEquals(s2.intersect(s3).toString(), "emptySet");
		assertEquals(s3.intersect(s2).toString(), "emptySet");	//test that order of intersection doesn't matter
		
		//now test intersection where both sets have multiple common elements, and at least one different
		s1.add("c");
		s2.add("b");
		s2.add("d");
		assertEquals(s1.intersect(s2).toString(), "{a, b}");
		assertEquals(s2.intersect(s1).toString(), "{a, b}");	//test that order of intersection doesn't affect set elements
		
		//test when one set is a complete subset of the other but the intersection has multiple elements
		s2.delete("d");	//now s2 is a subset of s1
		assertEquals(s1.intersect(s2).toString(), "{a, b}");
		assertEquals(s2.intersect(s1).toString(), "{a, b}");
		
		
	}
	
	@Test
	public void testUnion() {
		assertEquals(s1.union(s2).toString(), "{a, b}");
		assertEquals(s2.union(s1).toString(), "{a, b}");
		assertEquals(s1.union(s3).toString(), "{a, b}");
		assertEquals(s3.union(s1).toString(), "{a, b}");
		assertEquals(s2.union(s3).toString(), "{a}");
		assertEquals(s3.union(s2).toString(), "{a}");
		
		//test when sets have some common elements, some disparate
		s2.add("c");
		assertEquals(s1.union(s2).toString(), "{a, b, c}");
		assertEquals(s2.union(s1).toString(), "{a, c, b}");
		
		//test when sets have totally disparate elements
		s1.delete("a");
		assertEquals(s1.union(s2).toString(), "{b, a, c}");
		assertEquals(s2.union(s1).toString(), "{a, c, b}");
		
	}
	
	@Test
	public void testDifference() {
		assertEquals(s1.difference(s2).toString(), "{b}");
		assertEquals(s2.difference(s1).toString(), "emptySet");
		assertEquals(s1.difference(s3).toString(), "{a, b}");
		assertEquals(s3.difference(s1).toString(), "emptySet");
		
		//Also test cases where there is some overlap between sets but also non-overlap
		s2.add("c");
		assertEquals(s1.difference(s2).toString(),"{b}");
		assertEquals(s2.difference(s1).toString(),"{c}");
	}
	
	@Test
	public void testIsSubset() {
		//test that any set is a subset of itself
		assertTrue(s1.isSubset(s1));
		assertTrue(s2.isSubset(s2));
		assertTrue(s3.isSubset(s3));	//tests the emptySet on itself
		
		//test all the combinations of our setUp-created sets
		assertTrue(s1.isSubset(s2));
		assertFalse(s2.isSubset(s1));
		assertTrue(s1.isSubset(s3));
		assertFalse(s3.isSubset(s1));
		assertTrue(s2.isSubset(s3));
		assertFalse(s3.isSubset(s2));
		
		//test for larger sets
		s1.add("c");	//s1 is now {a, b, c}
		assertTrue(s1.isSubset(s2));
		s2.add("c");	//s2 is now {a, c}
		assertTrue(s1.isSubset(s2));	//this case both tests for larger subsets and one ordering case
		s2.add("d");	//s2 is now {a, c, d}
		assertFalse(s1.isSubset(s2));
		s3.add("b");
		s3.add("c");	//s3 is now the same as s1, but in different order
		assertTrue(s1.isSubset(s3));
		
		//test the case where the sets are larger than one element and equal
		s3.add("a");		//s3 is now {b, c, a}, so is identical to a but different order entered
		assertTrue(s1.isSubset(s3));
		assertTrue(s3.isSubset(s1));
		
		//one more for good measure
		s3.add("z");
		assertFalse(s1.isSubset(s3));
		assertTrue(s3.isSubset(s1));	
	}
	
	@Test
	public void testCardinality() {
		assertEquals(s1.cardinality(), 2);
		assertEquals(s2.cardinality(), 1);
		assertEquals(s3.cardinality(), 0);	//empty set
		
		s1.add("d");	//s1 is now {a, b, d}
		assertEquals(s1.cardinality(), 3);
		
		//check that order of elements doesn't matter
		SetOfNames s4 = new SetOfNames();
		s4.add("b");
		s4.add("c");
		s4.add("a");//s4 is same as s1, just different order
		assertEquals(s4.cardinality(), 3);

	}
	
	@Test
	public void testToString() {
		//this method is the method we use to test if sets are equal
		assertEquals(s1.toString(),"{a, b}");
		assertEquals(s1.toString(),"{a, b}");	//test we can do it again
		assertEquals(s2.toString(),"{a}");
		assertEquals(s3.toString(), "emptySet");
		
		s1.add("a");
		assertEquals(s1.toString(),"{a, b}");	//doesn't change
		s1.add("alpha");
		assertEquals(s1.toString(),"{a, b, alpha}");
	}

}
