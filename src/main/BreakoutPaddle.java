package main;

/**
 * This is the paddle for paddlin'
 * 
 * @author Jordan
 * @see BreakoutObject
 * @since 1.0
 */
public class BreakoutPaddle extends BreakoutObject {
	
	
	/**
	 * Default constructor, initializes with default values
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public BreakoutPaddle() {
		
		// Default coordinates are zero and sets the brick to the default size
		initializeWith(BreakoutBoard.DEFAULT_PADDLE_X_COORDINATE,
					   BreakoutBoard.DEFAULT_PADDLE_Y_COORDINATE,
					   BreakoutBoard.DEFAULT_PADDLE_WIDTH, 
					   BreakoutBoard.DEFAULT_PADDLE_HEIGHT);
	}
	
	
	/**
	 * Coordinate constructor, initializes with default values for dimensions.
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @return none
	 * @since 1.0
	 */
	public BreakoutPaddle(int newXCoordinate, int newYCoordinate) {
		
		// Default values for dimensions
		initializeWith(newXCoordinate, 
					   newYCoordinate, 
					   BreakoutBoard.DEFAULT_PADDLE_WIDTH, 
					   BreakoutBoard.DEFAULT_PADDLE_HEIGHT);
	}

	
	/**
	 * Coordinate and size constructor.
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @param newWidth
	 * @param newHeight
	 * @return none
	 * @since 1.0
	 */
	public BreakoutPaddle(int newXCoordinate, int newYCoordinate, int newWidth, int newHeight) {
		
		// All of the data is specified in this method
		initializeWith(newXCoordinate, 
					   newYCoordinate, 
					   newWidth, 
					   newHeight);		
	}
}
