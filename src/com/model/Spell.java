package com.model;

//Import statements
import java.util.ArrayList;

/**
 * Spell class, with these effects 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * 
 */
public class Spell extends AbstractItem {

	private static final long serialVersionUID = 1L;
	private String effect;
	public String getEffect(){return this.effect;}
	
	
	// Available Spells 
	
	private static String[] S0 = {"Lightning (Minor) ", "20", "Middle ranged lightning attack", "ATTACK"};
	private static String[] S1 = {"Lightning (Major)", "30", "Middle ranged lightning attack", "ATTACK"};
	private static String[] S2 = {"Heal (Minor)", "5", "Heal on self", "HEAL"};
	private static String[] S3 = {"Heal (Major)", "10", "Heal on self", "HEAL"};

	private ArrayList<String[]> allDestructionSpells = new ArrayList<String[]>();
	private ArrayList<String[]> allHealSpells = new ArrayList<String[]>();

	
	/**
	 * Puts all available Items into a list
	 */
    private void setAllSpells(){
    	allDestructionSpells.add(S0);
    	allDestructionSpells.add(S1);
    	allHealSpells.add(S2);
    	allHealSpells.add(S3);
    }
    
    private void setDestructionSpell(){
		this.name = allDestructionSpells.get(id)[0];
		this.value =  Integer.parseInt(allDestructionSpells.get(id)[1]);   // Must convert from String to int
		this.description = allDestructionSpells.get(id)[2];
		this.effect = allDestructionSpells.get(id)[3];
    }
    
    private void setHealSpell(){
		this.name = allHealSpells.get(id)[0];
		this.value =  Integer.parseInt(allHealSpells.get(id)[1]);       // Must convert from String to int
		this.description = allHealSpells.get(id)[2];
		this.effect = allHealSpells.get(id)[3];
    }
    
	//Constructor ----------------------------------------------------------------------------------------
	
	/**
	 * References, this list is structured as a list of strings, giving all the parameters of a given weapon
     * following : {'NAME', 'FORCE VALUE', 'DESCRIPTION'}
	 * @param key  Gives a different spell depending on the ID
	 */
	
	public Spell(int key, String type){        
		setAllSpells();
		this.id = key;
		if(type == "DESTRUCTION"){
			setDestructionSpell();
		}
		if(type == "HEAL"){
			setHealSpell();
		}
	}
}
