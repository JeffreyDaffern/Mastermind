//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

/**
 * Creates and implements a GUI-driven instance of the Mastermind game.
 * @author Jacob J and Jeffrey D (primary)
 */
public class Board extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ImagePanel imagePanel;
	private ImageIcon icon;
	private JLabel[] labels = new JLabel[40];
	private JLabel[] miscLabels = new JLabel[13];
	private JButton[] buttons = new JButton[10];
	private static JLabel[] feedbackLabels = new JLabel[40];
	private int pegIndex = 0;
	private static int feedbackIndex = 0;
	private int guessIndex = 0;
	private Colors color;
	private Colors[] colors = new Colors[4];
	private int guessCount = 0;
	private static MasterMindGame game;
	private int winCount = 0;
	private int lossCount = 0;

/**
* Launch the application.
* @param args the main args
*/
	public static void main(String[] args) 
	{

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					game = new MasterMindGame(4);
					Board frame = new Board();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the frame.
	 * 
	 */
	public Board()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 50, 775, 950);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		imagePanel = new ImagePanel();
		contentPane.add(imagePanel, BorderLayout.CENTER);
		imagePanel.setLayout(null);

		createGui();
		clickEvents();
	}

	
	
	
	//-----METHODS-----
	
	/**
	 * Checks the current guess displayed on the GUI against the solution and responds appropriately.
	 */
	private void checkInputGuess() {
		System.out.println(guessCount);
		if (guessIndex == 4)
		{
			if (game.checkGuess(colors))
			{
				updateWinLoss(true);
				clearBoard();
				guessCount = 99; //set over max to stop the submit button listener from allowing further submissions until the reset button is pressed
				paintSolution(game.getSolution());
				miscLabels[10].setIcon(scaleImage(new ImageIcon(ImagePanel.class
					.getResource("/mastermind/images/YouWin.jpg")), 450, 350));
				System.out.println("Wins: " + winCount + "\nLosses: " + lossCount);//TODO
			} else if (guessCount < 10)
			{
				SetFeedbackPegs(game.getResults());
				paintLabels();
				clearCurrentGuesses();
				guessCount++;
			} 
			
		}
		if (guessCount >= 10 && guessCount != 99) {
			guessCount = 99;
			updateWinLoss(false);
			clearBoard();
			paintSolution(game.getSolution());
			miscLabels[10].setIcon(scaleImage(new ImageIcon(ImagePanel.class
					.getResource("/mastermind/images/YouLose.jpg")), 550, 550));
			System.out.println("Wins: " + winCount + "\nLosses: " + lossCount);//TODO				
		}
	}
	
	/**
	 * Updates the winCount or lossCount and related labels based on the provided boolean.
	 * @param b is a boolean where true represents a win and false represents a loss
	 */
	private void updateWinLoss(boolean b) {
		if (b) {
			winCount++;
		} else {
			lossCount++;
		}
		miscLabels[11].setText("Wins: " + winCount);
		miscLabels[12].setText("Losses: " + lossCount);
	}
	
	/**
	 * Displays the current game solution to the user.
	 * @param pegs PegCode passed from the game representing the current solution 
	 */
	protected void paintSolution(PegCode pegs)
	{
		for (int i = 0, j = 6; i < 4; i++, j++)
		{
			miscLabels[j].setIcon(scaleImage(new ImageIcon(ImagePanel.class.getResource(
				"/mastermind/images/" + pegs.getPegsInCode().get(i).getColor() + "Peg.png")),
				46, 46));

		}
	}

	/**
	 * Displays all of the current results pegs for the current guess.
	 * @param resultsPegs passed from the game's current ArrayList of results
	 */
	public static void SetFeedbackPegs(ArrayList<PegCode> resultsPegs)
	{
		for (int j = 0; j < resultsPegs.get(resultsPegs.size() - 1).getPegsInCode().size(); j++)
		{
			feedbackLabels[feedbackIndex].setIcon(scaleImage(new ImageIcon(
				ImagePanel.class.getResource("/mastermind/images/"
					+ resultsPegs.get(resultsPegs.size() - 1).getPegsInCode().get(j).getColor()
					+ "FeedbackPeg.png")), 25, 25));
			feedbackIndex++;
		}

	}

	/**
	 * Sets the appropriate ImageIcon to the color currently chosen by the user.
	 * @param color provided by the color selection buttons
	 */
	public void setGuess(Colors color)
	{
		if (guessIndex < 4 && guessCount <= 9)
		{
			icon = scaleImage(new ImageIcon(ImagePanel.class.getResource(
				"/mastermind/images/" + color.toString().toLowerCase() + "Peg.png")), 48,
				48);
			miscLabels[guessIndex].setIcon(icon);
			colors[guessIndex] = color;
			guessIndex++;
		}
	}

	/**
	 * Sets the appropriate icon for each Peg's label based on the color.
	 */
	public void paintLabels()
	{
		for (int i = 0; i < 4; i++)
		{
			icon = scaleImage(new ImageIcon(ImagePanel.class.getResource(
				"/mastermind/images/" + colors[i].toString().toLowerCase() + "Peg.png")),
				46, 46);
			labels[pegIndex].setIcon(icon);
			pegIndex++;			
		}
	}

	/**
	 * Clears the labels that displays the current guess selected by the user.
	 */
	private void clearCurrentGuesses()
	{
		colors = new Colors[4];
		guessIndex = 0;
		for (int i = 0; i < colors.length; i++)
		{
			miscLabels[i].setIcon(new ImageIcon());
		}
	}
	
	/**
	 * Used to clear the board of all displayed guess and feedback labels, clears the guesses, and resets the appropriate counters.
	 */
	private void clearBoard() {
		for (int i = 0; i < labels.length; i++)
		{
			labels[i].setIcon(new ImageIcon());
			feedbackLabels[i].setIcon(new ImageIcon());
			if (i > 5 && i < 11)
				miscLabels[i].setIcon(new ImageIcon());
		}
		clearCurrentGuesses();
		guessIndex = 0;
		pegIndex = 0;
		feedbackIndex = 0;
	}
	
	/**
	 * Scales an image and returns the scaled image as an icon.
	 * @param icon provided by the user
	 * @param w the desired width
	 * @param h the desired height
	 * @return an ImageIcon of the given width/height
	 */
	public static ImageIcon scaleImage(ImageIcon icon, int w, int h)
	{
		Image i = icon.getImage();
		Image newimg = i.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newimg);
	}

	
	
	//---- SET UP OF BUTTONS & LABELS

	/**
	 * Creates labels and buttons and adds them to the image panel.
	 */
	private void createGui()
	{
		for (int i = 0; i < buttons.length; i++)
		{
			buttons[i] = setButtons(i);
			imagePanel.add(buttons[i]);
			
		}
		for (int i = 0; i < labels.length; i++)
		{
			labels[i] = new JLabel();
			feedbackLabels[i] = new JLabel();
			imagePanel.add(labels[i]);
			imagePanel.add(feedbackLabels[i]);
		}
		
		for (int i = 0; i < miscLabels.length; i++)
		{
			miscLabels[i] = new JLabel();
			imagePanel.add(miscLabels[i]);
		}
		setLabelData();
		setButtonData();
	}
	
	/**
	 * Calls methods and sets guesses based on buttons clicked by the user.
	 */
	private void clickEvents()
	{
//Pink
		buttons[0].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				color = Colors.PINK;
				setGuess(color);
			}
		});
