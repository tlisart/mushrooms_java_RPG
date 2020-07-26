//Package Statements
package com.model;

import java.io.Serializable;
import java.util.Observable;

/**
 * This class is the superclass of all cells. A cell is an element of the grid, instanced in map
 * @see com.model.Wall    
 * @see com.model.Mushroom 
 * @see com.model.Player
 * @see com.model.Chest
 * @see com.model.Exit
 * @see com.model.AbstractItem  for the looted items laying on the ground
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * 
 */ 
public abstract class Cell extends Observable implements Serializable{ // Aggregated to Map

	private static final long serialVersionUID = 1L;
	transient Map map;
	
	/**
	 * returns the value associated to the type of the Cell
	 * 
	 * @param map
	 * @return
	 */
	
	public int whoIsThere(Map map){

		//Conditions
		if (this instanceof Wall){
			return 1;}
		if (this instanceof Mushroom){
			return 2;}
		if (this instanceof Player){
			return 3;}
		if (this instanceof Chest){
			return 4;}
		if (this instanceof Exit){
			return 5;}
		if(this instanceof AbstractItem){
			return 6;}
		else {return 0;}  
			
	}
}	
