package com.ia;

import java.io.Serializable;

import com.model.Mushroom;
import com.model.Player;

public class GoToThePlayer implements Runnable, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Mushroom ghost;
	private Player player;
	Pauser pauser;
	int counter;

	
	public GoToThePlayer(Mushroom ghost, Pauser pauser){
		this.ghost = ghost;
		this.pauser = pauser;
		counter = 0;
	}
	
	public void run(){
		synchronized(this){
			while(ghost.alive == true){
				if (counter == 0) {
					try {Thread.sleep(1000);}//Initialisation to avoid nullPointer  
					catch (InterruptedException e) {e.printStackTrace();} 
					counter++;} 
				
				pauser.look();
				
				this.player = ghost.searchPlayer();
				
				goToThePlayer(player);
				
				try {Thread.sleep(ghost.movingTime);} 
				catch (InterruptedException e){e.printStackTrace();}
			}
		}
	}
	
	private synchronized void goToThePlayer(Player player) {
		int previousGridPosY = ghost.getGridPosY();
		int previousGridPosX = ghost.getGridPosX();
		
		if(player != null){
		
		if(player.getGridPosY() > ghost.getGridPosY()){  //joueur plus bas
			ghost.setOrientation(1);
			if (ghost.check(1) == 0
					|| ghost.check(1) == 6){ 
				ghost.setGridPosY(ghost.getGridPosY() + 1);
			}
		}
		else if(player.getGridPosY() < ghost.getGridPosY()){
			ghost.setOrientation(3);
			if (ghost.check(3) == 0
					|| ghost.check(3) == 6){ 
				ghost.setGridPosY(ghost.getGridPosY() - 1);
			}

		}
		else if(player.getGridPosX() > ghost.getGridPosX()){ //joueur plus à droite
			ghost.setOrientation(0);
				if (ghost.check(0) == 0
						|| ghost.check(0) == 6){ 
					ghost.setGridPosX(ghost.getGridPosX() + 1);
				}

		}
		else if(player.getGridPosX() < ghost.getGridPosX()){
			ghost.setOrientation(2);
				if (ghost.check(2) == 0
						|| ghost.check(2) == 6){ 
					ghost.setGridPosX(ghost.getGridPosX() - 1);
				}
		}
		int allPos[] = {previousGridPosX, previousGridPosY, ghost.getGridPosX(), ghost.getGridPosY()};
		ghost.notifyObservers(allPos); 
		
		}
		}
}
