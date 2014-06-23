package main;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * The meat of the project! 
 * This is the board that has the list of balls, the list of bricks, and the list of paddles.
 * It updates a certain number of times each second, and computes collision, scores, and displays.
 * It draws everything to the screen each time it updates, with nicely modularized methods.
 * There are many constants in this project to allow the player to have oodles of fun! 
 * 
 * @author Jordan
 * @see javax.swing.JPanel
 * @version 1.0
 */
@SuppressWarnings("serial") // because i know nothing
public class BreakoutBoard extends JPanel implements KeyListener, Runnable {

	
	/*
	 * These constants create certain standards for the image assets (found through debugging):
	 * Frame size: h 1000 w 1000
	 * Brick size: h 41 px w 100 px
	 * Paddle size: h 17 px w 180 px
	 * Ball radius: 15 px
	 */
	
	
	// Constants (Should go somewhere pretty?) (The comments are dirty, but so are the constants...)
    static public final int BOARD_WIDTH = 1000;
    static public final int BOARD_HEIGHT = 800;
    
    // Default coefficients of the bricks and paddles
    
    /**
     * How much of the parallel velocity is kept (bounciness) (this is an experimental doc comment)
     */
    static public final double DEFAULT_FRICTION = 1.02; 
    
    /**
     * How much of the perpendicular velocity is kept (bounciness) (eh imma be lazy with these)
     */
    static public final double DEFAULT_RESTITUTION = 1.02;
    
	// Default constants for the bricks
	static public final int DEFAULT_BRICK_X_COORDINATE = 0; 
	static public final int DEFAULT_BRICK_Y_COORDINATE = 0;
	static public final int DEFAULT_BRICK_WIDTH = 100; // be sure to respect the art
	static public final int DEFAULT_BRICK_HEIGHT = 41; 
	
	// Default constants for the paddles
	static public final int DEFAULT_PADDLE_X_COORDINATE = 0;
	static public final int DEFAULT_PADDLE_Y_COORDINATE = 0;
	static public final int DEFAULT_PADDLE_WIDTH = 180; // THE ART IS FIXED
	static public final int DEFAULT_PADDLE_HEIGHT = 17;
	
	// Default constants for the balls
	static public final int DEFAULT_BALL_X_COORDINATE = 0;
	static public final int DEFAULT_BALL_Y_COORDINATE = 0;
	static public final double DEFAULT_BALL_X_VELOCITY = 0; // starting velocity for ball
	static public final double DEFAULT_BALL_Y_VELOCITY = -2;
	static public final int DEFAULT_BALL_RADIUS = 15; // respect art
	static public final double RANDOM_STARTING_RANGE_X = 2; // so each game begins differently
	static public final double RANDOM_STARTING_RANGE_Y = .1;


	// Numbers for computing brick list
	static public final double NUMBER_OF_INITIAL_ROWS = 2; // because rules
	static public final double MAX_NUMBER_OF_BRICK_ROWS = 8;
	static public final double NUMBER_OF_BRICK_COLUMNS = 8;
	static public final double PERCENT_FOR_SIDE_PADDING = 0.1;
	static public final double PERCENT_FOR_TOP_PADDING = 0.1;

	// Numbers for computing paddle list
	static public final double NUMBER_OF_INITIAL_PADDLES = 1; // too many will fill the bottom

	// Numbers for computing ball list
	static public final double NUMBER_OF_INITIAL_BALLS = 3; // CHANGE THIS FOR FUN
	static public final boolean EACH_PADDLE_HAS_BALL = true;
	
	// Rules #nofun
	static public final double MAX_NEGATIVE_BALL_VELOCITY = -4; // IMPORTANT
	static public final double MAX_POSITIVE_BALL_VELOCITY = 4;
	static public final double PADDLE_INFLUENCE_ON_VELCITY = 0.5; // AWESOME
	static public final int PADDLE_MOVE_PIXELS_PER_FRAME = 5; // PREFERENCE
	
	// Meta Strings
	static public final String SCORE_DISPLAY_STRING = "Score: ";
	static public final String LEVEL_DISPLAY_STRING = "Level: ";
	static public final String PAUSED_DISPLAY_STRING = "Paused!";
	static public final String RESTART_TIP_DISPLAY_STRING = "Press 'r' to reset the game.";
	static public final String GAME_OVER_DISPLAY_STRING = "Game Over!";
	static public final String HIGH_SCORE_DISPLAY_STRING = "High Score: ";

