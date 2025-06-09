import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




@SuppressWarnings("serial")

public class PenguinCrossing extends JApplet implements Runnable, KeyListener, ActionListener {
	// Flag to determine if running as desktop app or applet
	private boolean runningAsApplication = false;


	//Declare strings for the images we will be using on the labels(characters) and background images
	private String fella = "src/assets/sprites/penguin.png",
	fella2 = "src/assets/sprites/pengwin.png",
	evil = "src/assets/sprites/baddie.png",
	evil2 = "src/assets/sprites/baddie2.png",
	evil3 = "src/assets/sprites/baddie2flipped.png",
	boss = "src/assets/sprites/baddie3flipped.png",
	end = "src/assets/winpic.jpg",
	title = "src/assets/title.jpg";;

	//Declare audioclips that will be assigned later 
	AudioClip audioClip1,
	audioClip2,
	audioClip3,
	audioClip4,
	audioClip5,
	audioClip6;



	//Selected images are applied to labels which will be our on screen characters 
	//and our title and win screens
	private JLabel thedude = new JLabel(new ImageIcon(fella));
	private JLabel thedude2 = new JLabel(new ImageIcon(fella2));
	private JLabel baddie = new JLabel(new ImageIcon(evil));
	private JLabel baddie2 = new JLabel(new ImageIcon(evil3));
	private JLabel baddie3 = new JLabel(new ImageIcon(evil));
	private JLabel baddie4 = new JLabel(new ImageIcon(evil2));
	private JLabel baddie5 = new JLabel(new ImageIcon(boss));
	private JLabel titlepic = new JLabel(new ImageIcon(title));
	private JLabel winpic = new JLabel(new ImageIcon(end));


	//Declare a button to start the game from the main page
	private JButton bStart = new JButton("Start Game");

	//game screen and title screen are declared 
	private Container titlescreen = getContentPane(),
	content = getContentPane();


	//A separate window for a winning screen, probably could have used a container as well
	private JFrame winScreen = new JFrame("The End");

	//X and Y variables for penguin and all enemy characters.
	//Probably a more efficient way to do this that hopefully will be in the next version

	int x, y, xbad = 0, ybad = 0, xbad2 = 0, ybad2 = 0, xbad3 = 0, ybad3 = 0, xbad4 = 0, ybad4 = 0, xbad5 = 0, ybad5 = 0;

	//Flag for enemy collisions, is used to stop the game.
	int collisionflag = 0;

	//Strings to control when the animation threads begin
	String TFB1 = "",
	TFB2 = "",
	TFB3 = "",
	TFB4 = "",
	TFB5 = "";



	public void init() {

	//Assign the first audio clip and start it looping

	try {
		if (runningAsApplication) {
			// Desktop mode - load audio from file system
			System.out.println("Loading title music for desktop mode");
			audioClip6 = Applet.newAudioClip(new java.io.File("src/assets/music/break1.wav").toURI().toURL());
		} else {
			// Applet mode - use getCodeBase
			audioClip6 = getAudioClip(getCodeBase(), "break1.wav");
		}
		audioClip6.loop();
	} catch (Exception e) {
		System.out.println("Failed to load title music: " + e.getMessage());
		e.printStackTrace();
	}

	//Set the size of the title screen window
	//Setup a new flow layout so our title pic and start button are shown

	this.setSize(500, 700);
	titlescreen.setLayout(new FlowLayout());
	titlescreen.setBackground(Color.BLACK);

	//Add the picture and start button, add an action listener for the button
	titlescreen.add(titlepic);
	titlescreen.add(bStart);
	bStart.addActionListener(this);


	}

