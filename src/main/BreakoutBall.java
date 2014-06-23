package main;


/**
 * This is the ball class.
 * Less Brilliant.
 * The x and y coordinates correspond to the origin, not the draw corner.
 * 
 * @author Jordan
 * @since 1.0
 */
public class BreakoutBall {

	double xCenter;
	double yCenter;
	double xVelocity;
	double yVelocity;
	double radius;

	
	/**
	 * Default constructor, initializes with default values
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public BreakoutBall() {
		
		// Default ball stuff
		initializeWith(BreakoutBoard.DEFAULT_BALL_X_COORDINATE, 
					   BreakoutBoard.DEFAULT_BALL_Y_COORDINATE, 
					   BreakoutBoard.DEFAULT_BALL_X_VELOCITY, 
					   BreakoutBoard.DEFAULT_BALL_Y_VELOCITY,
					   BreakoutBoard.DEFAULT_BALL_RADIUS);
	}
	
	
	/**
	 * Coordinate constructor, initializes with default values for dimensions.
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @return none
	 * @since 1.0
	 */
	public BreakoutBall(double newXCoordinate, double newYCoordinate) {
		
		// Default values for dimensions
		initializeWith(newXCoordinate, 
				   	   newYCoordinate, 
				   	   BreakoutBoard.DEFAULT_BALL_X_VELOCITY, 
				   	   BreakoutBoard.DEFAULT_BALL_Y_VELOCITY,
				   	   BreakoutBoard.DEFAULT_BALL_RADIUS);
	}

	
	/**
	 * Explicit in everything constructor
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @param newXVelocity
	 * @param newYVelocity
	 * @param newRadius
	 * @return none
	 * @since 1.0
	 */
	public BreakoutBall(double newXCoordinate, double newYCoordinate, double newXVelocity, 
						 double newYVelocity, double newRadius) {
		
		// All of the data is specified in this method
		initializeWith(newXCoordinate, 
					   newYCoordinate, 
					   newXVelocity, 
					   newYVelocity, 
					   newRadius);
	}

	
	/**
	 * Initializes the ball with the data provided
	 * 
	 * @param newXCoordinate
	 * @param newYCoordinate
	 * @param newXVelocity
	 * @param newYVelocity
	 * @param newRadius
	 * @return none
	 * @since 1.0
	 */
	protected void initializeWith(double newXCoordinate, double newYCoordinate, double newXVelocity,
			double newYVelocity, double newRadius) {
		// Set the coordinates
		xCenter = newXCoordinate;
		yCenter = newYCoordinate;
			
		// Set the velocities
		xVelocity = newXVelocity;
		yVelocity = newYVelocity;
				
		// Set the dimensions
		radius = newRadius;
	}

	
	/**
	 * Gets the x coordinate of the center
	 * 
	 * @param none
	 * @return the xCoordinate of the center
	 * @since 1.0
	 */
	public double getXCenter() {
		return xCenter;
	}

	
	/**
	 * Sets the x coordinate of the center
	 * 
	 * @return none
	 * @param xCoordinate the xCoordinate to set
	 * @since 1.0
	 */
	public void setXCenter(double xCoordinate) {
		this.xCenter = xCoordinate;
	}

	
	/**
	 * Gets the y coordinate of the center
	 * 
	 * @param none
	 * @return the yCoordinate of the center
	 * @since 1.0
	 */
	public double getYCenter() {
		return yCenter;
	}

	
	/**
	 * Sets the y coordinate of the center
	 * 
	 * @param yCoordinate the yCoordinate to set
	 * @return none
	 * @since 1.0
	 */
	public void setYCenter(double yCoordinate) {
		this.yCenter = yCoordinate;
	}


	/**
	 * Returns the radius
	 * 
	 * @param none
	 * @return the radius
	 * @since 1.0
	 */
	public double getRadius() {
		return radius;
	}


	/**
	 * Sets the radius
	 * 
	 * @param radius the radius to set
	 * @return none
	 * @since 1.0
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}


	/**
	 * Gets the x Velocity of the ball
	 * 
	 * @param none
	 * @return xVelocity the x velocity of the ball
	 * @since 1.0
	 */
	public double getXVelocity() {
		return xVelocity;
	}


	/**
	 * Sets the x velocity of the ball
	 * 
	 * @param newXVelocity the new x velocity of the ball
	 * @return none
	 * @since 1.0
	 */
	public void setXVelocity(double newXVelocity) {
		
		// max speed allowed #nofun
		if (newXVelocity > BreakoutBoard.MAX_POSITIVE_BALL_VELOCITY) {
			newXVelocity = BreakoutBoard.MAX_POSITIVE_BALL_VELOCITY;
		} else if (newXVelocity < BreakoutBoard.MAX_NEGATIVE_BALL_VELOCITY) {
			newXVelocity = BreakoutBoard.MAX_NEGATIVE_BALL_VELOCITY;
		}
		
		this.xVelocity = newXVelocity;
	}


