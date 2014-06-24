package main;


//HONOR CODE
//
//Project 2 
//Name: Jordan King
//E-mail: jwk67@georgetown.edu
//Instructor: Singh 
//COSC 150 
//
//In accordance with the class policies and Georgetown's Honor Code, 
//I certify that, with the exceptions of the lecture and Blackboard 
//notes and those items noted below, I have neither given nor received 
//any assistance on this project. 
//
//I consulted some collision detection stuff on stack overflow here:
//http://stackoverflow.com/questions/19198359/\
//how-to-determine-which-side-of-a-rectangle-collides-with-a-circle
//Most other stuff may have been spawned from Java reference, but I struggled my own version out
//everywhere. I am confident that this work is unique in many ways to me, and if my comments don't
//reflect that I know what I am doing, feel free to inquire.
//
//
//The commenting should explain everything well enough that any student is able to code it back!
//
//
//Description: See below.
//


/**
 * Time log for science:
 * Date: Start - End   >> H:M  Totals Lines Summary
 * 2/27: 23:00 - 00:20 >> 1:20  1:20  400   Repurposed the star example to be clean for my game
 * 2/28: 15:10 - 18:40 >> 3:30  4:50  1078  Made everything draw but the ball. Almost to ball.
 * 2/28: 22:00 - 23:00 >> 1:00  5:50  1200  Got some art assets up in here.
 * 3/1 : 11:00 - 14:30 >> 3:30  9:20  1400  Worked on collision detection
 * 3/1 : 16:20 - 19:00 >> 2:40 12:00  1860  Got it playable: testing tonight!
 * 3/1 : 21:00 - 23:00 >> 2:00 14:00  more  Fixed bugs
 * 3/2 : 12:00 - 14:00 >> 2:00 16:00  more  Fiddled with stuff, time is underestimated here
 * 3/3 : 00:00 - 02:00 >> 2:00 18:00  2000  Worked on balance, refactoring too 
 * 3/6 : I have worked on this off and on, and have given up on the log.
 * 6/30: Lookin at it again!				 ~2200
 */


import java.awt.EventQueue;

import javax.swing.JFrame;


/**
 * IS the game. (many description:)
 * This is a pretty standard breakout game.
 * Highlights:
 * Collision computed accurate to physics using normal vectors for angular computations and various
 * modifiable physics constants to alter the reactions.
 * The art assets are all stored in the images package, allowing the path constants to be easily
 * changed to form an entirely different look to the game.
 * The scores are computed in nice modular ways with various constants, allowing the meta game to be
 * altered easily.
 * The game components can all be altered in the constants sections, if you want to play with 100
 * paddles and 10009 balls, I HAVE ENABLED YOU.
 * There are some minor odd behaviors with the collisions, but if any are of concern to you, feel 
 * free to ask me why they remain. (Trust me, I know whats there)
 * The bricks and paddle inherit from the same class, allowing their collisions to be easily 
 * standardized.
 * Everyone's favorite is the high score mechanic, allowing you to be better than other people.
 * The reset feature lets people quickly try to be better than other people.
 * Power ups and fully customizable levels are actually not far off, I focused on an infrastructure
 * that enables further content creation (even if none ever gets done).
 * The ball also is influenced by the motion of the paddle at the moment of contact. WOOOOOOOW.
 * There are dynamic levels that are crudely created, each one changes the scoring and some
 * change the board by adding rows (lame but a level factory would take more time than I have before
 * break).
 * 
 * @author jordan
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Breakout extends JFrame {

	/**
	 * Constructor.
	 * Calls the init UI to do the real work
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
    public Breakout() {
    	
    	// Set up the UI
    	initUI();
    	
    }
    
    
    /**
     * Initialize the GUI with simple settings, as the BreakoutBoard does most of the real work
     * 
     * @param none
     * @return none
     * @since 1.0
     */
    public void initUI() {
    	
    	// This is where the real magic happens
        add(new BreakoutBoard());

        // Set the title of the frame to the name of the game
        setTitle("Breakout");
        
        // Maybe just because there is a single panel
        pack();
        
        // Do not allow it to be resized because I am a tyrant
        setResizable(false);
        
        // This centers the game window, I will allow the peasants to move the game
        setLocationRelativeTo(null);
        
        // Exit cleanly so as to not be a jerk
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /**
     * I don't like this main, we will fix it later
     * 
     * @param args
     */
    public static void main(String[] args) {
		
    	// INVOKE THE GAME																	  later
    	invokeGame();
    	
    }
    

	/**
	 * Invokes the game and its board on its own thread
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private static void invokeGame() {
		
		// Create and tart up this magical runnable
        EventQueue.invokeLater(new Runnable() {
            
        	/**
        	 * Runs the game runnable (wow such method)
        	 * 
        	 * @param none
        	 * @return none
        	 * @see Runnable
        	 * @since 1.0
        	 */
            @Override
            public void run() {  
            	
            	// Create the game (wow so ez 2 do...)
                JFrame BreakoutGameFrame = new Breakout();
                
                // Show the game to my friends (YOU)(OH YOU, ME)
                BreakoutGameFrame.setVisible(true);                
            }
        });
	}
}
