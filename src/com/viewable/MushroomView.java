package com.viewable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MushroomView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Attributs
	protected Image appearance;
	protected Image[] appearanceMoove = new Image[4];
	protected Image[] appearanceAttack = new Image [4];
	
	//Constructeur
	public MushroomView(){
		////////// Initialisation première image
		try {this.appearance = ImageIO.read(new File("Monster/ghost.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		//////////Initialisation des appearancesMoove
		try {this.appearanceMoove[0] = ImageIO.read(new File("Monster/m0.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		try {this.appearanceMoove[1] = ImageIO.read(new File("Monster/m1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		try {this.appearanceMoove[2] = ImageIO.read(new File("Monster/m2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		try {this.appearanceMoove[3] = ImageIO.read(new File("Monster/m3.png"));} 
		catch (IOException e) {e.printStackTrace();}

	}
	//Methodes
		public Image getAppearance() {
			return appearance;
		}
		public Image getAppearanceMoove(int i) {
			return appearanceMoove[i];
		}
		public Image getAppearanceAttack(int i) {
			return appearanceAttack[i];
		}
		public void setAppearance(Image appearance) {
			this.appearance = appearance;
		}
		
		
		public void rawAttackAnimation(Image previousAppearance, View view) throws InterruptedException{
	  		//the arg permits to know the target and to reinitialize the position after hit
	   		
	  		//constructeur TimerTask
	  		TimerTask task = new TimerTask(){
	  			//Definition de la tâche
				int i = 0;
	  			public void run(){
					setAppearance(getAppearanceAttack(i));
					i++;
			  		view.repaint();
					if (i>3) {
						cancel();
						i = 0;
						setAppearance(previousAppearance);
					}
				}	
			};
			
			Timer timer = new Timer();
			timer.schedule(task, 0, 100);			
	  	}
}
