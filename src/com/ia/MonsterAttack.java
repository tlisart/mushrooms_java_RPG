//Package Statements
package com.ia;
//Import Statements
import java.io.IOException;

import com.model.Mushroom;

/**
 * Class which gives instruction to Mushrooms in threads, takes the pauser and the mushrooms as an argument
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @category:   IA
 * 
 */ 

public class MonsterAttack implements Runnable { 

	private Mushroom ghost;
	Pauser pauser;


	/**
	 * Override the run method from runnable, gives attack instruction, and checks the timer for reloatime
	 * @see com.model.Map
	 * @see com.model.Mushrooms
	 */
	
	public void run(){
		int counter = 0;
		
		if (counter == 0) {
			try {Thread.sleep(1000);}                 //Initialisation to avoid nullPointer rightCheck  
			catch (InterruptedException e) {e.printStackTrace();} 
			counter++;} 
		
		while (ghost.alive == true){
			pauser.look();
			try {ghost.rawAttack();} 
			catch (IOException e1) {e1.printStackTrace();} 
			try {Thread.sleep(ghost.reloadTime);}
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}

	/**
	 * Construction of the behaviour of the enemies, takes the pauser as an argument (listens to keypress, and pause the threads when asked to)
	 * @param mushroom
	 * @param pauser
	 */
	
	public MonsterAttack(Mushroom mushroom, Pauser pauser){
		this.ghost = mushroom;
		this.pauser = pauser;
	}
}
