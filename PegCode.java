//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

import java.util.ArrayList;

/**
 * Creates and Implements PegCodes of any length
 * @author Jacob J (primary) and Jeffery D
 */
public class PegCode {
	private ArrayList<Peg> pegs = new ArrayList<Peg>();

	/**
	 * Constructor to create a PegCode with provided Colors
	 * @param pegColors; a list of Colors for the desired number of pegs
	 */
	public PegCode(Colors ...pegColors) {
		for (Colors c: pegColors) {
			pegs.add(new Peg(c));
		}
	}
	
	/**
	 * Constructor to create a PegCode with random colors of the provided length
	 * @param length in integer form
	 */
	public PegCode(int length) {
		for (int i = 0; i < length; i++) {
			pegs.add(new Peg());
		}
	}
	
	/**
	 * Used for manually resetting the pegs of any existing PegCode
	 * The number of pegs provided does not have to be the same as the original
	 * @param pegColors; a list of Colors for the desired number of pegs
	 */
	public void setPegCode(Colors ...pegColors) {
		pegs.clear();
		for (Colors c: pegColors) {
			pegs.add(new Peg(c));
		}
	}
	
	/**
	 * Used for randomly resetting the pegs of any existing PegCode
	 */
	public void randomizePegCode() {
		int previousLength = pegs.size();
		pegs.clear();
		for (int i = 0; i < previousLength; i++) {
			pegs.add(new Peg());
		}
	}
	
	/**
	 * Makes a comparison between the calling PegCode and a provided other PegCode
	 * Returns an array of ints:
	 * Index[0] = number of pegs with the correct Color and position
	 * Index[1] = number of pegs that match on a color
	 * @param that PegCode to which you would like to compare
	 * @return an int array representing the number of exact peg matches followed by the number of color matches, which can overlap
	 */
	public int[] comparePegCodes(PegCode that) {
		int[] comparisonResult = new int[2];
		int exactPegMatchCount = 0;
		int correctColorCount = 0;
		for (Peg thisPeg : this.getPegsInCode()) {
			int thisIndex = this.getPegsInCode().indexOf(thisPeg);
			if (thisPeg.comparePeg(that.getPegsInCode().get(thisIndex))) {
				exactPegMatchCount++;
			}
		}	
		for (Peg thisPeg : this.getPegsInCode()) {
			for (Peg thatPeg : that.getPegsInCode()) {
				if (thisPeg.comparePeg(thatPeg)) {
					correctColorCount++;
					that.getPegsInCode().remove(thatPeg);
					break;
				}
			}
		}
		
		comparisonResult[0] = exactPegMatchCount;	
		comparisonResult[1] = correctColorCount;
		return comparisonResult;
	}
	
	/**
	 * Returns the stored ArrayList of pegs within the PegCode
	 * @return pegs ArrayList
	 */
	public ArrayList<Peg> getPegsInCode() {
		return pegs;
	}
	
	/**
	 * @return integer number representing the number of pegs in the code
	 */
	public int getCodeLength() {
		return pegs.size();
	}
	
	/** 
	 * Overrides the super to return a String representing a human-readable format of the PegCode
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		for (Peg p : pegs) {
			output.append(String.format("%s ", p.getColor()));
		}
		return output.toString();
	}

	/**
	 * Adds a peg to the code
	 * @param peg of any color
	 */
	public void add(Peg peg) {
		pegs.add(peg);		
	}
}
