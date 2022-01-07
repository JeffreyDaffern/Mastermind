//Assignment: Mastermind Game
//Course: 2410 Advanced Programming
//Written By:  	Jacob Jackson & Jeffery Daffern
//Date Created:	Jan 27, 2020
package mastermind;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Jacob J and Jeffery D (primary)
 */
public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private ImageIcon icon = Board.scaleImage(new ImageIcon(ImagePanel.class.getResource(
		"/mastermind/images/Bottom.png")), 750, 900);;

	public ImagePanel()
	{

	}

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		
		icon.paintIcon(this, g, 0, 0);

	}
}
