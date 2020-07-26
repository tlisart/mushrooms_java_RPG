package com.model;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import com.utils.TrackLog;

public class Inventory extends Observable implements Serializable {
	

	private static final long serialVersionUID = 1L;

	/**
	 * The inventory is mainly a list of a list of Items (AbstractItem.java)
	 * 1 --> Weapons 
	 * 2 --> Armors 
	 * 3 --> Buffs 
	 * 4 --> Spells  
	 */
	
	// Parameters
	public Player player;
	
	int capacity;    // By default, the amount of Weapons and armors you can carry is 2 Maximum for each, then is Updated depending on the STRENGTH of the player.

	public ArrayList<Weapon> Weapons;        // We don't initialize the size right away, it's easier to just check the amount of items already in the list
	public ArrayList<AbstractItem> Armors;
	public ArrayList<AbstractItem> Buffs;
	public ArrayList<AbstractItem> Spells;
	public ArrayList<AbstractItem> Rare;	
	
	// Equipment
	Weapon Eweapon;
	Armor Earmor;
	Spell Espell;
	
	// Getters setters
	public Weapon getEweapon() {return Eweapon;}
	public void setEweapon(Weapon eweapon) {Eweapon = eweapon;}
	
	public Spell getEspell(){return Espell;}
	public void setEspell(Spell espell){Espell = espell;}
	
	public Armor getEarmor() {return Earmor;}
	public void setEarmor(Armor earmor) {Earmor = earmor;}
	
	public int getCapacity() {return capacity;}
	public void setCapacity(int capacity) {this.capacity = capacity;}
	
	
	
//Inventory management ----------------------------------------------------------------------------------------------------
	