	// Control values
	static public final int BRICK_POINT_SCORE_VALUE = 1;
	static public final int LEVEL_CLEAR_SCORE_VALUE = 10;
	static public final Color TEXT_COLOR = Color.WHITE;
	static public final boolean GAME_PAUSES_ON_LEVEL_CLEAR = false;

	// Image paths
	static public final String BRICK_IMAGE_PATH = "images/brick_rainbow.png";
	static public final String PADDLE_IMAGE_PATH = "images/paddle_180x17_green_black.png";
	static public final String BALL_IMAGE_PATH = "images/ball_30_blue_swirl.png";
	static public final String BACKGROUND_IMAGE_PATH = "images/back_1000x1000_rainbow.png";
	
	// High score path (in the images folder to trick you)
	static public final String HIGH_SCORE_FILE_PATH = "images/high_score.txt";
	
    // The animation ticks per second since timers suck (DOESN'T LIKE TO CHANGE)
    private final int TIMER_TICK = 5;

    // Image members
    private Image brickImageAsset;
    private Image paddleImageAsset;
    private Image ballImageAsset;
    private Image backImageAsset;

    
    // The cyber-artist
    private Thread animator;
    
    // The keyboard states
    private boolean leftArrowPressed;
    private boolean rightArrowPressed;
    
    // Game states
    private boolean gamePaused;
    private boolean gameover;

    
    // The game's data model
    private ArrayList<BreakoutBrick> brickList;
    private ArrayList<BreakoutPaddle> paddleList;
    private ArrayList<BreakoutBall> ballList;
    
    // The meta assets
    private int playerScore;
    private int playerLevel;
    private int highScore;

    // The timer
    Timer frameTimer;
    
    

    /**
     * Constructor. 
     * Loads images and initializes board
     * 
     * @param none
     * @return none
     * @since 1.0
     */
    public BreakoutBoard() {
    	
    	// Load images
        loadImages();
        
    	loadHighScore();
        
        // Add the key listener
        addKeyListener(this);
        
        // set it focusable
        setFocusable(true);
        
        // Initialize board (wow such method comment)
        initializeBoard();
        
        // Initialize keyboard states
        initializeKeyboardState();
        
    }

	/**
	 * Loads the high score from a file in the resources
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void loadHighScore() {
		// The streams we will use
    	InputStream inputStream = null;
    	BufferedReader bufferedReader = null;
        
        // Load the high score
        try {
        	
        	// Open the hs file
        	inputStream= getClass().getClassLoader().getResource(HIGH_SCORE_FILE_PATH).openStream();
        	bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        	
        	// read the hs
        	try {
        		if (bufferedReader.ready()) {
        			highScore = (int) Integer.parseInt(bufferedReader.readLine());
        		}
			} catch (NumberFormatException exception) {
				exception.printStackTrace();
			}
        	
        } catch (IOException except){ 

        	System.err.println("Failed to load high score"); 

        } finally {
			// Close the reader whether it failed to open or not
			try {
				if (!(bufferedReader == null)){
					bufferedReader.close();
				}
			} catch (IOException exception) {
				System.err.println("File load error.");
			} 	        	
        }
	}

	/**
	 * Initializes the keyboard states to no keys being pressed.
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializeKeyboardState() {
		
		// no keys pressed to start
		leftArrowPressed = false;
        rightArrowPressed = false;
        gamePaused = true;
	}

    /**
     * Gets an image and store it as an imageIcon fo fun?
     * 
     * @param none
     * @return none
     * @since 1.0
     */
    private void loadImages() {
        
        // Then hold on to it... for use.
        brickImageAsset = Toolkit.getDefaultToolkit().getImage(
        		getClass().getClassLoader().getResource(BRICK_IMAGE_PATH));
        paddleImageAsset = Toolkit.getDefaultToolkit().getImage(
        		getClass().getClassLoader().getResource(PADDLE_IMAGE_PATH));
        ballImageAsset = Toolkit.getDefaultToolkit().getImage(
        		getClass().getClassLoader().getResource(BALL_IMAGE_PATH));
        backImageAsset = Toolkit.getDefaultToolkit().getImage(
        		getClass().getClassLoader().getResource(BACKGROUND_IMAGE_PATH));;

    }

    
    /**
     * Setup the parameters for the board.
     * 
     * @param none
     * @return none
     * @since 1.0
     */
    private void initializeBoard() {

    	// Set background color (method of a JPanel!)
        setBackground(Color.WHITE);
        
        // Set JPanel size using constants to stay cool
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        // Set the layout to allow components
        setLayout(null);
        
        /*
         * Sets whether a component should use a buffer to paint. Doing this can
         * help make animation smoother. If true, all the drawing from this 
         * will be painted in the buffer offscreen before onscreen - 
         * helps reduce clunky animation.
         */
        setDoubleBuffered(true);
        
        // Initialize the meta
        initializeMeta();
        
        // Initialize data model
        initializeDataModel();
        
        // game not over!
        gameover = false;
    }

