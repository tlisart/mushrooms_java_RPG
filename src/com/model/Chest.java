package com.model;

/**
 * Chest model, gives a random loot to the player, two states exists, "Available" and "Looted".
 * 
* @author      Lamas Neil<nlamas@ulb.ac.be>
* @author      Lisart Théo<theo@lisart.be>
* @version     0.1.1                 (current version number of program)
* @since       12-03-2016          (the version of the package this class was first added to)
* @category:   Cell static
*/

public class Chest extends Cell{

	private static final long serialVersionUID = 1L;
	
	// Chest State settings
	public int state; 				//0 = closed, 1 = open
	//Getters&Setters
	public int getState() {return state;}
	public void setState(int state) {
		this.state = state;
		setChanged();             
		notifyObservers();
	}
	
	/**
	 * Set the Chest state to 0 when the game starts
	 */
	
	public Chest(){
		this.state = 0;
	}
}






