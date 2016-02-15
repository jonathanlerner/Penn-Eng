package musicProject;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SongTest {
	Song oneNote;
	Song twoNote;
	Song threeNote;
	Song notesandRest;
	Song simple_withrepeat;
	
	
	Song onerepeat;
	Song tworepeats;

	@Before
	public void setUp() throws Exception {
		//create some simple songs to test
		ArrayList<String> simplesong_list = new ArrayList<String>();
		simplesong_list.add("My Simple Song");
		simplesong_list.add("Zaks Isagenius");
		simplesong_list.add("5");
		simplesong_list.add("1 A 5 NATURAL false");
		
		oneNote = new Song(simplesong_list);
		
		simplesong_list.add("2 B 6 FLAT false");
		twoNote = new Song(simplesong_list);
		
		simplesong_list.add("3 C 7 SHARP false");
		threeNote = new Song(simplesong_list);
		
		simplesong_list.add("4 R false");
		
		notesandRest = new Song(simplesong_list);
		
		simplesong_list.add("1 D 6 NATURAL true");
		simplesong_list.add("1 D 6 NATURAL true");
		
		simple_withrepeat = new Song(simplesong_list);	//create a simple song with a repeat at the end
		
		
		//create an ArrayList inspired by the first section of the 'He's a Pirate' song. For testing we've multiplied the
		//duration of each song by 10
		//note that this song has one repeat
		ArrayList<String> piratesong_list= new ArrayList<String>();
		piratesong_list.add("He's a Pirate");
		piratesong_list.add("Hans Zimmer");
		piratesong_list.add("262");
		piratesong_list.add("2 D 5 NATURAL true");
		piratesong_list.add("1 D 5 NATURAL false");
		piratesong_list.add("2 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL false");
		piratesong_list.add("2 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL true");
		piratesong_list.add("2 D 5 NATURAL false");
		piratesong_list.add("1 D 5 NATURAL false");

		onerepeat = new Song(piratesong_list);
		
		piratesong_list.add("2 D 6 NATURAL true");
		piratesong_list.add("1 D 6 NATURAL false");
		piratesong_list.add("2 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL false");
		piratesong_list.add("2 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL true");
		piratesong_list.add("2 D 6 NATURAL false");
		piratesong_list.add("1 D 6 NATURAL false");
		
		tworepeats = new Song(piratesong_list);	
	}

	@Test
	public void testSongArrayListOfString() {
		assertTrue(simple_withrepeat instanceof Song);
		assertEquals(simple_withrepeat.getTitle(), "My Simple Song");
		assertEquals(simple_withrepeat.getArtist(), "Zaks Isagenius");
		assertEquals(simple_withrepeat.getTotalDuration(), 14, .000001);
		
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4, false));

		//test that you can also construct repeat notes
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 6, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 6, Accidental.NATURAL, true));
	}

	@Test
	public void testComputeDuration() {
		assertEquals(oneNote.computeDuration(), 1, .000001);
		assertEquals(twoNote.computeDuration(), 3, .000001);
		assertEquals(threeNote.computeDuration(), 6, .000001);
		assertEquals(notesandRest.computeDuration(), 10, .000001);
		assertEquals(simple_withrepeat.computeDuration(), 14, .000001);
		assertEquals(onerepeat.computeDuration(), 27, .000001);
		assertEquals(tworepeats.computeDuration(), 54, .000001);
	}

	@Test
	public void testOctaveDown() {
		assertTrue(simple_withrepeat.octaveDown());		//lowest note is now octave 4
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 4, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 5, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 6, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 5, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 5, Accidental.NATURAL, true));
		
		assertTrue(simple_withrepeat.octaveDown());		//lowest note is now octave 3
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 3, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 4, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 5, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 4, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 4, Accidental.NATURAL, true));
		
		assertTrue(simple_withrepeat.octaveDown());		//lowest note is now octave 2
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 2, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 3, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 4, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 3, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 3, Accidental.NATURAL, true));
		
		
		assertTrue(simple_withrepeat.octaveDown());		//lowest note is now octave 1
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 1, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 2, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 3, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
		
		assertFalse(simple_withrepeat.octaveDown());	//should not be able to lower
		//check nothing changed
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 1, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 2, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 3, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
		
		assertFalse(simple_withrepeat.octaveDown());	//once more for good measure
		//again check nothing changed
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 1, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 2, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 3, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 2, Accidental.NATURAL, true));
	}

	@Test
	public void testOctaveUp() {	
		assertTrue(simple_withrepeat.octaveUp());		//highest note is now octave 8
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 6, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 7, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 8, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 7, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 7, Accidental.NATURAL, true));
		
		assertTrue(simple_withrepeat.octaveUp());		//highest note is now octave 9
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 7, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 8, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 9, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 8, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 8, Accidental.NATURAL, true));
		
		
		assertTrue(simple_withrepeat.octaveUp());		//highest note is now octave 10
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 8, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 9, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 10, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
		
		assertFalse(simple_withrepeat.octaveUp());	//should not be able to raise
		//check nothing changed
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 8, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 9, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 10, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
		
		assertFalse(simple_withrepeat.octaveUp());	//once more for good measure
		//again check nothing changed
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.A, 8, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(2, Pitch.B, 9, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(3, Pitch.C, 10, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.D, 9, Accidental.NATURAL, true));
	}

	@Test
	public void testChangeTempo() {
		Note[] orig_notes = simple_withrepeat.noteArray.clone();
		simple_withrepeat.changeTempo(1);
		//make sure that nothing changed
		assertEquals(orig_notes.length,simple_withrepeat.noteArray.length);
		for (int i=0;i < orig_notes.length; i++){
			assertEquals(orig_notes[i],simple_withrepeat.noteArray[i]);
		}
		
		//speed up example
		simple_withrepeat.changeTempo(3);
		assertEquals(simple_withrepeat.noteArray[0], new Note(3, Pitch.A, 5, Accidental.NATURAL, false));
		assertEquals(simple_withrepeat.noteArray[1], new Note(6, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[2], new Note(9, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[3], new Note(12,false));
		assertTrue(simple_withrepeat.noteArray[3].isRest());
		assertEquals(simple_withrepeat.noteArray[4], new Note(3, Pitch.D, 6, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[5], new Note(3, Pitch.D, 6, Accidental.NATURAL, true));
		
		//slow down example
		notesandRest.changeTempo(.5);
		assertEquals(notesandRest.noteArray[0], new Note(0.5, Pitch.A, 5, Accidental.NATURAL, false));
		assertEquals(notesandRest.noteArray[1], new Note(1, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(notesandRest.noteArray[2], new Note(1.5, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(notesandRest.noteArray[3], new Note(2,false));
		assertTrue(notesandRest.noteArray[3].isRest());
	}

	@Test
	public void testReverse() {
		//oneNote example
		oneNote.reverse();
		assertEquals(oneNote.noteArray[0], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));	//nothing changes
		
		twoNote.reverse();
		//test reversed order
		assertEquals(twoNote.noteArray[0], new Note(2, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(twoNote.noteArray[1], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));
		
		threeNote.reverse();
		assertEquals(threeNote.noteArray[0], new Note(3, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(threeNote.noteArray[1], new Note(2, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(threeNote.noteArray[2], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));
		
		notesandRest.reverse();
		assertEquals(notesandRest.noteArray[0], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(notesandRest.noteArray[0].isRest());
		assertEquals(notesandRest.noteArray[1], new Note(3, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(notesandRest.noteArray[2], new Note(2, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(notesandRest.noteArray[3], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));
		
		simple_withrepeat.reverse();
		assertEquals(simple_withrepeat.noteArray[0], new Note(1, Pitch.D, 6, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[1], new Note(1, Pitch.D, 6, Accidental.NATURAL, true));
		assertEquals(simple_withrepeat.noteArray[2], new Note(4,false));	//make sure you didn't change the rest
		assertTrue(simple_withrepeat.noteArray[2].isRest());
		assertEquals(simple_withrepeat.noteArray[3], new Note(3, Pitch.C, 7, Accidental.SHARP, false));
		assertEquals(simple_withrepeat.noteArray[4], new Note(2, Pitch.B, 6, Accidental.FLAT, false));
		assertEquals(simple_withrepeat.noteArray[5], new Note(1, Pitch.A, 5, Accidental.NATURAL, false));	
	}

	@Test
	public void testToString() {
		String s = "";
		s+= "1.0 A 5 NATURAL false\n";
		s+= "2.0 B 6 FLAT false\n";
		s+= "3.0 C 7 SHARP false\n";
		s+= "4.0 R false\n";		
		s+= "1.0 D 6 NATURAL true\n";
		s+= "1.0 D 6 NATURAL true\n";

		assertEquals(simple_withrepeat.toString(), s);
	}

}