	/**
	 * Initializes the meta info
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializeMeta() {
		// Score starts at 0
		playerScore = 0;
		
		// level starts at 0
		playerLevel = 1;
		
		// game over is not over!
		gameover = false;
	}

	/**
	 * Initializes the data model with the bricks, balls, and paddles.
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializeDataModel() {
		brickList = new ArrayList<BreakoutBrick>();
		paddleList = new ArrayList<BreakoutPaddle>();
		ballList = new ArrayList<BreakoutBall>();

		
		// Initialize the brick model
		initializeBricks();
		
		// Initialize the paddles (Plural in case I want to get fancy someday.)
		initializePaddles();
		
		// Initialize the balls
		initializeBalls();
		
	}

	
	/**
	 * Figures out the initial data for the bricks using secret algorithms
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializeBricks() {
		
		// Get a brick handle
		BreakoutBrick brick;
		
		// Computed numbers
		int newXCoordinate;
		int newYCoordinate;
		int newWidth;
		int newHeight;
		
		// The initial rows are due to the project description
		double numberOfRows = playerLevel + NUMBER_OF_INITIAL_ROWS - 1;
		
		// Make sure we don't exceed max level
		if (numberOfRows > MAX_NUMBER_OF_BRICK_ROWS){
			numberOfRows = MAX_NUMBER_OF_BRICK_ROWS;
		}
		
		// For rows of bricks
		for (int row = 0; row < numberOfRows; row++) {
			
			// For columns of bricks
			for (int column = 0; column < NUMBER_OF_BRICK_COLUMNS; column++) {			
				
				// now with a default!
				newWidth = DEFAULT_BRICK_WIDTH;
				
				// The height is the default
				newHeight = DEFAULT_BRICK_HEIGHT;
				
				// The new x coordinate is 10% plus the widths of previous blocks
				newXCoordinate = (int) Math.ceil(BOARD_WIDTH*(PERCENT_FOR_SIDE_PADDING) + 
						column*newWidth);
				
				// The new y coordinate is 10% down, plus the height of previous bricks
				newYCoordinate = (int) Math.ceil(BOARD_HEIGHT*(PERCENT_FOR_TOP_PADDING) + 
						row*newHeight);
				
				// Make the brick with these coordinates
				brick = new BreakoutBrick(newXCoordinate, 
										  newYCoordinate, 
										  newWidth, 
										  newHeight);
				
				// Add the brick
				brickList.add(brick);
			}
		}
	}
	
	
	/**
	 * Figures out the initial data for the paddles using less secret algorithms than the bricks
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializePaddles() {
		
		// Get a paddle handle (a reference; it is not a metaphor)
		BreakoutPaddle newPaddle;
		
		// Computed numbers
		int newXCoordinate;
		int newYCoordinate;
		int newWidth;
		int newHeight;

		// For number of paddles (prolly just 1?)
		for (int paddle = 0; paddle < NUMBER_OF_INITIAL_PADDLES; paddle++) {
			
			// The width is the default size 
			newWidth = DEFAULT_PADDLE_WIDTH;
			
			// The height is the default size
			newHeight = DEFAULT_PADDLE_HEIGHT;


			// The new x coordinate is the middle. or the thirds. etc.
			newXCoordinate = (int) Math.ceil((paddle+1)*(BOARD_WIDTH/(NUMBER_OF_INITIAL_PADDLES+1))- 
					(newWidth/2));

			// The new y coordinate is the bottom minus the height
			newYCoordinate = (int) Math.ceil(BOARD_HEIGHT - newHeight);

			// Make the paddle with these coordinates
			newPaddle = new BreakoutPaddle(newXCoordinate, 
										   newYCoordinate, 
										   newWidth, 
										   newHeight);
			
			// Add the paddle
			paddleList.add(newPaddle);

		}
	}
	
	
	/**
	 * Figures out the initial data for the balls using no algorithm
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void initializeBalls() {
		
		// Get a ball handle 
		BreakoutBall newBall;
		
		// Computed numbers
		int newXCoordinate;
		int newYCoordinate;
		double newRadius;
		double newXVelocity;
		double newYVelocity;

		// For number of balls (prolly just 1?)
		for (int ballNumber = 0; ballNumber < NUMBER_OF_INITIAL_BALLS; ballNumber++) {
			
			// A random number to make each game start differently: ((%) * (2*def))-def = +/- %def
			double randomNumberX = (Math.random() * (2 * RANDOM_STARTING_RANGE_X)) - 
															RANDOM_STARTING_RANGE_X;
			double randomNumberY = (Math.random() * (2 * RANDOM_STARTING_RANGE_Y)) - 
															RANDOM_STARTING_RANGE_Y;

			// The radius is the default size 
			newRadius = DEFAULT_BALL_RADIUS;

			// The x is the default plus some variability
			newXVelocity = DEFAULT_BALL_X_VELOCITY + randomNumberX;
			
			// The y is the default plus some variability
			newYVelocity = DEFAULT_BALL_Y_VELOCITY + randomNumberY;


			// The new x coordinate is above the middle of the first paddle
			newXCoordinate = paddleList.get(0).getxCoordinate() + 
					paddleList.get(0).getWidth()/2;

			// The new y coordinate is the first paddle minus the height
			newYCoordinate = paddleList.get(0).getyCoordinate() - (int) newRadius;

			// Make the ball with these coordinates
			newBall = new BreakoutBall(newXCoordinate,
									   newYCoordinate,
									   newXVelocity,
									   newYVelocity,
									   newRadius);
			
			// Add the ball
			ballList.add(newBall);

		}
	}


    /**
     * Overridden addNotify() method - this makes the the component on a new thread.
     * We want it displayed on a new thread.
     * Swing calls this method.
     * 
     * @param none
     * @return none
     * @see jpanel
     * @since 1.0
     */
    @Override
    public void addNotify() {
    	// call the supah
        super.addNotify();

        // create and run the magical artist
        animator = new Thread(this);
        animator.start();
    } 

    
    /**
     * Call the paintComponent of the parent class and the drawing methods
     * 
     * @param graphicsObject where does it come from where did it go (only swing knows)
     * @return none
     * @see jpanel
     * @since 1.0 
     */
    @Override
    public void paintComponent(Graphics graphicsObject) {
    	
    	// draw all the other stuff (actually erases it!)
        super.paintComponent(graphicsObject);
        
    	// This is a lot of magic and a picture
        Graphics2D graphicsObject2d = (Graphics2D) graphicsObject;
        
        // make it aa
    	graphicsObject2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
  	          RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    	graphicsObject2d.setRenderingHint(RenderingHints.KEY_RENDERING,
  	          RenderingHints.VALUE_RENDER_QUALITY);
        
        // draw background
        drawBackground(graphicsObject2d);
        
        // draw my stuff (what else is there really?)
        drawBricks(graphicsObject2d);
        drawPaddles(graphicsObject2d);
        drawBalls(graphicsObject2d);
        
        // Draw the meta
        drawMeta(graphicsObject2d);

        // Explicitly release the memory storing g. Do not wait for garbage collection vrooooom
        graphicsObject2d.dispose();
    }
    
    
    /**
     * Draws the background on the screen
     * 
     * @param graphicsObject
     * @return none
     * @since 1.0
     */
    private void drawBackground(Graphics2D graphicsObject2d) {
    	
    	// draw the background
    	graphicsObject2d.drawImage(backImageAsset, 0, 0, this);
        
        // Synchronize the graphics state - now is the the to draw! (more magic)
        Toolkit.getDefaultToolkit().sync();
        
        // DONT DISPOSE OF THE GRAPHICS OBJECT HERE ITLL SUCK EVERYTHING UP
    }
    
    
    /**
     * Draws the bricks on the screen
     * 
     * @param graphicsObject
     * @return none
     * @since 1.0
     */
    private void drawBricks(Graphics2D graphicsObject2d) {
    	
    	// iterate through all bricks and draw them
    	for (BreakoutBrick brick: brickList) {
    		graphicsObject2d.drawImage(brickImageAsset, brick.getxCoordinate(), brick.getyCoordinate(), this);
		}
        
        // Synchronize the graphics state - now is the the to draw! (more magic)
        Toolkit.getDefaultToolkit().sync();
        
        // DONT DISPOSE OF THE GRAPHICS OBJECT HERE ITLL SUCK EVERYTHING UP
    }

    
    /**
     * Draws the paddles on the screen
     * 
     * @param graphicsObject
     * @return none
     * @since 1.0
     */
    private void drawPaddles(Graphics2D graphicsObject2d) {

    	// iterate through all paddles and draw them
    	for (BreakoutPaddle paddle: paddleList) {
    		graphicsObject2d.drawImage(paddleImageAsset, paddle.getxCoordinate(),
    									paddle.getyCoordinate(), this);
		}
        
        // Synchronize the graphics state - now is the the to draw! (more magic)
        Toolkit.getDefaultToolkit().sync();
        
        // DONT DELETE THE OBJECT
    }
    
    
    /**
     * Draws the balls on the screen
     * 
     * @param graphicsObject
     * @return none
     * @since 1.0
     */
    private void drawBalls(Graphics2D graphicsObject2d) {
    	
    	// iterate through all balls and draw them
    	for (BreakoutBall ball: ballList) {
    		graphicsObject2d.drawImage(ballImageAsset, 
    						 ball.getLeftEdgeCoordinate(), 
    						 ball.getTopEdgeCoordinate(), 
    						 this);
		}
        
        // Synchronize the graphics state - now is the the to draw! (more magic)
        Toolkit.getDefaultToolkit().sync();
        
    }
    
    
    /**
     * Draws the meta information on the screen
     * 
     * @param graphicsObject
     * @return none
     * @since 1.0
     */
    private void drawMeta(Graphics2D graphicsObject2d) {
    	
    	// Set the font and color
        graphicsObject2d.setFont(new Font("Times", Font.BOLD, 25));
        graphicsObject2d.setColor(TEXT_COLOR);
        
        // Draw the score
   		graphicsObject2d.drawString(HIGH_SCORE_DISPLAY_STRING + highScore + "      " +
   				 SCORE_DISPLAY_STRING + playerScore, 5, 25);
   		
        // Draw the high score
//   		graphicsObject2d.drawString(HIGH_SCORE_DISPLAY_STRING + highScore, 5, 25);
   		
   		// Draw the level
   		graphicsObject2d.drawString(LEVEL_DISPLAY_STRING + playerLevel, 5, 50);
   		
   		// If paused, display that it is paused
   		if (gamePaused) {
   			drawPauseMeta(graphicsObject2d);
   		}

   		// If game over, draw game over too
   		if (gameover) {
   			drawGameOverMeta(graphicsObject2d);
   		}
        
        // Synchronize the graphics state - now is the the to draw! (more magic)
        Toolkit.getDefaultToolkit().sync();
    }

