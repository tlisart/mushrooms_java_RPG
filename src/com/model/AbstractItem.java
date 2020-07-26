package com.model;

import java.io.Serializable;

/**
	 * @author      Lamas Neil<nlamas@ulb.ac.be>
	 * @author      Lisart Théo<theo@lisart.be>
	 * @version     0.1.1                 (current version number of program)
	 * @since       11-03-2016          (the version of the package this class was first added to)
	 * 
	 * 
	 * Inherit from Weapons.java (Weapons, close or ranged), Armors.java (Armors) Buffs.java (Buffs as potions, special effects one-use effects), Rare.java(Rare special items)
	 * and Spells.java (Spells healing and attack spells) 
	 */


public abstract class AbstractItem extends Cell implements Serializable{	
	
	private static final long serialVersionUID = -4866834426076829079L;

	//Attributes
	public String name;
	public String description;
	public int value;
	public int id;

	//GETTERS
	public String getName(){return name;}
	public int getValue() {return this.value;}
	public int getID(){return this.id;}	
	
	//SETTERS
	public void setName(String newName){this.name = newName;}
	public void setValue(int newForce){this.value = newForce;}

}
