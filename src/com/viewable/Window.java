package com.viewable;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;


public class Window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Attributes
	View panGame;
	PlayerConsol panUI;
	
	//Constructor
	public Window(View panGame, PlayerConsol panUI){
		
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setName("Mush Rooms");
		setSize(width, height);	
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
			    
		this.panGame = panGame;
		this.panUI = panUI;
						
	    JLayeredPane lp = getLayeredPane();
	    
	    panGame.setBounds(0, 0, width, height);
	    panUI.setBounds(width-800,height- 230, 600, 130);

	    // Place the buttons in different layers
	    lp.add(panUI, new Integer(3));
	    lp.add(panGame, new Integer(2));

		this.setVisible(true);
	}
	
	
	public Window(View panGame){
		this.panGame = panGame;
		
		// Window
		this.setName("Mush Rooms");
		this.setLocationRelativeTo(null);
		
		// Content
		this.setContentPane(panGame);
	    this.pack();
		this.setVisible(true);
	}	
}