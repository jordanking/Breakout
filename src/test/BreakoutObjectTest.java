package test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import main.BreakoutBall;
import main.BreakoutBoard;
import main.BreakoutObject;

import org.junit.Test;

/**
 * This class tests all of the methods in the breakout object class.
 * 
 * @author jordan
 * @since 1.0
 */
public class BreakoutObjectTest {
	
	BreakoutObject primary; // The main bo under test
	BreakoutBall ball; // For methods that use a ball


	/**
	 * Test method for {@link main.BreakoutObject#BreakoutObject()}.
	 * Tests the constructor.
	 */
	@Test
	public void testBreakoutObject() {
		primary = new BreakoutObject();
		
		// Should all default to zero
		assertEquals(0, primary.getxCoordinate());
		assertEquals(0, primary.getyCoordinate());
		assertEquals(0, primary.getHeight());
		assertEquals(0, primary.getWidth());

	}


	/**
	 * Test method for {@link main.BreakoutObject#getxCoordinate()}.
	 */
	@Test
	public void testGetxCoordinate() {
		primary = new BreakoutObject();
		
		// Redundant, but will be nice if we change in the future
		assertEquals(0, primary.getxCoordinate());

	}

	
	/**
	 * Test method for {@link main.BreakoutObject#setxCoordinate(int)}.
	 */
	@Test
	public void testSetxCoordinate() {
		primary = new BreakoutObject();
		
		// Just a change
		primary.setxCoordinate(4);
		
		// Redundant, but will be nice if we change in the future
		assertEquals(4, primary.getxCoordinate());	
	}

	
	/**
	 * Test method for {@link main.BreakoutObject#getyCoordinate()}.
	 */
	@Test
	public void testGetyCoordinate() {
		primary = new BreakoutObject();
		
		// Redundant, but will be nice if we change in the future
		assertEquals(0, primary.getyCoordinate());	
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#setyCoordinate(int)}.
	 */
	@Test
	public void testSetyCoordinate() {
		primary = new BreakoutObject();
		
		// Just a change
		primary.setyCoordinate(4);
		
		// Redundant, but will be nice if we change in the future
		assertEquals(4, primary.getyCoordinate());
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#getWidth()}.
	 */
	@Test
	public void testGetWidth() {
		primary = new BreakoutObject();
		
		// Redundant, but will be nice if we change in the future
		assertEquals(0, primary.getWidth());	
	}

	
	/**
	 * Test method for {@link main.BreakoutObject#setWidth(int)}.
	 */
	@Test
	public void testSetWidth() {
		primary = new BreakoutObject();
		
		// Just a change
		primary.setWidth(4);
		
		// Redundant, but will be nice if we change in the future
		assertEquals(4, primary.getWidth());	
	}

	
	/**
	 * Test method for {@link main.BreakoutObject#getHeight()}.
	 */
	@Test
	public void testGetHeight() {
		primary = new BreakoutObject();
		
		// Redundant, but will be nice if we change in the future
		assertEquals(0, primary.getHeight());		
	}

	
	/**
	 * Test method for {@link main.BreakoutObject#setHeight(int)}.
	 */
	@Test
	public void testSetHeight() {
		primary = new BreakoutObject();
		
		// Just a change
		primary.setHeight(4);
		
		// Redundant, but will be nice if we change in the future
		assertEquals(4, primary.getHeight());	
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#getCenterXCoordinate()}.
	 */
	@Test
	public void testGetCenterXCoordinate() {
		primary = new BreakoutObject();
		
		// Change the coordinates to be in a nice square
		primary.setxCoordinate(4);
		primary.setyCoordinate(4);
		primary.setHeight(2);
		primary.setWidth(2);

		
		// Should be at (5,5)
		assertEquals(5, primary.getCenterXCoordinate());		
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#getCenterYCoordinate()}.
	 */
	@Test
	public void testGetCenterYCoordinate() {
		primary = new BreakoutObject();
		
		// Change the coordinates to be in a nice square
		primary.setxCoordinate(4);
		primary.setyCoordinate(4);
		primary.setHeight(2);
		primary.setWidth(2);

		
		// Should be at (5,5)
		assertEquals(5, primary.getCenterYCoordinate());	
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#getFriction()}.
	 */
	@Test
	public void testGetFriction() {
		primary = new BreakoutObject();
		
		// Should be 0
		assertEquals(0, primary.getFriction(), 0);		
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#setFriction(double)}.
	 */
	@Test
	public void testSetFriction() {
		primary = new BreakoutObject();
		
		// set it to something
		primary.setFriction(4.5);
		
		// Should be something
		assertEquals(4.5, primary.getFriction(), 0);	
	}

	/**
	 * Test method for {@link main.BreakoutObject#getRestitution()}.
	 */
	@Test
	public void testGetRestitution() {
		primary = new BreakoutObject();
		
		// Should be 0
		assertEquals(0, primary.getRestitution(), 0);		
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#setRestitution(double)}.
	 */
	@Test
	public void testSetRestitution() {
		primary = new BreakoutObject();
		
		// set it to something
		primary.setRestitution(4.5);
		
		// Should be the something
		assertEquals(4.5, primary.getRestitution(), 0);
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#getNormalForCollision(main.BreakoutBall)}.
	 */
	@Test
	public void testGetNormalForCollision() {
		
		// initialize the object with w/h of 2 at coord (4,4)
		primary = new BreakoutObject();
		
		// Change the coordinates to be in a nice square
		primary.setxCoordinate(4);
		primary.setyCoordinate(4);
		primary.setHeight(2);
		primary.setWidth(2);

		// initialize the ball to be touching the bottom of the object
		ball = new BreakoutBall(5, 6, 0, 0, 1); // bottom center of the object
		
		// Expected normal
		double[] expectedNormal = {0,1};

		// Should be the down normal		
		assertArrayEquals(expectedNormal, primary.getNormalForCollision(ball), 0);
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#canMoveLeft()}.
	 */
	@Test
	public void testCanMoveLeft() {
		
		// initialize the object 
		primary = new BreakoutObject();

		// Change the coordinates to be on the edge
		primary.setxCoordinate(0);
		primary.setyCoordinate(5);
		primary.setHeight(2);
		primary.setWidth(2);

		// Should not move left, it is on the edge		
		assertFalse(primary.canMoveLeft());
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#moveLeft()}.
	 */
	@Test
	public void testMoveLeft() {
		// initialize the object 
		primary = new BreakoutObject();

		// Change the coordinates to be in the mid
		primary.setxCoordinate(50);
		primary.setyCoordinate(5);
		primary.setHeight(2);
		primary.setWidth(2);
		
		// move
		primary.moveLeft();

		// Should move left		
		assertEquals(50-BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME, primary.getxCoordinate(), 0);
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#canMoveRight()}.
	 */
	@Test
	public void testCanMoveRight() {
		// initialize the object 
		primary = new BreakoutObject();

		// Change the coordinates to be on the edge
		primary.setxCoordinate(1000);
		primary.setyCoordinate(5);
		primary.setHeight(2);
		primary.setWidth(2);

		// Should not move right, it is off the board		
		assertFalse(primary.canMoveRight());
	}
	

	/**
	 * Test method for {@link main.BreakoutObject#moveRight()}.
	 */
	@Test
	public void testMoveRight() {
		// initialize the object
		primary = new BreakoutObject();

		// Change the coordinates to be in the mid
		primary.setxCoordinate(50);
		primary.setyCoordinate(5);
		primary.setHeight(2);
		primary.setWidth(2);
		
		primary.moveRight();

		// Should move left		
		assertEquals(50+BreakoutBoard.PADDLE_MOVE_PIXELS_PER_FRAME, primary.getxCoordinate(), 0);
	}
}
