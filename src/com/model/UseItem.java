package com.model;

//Import Statements
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;


public class UseItem extends Observable implements ActionListener{

	AbstractItem item;
	Inventory inv;
	Player player;
	int posInList;
	ItemAction window;

	
	public UseItem(AbstractItem item, Inventory inv, Player player, int posInList, ItemAction window){
		this.item = item;
		this.inv = inv;
		this.player = player;
		this.posInList = posInList;
		this.window = window;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (this.item instanceof Weapon){
			inv.setEweapon((Weapon) item);
		}
		if (this.item instanceof Spell){
			inv.setEspell((Spell) item);
		}
		if (this.item instanceof Armor){
			inv.setEarmor((Armor) item);
		}
		if (this.item instanceof Buff){
			((Buff) item).use(player);
			if(((Buff) this.item).getEffect() != "RESSURECTION"){    // The phoenix feather has no use other than be kept in the inventory
			inv.DeleteFromInventory(item, posInList);
			}
		}
		setChanged();
		notifyObservers();
		window.dispose();
	}

}
