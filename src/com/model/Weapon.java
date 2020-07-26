package com.model;

// Import statements
import java.util.ArrayList;

import com.engine.Util;

/**
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @version     0.1.1                 (current version number of program)
 * @since       12-03-2016          (the version of the package this class was first added to)
 * @category:   Weapons subclass
 * 
 */

public class Weapon extends AbstractItem{
	
	private static final long serialVersionUID = 1L;	
	
	// Available Weapons && Weapon Characteristics
	private static String[] W0 = {"Rusty Sword", "4", "An Old sword I found, will do for now"};
	private static String[] W1 = {"Tri Force", "6", "A strange spirit lamp, more effective than the short sword"};
	private static String[] W2 = {"Escalibur", "10", "Very sturdy, very sharp, very holy, very deadly"};

	private ArrayList<String[]> allWeapons = new ArrayList<String[]>();
	
	/**
	 * Puts all weapons into a list
	 */
    private void setAllWeapons(){
    	allWeapons.add(W0);
    	allWeapons.add(W1);
    	allWeapons.add(W2);
    }
    
    /**
     * Sets the parameters of the weapon from the list to the calss parameters
     */
    private void setIndividualWeapon(){
		this.name = allWeapons.get(id)[0];
		this.value =  Integer.parseInt(allWeapons.get(id)[1]);   // Must convert from String to int
		this.description = allWeapons.get(id)[2];
    }
    
	//Constructor -------------------------------------------------------------------------------------------------
	
	/**
	 * References, this list is structured as a list of strings, giving all the parameters of a given weapon
     * following : {'NAME', 'FORCE VALUE', 'DESCRIPTION'}
	 * @param key  Gives a different weapon depending on the ID
	 */
	public Weapon(int key){        // When the key is known
		setAllWeapons();
		this.id = key;  
		setIndividualWeapon();
	}
	
	/**
	 * Same as the previous constructor, except this one takes a randomly generated key
	 * @see Weapon(int key)
	 */
	
	public Weapon(){                               // When it is a random loot
		setAllWeapons();
		this.id = Util.randomNumberRanged(0, allWeapons.size() -1 );                     // Gives a random number within the range
		setIndividualWeapon();
		}
	}
	





