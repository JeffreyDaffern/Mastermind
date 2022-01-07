//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

/**
 * Creates and implements Pegs
 * @author Jacob J (primary) and Jeffery D
 */
public class Peg {
	private Colors color;

	/** 
	 * Constructor for a peg of a given color
	 * @param color that is desired for the Peg
	 */
	public Peg(Colors color) {
		this.color = color;
	}

	/**
	 * Constructor for a peg of a random color
	 */
	public Peg() {
		this.color = Colors.getRandomColor();
	}

	/**
	 * Resets the color of the Peg to a provided color
	 * @param color for the desired peg color
	 */
	public void setColor(Colors color) {
		this.color = color;
	}

	/**
	 * Resets the color of the Peg to a random color
	 */
	public void setRandomColor() {
		this.color = Colors.getRandomColor();
	}

	/**
	 * Compares the calling Peg against a provided second Peg
	 * Pegs are considered equal if they have the same color
	 * @param that a peg to compare against
	 * @return a boolean
	 */
	public boolean comparePeg(Peg that) {
		if (this.getColor().equals(that.getColor())) {
			return true;
		}
		return false;
	}

	/**
	 * @return the color of the peg
	 */
	public String getColor() {
		return color.toString().toLowerCase();

	}

	/** 
	 * Overrides the super to return a String representing the object in a human-readable format
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("A peg of the Color %s", this.color);
	}
	
}
