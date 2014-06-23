package main;


/**
 * The main brick class for a normal breakout brick.
 * 
 * @author Jordan
 * @see BreakoutObject
 * @since 1.0
 */
public class BreakoutBrick extends BreakoutObject {
	
	
	/**
	 * Default constructor, initializes with default values
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public BreakoutBrick() {
		
		// Default coordinates are zero and sets the brick to the default size
		initializeWith(BreakoutBoard.DEFAULT_BRICK_X_COORDINATE,
					   BreakoutBoard.DEFAULT_BRICK_Y_COORDINATE,
					   BreakoutBoard.DEFAULT_BRICK_WIDTH, 
					   BreakoutBoard.DEFAULT_BRICK_HEIGHT);
	}
	
	
	/**
	 * Coordinate constructor, initializes with default values for dimensions.
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @return none
	 * @since 1.0
	 */
	public BreakoutBrick(int newXCoordinate, int newYCoordinate) {
		
		// Default values for dimensions
		initializeWith(newXCoordinate, 
					   newYCoordinate, 
					   BreakoutBoard.DEFAULT_BRICK_WIDTH, 
					   BreakoutBoard.DEFAULT_BRICK_HEIGHT);
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
	public BreakoutBrick(int newXCoordinate, int newYCoordinate, int newWidth, int newHeight) {
		
		// All of the data is specified in this method
		initializeWith(newXCoordinate, 
					   newYCoordinate, 
					   newWidth, 
					   newHeight);		
	}
}
