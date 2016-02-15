package musicProject;

/*
 * Music Player
 *
 * This instructor-provided file implements the graphical user interface (GUI)
 * for the Music Player program and allows you to test the behavior
 * of your Song class.
 */

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MusicPlayer extends JFrame implements ActionListener, StdAudio.AudioEventListener {

    // instance variables
    private Song song;
    private boolean playing; // whether a song is currently playing
    private JFrame frame;
    private JSpinner tempoSpinner; //controls the tempo.
    private JSlider currentTimeSlider;
    private JPanel buttonPanel1;
    private JPanel buttonPanel2;
    private	JPanel sliderPanel;
    private JPanel timePanel;
    private	JPanel headerPanel;
    private JPanel tempoPanel;
    private JButton play;
    private JButton pause;
    private JButton stop;
    private JButton load;
    private JButton reverse;
    private JButton up;
    private JButton down;
    private JButton change;
    private JLabel tempoLabel;
    
    //these are the two labels that indicate time
    // to the right of the slider
    private JLabel currentTimeLabel;
    private JLabel totalTimeLabel;
    
    //this is the label that says 'welcome to the music player'
    private JLabel statusLabel; 
   
    /**
     * Creates the music player GUI window and graphical components.
     */
    public MusicPlayer() {
    	frame = new JFrame("Music Player");
        song = null;
        createComponents();
        makeLayout();
        StdAudio.addAudioEventListener(this);
        frame.setVisible(true);
    }

    /**
     * Called when the user interacts with graphical components, such as
     * clicking on a button.
     */
    public void actionPerformed(ActionEvent event) {
        String cmd = event.getActionCommand();
        if (cmd.equals("Play")) {
            playSong();
        } else if (cmd.equals("Pause")) {
            StdAudio.setPaused(!StdAudio.isPaused());
        } else if (cmd == "Stop") {
            StdAudio.setMute(true);
            StdAudio.setPaused(false);
        } else if (cmd == "Load") { 
        	try {
                loadFile();
            } catch (IOException ioe) {
                System.out.println("not able to load from the file");
            }
        } else if (cmd == "Reverse") {
           song.reverse(); 
        } else if (cmd == "Up") {
           song.octaveUp();
        } else if (cmd == "Down") {
           song.octaveDown();
        } else if (cmd == "Change") {
        	System.out.println("change stuff");
        	Double tempoInput = (Double) tempoSpinner.getValue();
        	song.changeTempo(tempoInput);
        	this.updateTotalTime();
        }
    }

    /**
     * Called when audio events occur in the StdAudio library. We use this to
     * set the displayed current time in the slider.
     */
    public void onAudioEvent(StdAudio.AudioEvent event) {
        // update current time
        if (event.getType() == StdAudio.AudioEvent.Type.PLAY
                || event.getType() == StdAudio.AudioEvent.Type.STOP) {
            setCurrentTime(getCurrentTime() + event.getDuration());
        }
    }

    /**
     * Sets up the graphical components in the window and event listeners.
     */
    private void createComponents() {
        //make the header label.  Set them as necessary.  Add to a header panel.
    	statusLabel = new JLabel("Select a song with the 'Load' button."  );
        headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout());
        headerPanel.add(statusLabel);       
        
        
    	//make the required buttons.  add them to listeners and add them to a panel.
    	play = new JButton("Play");
    	play.addActionListener(this);
    	reverse = new JButton("Reverse");
    	reverse.addActionListener(this);
    	stop = new JButton("Stop");
    	stop.addActionListener(this);
    	pause = new JButton("Pause");
    	pause.addActionListener(this);
    	load = new JButton("Load");
    	load.addActionListener(this);
        up = new JButton("Up");
    	up.addActionListener(this);
        down = new JButton("Down");
    	down.addActionListener(this);
    	buttonPanel1 = new JPanel();
    	buttonPanel1.setLayout(new FlowLayout());
        buttonPanel1.add(play);
        buttonPanel1.add(stop);
        buttonPanel1.add(pause);
        buttonPanel2 = new JPanel();
        buttonPanel2.add(load);
        buttonPanel2.add(up);
        buttonPanel2.add(down);
        buttonPanel2.add(reverse);
        
    	//make the slider. add it to the panel.
        currentTimeSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 30, 20);
        Dimension sliderDimension = new Dimension(400, 20);
        currentTimeSlider.setPreferredSize(sliderDimension);
        currentTimeSlider.setMajorTickSpacing(10);
        currentTimeSlider.setMinorTickSpacing(1);
        currentTimeSlider.setPaintTicks(true);
        currentTimeLabel = new JLabel("00000.00/");
        totalTimeLabel = new JLabel("00000.00" + " sec.");
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout());
        timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(2,1));
        timePanel.add(currentTimeLabel);
        timePanel.add(totalTimeLabel);
        sliderPanel.add(currentTimeSlider);
        sliderPanel.add(timePanel);
        
        
        //make the tempo panel
        tempoLabel = new JLabel("Tempo: ");
        SpinnerModel model = new SpinnerNumberModel(1, 0, 10, 0.25);
        tempoSpinner = new JSpinner(model); 
        change = new JButton("Change");
        change.addActionListener(this);
        tempoPanel = new JPanel();
        tempoPanel.setLayout(new FlowLayout());
        tempoPanel.add(tempoLabel);
        tempoPanel.add(tempoSpinner);
        tempoPanel.add(change);
        
        //add everything to the frame
        frame.setLayout(new GridLayout(5,1));
        frame.add(headerPanel);
        frame.add(sliderPanel);
        frame.add(buttonPanel1);
        frame.add(buttonPanel2);
        frame.add(tempoPanel);  
        doEnabling();
    }

    /**
     * Sets whether every button, slider, spinner, etc. should be currently
     * enabled, based on the current state of whether a song has been loaded and
     * whether or not it is currently playing. This is done to prevent the user
     * from doing actions at inappropriate times such as clicking play while the
     * song is already playing, etc.
     */
    private void doEnabling() {
       ArrayList<JButton> buttonList = new ArrayList<JButton>();
       buttonList.add(play);
       buttonList.add(stop);
       buttonList.add(reverse);
       buttonList.add(up);
       buttonList.add(down);
       buttonList.add(load);
       buttonList.add(pause);
       buttonList.add(change);
       
       if(song == null) {
    	   for(JButton button : buttonList) {
    		   button.setEnabled(false);
    	   }
    	   tempoSpinner.setEnabled(false);
    	   load.setEnabled(true);
       }
       if (song instanceof Song) {
    	   for (JButton button : buttonList) {
    		   button.setEnabled(true);
    	   }
    	   tempoSpinner.setEnabled(true);
       }
       if(playing == true) {
    	   for(JButton button : buttonList) {
    		   button.setEnabled(false);
    	   }
    	   tempoSpinner.setEnabled(false);
    	   stop.setEnabled(true);
    	   pause.setEnabled(true);
       }
    }

    /**
     * Performs layout of the components within the graphical window. 
     * Also make the window a certain size and put it in the center of the screen.
     */
    private void makeLayout() {     
    	pack();
    	frame.setSize(600, 300);
		//place in the center
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width - dim.getSize().width / 2 - 100, dim.height - dim.getSize().height / 2 - 100);
    }

    /**
     * Returns the estimated current time within the overall song, in seconds.
     */
    private double getCurrentTime() {
        String timeStr = currentTimeLabel.getText();
        timeStr = timeStr.replace(" /", "");
        try {
            return Double.parseDouble(timeStr);
        } catch (NumberFormatException nfe) {
            return 0.0;
        }
    }

    /**
     * Pops up a file-choosing window for the user to select a song file to be
     * loaded. If the user chooses a file, a Song object is created and used
     * to represent that song.
     */
    private void loadFile() throws IOException {
    	//create the FileChooser and display the dialog box.  Advance the UI if its a valid input.
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setDialogTitle("Which song do you want to play?");
    	if (fileChooser.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        File selected = fileChooser.getSelectedFile();
        if (selected == null) {
            return;
        }
        
        //change the Label for the song to the name of the song.
        statusLabel.setText("Current song: " + selected.getName());
        String filename = selected.getAbsolutePath();
        System.out.println("Loading song from " + selected.getName() + " ...");
        
        //set the song to the file that is chosen.
        song = new Song(filename);
        
        //set the default value for the spinner to 1.  Change the time labels to the values for the song.
        tempoSpinner.setValue(1.0);
        setCurrentTime(0.0);
        updateTotalTime();
        System.out.println("Loading complete.");
        System.out.println("Song: " + song);
        
        //enable different buttons given that the song is loaded now.
        doEnabling(); 
    }

    /**
     * Initiates the playing of the current song in a separate thread (so
     * that it does not lock up the GUI). 
     * You do not need to change this method.
     * It will not compile until you make your Song class.
     */
    private void playSong() {
        if (song != null) {
            setCurrentTime(0.0);
            Thread playThread = new Thread(new Runnable() {
                public void run() {
                    StdAudio.setMute(false);
                    playing = true;
                    doEnabling();
                    String title = song.getTitle();
                    String artist = song.getArtist();
                    double duration = song.getTotalDuration();
                    System.out.println("Playing \"" + title + "\", by "
                            + artist + " (" + duration + " sec)");
                    song.play();
                    System.out.println("Playing complete.");
                    playing = false;
                    doEnabling();
                }
            });
            playThread.start();
        }
    }

    /**
     * Sets the current time display slider/label to show the given time in
     * seconds. Bounded to the song's total duration as reported by the song.
     */
    private void setCurrentTime(double time) {
        double total = song.getTotalDuration();
        time = Math.max(0, Math.min(total, time));
        currentTimeLabel.setText(String.format("%08.2f /", time));
        currentTimeSlider.setValue((int) (100 * time / total));
    }

    /**
     * Updates the total time label on the screen to the current total duration.
     */
    private void updateTotalTime() {
    	double total = song.getTotalDuration();
    	totalTimeLabel.setText(String.format("%08.2f", total) + " sec.");
    }
}

