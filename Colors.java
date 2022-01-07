//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

import java.util.Random;

/**
 * Used for enumerating the exact colors desired for use in the peg class. 
 * For the purposes of this class and package, the CROSS is a representation of a null color.
 * Although "Color" would be a more appropriate name for this class, we avoided that designation due to it overlapping with other Java resources
 * @author Jacob J (primary) and Jeffery D 
 */
public enum Colors {
	PINK, RED, YELLOW, BLUE, GREEN, CYAN, WHITE, BLACK, CROSS;
	
	/**
	 * Provides a random color from the first 6 colors in the set.
	 * @return a random color
	 */
	public static Colors getRandomColor() {
		Random random = new Random();
		return values()[random.nextInt(values().length-3)];
	}
}