	/**
	 * Draws everything that should appear when the game ends
	 * 
	 * @param graphicsObject2d
	 * @return none
	 * @since 1.0
	 */
	private void drawGameOverMeta(Graphics2D graphicsObject2d) {
		
		// Draw the game over message
		graphicsObject2d.drawString(GAME_OVER_DISPLAY_STRING, 5, 75);
	}

    
	/**
	 * Draws all of the things that the pause screen needs
	 * 
	 * @param graphicsObject2d
	 * @return none
	 * @since 1.0
	 */
	private void drawPauseMeta(Graphics2D graphicsObject2d) {
		
		// Just a heads up
		graphicsObject2d.drawString(PAUSED_DISPLAY_STRING, 5, 775);
		graphicsObject2d.drawString(RESTART_TIP_DISPLAY_STRING, 5, 750);

	}

    
    /**
     * Update the model!
     * 
     * @param none
     * @return none
     * @since 1.0
     */
    private void cycle() {
    	
    	boolean hitFloor = false;
    	
    	// Update the paddles
    	updatePaddlePositions();
    	
    	// Check updates relative to each ball:
        for (int ballIndex = 0; ballIndex < ballList.size();) {
        	
        	// Update the ball position
        	ballList.get(ballIndex).updatePosition();
        	
        	// Check Brick collision
        	checkBrickCollision(ballList.get(ballIndex));
        	
        	// Check for collision with paddles
        	checkPaddleCollision(ballList.get(ballIndex));
        	
        	// Check for collision with balls (no collision)
        	checkBallCollision(ballList.get(ballIndex));
        	
        	// Check for collision with walls (and floor!)
        	hitFloor = checkWallCollision(ballList.get(ballIndex));
        	
        	// missed the paddle! uh oh!
        	if (hitFloor) {
        		
        		// remove ball (maybe mark it so it can do behaviors?)
        		ballList.remove(ballIndex);
        		
        	} else {
        		
        		// check next ball!
        		ballIndex++;
        	}
        	
        }
        
        // Update Meta
        updateMeta();

    }

