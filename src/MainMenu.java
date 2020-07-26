import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.utils.EditSettings;


public class MainMenu extends JFrame{
		
	private static final long serialVersionUID = 1L;
	private static final String IMG_PATH = "mainmenu.jpg";	
	
	//Constructor
	public MainMenu() throws IOException{  
		//Settings
		EditSettings.upDateSettings(50, 0); //Default in this order : HEIGHT, WIDTH, SKIN (0 classic, 1 Hawaii)

		
		//Window
		this.setTitle("MushRooms : An Exploration journey");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		
		//Panel
		JPanel pan = new JPanel(new BorderLayout());

		// Buttons
		///newGame
		JButton newGame = new JButton();
		newGame.setText("New Game");
		NewGame action1  = new NewGame(this);
		newGame.addActionListener(action1);

		///Continue
		JButton continueGame = new JButton();
		continueGame.setText("Continue");
		Continue action2 = new Continue(this);
		continueGame.addActionListener(action2);
		
		///Settings
		JButton settings = new JButton();
		settings.setText("Settings");
		Settings action3 = new Settings();
		settings.addActionListener(action3);
		
		///HowToPlay
		JButton howTo = new JButton();
		howTo.setText("How to Play");
		HowTo action4 = new HowTo();
		howTo.addActionListener(action4);

		
		//Menu Image
	    // Add picture to the frame and adding contents to the panel
		pan.add(newGame, BorderLayout.PAGE_START);
		pan.add(continueGame, BorderLayout.LINE_START);
		pan.add(howTo);
		pan.add(settings, BorderLayout.LINE_END);

		
	      try {
	          BufferedImage img = ImageIO.read(new File(IMG_PATH));
	          ImageIcon icon = new ImageIcon(img);
	          JLabel label = new JLabel(icon);
	  		pan.add(label, BorderLayout.PAGE_END);

	       } catch (IOException e) {
	          e.printStackTrace();
	       }
	      
	    
		this.getContentPane().add(pan);
	    this.pack();
		this.setVisible(true);
		}
}