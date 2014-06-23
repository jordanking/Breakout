package main;

/**
 * This is the class that paddles and bricks (not balls) inherit from. 
 * Brilliant.
 * 
 * @author Jordan
 * @since 1.0
 */
public class BreakoutObject {

	int xCoordinate;
	int yCoordinate;
	int width;
	int height;
	double friction;
	double restitution;

	public BreakoutObject() {
		xCoordinate = 0;
		yCoordinate = 0;
		width = 0;
		height = 0;
		friction = 0;
		restitution = 0;
	}

	/**
	 * Initializes the object with the data provided, and the default friction and restitution.
	 * These coefficients can not be specified by a constructor. 
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @param newWidth
	 * @param newHeight
	 * @return none
	 * @since 1.0
	 */
	protected void initializeWith(int newXCoordinate, int newYCoordinate, int newWidth,
			int newHeight) {
		// Set the coordinates
		xCoordinate = newXCoordinate;
		yCoordinate = newYCoordinate;
				
		// Set the dimensions
		width = newWidth;
		height = newHeight;
		
		// Set the coefficients
		friction = BreakoutBoard.DEFAULT_FRICTION;
		restitution = BreakoutBoard.DEFAULT_RESTITUTION;
	}

	
	/**
	 * Gets the x coordinate
	 * 
	 * @param none
	 * @return the xCoordinate
	 * @since 1.0
	 */
	public int getxCoordinate() {
		return xCoordinate;
	}

	
	/**
	 * Sets the x coordinate
	 * 
	 * @return none
	 * @param xCoordinate the xCoordinate to set
	 * @since 1.0
	 */
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	
	/**
	 * Gets the y coordinate
	 * 
	 * @param none
	 * @return the yCoordinate
	 * @since 1.0
	 */
	public int getyCoordinate() {
		return yCoordinate;
	}

	
	/**
	 * Sets the y coordinate
	 * 
	 * @param yCoordinate the yCoordinate to set
	 * @return none
	 * @since 1.0
	 */
	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	
	/**
	 * Gets the width of the object
	 * 
	 * @param none
	 * @return the width
	 * @since 1.0
	 */
	public int getWidth() {
		return width;
	}

	
	/**
	 * Sets the width of the object
	 * 
	 * @param width the width to set
	 * @return the width of the object
	 * @since 1.0
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	
	/**
	 * Gets the height of the object
	 * 
	 * @param none
	 * @return the height
	 * @since 1.0
	 */
	public int getHeight() {
		return height;
	}

	
	/**
	 * Sets the height of the object
	 * 
	 * @param height the height to set
	 * @return none
	 * @since 1.0
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	
	
	/**
	 * Gets the center x coordinate of the object
	 * 
	 * @param none
	 * @return the center's x coordinate
	 * @since 1.0
	 */
	public int getCenterXCoordinate() {
		return (xCoordinate+(width/2));
	}
	
	/**
	 * Gets the center y coordinate of the object
	 * 
	 * @param none
	 * @return the center's y coordinate
	 * @since 1.0
	 */
	public int getCenterYCoordinate() {
		return (yCoordinate+(height/2));
	}
	
	
	/**
	 * Returns the friction coefficient
	 * 
	 * @param none
	 * @return the friction
	 * @since 1.0
	 */
	public double getFriction() {
		return friction;
	}

	/**
	 * Sets the friction coefficient
	 * 
	 * @param friction the friction to set
	 * @return none
	 * @since 1.0
	 */
	public void setFriction(double friction) {
		this.friction = friction;
	}

	
	/**
	 * Returns the restitution coefficient of the object
	 * 
	 * @param none
	 * @return the restitution
	 * @since 1.0
	 */
	public double getRestitution() {
		return restitution;
	}

	/**
	 * Sets the restitution coefficient
	 * 
	 * @param restitution the restitution to set
	 * @return none
	 * @since 1.0
	 */
	public void setRestitution(double restitution) {
		this.restitution = restitution;
	}

	/**
	 * Returns a vector normal to the edge that the ball passed in is beside
	 * 
	 * @param ball the ball to find the edge close to
	 * @return normal the array of doubles that is a normal vector to the edge of the object
	 * @since 1.0
	 */
	public double[] getNormalForCollision(BreakoutBall ball) {
//		int topLeftX = xCoordinate;
//		int topLeftY = yCoordinate;
//		int topRightX = xCoordinate+width;
//		int topRightY = yCoordinate;
//		int bottomLeftX = xCoordinate;
//		int bottomLeftY = yCoordinate+height;
//		int bottomRightX = xCoordinate+width;
//		int bottomRightY = yCoordinate+height;

		
		
	    boolean isAboveTopLeftToBottomRight = isOnUpperSideOfLine(xCoordinate+width,
	    														  yCoordinate+height, 
	    														  xCoordinate, 
	    														  yCoordinate, 
	    														  (int) ball.getXCenter(), 
	    														  (int) ball.getYCenter());
	    
	    boolean isAboveTopRightToBottomLeft = isOnUpperSideOfLine(xCoordinate+width,
	    														  yCoordinate, 
	    														  xCoordinate, 
	    														  yCoordinate+height, 
	    														  (int) ball.getXCenter(), 
	    														  (int) ball.getYCenter());

	    if (isAboveTopLeftToBottomRight)
	    {
	        if (isAboveTopRightToBottomLeft)
	        {
	            //top edge has intersected
	            return new double[] {0,-1};
	        }
	        else
	        {
	            //right edge intersected
	            return new double[] {1,0};
	        }
	    }
	    else
	    {
	        if (isAboveTopRightToBottomLeft)
	        {
	            //left edge has intersected
	            return new double[] {-1,0};
	        }
	        else
	        {
	            //bottom edge intersected
	            return new double[] {0,1};
	        }
	    }
	}
	
	
	/**
	 * Returns whether a point is above a given line
	 * 
	 * @param corner1
	 * @param oppositeCorner
	 * @param ballCenter
	 * @return
	 */
	private boolean isOnUpperSideOfLine(int cornerOneX, int cornerOneY, int cornerTwoX, 
										int cornerTwoY, int ballCenterX, int ballCenterY)
	{
	    return ((cornerTwoX - cornerOneX) * (ballCenterY - cornerOneY) - 
	    		(cornerTwoY - cornerOneY) * (ballCenterX - cornerOneX)) > 0;
	}
	
	
	/**
	 * Checks to see if the paddle can move the move unit constant without leaving the screen
	 * 
	 * @param none
	 * @return canMoveLeft true or false
	 * @since 1.0
	 */
	public boolean canMoveLeft() {
		if (xCoordinate < BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME) {
			return false;
		}
		return true;
	}


	/**
	 * Moves the paddle to the left the number of move units
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public void moveLeft() {
		setxCoordinate(xCoordinate - BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME);
	}


	/**
	 * Checks to see if the paddle can move the move unit constant without leaving the screen
	 * 
	 * @param none
	 * @return canMoveRight true or false
	 * @since 1.0
	 */
	public boolean canMoveRight() {
		
		// combines all widths
		if (xCoordinate+width+BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME>BreakoutBoard.BOARD_WIDTH){
			return false;
		}
		return true;
	}


	/**
	 * Moves the paddle to the right the number of move units
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public void moveRight() {
		setxCoordinate(xCoordinate + BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME);		
	}
}