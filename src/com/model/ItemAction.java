package com.model;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.viewable.InventoryFrame;

/**
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 */

public class ItemAction extends JFrame implements ActionListener{


	private static final long serialVersionUID = 1L;
	//Attributes 
	AbstractItem item;
	Inventory inv;
	Player player;
	int posInList;
	InventoryFrame bigWindow;
	
	public ItemAction(AbstractItem item, Inventory inv, Player player, int posInList, InventoryFrame bigWindow){
		this.item = item;
		this.inv = inv;
		this.player = player;
		this.posInList = posInList;
		this.bigWindow = bigWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setTitle("Actions disponibles");
		this.setLocationRelativeTo(null);
	    this.setSize(100, 150);


		// Buttons
			///Use
			JButton use = new JButton();
			use.setText("Use");
			UseItem action1  = new UseItem(item, inv, player, posInList, this);
			use.addActionListener(action1);
			action1.addObserver(bigWindow);
			//Throw away
			JButton throwAway = new JButton();
			throwAway.setText("Throw away");
			ThrowItem action2  = new ThrowItem(item, inv, posInList, this);
			throwAway.addActionListener(action2);
			action2.addObserver(bigWindow);
			
		    this.setLayout(new GridLayout(2, 1));

		    this.getContentPane().add(use);
			this.getContentPane().add(throwAway);
			
			this.setVisible(true);
		
	}

}