	/**
	 * Updates the meta information
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void updateMeta() {
		
		// If all bricks gone
		if (brickList.size() == 0) {
        	
        	// Update the score and level upon clear
        	playerScore += LEVEL_CLEAR_SCORE_VALUE * playerLevel;
        	playerLevel += 1;
        	
        	// Put more bricks up, give more balls
        	initializeBricks();
        	initializeBalls();
        	
        	// for convenience
        	gamePaused = GAME_PAUSES_ON_LEVEL_CLEAR;
        	
        	// log the number of balls to console for fun
        	System.out.println(playerLevel + ": " + ballList.size());
        }
        
        // If the game is over
		if (ballList.size() == 0 && !gameover){
			gameover();
		}
	}

	/**
	 * Performs the actions required upon a loss
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void gameover() {
		gameover = true;

		// Only write the high score if it is necessary.
		if (playerScore > highScore) {
			highScore = playerScore;
			writeHighScore();

		}
		
	}

	
	/**
	 * Writes the high score to the high score file
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void writeHighScore() {

		Writer writer = null;

		try {
			// Open the file
			File file = new File(
					getClass().getClassLoader().getResource(HIGH_SCORE_FILE_PATH).getPath());
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file), "utf-8"));
			
			// Write to the file
			writer.write(String.valueOf(highScore));
			
			
		} catch (IOException ex) {
			
			// failure is not an option
			System.err.println("Failed to write highscore");
		} finally {

			// Always close
			try {
				writer.close();
			} catch (Exception ex) {
				;
			}
		}
	}
	

	/**
	 * Updates the paddles based on the state of the keyboard
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void updatePaddlePositions() {
		if (leftArrowPressed == true) {

			// Make sure the first paddle isn't on the edge
			if (paddleList.get(0).canMoveLeft()) {
				
				// Move all paddles
				for (BreakoutPaddle paddle: paddleList) {
					paddle.moveLeft();
				}
			}
		} else if (rightArrowPressed == true) {
			
			// make sure the last paddle is not all the way to the right
			if (paddleList.get(paddleList.size()-1).canMoveRight()) {
				
				// move all paddles to the right
				for (BreakoutPaddle paddle: paddleList) {
					paddle.moveRight();
				}
			}
		}
	}

	/**
	 * Computes the collision and effects for a given ball with bricks
	 * 
	 * @param ball the ball to run brick collision checks on
	 * @return none
	 * @since 1.0
	 */
	private void checkBrickCollision(BreakoutBall ball) {
		
		// Check for collision with all bricks
		for (int brickIndex = 0; brickIndex < brickList.size();) {
			
			// If the ball collides with the brick, remove it
			if (ball.touchesBreakoutObject(brickList.get(brickIndex))) {
				
				// This will be the normal vector
				double[] normal;
				
				// Get a normal vector to the edge collided with
				normal = brickList.get(brickIndex).getNormalForCollision(ball);
				
				// Tell the ball to bounce off the normal vector and attributes
				ball.bounce(normal, brickList.get(brickIndex).getFriction(), 
						brickList.get(brickIndex).getRestitution());
				
				// Remove the brick
				brickList.remove(brickIndex);
				
				// Add points to the score
				playerScore += BRICK_POINT_SCORE_VALUE * playerLevel;
				
				// Stop checking brick collision for this frame:
				break;
				
			} else {
				
				// Only increment if you don't remove, as the rest of the bricks slide over
				brickIndex++;
			}
		}
	}

	
	