	/*The action listener for the buttons controls the start of everything in 
	* the main game screen. The rest of the audio clips are assigned and the titlescreen contents made invisible
	* The original audio loop is stopped and a new one begins
	*/
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() instanceof JButton)

			if (evt.getSource() == bStart) {
				// Load audio files based on application mode
				try {
					if (runningAsApplication) {
						// Desktop mode - load audio from file system
						System.out.println("Loading game audio for desktop mode");
						audioClip1 = Applet.newAudioClip(new java.io.File("src/assets/music/zen.wav").toURI().toURL());
						audioClip2 = Applet.newAudioClip(new java.io.File("src/assets/music/howweroll.wav").toURI().toURL());
						audioClip3 = Applet.newAudioClip(new java.io.File("src/assets/sfx/fail.wav").toURI().toURL());
						audioClip4 = Applet.newAudioClip(new java.io.File("src/assets/sfx/applause.wav").toURI().toURL());
						audioClip5 = Applet.newAudioClip(new java.io.File("src/assets/sfx/applause.wav").toURI().toURL());
						audioClip6 = Applet.newAudioClip(new java.io.File("src/assets/music/break1.wav").toURI().toURL());
					} else {
						// Applet mode - use getCodeBase
						audioClip1 = getAudioClip(getCodeBase(), "zen.wav");
						audioClip2 = getAudioClip(getCodeBase(), "howweroll.wav");
						audioClip3 = getAudioClip(getCodeBase(), "fail.wav");
						audioClip4 = getAudioClip(getCodeBase(), "applause.wav");
						audioClip5 = getAudioClip(getCodeBase(), "applause.wav");
						audioClip6 = getAudioClip(getCodeBase(), "break1.wav");
					}
				} catch (Exception e) {
					System.out.println("Failed to load game audio: " + e.getMessage());
					e.printStackTrace();
				}

				bStart.setVisible(false);
				titlepic.setVisible(false);
				audioClip6.stop();
				audioClip1.loop();

				/*
				 * Set the size of the playing screen, the layout and the background color
				 * Layout is null because we want the objects to be able to move freely around the screen
				 */
				this.setSize(500, 700);
				this.setFocusable(true);
				this.requestFocusInWindow();
				addKeyListener(this);
				content.setLayout(null);
				content.setBackground(Color.CYAN);
				// addKeyListener(this);
				content.setFocusable(true);

				// Add the main character and each of the enemy characters, set them enabled and give them sizes
				content.add(thedude);
				thedude.setEnabled(true);
				thedude.setSize(50, 50);
				thedude.setLocation(x, y);



			/*Set the size of the playing screen, the layout and the background color
			* Layout is null because we want the objects to be able to move freely around the screen
			*/
			this.setSize(500, 700);
			this.setFocusable(true);
			this.requestFocusInWindow();
			addKeyListener(this);
			content.setLayout(null);
			content.setBackground(Color.CYAN);
			// addKeyListener(this);
			content.setFocusable(true);


			//Add the main character and each of the enemy characters, set them enabled and give them sizes
			content.add(thedude);
			thedude.setEnabled(true);
			thedude.setSize(50, 50);
			thedude.setLocation(x, y);

			content.add(baddie);
			baddie.setEnabled(true);
			baddie.setSize(50, 50);
			baddie.setLocation(xbad, ybad);
		
			content.add(baddie2);
			baddie2.setEnabled(true);
			baddie2.setSize(50, 50);
			baddie2.setLocation(xbad2, ybad2);

			content.add(baddie3);
			baddie3.setEnabled(true);
			baddie3.setSize(50, 50);
			baddie3.setLocation(xbad3, ybad4);

			content.add(baddie4);
			baddie4.setEnabled(true);
			baddie4.setSize(50, 50);
			baddie4.setLocation(xbad4, ybad4);

			content.add(baddie5);
			baddie5.setEnabled(true);
			baddie5.setSize(50, 50);
			baddie5.setLocation(xbad5, ybad5);

			/*Threads controlling the start of enemy animations
			*/

			TFB1 = "not tripped";
			Thread t1 = new Thread(this);
			t1.start();
			TFB2 = "not tripped";
			Thread t2 = new Thread(this);
			t2.start();
			TFB3 = "not tripped";
			Thread t3 = new Thread(this);
			t3.start();
			TFB4 = "not tripped";
			Thread t4 = new Thread(this);
			t4.start();
			TFB5 = "not tripped";
			Thread t5 = new Thread(this);
			t5.start();

		}
	}



	public void run() {
	/*Makes use of the threads by initiating the animation controls inside moveBaddy functions. Sets the xbad and ybad variables to
	give them a starting location. */

	
		if (TFB1.equals("not tripped")) {
			TFB1 = "tripped";
			xbad = 50;
			ybad = 150;
			System.out.println("here2");
			moveBaddy();
		}

		if (TFB2.equals("not tripped")) {
			TFB2 = "tripped";
			xbad2 = 75;
			ybad2 = 250;
			System.out.println("here3");
			moveBaddy2();
		}
		if (TFB3.equals("not tripped")) {
			TFB3 = "tripped";
			xbad3 = 100;
			ybad3 = 350;
			System.out.println("here4");
			moveBaddy3();
		}
		if (TFB4.equals("not tripped")) {
			TFB4 = "tripped";
			xbad4 = 125;
			ybad4 = 450;
			System.out.println("here5");
			moveBaddy4();
		}
		if (TFB5.equals("not tripped")) {
			TFB5 = "tripped";
			xbad5 = 125;
			ybad5 = 540;
			System.out.println("here6");
			moveBaddy5();
		}

	}


	/* Moving animations. In an infinite loop have the enemy travel across the screen. 
	*  When the enemy moves off the screen, start over.
	*  This makes the enemies travel infinitely across the screen.
	*  The enemy will also stop when a collision is detected (shown later)
	*/

	public void moveBaddy() {

		int count = 0;
		while (count >= 0) {
			if (collisionflag == 1) break;
			if (xbad > 500) {
			xbad = 0;
			} else {
			try {
				Thread.sleep(40L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baddie.setLocation(xbad += 2, ybad);
			collision();
			}
			count++;

		}
		/* In future versions, We can make a baddie class with get and sets for sleep time, coordinates, etc */
	}

	public void moveBaddy2() {

		int count = 0;
		while (count >= 0) {
			if (collisionflag == 1) break;
			if (xbad2 < 0) {
			xbad2 = 500;
			} else {
			try {
				Thread.sleep(12L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baddie2.setLocation(xbad2 -= 2, ybad2);
			}
			count++;

		}

	}

	public void moveBaddy3() {

		int count = 0;
		while (count >= 0) {
			if (collisionflag == 1) break;
			if (xbad3 > 500) {
			xbad3 = 0;
			} else {
			try {
				Thread.sleep(20L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baddie3.setLocation(xbad3 += 2, ybad3);
			}
			count++;

		}

	}

	public void moveBaddy4() {

		int count = 0;
		while (count >= 0) {
			if (collisionflag == 1) break;
			if (xbad4 > 500) {
			xbad4 = 0;
			} else {
			try {
				Thread.sleep(12L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baddie4.setLocation(xbad4 += 2, ybad4);
			}
			count++;

		}

	}

	public void moveBaddy5() {

		int count = 0;
		while (count >= 0) {
			if (collisionflag == 1) break;
			if (xbad5 < 0) {
			xbad5 = 500;
			} else {
			try {
				Thread.sleep(8L);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			baddie5.setLocation(xbad5 -= 2, ybad5);
			}
			count++;

		}

	}


	/* Collision Detection -- If the user position is equal to any of the same as any
	*  of the 4 corners of the enemy characters
	*  flag a collision which will stop the animations, and display a Game Over message
	*  Upon game over the sound stops looping, a fail sound plays and the game exits
	*/

	public void collision() {

		//Collisions for Baddie1
		//Corner A
		if ((x >= xbad) && (x <= xbad) && (y >= ybad) && (y <= ybad) ||
			//Corner B
			(x + 50 >= xbad) && (x + 50 <= xbad + 50) && (y >= ybad) && (y <= ybad + 50) ||
			//Corner C
			(x >= xbad) && (x <= xbad + 50) && (y + 50 >= ybad) && (y + 50 <= ybad + 50) ||
			//Corner D
			(x + 50 >= xbad) && (x + 50 <= xbad + 50) && (y + 50 >= ybad) && (y + 50 <= ybad + 50)) 
		{
			System.out.println("Collision with baddie1");
			collisionflag = 1;
			audioClip1.stop();
			audioClip3.play();
			JOptionPane.showMessageDialog(null, "Game Over.");
			System.exit(0);
		}

		//Collisions for Baddie2
		//Corner A
		if ((x >= xbad2) && (x <= xbad2) && (y >= ybad2) && (y <= ybad2) ||
			//Corner B
			(x + 50 >= xbad2) && (x + 50 <= xbad2 + 50) && (y >= ybad2) && (y <= ybad2 + 50) ||
			//Corner C
			(x >= xbad2) && (x <= xbad2 + 50) && (y + 50 >= ybad2) && (y + 50 <= ybad2 + 50) ||
			//Corner D
			(x + 50 >= xbad2) && (x + 50 <= xbad2 + 50) && (y + 50 >= ybad2) && (y + 50 <= ybad2 + 50)) 
		{
			System.out.println("Collision with baddie2");
			collisionflag = 1;
			audioClip1.stop();
			audioClip3.play();
			JOptionPane.showMessageDialog(null, "Game Over.");
			System.exit(0);
		}

		//Collisions for Baddie3
		//Corner A
		if ((x >= xbad3) && (x <= xbad3) && (y >= ybad3) && (y <= ybad3) ||
			//Corner B
			(x + 50 >= xbad3) && (x + 50 <= xbad3 + 50) && (y >= ybad3) && (y <= ybad3 + 50) ||
			//Corner C
			(x >= xbad3) && (x <= xbad3 + 50) && (y + 50 >= ybad3) && (y + 50 <= ybad3 + 50) ||
			//Corner D
			(x + 50 >= xbad3) && (x + 50 <= xbad3 + 50) && (y + 50 >= ybad3) && (y + 50 <= ybad3 + 50)) 
		{
			System.out.println("Collision with baddie3");
			collisionflag = 1;
			audioClip1.stop();
			audioClip3.play();
			JOptionPane.showMessageDialog(null, "Game Over.");
			System.exit(0);
		}

		//Collisions for Baddie4
		//Corner A
		if ((x >= xbad4) && (x <= xbad4) && (y >= ybad4) && (y <= ybad4) ||
			//Corner B
			(x + 50 >= xbad4) && (x + 50 <= xbad4 + 50) && (y >= ybad4) && (y <= ybad4 + 50) ||
			//Corner C
			(x >= xbad4) && (x <= xbad4 + 50) && (y + 50 >= ybad4) && (y + 50 <= ybad4 + 50) ||
			//Corner D
			(x + 50 >= xbad4) && (x + 50 <= xbad4 + 50) && (y + 50 >= ybad4) && (y + 50 <= ybad4 + 50)) 
		{
			System.out.println("Collision with baddie4");
			collisionflag = 1;
			audioClip1.stop();
			audioClip3.play();
			JOptionPane.showMessageDialog(null, "Game Over.");
			System.exit(0);
		}

		//Collisions for Baddie5
		//Corner A
		if ((x >= xbad5) && (x <= xbad5) && (y >= ybad5) && (y <= ybad5) ||
			//Corner B
			(x + 50 >= xbad5) && (x + 50 <= xbad5 + 50) && (y >= ybad5) && (y <= ybad5 + 50) ||
			//Corner C
			(x >= xbad5) && (x <= xbad5 + 50) && (y + 50 >= ybad5) && (y + 50 <= ybad5 + 50) ||
			//Corner D
			(x + 50 >= xbad5) && (x + 50 <= xbad5 + 50) && (y + 50 >= ybad5) && (y + 50 <= ybad5 + 50)) 
		{
			System.out.println("Collision with baddie5");
			collisionflag = 1;
			audioClip1.stop();
			audioClip3.play();
			JOptionPane.showMessageDialog(null, "Game Over.");
			System.exit(0);
		}

		/*
		This is the winning sequence: If the player reaches the "finish line" 
		Original character replaced by a winning character
		Enemies are disabled which greys them out.
		Display winning message
		Display a win screen containing an animation
		*/

		//Corner A
		if (y >= 620) {

			content.add(thedude2);
			thedude.setVisible(false);

			baddie.setEnabled(false);
			baddie2.setEnabled(false);
			baddie3.setEnabled(false);
			baddie4.setEnabled(false);
			baddie5.setEnabled(false);

			thedude2.setEnabled(true);
			thedude2.setSize(50, 60);
			thedude2.setLocation(x, y);

			/* Audio clip stops looping, a winning sound plays,
			*  Collision flag is used to stop the enemies from moving 
			*/

			audioClip1.stop();
			audioClip4.play();
			collisionflag = 1;

			//Display a win message
			JOptionPane.showMessageDialog(null, "YOU WIN!!!");


			//Show a new window which is our winning screen
			winScreen.setSize(800, 600);
			winScreen.setLocation(20, 170);
			winScreen.setVisible(true);
			winScreen.setLayout(null);

			//Display the winning picture and loop a new audio clip

			winScreen.add(winpic);
			winpic.setEnabled(true);
			winpic.setSize(800, 600);
			winpic.setLocation(0, 0);


			audioClip2.loop();
		}
	}




	//Key Events


	public void keyPressed(KeyEvent e) {

	int key = e.getKeyCode();


	if (key == KeyEvent.VK_LEFT) {
		System.out.println("KeyLeft");
		thedude.setLocation((x -= 4), y);

	}

	if (key == KeyEvent.VK_RIGHT) {
		System.out.println("KeyRight");
		thedude.setLocation((x += 4), y);


	}

	if (key == KeyEvent.VK_UP) {
		thedude.setLocation(x, (y -= 4));

	}

	if (key == KeyEvent.VK_DOWN) {
		thedude.setLocation(x, (y += 4));

	}

	}
	@Override
	public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub

	}




	@Override
	public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub

	}




	public void stop() {
	// Put your code here
	}

	public void destroy() {
	// Put your code here
	}

	/**
	 * Main method to run the application as a desktop app instead of an applet
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("Penguin Crossing");
		PenguinCrossing applet = new PenguinCrossing();
		applet.runningAsApplication = true; // Set BEFORE init()
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 700);
		frame.setContentPane(applet.getContentPane());
		applet.init(); // Call the applet's init method
		frame.setVisible(true);
		// Add KeyListener for desktop mode
		frame.addKeyListener(applet);
		frame.setFocusable(true);
		frame.requestFocus();
	}
}