	/**
	 * Adds a looted item into the inventory, checks if the capacity of the inventory allows it, if not the item is lost
	 * @param lootedItem (AbstractItem)
	 * @throws IOException
	 */
	public void addInInventory(AbstractItem lootedItem) throws IOException{    
		
		if(lootedItem instanceof Weapon) {
			if(this.Weapons.size() < this.capacity){
				Weapons.add((Weapon) lootedItem);
				TrackLog.writeLog(" --> " + lootedItem.getName() + " added into the inventory");
				this.Eweapon = (Weapon) lootedItem;
			}
			else{
				TrackLog.writeLog("I found " + lootedItem.getName() + "!");
				TrackLog.writeLog("Too bad, I can't carry that much! ");
			}
		}
		
		if(lootedItem instanceof Armor) {
			if(this.Armors.size() < this.capacity){
				Armors.add(lootedItem);
				TrackLog.writeLog(" --> " + lootedItem.getName() + " added into the inventory");

			}
			else{
				TrackLog.writeLog("I found " + lootedItem.getName() + "!");
				TrackLog.writeLog("Too bad, I can't carry that much! ");
			}
		}
		
		if(lootedItem instanceof Buff) {
			if(this.Buffs.size() < this.capacity){     
				Buffs.add(lootedItem);
				TrackLog.writeLog(" --> " + lootedItem.getName() + " added into the inventory");

			}
			else{
				TrackLog.writeLog("I found " + lootedItem.getName() + "!");
				TrackLog.writeLog("Too bad, I can't carry that much! ");
			}
		}
		
		if(lootedItem instanceof Spell) {
			if(this.Spells.size() < this.capacity){
				Spells.add(lootedItem);
				TrackLog.writeLog(" --> " + lootedItem.getName() + " added into the inventory");
			}
			else {TrackLog.writeLog("Too bad, I can't carry that much! ");}
		}
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Deletes one item from the inventory, throws away function or destroy a Buff if used
	 * @param item
	 * @param posInList
	 */
	
	public void DeleteFromInventory(AbstractItem item, int posInList){   
		if(item instanceof Weapon) {
			this.Weapons.remove(posInList);
		}
		if(item instanceof Spell) {
			this.Spells.remove(posInList);
		}
		if(item instanceof Armor) {
			this.Armors.remove(posInList);
		}
		if(item instanceof Buff) {
			this.Buffs.remove(posInList);
		}
	}

	/**
	 * Calls the findPotion method, then uses the potion if there's any
	 * @see findPotion()
	 * @throws IOException
	 */
	public void usePotion() throws IOException{
		int posList = findPotion();
		if(posList == -1){
			TrackLog.writeLog("I don't have any potion left!");
		}
		else{
			Buff potion = (Buff) this.Buffs.get(posList);
			player.heal(potion.getValue());
			this.Buffs.remove(posList);			
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Finds the localization of a potion in the inventory
	 * Used with KeyListener P, the function usePotion already exists in Buffs but it needs the player 
	 * @return pos (int) position in the Buff list
	 * @throws IOException
	 */
	private int findPotion() throws IOException{
		ArrayList<Integer> allPotions = new ArrayList<Integer>();
		
		for(int i =0; i < this.Buffs.size(); i ++){
			Buff checkBuff = (Buff) Buffs.get(i);
			if(checkBuff.getEffect() == "HEAL"){
				int coord = i;
				allPotions.add(coord);
			}
		}
		int pos =-1;
		try{
			pos = allPotions.get(0);
		}catch(Exception e){			
		}
		
		return pos;
	}
	

	
// Equipment system ----------------------------------------------------------------------------------------------
	
	/**
	 * Equip one weapon, from any positions in the inventory
	 * @param pos
	 */
	public void equipWeapon(int pos){
		try{
			this.Eweapon = (Weapon) Weapons.get(pos);
		}
		catch(Exception e) {
		}
	}
	
	/**
	 * Equip one armor, from any positions in the inventory
	 * @param pos
	 */
	public void equipArmor(int pos){
		try{
			this.Earmor = (Armor) Armors.get(pos);
		}
		catch(Exception e) {	
		}
	}
	
	/**
	 * Equip one spell, from any positions in the inventory
	 * @param pos
	 */
	public void equipSpell(int pos){
		try{
			this.Espell = (Spell) Spells.get(pos);
		}
		catch(Exception e) {	
		}
	}

	// Spells Unlocker ----------------------------------------------------------------------------------------------
	
	int skillDestruction=1;
	int skillHeal=1;
	
	/**
	 * Is called when the upDateAllSkills is called, unlocks spells (and add them to the inventory)  when a book is read. After two level in a skill increased, no more spells are added. (it still has consequences 
	 * to do so).
	 * 
	 * @see com.model.Player#increaseSkill(int, int)
	 * @param skillDestruction
	 * @param skillHeal
	 * @throws IOException
	 */
	public void upDateSpells(int skillDestruction, int skillHeal) throws IOException{
		if(skillDestruction > this.skillDestruction && skillDestruction < 4){
			addInInventory(new Spell(skillDestruction - 2, "DESTRUCTION"));
			this.skillDestruction = skillDestruction;
		}
		if(skillHeal > this.skillHeal && skillHeal < 4){
			addInInventory(new Spell(skillHeal - 2, "HEAL"));
			this.skillHeal = skillHeal;
		}
	}
	
	// Set inventory to default ----------------------------------------------------------------------------------
	
	/**
	 * Set the equipment to default
	 * @throws IOException 
	 */
	
	private void setToDefault() throws IOException{
		//Weapon
		TrackLog.writeLog("I found a Rusty Sword and a potion, not much but will do the trick for now");
		Weapon RustyFound = new Weapon(0);    //Adds armor ID = 0
		addInInventory(RustyFound);
		equipWeapon(0);
		
		//Potion
		Buff Potion = new Buff(0);            //Adds buff ID = 0
		addInInventory(Potion);
		
		//Armor
		Armor SilkShirt = new Armor(0);      //Adds armor ID = 0
		addInInventory(SilkShirt);
		equipArmor(0);
		
		setChanged();
		notifyObservers();
	}
	
// Constructor ---------------------------------------------------------------------------------------------------
	
	/**
	 * Inventory construction method, the default Items are "Rusty Sword", "Destroyed leather armor", and "Minor fire ball"
	 * @param capacity
	 * @throws IOException 
	 */
	
	public Inventory(Player player) throws IOException{
		this.player = player;
		this.capacity = player.getCapacity();
		
		this.Weapons =  new ArrayList<Weapon>();
		this.Armors =  new ArrayList<AbstractItem>();
		this.Buffs =  new ArrayList<AbstractItem>();
		this.Rare =  new ArrayList<AbstractItem>();
		this.Spells =  new ArrayList<AbstractItem>();
		
		setToDefault();
	}
}
