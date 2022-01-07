//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

/**
 * 
 * @author Jacob J (primary) and Jeffery D
 */
public class CodeTester
{
	/**
	 * This class is for testing purposes only
	 * 
	 * @param args the main args
	 */

	public static void main(String[] args)
	{
		@SuppressWarnings("unused")
		Colors[] guess1 = {Colors.CYAN, Colors.BLUE, Colors.CYAN, Colors.WHITE};
		Colors[] guess2 = {Colors.CYAN, Colors.CYAN, Colors.CYAN, Colors.BLUE};
		Colors[] currentGuess = guess2;

		MasterMindGame thisGame = new MasterMindGame(Colors.CYAN, Colors.CYAN, Colors.CYAN, Colors.BLUE);

		System.out.println("The solution is of length " + thisGame.getSolution().getCodeLength());
		System.out.println("Solution:   " + thisGame.getSolution().toString());
	
		
		System.out.println("Your guess: " + new PegCode(currentGuess)+"\n");
		if(thisGame.checkGuess(currentGuess)) {
			System.out.println("YOU WON!");
		} else {
			System.out.println("Not a winner...");
		}
		
		System.out.println("\nResults:    " + thisGame.getResults().get(thisGame.getResults().size()-1));
	}
}
