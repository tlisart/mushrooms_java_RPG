package com.viewable;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.ia.Pauser;
import com.model.Armor;
import com.model.Buff;
import com.model.Inventory;
import com.model.ItemAction;
import com.model.Player;
import com.model.Spell;
import com.model.Weapon;


public class InventoryFrame extends JFrame implements Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7147632941625182548L;
	
	//Attributs
	int capacity;
	Player player;
	Pauser pauser;
	
	//ToBeUsed to build the Mouse Listener
	Weapon weapon;
	Armor armor;
	Buff buff;
	Spell spell;
	
	
	
	public int getCapacity() {return capacity;}
	public void setCapacity(int capacity) {this.capacity = capacity;}
	
	//Constructor
	public InventoryFrame(Inventory inventory, Pauser pauser){
		
		this.pauser = pauser;
		
		Inventory inv = inventory;
		this.capacity = inv.getCapacity();
		this.player = inv.player;
				
	    this.setTitle("Inventory");
	    this.setSize(400*capacity, 300);
	    this.setLocationRelativeTo(null);
	    
	    // Layout to be used
	    this.setLayout(new GridLayout(4, capacity ));
	   

	    //Buttons
	    ///////////Weapons
	    //Label
	    JLabel labelWeapons = new JLabel("WEAPONS", SwingConstants.CENTER);
	    this.getContentPane().add(labelWeapons);
		labelWeapons.setFont(new Font("Serif", Font.PLAIN, 20));

	    //Buttons
	    for(int i = 0; i < inv.Weapons.size(); i++){				//Case Weapon
	    	JButton weapon = new JButton(inv.Weapons.get(i).name);
	    	this.getContentPane().add(weapon);
	    	ItemAction action1  = new ItemAction(inv.Weapons.get(i), inv, player, i, this);
	    	weapon.addActionListener(action1);
	    }
	    if(inv.Weapons.size() < this.capacity){						//Case Empty de weapon
	    	for(int i = this.capacity; i > inv.Weapons.size(); i--){	    	
	    		JButton empty = new JButton("Empty");
	    		this.getContentPane().add(empty);
	    	}
	    }
	    
	    ///////////Armors
	    //Label
	    JLabel labelArmors = new JLabel("ARMORS", SwingConstants.CENTER);
	    this.getContentPane().add(labelArmors);
		labelArmors.setFont(new Font("Serif", Font.PLAIN, 20));

	    //Buttons
	    for(int i = 0; i < inv.Armors.size(); i++){					//Case Armors
    	JButton armor = new JButton(inv.Armors.get(i).name);
	    	this.getContentPane().add(armor);
	    	ItemAction action2  = new ItemAction(inv.Armors.get(i), inv, player, i, this);
	    	armor.addActionListener(action2);
	    }
	    	

	    if(inv.Armors.size() < this.capacity){						//Empty de Armors
	    	for(int i = this.capacity; i > inv.Armors.size(); i--){	    	
	    		JButton empty = new JButton("Empty");
	    		this.getContentPane().add(empty);
	    	}
	    }

	    ///////////Buffs
	    //Label
	    JLabel labelBuffs = new JLabel("BUFFS", SwingConstants.CENTER);
	    this.getContentPane().add(labelBuffs);
		labelBuffs.setFont(new Font("Serif", Font.PLAIN, 20));

	    //Buttons
	    for(int i = 0; i < inv.Buffs.size(); i++){					//Case Buffs
    	JButton buff = new JButton(inv.Buffs.get(i).name);
	    	this.getContentPane().add(buff);
	    	ItemAction action3  = new ItemAction(inv.Buffs.get(i), inv, player, i, this);
	    	buff.addActionListener(action3);
	    }
	    if(inv.Buffs.size() < this.capacity){						//Empty de Buffs
	    	for(int i = this.capacity; i > inv.Buffs.size(); i--){	    	
	    		JButton empty = new JButton("Empty");
	    		this.getContentPane().add(empty);
	    	}
	    }
	    
	    ///////////Spells
	    //Label
	    JLabel labelSpells = new JLabel("SPELLS", SwingConstants.CENTER);
	    this.getContentPane().add(labelSpells);
		labelSpells.setFont(new Font("Serif", Font.PLAIN, 20));

	    //Buttons
	    for(int i = 0; i < inv.Spells.size(); i++){				//Case Spells
	    	JButton spell = new JButton(inv.Spells.get(i).name);
	    	this.getContentPane().add(spell);
	    	ItemAction action1  = new ItemAction(inv.Spells.get(i), inv, player, i, this);
	    	spell.addActionListener(action1);
	    }
	    if(inv.Spells.size() < this.capacity){						//Case Empty de Spell
	    	for(int i = this.capacity; i > inv.Spells.size(); i--){	    	
	    		JButton empty = new JButton("Empty");
	    		this.getContentPane().add(empty);
	    	}
	    }
	    

	    this.setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
        	 public void windowClosing(java.awt.event.WindowEvent e) {
        		pauser.resume();
        		dispose();             }
         });
	}

	@Override
	public void update(Observable o1, Object arg) {
		//End of the pause only if object used or thrown
		pauser.resume();
		dispose();
	}
}
