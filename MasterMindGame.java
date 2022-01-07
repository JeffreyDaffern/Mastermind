//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

import java.util.ArrayList;

/**
 *Create and implements a MasterMind game with codes of any desired length
 * @author Jacob J (primary) and Jeffery D
 */
public class MasterMindGame {
	private PegCode solutionPegCode;
	private ArrayList<PegCode> guesses = new ArrayList<PegCode>();
	private ArrayList<PegCode> results = new ArrayList<PegCode>();
	
	
	/**
	 * Constructor to create a MasterMindGame with a solution created using the provided colors
	 * @param pegColors is a variable number of Colors, which will be passed to the PegCode class 
	 * to instantiate a solution PegCode of the given colors in the given order
	 */
	public MasterMindGame (Colors ...pegColors) {
		solutionPegCode = new PegCode(pegColors);
	}
	
	/**
	 * Constructor to create a MasterMindGame with a random solution based on the provided length
	 * @param length is an integer that represents the length of the code desired, which will be passed to the PegCode class 
	 * to instantiate a random solution PegCode of the given length
	 */
	public MasterMindGame (int length) {
		solutionPegCode = new PegCode(length);
	}
	
	/**
	 * Resets the game with a new, random solution based on the length of the original game
	 */
	public void resetGame() {
		guesses.clear();
		results.clear();
		solutionPegCode = new PegCode(solutionPegCode.getCodeLength());
	}
	
	/**
	 * Checks the current guess against the solution, records the guess into the list of guesses, and records the results
	 * This method relies on the PegCode method comparePegCodes
	 * @param colors is the guess in the form of a vararg of colors
	 * @return boolean representing if the current guess was an exact match
	 */
	public boolean checkGuess(Colors ...colors) {
		PegCode thisGuess = new PegCode(colors);
		guesses.add(thisGuess);
		PegCode thisResult = new PegCode();
		int[] tempResultTallies = solutionPegCode.comparePegCodes(thisGuess);
		for (int i = 0; i < tempResultTallies[0]; i++) {
			thisResult.add(new Peg(Colors.BLACK));
		}
		for (int i = 0; i < tempResultTallies[1]-tempResultTallies[0]; i++) {
			thisResult.add(new Peg(Colors.WHITE));
		}
		for (int i = thisResult.getCodeLength(); i < getGameCodeLength(); i++)
		{
			thisResult.add(new Peg(Colors.CROSS));
		}
		results.add(thisResult);
		if (tempResultTallies[0] == this.getGameCodeLength()) {
			return true;			
		} else {
			return false;
		}
	}
	
	/**
	 * @return the length of the PegCode solution
	 */
	public int getGameCodeLength() {
		return solutionPegCode.getCodeLength();
	}
	
	/**
	 * @return the solution PegCode
	 */
	public PegCode getSolution() {
		return solutionPegCode;
	}
	
	/**
	 * @return the ArrayList of guesses
	 */
	public ArrayList<PegCode> getGuesses() {
		return guesses;
	}
	
	/**
	 * @return the ArrayList of results for each guess
	 */
	public ArrayList<PegCode> getResults() {
		return results;
	}
	
	
}
