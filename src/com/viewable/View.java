package com.viewable;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.model.Chest;
import com.model.Map;
import com.model.Mushroom;
import com.model.Player;

public class View extends JPanel implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Attributes
	protected Image gameOver;
	
	//All gameplay elements
	Map map;
	Player player;
	
	ExitView exitView;
	PlayerView playerView;
	WallView wallView;
	MushroomView mushroomView;
	FloorView floorView;
	ChestView chestView;
	
	// UI elements
	PlayerConsol playerConsol;    // Different panel
	LifeBar playerLifeBar;

	
	int dungeonStyle;
	
	int unit;                      //Determines the ratio cell/pixels
	public int highestLeftCornerI; //changes with MoveTo
	public int highestLeftCornerJ;
	int screenHeight = 20;
	int screenWidth = 35;
	
	
	Image thunderView;
	
	//Constructor
	
	/**
	 * Initiate all graphics components 
	 * @param playerView
	 * @param player 
	 * @param mapView
	 * @param wallView
	 * @param mushroomView
	 * @param floorView
	 * @param chestView

	 */
	public View(PlayerView playerView, Map map, int dungeonStyle){
		
		this.playerView = playerView;
		this.dungeonStyle = dungeonStyle;
		this.map = map;
		
		this.unit = Map.unit;

		this.player = playerView.player;
		
		this.exitView = new ExitView();
		this.wallView = new WallView();
		this.floorView = new FloorView();
		this.mushroomView = new MushroomView();
		try {this.chestView = new ChestView();} 
		catch (IOException e1) {e1.printStackTrace();}
		

		this.playerLifeBar = new LifeBar(player);
		player.addObserver(playerLifeBar);
		
		try {this.gameOver = ImageIO.read(new File("GameOver/Game_Over.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		highestLeftCornerI = playerView.getPosViewY() - screenHeight*unit/2 + unit;
		highestLeftCornerJ = playerView.getPosViewX() - screenWidth*unit/2 + 2*unit;
				
		this.setPreferredSize(new Dimension(map.getSize(), map.getSize()));
	}
	
	// Display


	public void paintComponent(Graphics g){  
		
		//Background 
		g.setColor(Color.black);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());    
		
		// DUNGEON STYLE ---------------------------------------------------------------------------------------------------------------------------
		
		//Floor
		if (dungeonStyle == 0){ 
		int repaintCounter = 0; //used for LifeBar
		for (int i = 0; i<map.getSize() ; i++){
			for (int j = 0; j<map.getSize(); j++){
				//Floor
				if (map.grid[i][j].whoIsThere(map) != 1){
					g.drawImage(floorView.floor, j*unit-highestLeftCornerJ + unit/5, i*unit-highestLeftCornerI + unit/5, unit, unit, null);
				}
			}
		}
		//Dungeon
		for (int i = 0; i<map.getSize() ; i++){
			for (int j = 0; j<map.getSize(); j++){
				//Wall
				if (map.grid[i][j].whoIsThere(map) == 1){	
					g.drawImage(wallView.wall, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit+15, unit+15, null);
				}
				//Chest
				if (map.grid[i][j].whoIsThere(map) == 4){	
					Chest chest = (Chest) map.grid[i][j];
					if (chest.getState()==0){
						g.drawImage(chestView.closedChest, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
					}
					if (chest.getState()==1){
						g.drawImage(chestView.openChest, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
					}
				}
				
				//Exit
				if (map.grid[i][j].whoIsThere(map) == 5){	
					g.drawImage(exitView.totem, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
				}
				
				//Gift (Weapon)
				if (map.grid[i][j].whoIsThere(map) == 6){	
					int taille = 3*unit/4;
					g.drawImage(chestView.gift, j*unit-highestLeftCornerJ + taille/4, i*unit-highestLeftCornerI + taille/4, taille, taille, null);
				}
				
				//mushroom
				if (map.grid[i][j].whoIsThere(map) == 2){
					Mushroom mushroom; mushroom = (Mushroom) map.grid[i][j];
					g.drawImage(mushroomView.getAppearanceMoove(mushroom.getOrientation()), j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
					///////////LifeBar mushroom
					LifeBar mushroomLifeBar = null;
					if(repaintCounter == 0){
						mushroomLifeBar  = new LifeBar(mushroom);
						mushroom.addObserver(mushroomLifeBar);
					}
					
					g.drawImage(mushroomLifeBar.background, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, 65*unit/80, 10*unit/80, null);
					g.drawImage(mushroomLifeBar.HPView, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, (int)(65*unit/80*mushroomLifeBar.HPpercentage), 10*unit/80, null);
					///////////Thunder 
					if(mushroom.getState()==5){
						g.drawImage(thunderView,  j*unit-highestLeftCornerJ + unit/10, i*unit-highestLeftCornerI, 60*unit/80, 100*unit/80, null);
					}
				}				
			}
		}
		
  		//PLAYER
		//////////Player is attacking
		if (playerView.getState() == 1){ 
			if (player.getOrientation()==0){
				g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 85*unit/80, 95*unit/80, null);	
			}
			if (player.getOrientation()==2){
				g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ-unit/2, playerView.getPosViewY()-highestLeftCornerI, 85*unit/80, 95*unit/80, null);	
			}
			if (player.getOrientation()==1 ||
				player.getOrientation() == 3){
				g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI - unit/2, 60*unit/80, 140*unit/80, null);	
			}			
		}
		
		//////////Player is using TriForce
		else if (playerView.getState() == 4 & player.getOrientation() == 0){ 
			g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 130*unit/80, 95*unit/80, null);	
		}
		else if (playerView.getState() == 4 & player.getOrientation() == 1){ 
			g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 60*unit/80, 200*unit/80, null);	
		}
		else if (playerView.getState() == 4 & player.getOrientation() == 2){ 
			g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ - unit, playerView.getPosViewY()-highestLeftCornerI, 130*unit/80, 95*unit/80, null);	
		}
		else if (playerView.getState() == 4 & player.getOrientation() == 3){ 
			g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI - unit - unit/5, 60*unit/80, 200*unit/80, null);	
		}
		//////////Player is moving, using Thunder or standing peacefully
		else {g.drawImage(playerView.getAppearance(),playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 60*unit/80, 100*unit/80, null);}
		
		//Player's LifeBar
		g.drawImage(playerLifeBar.background, playerView.getPosViewX()-highestLeftCornerJ +9*unit, playerView.getPosViewY()-highestLeftCornerI - 9*unit, 500*unit/80, 80*unit/80, null);
		g.drawImage(playerLifeBar.HPView, playerView.getPosViewX()-highestLeftCornerJ +9*unit, playerView.getPosViewY()-highestLeftCornerI - 9*unit, (int)(500*unit/80*playerLifeBar.HPpercentage), 80*unit/80, null);

		//MAP CONSOLE
		//mapView.afficher();
		
		//Game Over
		if (player.getState() == 3){
			g.drawImage(gameOver, 0, 0, screenWidth*unit - 3*unit, screenHeight*unit - 2, null);
			}
		
		// Used for init LifeBar
		repaintCounter ++;
		}
		
		// HAWAI STYLE ---------------------------------------------------------------------------------------------------------------------------

			if (dungeonStyle == 1){
				int repaintCounter = 0; //used for LifeBar
				for (int i = 0; i<map.getSize() ; i++){
					for (int j = 0; j<map.getSize(); j++){
						
						//Floor
						if (map.grid[i][j].whoIsThere(map) != 1){
							g.drawImage(floorView.floorWood, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
						}
					}
				}

				for (int i = 0; i<map.getSize() ; i++){
					for (int j = 0; j<map.getSize(); j++){
						
						//Wall
						if (map.grid[i][j].whoIsThere(map) == 1){	
							g.drawImage(wallView.water, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit+10, unit+10, null);
						}
						//Chest
						if (map.grid[i][j].whoIsThere(map) == 4){	
							Chest chest = (Chest) map.grid[i][j];
							if (chest.getState()==0){
								g.drawImage(chestView.closedChest, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
							}
							if (chest.getState()==1){
								g.drawImage(chestView.openChest, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
							}
						}
						
						//Exit
						if (map.grid[i][j].whoIsThere(map) == 5){	
							g.drawImage(exitView.totem, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
						}
						
						//Gift (AbstractItem)
						if (map.grid[i][j].whoIsThere(map) == 6){	
							int taille = 3*unit/4;
							g.drawImage(chestView.gift, j*unit-highestLeftCornerJ + taille/4, i*unit-highestLeftCornerI + taille/4, taille, taille, null);
						}
						
						//Mushroom
						if (map.grid[i][j].whoIsThere(map) == 2){
							Mushroom mushroom; mushroom = (Mushroom) map.grid[i][j];
							g.drawImage(mushroomView.getAppearanceMoove(mushroom.getOrientation()), j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, unit, unit, null);
							///////////LifeBar mushroom
							LifeBar mushroomLifeBar = null;
							if(repaintCounter == 0){
								mushroomLifeBar  = new LifeBar(mushroom);
								mushroom.addObserver(mushroomLifeBar);
							}
							
							g.drawImage(mushroomLifeBar.background, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, 65*unit/80, 10*unit/80, null);
							g.drawImage(mushroomLifeBar.HPView, j*unit-highestLeftCornerJ, i*unit-highestLeftCornerI, (int)(65*unit/80*mushroomLifeBar.HPpercentage), 10*unit/80, null);
							///////////Thunder 
							if(mushroom.getState()==5){
								g.drawImage(thunderView,  j*unit-highestLeftCornerJ + unit/10, i*unit-highestLeftCornerI, 60*unit/80, 100*unit/80, null);
							}
						}				
					}
				}
				
		  		//PLAYER
				//////////Player is attacking
				if (playerView.getState() == 1){ 
					if (player.getOrientation()==0){
						g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 85*unit/80, 95*unit/80, null);	
					}
					if (player.getOrientation()==2){
						g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ-unit/2, playerView.getPosViewY()-highestLeftCornerI, 85*unit/80, 95*unit/80, null);	
					}
					if (player.getOrientation()==1 ||
						player.getOrientation() == 3){
						g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI - unit/2, 60*unit/80, 140*unit/80, null);	
					}			
				}
				
				//////////Player is using TriForce
				else if (playerView.getState() == 4 & player.getOrientation() == 0){ 
					g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 130*unit/80, 95*unit/80, null);	
				}
				else if (playerView.getState() == 4 & player.getOrientation() == 1){ 
					g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 60*unit/80, 200*unit/80, null);	
				}
				else if (playerView.getState() == 4 & player.getOrientation() == 2){ 
					g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ - unit, playerView.getPosViewY()-highestLeftCornerI, 130*unit/80, 95*unit/80, null);	
				}
				else if (playerView.getState() == 4 & player.getOrientation() == 3){ 
					g.drawImage(playerView.getAppearance(), playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI - unit - unit/5, 60*unit/80, 200*unit/80, null);	
				}
				//////////Player is moving, using Thunder or standing peacefully
				else {g.drawImage(playerView.getAppearance(),playerView.getPosViewX()-highestLeftCornerJ, playerView.getPosViewY()-highestLeftCornerI, 60*unit/80, 100*unit/80, null);}
				
				//Player's LifeBar
				g.drawImage(playerLifeBar.background, playerView.getPosViewX()-highestLeftCornerJ +9*unit, playerView.getPosViewY()-highestLeftCornerI - 9*unit, 500*unit/80, 80*unit/80, null);
				g.drawImage(playerLifeBar.HPView, playerView.getPosViewX()-highestLeftCornerJ +9*unit, playerView.getPosViewY()-highestLeftCornerI - 9*unit, (int)(500*unit/80*playerLifeBar.HPpercentage), 80*unit/80, null);
				
				//Game Over
				if (player.getState() == 3){
					g.drawImage(gameOver, 0, 0, screenWidth*unit - 3*unit, screenHeight*unit - 2, null);
					}
				// Used for init LifeBar
				repaintCounter ++;
		}
	}
	
	public void rawAttackAnimation() throws InterruptedException{ //MoveToPlayerView
   		
  		//constructeur TimerTask
  		TimerTask task = new TimerTask(){
  			//Definition de la tâche
			int i = 0;
  			public void run(){
  				
  				if(i<4){
  					if (player.getInventory().getEweapon().getID() == 1){
  						if(i==0){playerView.setState(4);}
  						playerView.setAppearance(playerView.getTriForce(player.getOrientation(), i));
  					}
  					else{
  						playerView.setAppearance(playerView.getAppearanceAttack(player.getOrientation(),i));
  					}
  				}
  				if(i==4){
					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(),1));
					playerView.setState(0);	
  				}
  				if(i>4){
  					cancel();
  				}
				i++;
  			}
  		};
		
		Timer timer = new Timer();
		timer.schedule(task, 0, 80);
		
	}
	
	public void rangedAttackAnimation(Mushroom mushroom){
		
		playerView.setAppearance(playerView.getThunderPlayer(player.getOrientation()));
		
		//constructeur TimerTask
  		TimerTask task = new TimerTask(){
  			//Definition de la tâche
			int i = 0;
  			public void run(){
  				if(i<6){
  					mushroom.setState(5);
  					thunderView = playerView.getThunderView(i);
  					playerView.setAppearance(playerView.getThunderPlayer(player.getOrientation()));
  				}
  				if(i==6){
					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(),1));
  					playerView.setState(0);
  					mushroom.setState(0);
  				}
  				if(i>6){
  					cancel();
  				}
				i++;
  			}
  		};
		
		Timer timer = new Timer();
		timer.schedule(task, 0, 120);
		
	}
	
	//////////////// Continuous movement 
		public void moveTo(int orientation){ //ATTENTION IF UNIT/4 != ENTIER --> DECALAGE DE + en + fort
	   		
			//constructeur TimerTask
	  		TimerTask task = new TimerTask(){
	  			//Definition de la tâche
				int i = 0;
	  			public void run(){
	  				if(i<4){
  						playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(), i));
		  				if (orientation == 0){
		  					playerView.setPosViewX(playerView.getPosViewX()+unit/4);
		  					highestLeftCornerJ += unit/4;
		  				}
		  				if (orientation == 1){
		  					playerView.setPosViewY(playerView.getPosViewY()+unit/4);
		  					highestLeftCornerI += unit/4;
		  				}
		  				if (orientation == 2){
		  					playerView.setPosViewX(playerView.getPosViewX()-unit/4);
		  					highestLeftCornerJ -= unit/4;
		  				}
		  				if (orientation == 3){
		  					playerView.setPosViewY(playerView.getPosViewY()-unit/4);
		  					highestLeftCornerI -= unit/4;
		  				}
	  				}
	  				
	  				if(i==4){
						playerView.setState(0);	
	  				}
	  				if(i>4){
	  					cancel();
	  				}
					i ++;
	  			}
	  		};
			
			Timer timer2 = new Timer();
			timer2.schedule(task, 0, player.getMovingTime());
  	}

	
	public void update(Observable o, Object arg) {
		this.repaint(); 
	}

	
}