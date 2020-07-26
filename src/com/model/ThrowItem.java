package com.model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

public class ThrowItem extends Observable implements ActionListener{

	//Attributs 
	AbstractItem item;
	Inventory inv;
	int posInList;
	ItemAction window;
		
	//Constructor
	public ThrowItem(AbstractItem item, Inventory inv, int posInList, ItemAction window){
		this.item = item;
		this.inv = inv;
		this.posInList = posInList;
		this.window = window;
	}
		
	public void actionPerformed(ActionEvent e) {
		inv.DeleteFromInventory(item, posInList);
		setChanged();
		notifyObservers();
		window.dispose();
	}

}
