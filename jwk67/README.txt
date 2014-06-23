Jordan’s Breakout README:
Friday, March 7 2014
Advanced Programming



To Open:

	The jar is runnable, so if you have java 7 it should just open and be playable by itself. It is possible that it works with earlier versions, but I have not personally explored them, and I also don’t understand the java dependancies fully enough to guess what would happen. Additionally, the game should run from eclipse if you open the archive, and I recommend that you play around with some of the constants if you are in need of some fun (they are all in the BreakoutBoard class near the top).

	There is also a google spreadsheet with a set of responses to my usability study.

	There are only unit tests for my parent BreakoutObject class. This is because all of the game stuff was not only very tricky to test thoroughly, I was experimenting constantly and did not have a consistently testable vision for what the game would do.



Known Problems:

	There are no known problems, but maybe a few… interesting features. Like if you manage to get lots of balls on the screen, the paddle speeds up too. This happens because I don’t understand the back end of the animation fully. Other than that EVERYTHING IS PERFECT.

	ALSO THE BALL STARTS ON THE PADDLE NOT ON THE WALL THIS IS NOT WHAT THE PROJECT SAID TO DO BUT I LIKE IT MORE.




Additional Features:
	
	There are a lot of features beyond the required. I don’t want to list everything, but there are some highlights that I think stand out.

	Stolen from the class doc comment for the game:

 * Collision computed accurate to physics using normal vectors for angular computations and various modifiable physics constants to alter the reactions.

 * The art assets are all stored in the images package, allowing the path constants to be easily changed to form an entirely different look to the game.

 * The scores are computed in nice modular ways with various constants, allowing the meta game to be altered easily.

 * The game components can all be altered in the constants sections, if you want to play with 100 paddles and 10009 balls, I HAVE ENABLED YOU.

 * The bricks and paddle inherit from the same class, allowing their collisions to be easily standardized.

 * Everyone's favorite is the high score mechanic, allowing you to be better than other people.

 * The reset feature lets people quickly try to be better than other people.

 * Power ups and fully customizable levels are actually not far off, I focused on an infrastructure that enables further content creation (even if none ever gets done).

 * The ball also is influenced by the motion of the paddle at the moment of contact. WOOOOOOOW.

 * There are dynamic levels that are crudely created, each one changes the scoring and some change the board by adding rows (lame but a level factory would take more time than I have before break).




Usability Findings:

	I found that people liked to be able to easily play again, and not have it get hard in multiple ways. I let them reset it, but I kept the difficulty scaling how I wanted! People really liked the controls though, so that was nice. Everyone reacted differently to the art assets though. I have some cool rainbow ones in the program, but the default is my Tron-like theme.