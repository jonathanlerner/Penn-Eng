package musicProject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Song {
	Note[] noteArray;
	private String title;
	private String artist;
	private double totalDuration;
	
	/**
	 * Creates a new song based on the song data in the filename file.
	 *
	 * Assume valid input.  File exists, is readable, contents exactly follow the correct format
	 * @param filename
	 */
	public Song(String filename) {
		//pass the ArrayList created by constructFileArray to the constructor that takes in an ArrayList of Strings
		this(constructFileArray(filename));
	}

	/**
	 * Creates a new song based on the song data in the ArrayList fileArray.
	 * fileArray corresponds to an array of Strings, each of which is a line in the line-by-line data
	 * in a valid Song data file.
	 *
	 * Assume valid input.  fileArray is populated and exactly follow the correct format
	 * @param fileArray
	 */
	public Song(ArrayList<String> fileArray) {

		
		this.title = fileArray.get(0);	//title in the first line of the file
		this.artist = fileArray.get(1);	//artist in the second line of the file
		//skip the third line of the file (with the total number of notes ex-repeats)
		
		 /* Now populate the song's array of notes by reading note data from the rest of the fileArray. Each line is a note.
		  * Parse each note assuming the data is correctly formatted as in the assignment specification 
		  */ 
		this.noteArray = new Note[fileArray.size() - 3];	//the noteArray's size ignores the first three lines
		for (int i = 3; i < fileArray.size(); i++) { 
			String[] noteSplitter = fileArray.get(i).split(" ");
			double noteDuration = Double.parseDouble(noteSplitter[0]);
			Pitch notePitch = Pitch.getValueOf(noteSplitter[1]);
			if (notePitch == Pitch.R) {
				//note is a rest, so construct one with the shorter Note constructor
				boolean noteBoolean = Boolean.getBoolean(noteSplitter[2]);
				this.noteArray[i-3] = new Note(noteDuration, noteBoolean);
			}
			else {
				//non-rest Note
				int noteOctave = Integer.parseInt(noteSplitter[2]);
				Accidental noteAccidental = Accidental.getValueOf(noteSplitter[3]);
				boolean noteBoolean = Boolean.parseBoolean(noteSplitter[4]);
				this.noteArray[i-3] = new Note(noteDuration, notePitch, noteOctave, noteAccidental, noteBoolean);
			}
		}
		this.totalDuration = this.computeDuration();
	}
	
	/**
	 * Static helper method used in the first constructor. It takes in a string for a filename, tries to open and read
	 * the file, and passes back an ArrayList of Strings corresponding to the lines in the file
	 * @param filename
	 * @return
	 */
	private static ArrayList<String> constructFileArray(String filename) {
		ArrayList<String> fileArray = new ArrayList<String>();  //will hold our file, line by line
		File songFile = new File(filename);
		try {
			FileReader songReader = new FileReader(songFile);  
			BufferedReader reader = new BufferedReader(songReader);
			while (true) {
				String songLine;
				try {
					songLine = reader.readLine();
					if (songLine == null) break;
					songLine = songLine.trim();	
					fileArray.add(songLine);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
	
			}
			try {
				reader.close();
				songReader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return fileArray;
	}
	
	
	/**
	 * Helper function that takes in an array of notes and returns the total duration
	 * totalDuration is the sum of the lengths of the song's notes with adjustments for repeats. Repeats are INCLUSIVE
	 * @param Array_of_Notes
	 * @return
	 */
	double computeDuration() {
		double duration = 0;
		boolean curr_repeat_on = false;		//will hold whether or not we are currently reading through repeat section
		double curr_repeatDuration = 0;		//will hold the duration of the song since the last repeat
		Note[] Array_of_Notes = this.noteArray;

		//read through the whole noteArray tallying up the duration
		for (Note n : Array_of_Notes) {
			duration += n.getDuration();
			if (curr_repeat_on) {
				curr_repeatDuration += n.getDuration();
			}
			if (n.isRepeat()) {
				//we hit a repeat, do different things if we're already in a repeating section or not
				if (!curr_repeat_on) {
					//start a new repeating section
					curr_repeat_on = true;
					curr_repeatDuration += n.getDuration();	//repeats are inclusive, get first note's duration
				}
				else {
					//we were in a repeated section, which now is over. add the repeat duration we've been keeping track of to the
					//song duration, then reset the variables tracking repeats
					duration += curr_repeatDuration;	//adds in the duration from the repeating section
					curr_repeat_on = false;
					curr_repeatDuration = 0;
				}
			}
		}
		return duration;
	}
	
	/**
	 * returns the title of the song
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * returns the artist of the song
	 * @return
	 */
	public String getArtist() {
		return this.artist;
	}
	
	/**
	 * returns the total duration of the song, in seconds
	 * @return
	 */
	public double getTotalDuration() {
		return this.totalDuration;
	}
	
	/** 
	 * Plays the song through the computer's speakers from beginning to end by playing every note
	 * Correctly processes repeated sections. Uses the play method from the Note class
	 * 
	 * Makes no modifications to the Song instance
	 */
	public void play() {
		int repeat_end_index = -1;	//holds where the last repeat ended. initialize to a garbage number
		
		//our method for reading is: in the for loop we simply play through the notes of the song. however, if we come across a new repeat
		//we start an inner loop that plays through the repeated section the first time. at the end of that we return to the "main" play
		//
		for (int i = 0; i < this.noteArray.length; i++) {
			
			if ((this.noteArray[i].isRepeat()) && (i != repeat_end_index)) {
				this.noteArray[i].play();	//first play the current note
				
				repeat_end_index = i+1;		//now start the repeat end index at the next note. go through notes until reaching the end repeat mark
				while(!this.noteArray[repeat_end_index].isRepeat()) {
					//we're at a note in the repeat section which isn't the begin or end marker. play the note
					this.noteArray[repeat_end_index].play();
					repeat_end_index++;
				}
				//we reached the note at the end of the repeat section. play the note.
				//repeat_end_index now is the index number of the end repeat note. saving this and comparing to our main loop index
				//ensures that, when our main loop again reaches this index we know that this note corresponds to the end of a
				//previously-repeated section, and does not initialize a new repeat
				this.noteArray[repeat_end_index].play();
			}
			
			//now play the original note in the main loop
			this.noteArray[i].play();
			
		}
		
	}
	
	/**
	 * Lower the song by one octave:
	 * 
	 * Lowers by one octave each of the Song's notes. Notes at rest are unaffected. Notes are otherwise unchanged except for octave.
	 * 
	 * If any note's octave is 1, method does nothing and returns false.
	 */
	public boolean octaveDown() {
		//first check the special case that there is a note in octave 1
		for (Note n : this.noteArray) {
			if (n.getOctave() == 1 && !n.isRest()) {
				return false;
			}
		}
		
		//all octaves can be lowered, so lower them
		for (Note n : this.noteArray) {
			if (!n.isRest()) {
				n.setOctave(n.getOctave() - 1);
			}
		}
		return true;
	}
	
	/** 
	 * Raise the song by one octave:
	 * 
	 * Raises by one octave each of the Song's notes. Notes at rest are unaffected. Notes are otherwise unchanged except for octave.
	 * 
	 * If any note's octave is 10, method does nothing and returns false.
	 */
	public boolean octaveUp() {
		//first check the special case that there is a note in octave 10
		for (Note n : this.noteArray) {
			if (n.getOctave() == 10 && !n.isRest()) {
				return false;
			}
		}
		
		//all octaves can be raised, so raise them
		for (Note n : this.noteArray) {
			if (!n.isRest()) {
				n.setOctave(n.getOctave() + 1);
			}
		}
		return true;
	}
	
	/**
	 * Multiplies the length of a Song by a given ratio
	 * by multiplying the duration of each note by that ratio
	 * 
	 * For example, passing a ratio of 3 makes the song 3x slower/longer, 
	 * and passing 0.25 makes it 4x faster/shorter;
	 * passing a ratio of 1 does nothing.
	 * 
	 * @param ratio
	 */
	public void changeTempo(double ratio) {
		for (Note n : this.noteArray) {
			n.setDuration(n.getDuration() * ratio);
		}
		this.totalDuration *= ratio;
		
	}
	
	/** 
	 * Reverse the order of the Song's notes
	 * 
	 * Future calls to play will now play the notes in the opposite order they were in before the call.
	 */
	public void reverse() {
		 /** As per the spec, reverse the order of elements of the noteArray.
		  * Method does not copy the array and does not create other data structures. It simply modifies the noteArray in-place.
		  */
		
		//basic strategy is to exchange the first and last elements in the noteArray, and move inward until reaching the middle
		int index = 0;	//integer that keeps track of the first element of the current pair in the array we are exchanging
		Note reverse_note;	//temporarily holds one of the notes we are exchanging
		
		//for each loop through, compare whether the index for the left element in the pair is still less than the right element index
		//in the pair currently being exchanged, index for the left element: index
		//index for the right element: this.noteArray.length - 1 - index
		while (index < this.noteArray.length - 1 - index) {
			reverse_note = this.noteArray[index];	//temporarily hold this first note in the pair
			this.noteArray[index] = this.noteArray[this.noteArray.length - 1 - index];	//replace the first note in the pair with the second
			this.noteArray[this.noteArray.length - 1 - index] = reverse_note;	//replace the second note in the pair with the temp hold note
			
			index++;
		}
	}
	
	
	/**
	 * Returns a string representation of all the Song's notes
	 * Can be used for the purposes of debugging and running unit tests
	 */
	public String toString() {
		String s = "";
		for (Note n : this.noteArray) {
			s+= n.toString() + "\n";
		}
		return s;
	}
}