	/**
	 * Computes the collision and effects for a given ball with paddles
	 * 
	 * @param ball the ball to run paddle collision checks on
	 * @return none
	 * @since 1.0
	 */
	private void checkPaddleCollision(BreakoutBall ball) {
		
		// Check for collision with all bricks
		for (int paddleIndex = 0; paddleIndex < paddleList.size();) {
			
			// If the ball collides with the paddle, bounce
			if (ball.touchesBreakoutObject(paddleList.get(paddleIndex))) {
				
				// This will be the normal vector
				double[] normal;
				
				// Get a normal vector to the edge collided with
				normal = paddleList.get(paddleIndex).getNormalForCollision(ball);
				
				// Tell the ball to bounce off the normal vector and attributes
				ball.bounce(normal, paddleList.get(paddleIndex).getFriction(), 
						paddleList.get(paddleIndex).getRestitution());
				
				// This is to fix any computational error that lands the ball in the paddle...
				ball.setYCenter(ball.getYCenter()-2);
				
				// This is to add velocity based on paddle velocity
				if (leftArrowPressed) {
					ball.setXVelocity(ball.getXVelocity() - PADDLE_INFLUENCE_ON_VELCITY);
				}
				
				if (rightArrowPressed) {
					ball.setXVelocity(ball.getXVelocity() + PADDLE_INFLUENCE_ON_VELCITY);
				}
				
				
				// Stop checking paddle collision for this frame:
				break;
				
			}
			
			// Go to the next paddle
			paddleIndex++;
		}
	}
	
	
	/**
	 * Computes the collision and effects for a given ball with other balls.
	 * I currently have the balls ignoring each other.
	 * 
	 * @param ball the ball to run ball collision checks on
	 * @return none
	 * @since 1.0
	 */
	private void checkBallCollision(BreakoutBall ball) {
		
		// do nothing
	}
	
	
	/**
	 * Computes the collision and effects for a given ball with walls
	 * 
	 * @param ball the ball to run wall collision checks on
	 * @return hitFloor whether or not it hit the bottom (bad)
	 * @since 1.0
	 */
	private boolean checkWallCollision(BreakoutBall ball) {
		
		// Check the left wall:
		if (ball.getLeftEdgeCoordinate() < 0) {
			
			// bounce off of the wall with the normal vector
			ball.bounce(new double[] {1, 0}, 1.0, 1.0);
			return false;
		} else if (ball.getXCenter() + ball.getRadius() > BOARD_WIDTH) {
			
			// bounce off of the wall
			ball.bounce(new double[] {-1, 0}, 1.0, 1.0);
			return false;
		} else if (ball.getTopEdgeCoordinate() < 0) {
			
			// bounce off of the top
			ball.bounce(new double[] {0, 1}, 1.0, 1.0);
			return false;
		} else if (ball.getYCenter() + ball.getRadius() > BOARD_HEIGHT) {
			
			return true;
		}
		return false;
	}
	
	
	/**
	 * Calls all of the necessary stuff to reset the game board.
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	private void reset() {
		
		// Reset meta
		initializeMeta();
		
		// Reset balls and bricks
		initializeDataModel();
	}
	
    
    /**
     * Run method of the Runnable Board.
     * 
     * @param none
     * @return none
     * @see Runnable
     * @since 1.0
     */
    @Override
    public void run() {
    	        
        // The timer that draws
        Timer frameTimer = new Timer(TIMER_TICK, new ActionListener() {
        	
        	// inner method for ActionListener for this timer
            public void actionPerformed(ActionEvent timeTick) {
            	
            	// Update model
            	if (!gamePaused) {
            		cycle();
            	}

                // Update the view
                repaint();
                
            }
            
        });
        
        // Start the timer
        frameTimer.start();
        
    }
	
    
	/**
	 * Detects when a key is typed
	 * 
	 * @param keyEvent
	 * @return none
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 * @since 1.0
	 */
	@Override
	public void keyTyped(KeyEvent keyEvent) {

		// If the game is paused, unpause, and vice versa
		if (keyEvent.getKeyChar() == ' ') {
			gamePaused = gamePaused ? false : true;
		}
		
		// If r is pressed, restart the game
		if (keyEvent.getKeyChar() == 'r') {
			reset();
		}
		
	}
	

	/**
	 * Detects when a key is pressed
	 * 
	 * @param keyEvent
	 * @return none
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 * @since none
	 */
	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == 37) {
			leftArrowPressed = true;
		} else if (keyEvent.getKeyCode() == 39) {
			rightArrowPressed = true;
		}
	}

	
	/**
	 * Detects when a key is released
	 * 
	 * @param keyEvent
	 * @return
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 * @since 1.0
	 */
	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == 37) {
			leftArrowPressed = false;
		} else if (keyEvent.getKeyCode() == 39) {
			rightArrowPressed = false;
		}
	}	
}