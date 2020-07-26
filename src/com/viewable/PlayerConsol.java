package com.viewable;
//import statements
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import com.model.Inventory;
import com.model.Player;
import com.utils.TrackLog;

public class PlayerConsol extends JLabel implements Observer{
	Player player;
	Inventory inventory;
	
	String line0 = "";
	String line1 = "";
	String line2 = "";
	String line3 = "";

	
	String text = null;

	private static final long serialVersionUID = 1L;

	Image parchemin;

	public PlayerConsol() throws IOException{
		
		this.text = "<html>"+ line0 + "<br />"   + line1 + "<br />" + line2 + "<br />" + line3;
		this.setFont(new Font("Serif", Font.PLAIN, 20));
	    setForeground(Color.black);

		try {parchemin = ImageIO.read(new File("parchemin.jpg"));} 
		catch (IOException e) {e.printStackTrace();}
		
	}
	
	public void paintComponent(Graphics g) {
		
        g.drawImage(parchemin, 0, 0, null);
        super.paintComponent(g);
      }

	@Override
	public void update(Observable o1, Object arg) {
			ArrayList<String> allLines = null;
			try {
				allLines = TrackLog.readLog();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			if (allLines.size() == 0){}
			else if (allLines.size() == 1){
				this.line0 = allLines.get(allLines.size()-1);
			}
			else if (allLines.size() == 2){
				this.line0 = allLines.get(allLines.size()-2);
				this.line1 = allLines.get(allLines.size()-1);
			}
			else if (allLines.size() == 3){
				this.line0 = allLines.get(allLines.size()-3);
				this.line1 = allLines.get(allLines.size()-2);
				this.line2 = allLines.get(allLines.size()-1);
			}				
			else{
			this.line0 = allLines.get(allLines.size()-4);
			this.line1 = allLines.get(allLines.size()-3);
			this.line2 = allLines.get(allLines.size()-2);
			this.line3 = allLines.get(allLines.size()-1);
			}
			this.text = "<html>"+ line0 + "<br />"   + line1 + "<br />" + line2 + "<br />" + line3;

			this.setText(this.text);
	}		
}
	
	