	/**
	 * Gets the y velocity
	 * 
	 * @param none
	 * @return yVelocity the y velocity of the ball
	 * @since 1.0
	 */
	public double getYVelocity() {
		return yVelocity;
	}


	/**
	 * Sets the y velocity
	 * 
	 * @param newYVelocity the new y velocity to set the ball with
	 * @return none
	 * @since 1.0
	 */
	public void setYVelocity(double newYVelocity) {
		
		// max speed
		if (newYVelocity > BreakoutBoard.MAX_POSITIVE_BALL_VELOCITY) {
			newYVelocity = BreakoutBoard.MAX_POSITIVE_BALL_VELOCITY;
		} else if (newYVelocity < BreakoutBoard.MAX_NEGATIVE_BALL_VELOCITY) {
			newYVelocity = BreakoutBoard.MAX_NEGATIVE_BALL_VELOCITY;
		}
		
		this.yVelocity = newYVelocity;
	}
	
	
	/**
	 * Gets the left edge for drawing
	 * 
	 * @param none
	 * @return the left edge's x coordinate
	 * @since 1.0
	 */
	public int getLeftEdgeCoordinate() {
		return (int) (xCenter - radius);
	}
	
	
	/**
	 * Gets the top edge for drawing
	 * 
	 * @param none
	 * @return the top edge y coordinate
	 * @since 1.0
	 */
	public int getTopEdgeCoordinate() {
		return (int) (yCenter - radius);
	}
	
	
	/**
	 * Updates the position of the ball according to the velocity, angle, and current location
	 * 
	 * @param none
	 * @return none
	 * @since 1.0
	 */
	public void updatePosition() {
		yCenter += yVelocity;
		xCenter += xVelocity;
	}


	public boolean touchesBreakoutObject(BreakoutObject breakoutObject) {
		
		if (	(breakoutObject.getxCoordinate() < 
					(xCenter + radius)) && // the ball is right of the left edge
				
				((breakoutObject.getxCoordinate() + breakoutObject.getWidth()) > 
						(xCenter - radius)) && // the ball is left of the right edge
						
				(breakoutObject.getyCoordinate() < 
					(yCenter + radius)) && // the ball is below the top edge
					
				((breakoutObject.getyCoordinate() + breakoutObject.getHeight()) > 
					(yCenter - radius)) // the ball is above the bottom edge
			){
			
			// We know the ball is touching the object
			return true;
		}
		
		// It must not be touching otherwise
		return false;
	}


	/**
	 * Bounces the ball off a surface with normal vector 'normal'
	 * 
	 * @param normal the normal vector to the surface
	 * @return none
	 * @since 1.0
	 */
	public void bounce(double[] normal, double friction, double restitution) {
		
		// the velocity vector
		double[] velocityVector = {xVelocity, yVelocity};
		
		// Part of the perpendicular vector (projecting velocity vect onto normal vect)
		double scalar = dotProduct(velocityVector, normal)/dotProduct(normal, normal);
		
		// The component vectors	
		double[] perpendicularToSurface = scalarProduct(normal, scalar);
		double[] parallelToSurface = {velocityVector[0] - perpendicularToSurface[0],
									  velocityVector[1] - perpendicularToSurface[1]};
		
		// Post collision perpendicular (restitution)
		perpendicularToSurface = scalarProduct(perpendicularToSurface, (restitution));
		
		// Post collision parallel
		parallelToSurface = scalarProduct(parallelToSurface, friction);
		
		// Post collision velocity
//		velocityVector = new double[] {parallelToSurface[0] - perpendicularToSurface[0],
//									   parallelToSurface[1] - perpendicularToSurface[1]};
		
		// Decompose this vector
		setXVelocity(parallelToSurface[0] - perpendicularToSurface[0]);
		setYVelocity(parallelToSurface[1] - perpendicularToSurface[1]);
				
	}
	
	
	/**
	 * Computes the dot product of two vectors
	 * 
	 * @param vectorOne
	 * @param vectorTwo
	 * @return dotProduct the double of the dot product
	 * @since 1.0
	 */
	public double dotProduct(double[] vectorOne, double[] vectorTwo) {
		
		// The dot product
		return ((vectorOne[0]*vectorTwo[0]) + (vectorOne[1]*vectorTwo[1]));
	}
	
	
	/**
	 * Computes the scalar product of a vector and a constant
	 * 
	 * @param vectorOne
	 * @param scalar
	 * @return scaled the scalar product
	 * @since 1.0
	 */
	public double[] scalarProduct(double[] vector, double scalar) {
		
		// The scalar product
		return new double[] {scalar*vector[0], scalar*vector[1]};
	}
	
	
}
