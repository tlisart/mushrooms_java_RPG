package com.model;

import java.util.ArrayList;

import com.engine.Util;


/**
 * Armor class, has all attributes for the armors as the Name, the force factor, and a description
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @version     0.1.1                 (current version number of program)
 * @since       12-03-2016          (the version of the package this class was first added to)
 * @category:   AbstractItem
 * 
 */ 

public class Armor extends AbstractItem {
	
	//Characteristics

	private static final long serialVersionUID = 1L;
	
	//Getters&Setters
	
	public int getProtection() {return this.value;}
	public int getID(){return this.id;}
	public String getDescription(){ return this.description;}
	
	public void setName(String newName){this.name = newName;}
	public void setForce(int newForce){this.value = newForce;}
	public void setDescription(String newDescription){this.description = newDescription;}

	
	// Available Armors && Armors Characteristics
	
	private static String[] A0 = {"Thin Silk Shirt ", "0", "A very stylish, but not very effective shirt"};
	private static String[] A1 = {"Leather armor", "2", "Small armor, protects very little but better than nothing"};
	private static String[] A2 = {"Old Iron Plate", "3", "An old but sturdy armor"};
	private static String[] A3 = {"Good Iron Armor", "5", "Not very good looking, but effective enough"};
	private static String[] A4 = {"Steel Plate", "7", "Shiny Steel Plate, much more effective"};
	private static String[] A5 = {"Tempered Steel Armor", "8", "Very Strong Armor"};
	private static String[] A6 = {"Mithril Wall", "10", "Old armor Build in raw mithril by Dwarves, there's no better armor"};

	private ArrayList<String[]> allArmors = new ArrayList<String[]>();
	
	/**
	 * Puts all available Armors into a list
	 */
	
    private void setAllArmors(){
    	allArmors.add(A0);
    	allArmors.add(A1);
    	allArmors.add(A2);
    	allArmors.add(A3);
    	allArmors.add(A4);
    	allArmors.add(A5);
    	allArmors.add(A6);
    }
    
    /**
     * Sets the weapon parameters depending on the given key, set into the constructor, or a random key if looted
     */
    
    private void setIndividualArmor(){
		this.name = allArmors.get(id)[0];
		this.value =  Integer.parseInt(allArmors.get(id)[1]);   // Must convert from String to int
		this.description = allArmors.get(id)[2];
    }
    
	//Constructor ------------------------------------------------------------------------------------------------------
	
	/**
	 * Uses an ID as reference to a list,  this list is structured as a list of strings, giving all the parameters of a given weapon
     * following : {'NAME', 'FORCE VALUE', 'DESCRIPTION'}
	 * @param key  Gives a different weapon depending on the ID
	 */
	
	public Armor(int key){        // When the key is known
		setAllArmors();
		this.id = key;  
		setIndividualArmor();
	}
	
	/**
	 * Sets a random key, then call the same methods as in Armor(int key)
	 * @see Armor()
	 */
	public Armor(){                               // When it is a random loot
		setAllArmors();
		this.id =Util.randomNumberRanged(0, allArmors.size() -1 );                     // Gives a random number within the range
		setIndividualArmor();
		}
	
}