//Red
		buttons[1].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				color = Colors.RED;
				setGuess(color);
			}
		});
//Yellow
		buttons[2].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				color = Colors.YELLOW;
				setGuess(color);
			}

		});
//Green
		buttons[3].addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent e)
			{
				color = Colors.GREEN;
				setGuess(color);
			}
		});
//Blue
		buttons[4].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				color = Colors.BLUE;
				setGuess(color);
			}
		});
//Cyan
		buttons[5].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				color = Colors.CYAN;
				setGuess(color);
			}
		});
//RemovePeg
		buttons[6].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (guessIndex > 0 && guessIndex <= colors.length)
				{
					guessIndex--;
					miscLabels[guessIndex].setIcon(new ImageIcon());
				}
			}
		});
//Restart
		buttons[7].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				clearBoard();
				guessCount = 0;
				game.resetGame();
			}

			
		});
//Quit
		buttons[8].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
//Submit
		buttons[9].addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
					checkInputGuess();
			}
		});
	}
	
	/**
	 * Sets label locations and text.
	 */
	private void setLabelData()
	{
		labels[0].setBounds(135, 29, 46, 46);
		labels[1].setBounds(208, 27, 48, 47);
		labels[2].setBounds(280, 27, 48, 47);
		labels[3].setBounds(353, 27, 48, 47);
		labels[4].setBounds(136, 101, 48, 47);
		labels[5].setBounds(208, 101, 48, 47);
		labels[6].setBounds(280, 101, 48, 47);
		labels[7].setBounds(353, 101, 48, 47);
		labels[8].setBounds(136, 173, 48, 47);
		labels[9].setBounds(208, 173, 48, 47);
		labels[10].setBounds(280, 173, 48, 47);
		labels[11].setBounds(353, 173, 48, 47);
		labels[12].setBounds(136, 245, 48, 47);
		labels[13].setBounds(208, 245, 48, 47);
		labels[14].setBounds(280, 245, 48, 47);
		labels[15].setBounds(353, 245, 48, 47);
		labels[16].setBounds(136, 316, 48, 47);
		labels[17].setBounds(208, 316, 48, 47);
		labels[18].setBounds(280, 316, 48, 47);
		labels[19].setBounds(353, 316, 48, 47);
		labels[20].setBounds(136, 391, 48, 47);
		labels[21].setBounds(208, 391, 48, 47);
		labels[22].setBounds(280, 391, 48, 47);
		labels[23].setBounds(353, 391, 48, 47);
		labels[24].setBounds(136, 464, 48, 47);
		labels[25].setBounds(208, 464, 48, 47);
		labels[26].setBounds(280, 464, 48, 47);
		labels[27].setBounds(353, 464, 48, 47);
		labels[28].setBounds(136, 537, 48, 47);
		labels[29].setBounds(208, 537, 48, 47);
		labels[30].setBounds(280, 537, 48, 47);
		labels[31].setBounds(353, 537, 48, 47);
		labels[32].setBounds(136, 610, 48, 47);
		labels[33].setBounds(208, 610, 48, 47);
		labels[34].setBounds(280, 610, 48, 47);
		labels[35].setBounds(353, 610, 48, 47);
		labels[36].setBounds(136, 683, 48, 47);
		labels[37].setBounds(208, 683, 48, 47);
		labels[38].setBounds(280, 683, 48, 47);
		labels[39].setBounds(353, 683, 48, 47);

		feedbackLabels[0].setBounds(472, 26, 24, 23);
		feedbackLabels[1].setBounds(496, 26, 24, 23);
		feedbackLabels[2].setBounds(472, 51, 24, 23);
		feedbackLabels[3].setBounds(496, 51, 24, 23);
		feedbackLabels[4].setBounds(472, 99, 24, 23);
		feedbackLabels[5].setBounds(496, 99, 24, 23);
		feedbackLabels[6].setBounds(472, 124, 24, 23);
		feedbackLabels[7].setBounds(496, 124, 24, 23);
		feedbackLabels[8].setBounds(472, 172, 24, 23);
		feedbackLabels[9].setBounds(496, 172, 24, 23);
		feedbackLabels[10].setBounds(472, 197, 24, 23);
		feedbackLabels[11].setBounds(496, 197, 24, 23);
		feedbackLabels[12].setBounds(472, 244, 24, 23);
		feedbackLabels[13].setBounds(496, 244, 24, 23);
		feedbackLabels[14].setBounds(472, 269, 24, 23);
		feedbackLabels[15].setBounds(496, 269, 24, 23);
		feedbackLabels[16].setBounds(472, 315, 24, 23);
		feedbackLabels[17].setBounds(496, 315, 24, 23);
		feedbackLabels[18].setBounds(472, 340, 24, 23);
		feedbackLabels[19].setBounds(496, 340, 24, 23);
		feedbackLabels[20].setBounds(472, 390, 24, 23);
		feedbackLabels[21].setBounds(496, 390, 24, 23);
		feedbackLabels[22].setBounds(472, 415, 24, 23);
		feedbackLabels[23].setBounds(496, 415, 24, 23);
		feedbackLabels[24].setBounds(472, 462, 24, 23);
		feedbackLabels[25].setBounds(496, 462, 24, 23);
		feedbackLabels[26].setBounds(472, 487, 24, 23);
		feedbackLabels[27].setBounds(496, 487, 24, 23);
		feedbackLabels[28].setBounds(472, 536, 24, 23);
		feedbackLabels[29].setBounds(496, 536, 24, 23);
		feedbackLabels[30].setBounds(472, 561, 24, 23);
		feedbackLabels[31].setBounds(496, 561, 24, 23);
		feedbackLabels[32].setBounds(472, 609, 24, 23);
		feedbackLabels[33].setBounds(496, 609, 24, 23);
		feedbackLabels[34].setBounds(472, 634, 24, 23);
		feedbackLabels[35].setBounds(496, 634, 24, 23);
		feedbackLabels[36].setBounds(472, 680, 24, 23);
		feedbackLabels[37].setBounds(496, 680, 24, 23);
		feedbackLabels[38].setBounds(472, 705, 24, 23);
		feedbackLabels[39].setBounds(496, 705, 24, 23);

		miscLabels[4].setText("Solution");
		miscLabels[4].setForeground(new Color(255, 255, 255));
		miscLabels[4].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));

		miscLabels[5].setText("Current Guess");
		miscLabels[5].setForeground(new Color(255, 255, 255));
		miscLabels[5].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));

		miscLabels[11].setText("Wins: " + winCount);
		miscLabels[11].setForeground(new Color(255, 255, 255));
		miscLabels[11].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));

		miscLabels[12].setText("Losses: " + lossCount);
		miscLabels[12].setForeground(new Color(255, 255, 255));
		miscLabels[12].setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));
		
		miscLabels[0].setBounds(135, 757, 46, 42);
		miscLabels[1].setBounds(208, 757, 46, 42);
		miscLabels[2].setBounds(281, 757, 46, 42);
		miscLabels[3].setBounds(352, 757, 46, 42);
		miscLabels[4].setBounds(10, 846, 82, 14);
		miscLabels[5].setBounds(10, 773, 105, 14);
		miscLabels[6].setBounds(135, 830, 46, 42);
		miscLabels[7].setBounds(208, 830, 46, 42);
		miscLabels[8].setBounds(281, 830, 46, 42);
		miscLabels[9].setBounds(352, 830, 46, 42);
		miscLabels[10].setBounds(116, 268, 550, 332);
		miscLabels[11].setBounds(620, 50, 82, 14);
		miscLabels[12].setBounds(620, 100, 82, 14);
	}

	/**
	 * Creates buttons and sets button appearance.
	 * @param index the index of the button
	 * @return a JButton
	 */
	public JButton setButtons(int index)
	{
		JButton btn = new JButton("");
		btn.setOpaque(false);
		btn.setContentAreaFilled(false);
		btn.setBorderPainted(false);

		if (index > 5)
		{
			btn.setBorderPainted(true);
			btn.setForeground(new Color(255, 255, 255));
			btn.setFont(new Font("Tw Cen MT Condensed Extra Bold", Font.PLAIN, 20));
			btn.setFocusPainted(false);
		}

		return btn;
	}

	/**
	 * Sets button locations and text.
	 */
	private void setButtonData()
	{
		buttons[6].setText("Remove Peg");
		buttons[7].setText("Restart");
		buttons[8].setText("Quit");
		buttons[9].setText("Submit");
		buttons[8].setForeground(new Color(255, 0, 0));

		buttons[0].setBounds(566, 302, 57, 65); // Pink
		buttons[1].setBounds(566, 381, 57, 65); // Red
		buttons[2].setBounds(566, 448, 57, 65); // Yellow
		buttons[3].setBounds(566, 530, 57, 65); // Green
		buttons[4].setBounds(566, 600, 57, 65); // Blue
		buttons[5].setBounds(566, 674, 57, 65); // Cyan
		buttons[6].setBounds(595, 757, 128, 45); // RemovePeg
		buttons[7].setBounds(431, 834, 128, 45); // Restart
		buttons[8].setBounds(595, 834, 128, 45); // Quit
		buttons[9].setBounds(431, 757, 128, 45); // Submit
	}

}
