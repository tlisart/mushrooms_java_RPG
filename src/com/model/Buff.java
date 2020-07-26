//PAckage Statements
package com.model;
import java.io.IOException;
import java.io.Serializable;
//Import statements
import java.util.ArrayList;

import com.engine.Util;
import com.utils.TrackLog;

/**
* @author      Lamas Neil<nlamas@ulb.ac.be>
* @author      Lisart Théo<theo@lisart.be>
* @category:   AbstractItem
*/

public class Buff extends AbstractItem implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//Characteristics
	private String effect;
	
	//Getters&&Setters
	public String getEffect(){return this.effect;}
	
	// Available Weapons && Weapon Characteristics
	private static String[] B0 = {"Minor Healing Potion","30", "Heals instantly of 30 HP", "HEAL"};
	private static String[] B1 = {"Major Healing Potion","50", "Heals instantly of 50 HP", "HEAL"};
	private static String[] B2 = {"Potion of Regeneration","1500", "Heals completely", "HEAL"};
	
	private static String[] B3 = {"Book :The art of Destroying thys enemy (Marc Haeltermann the Strong)", "0", "Gives one point of STRENGTH", "BOOK"};
	private static String[] B4 = {"Book :How to Code Fast without looking at the keyboard (Hughes Bersini the quick)", "1", "Gives one point of SPEED", "BOOK"};
	private static String[] B5 = {"Book :Under control uncertain fireballs (JM Sparenberg)", "2", "Gives one point of DESTRUCTION", "BOOK"};
	private static String[] B6 = {"Book :How not to be fragile(Neil Lamas)", "3", "Gives one point of HEALING", "BOOK"};
	
	private static String[] B7 = {"Phoenix feather", "1", "If in inventory, ressurect the character", "RESSURECTION"};

	private ArrayList<String[]> allBuffs = new ArrayList<String[]>();

	 private void setAllBuffs(){
		 allBuffs.add(B0);
		 allBuffs.add(B1);
		 allBuffs.add(B2);
		 allBuffs.add(B3);
		 allBuffs.add(B4);
		 allBuffs.add(B5);
		 allBuffs.add(B6);
		 allBuffs.add(B7);
	 }
 
	 private void setIndividualBuffs(){
			this.name = allBuffs.get(id)[0];
			this.value = Integer.parseInt(allBuffs.get(id)[1]);
			this.description = allBuffs.get(id)[2];
			this.effect = allBuffs.get(id)[3];
	 }
	 
	 
	 //Use ----------------------------------------------------------------------------------------------------------
	 
		/**
		 * Uses the buff and apply its effects
		 * @param player
		 */
		public void use(Player player){
			if(this.getEffect()=="HEAL"){
				player.heal(this.getValue());
			}
			if(this.getEffect() == "BOOK"){ 
				player.increaseSkill(this.getValue(), 1);  
			}
			if(this.getEffect() == "RESSURECTION"){
				try {TrackLog.writeLog("I think I should keep this one, migth save my life some days");} 
				catch (IOException e) {e.printStackTrace();}
			}
		}
		
	//Constructor ------------------------------------------------------------------------------------------------------
	
	/**
	 * References, this list is structured as a list of strings, giving all the parameters of a given Buff or Bonus
     * following : {'NAME', 'EFFECT VALUE', 'DESCRIPTION', "EFFECT}
	 * @param key  Gives a different weapon depending on the ID
	 */
 
	public Buff(int key){        // When the key is known
		this.id = key; 
		setAllBuffs();
		setIndividualBuffs();
	}
	
	/**
	 * Same construction method, except the key is generated randomly for the loot
	 * @see Buff(int key)
	 */
	public Buff(){                               // When it is a random loot
		setAllBuffs();
		this.id =Util.randomNumberRanged(0, allBuffs.size() -1 );                     // Gives a random number within the range
		setIndividualBuffs();

		}
	}
	





