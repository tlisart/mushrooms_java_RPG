package com.viewable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ChestView extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8596852093717087632L;
	
	//Attributs
	protected Image closedChest;
	protected Image openChest;
	protected Image gift;

	
	public ChestView() throws IOException{
		closedChest = ImageIO.read(new File("Chest/coffre_fermé.png"));
		openChest = ImageIO.read(new File("Chest/coffre_ouvert.png"));
		gift = ImageIO.read(new File("Chest/gift.png"));

	}
}