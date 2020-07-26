package com.viewable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FloorView {
		
	//Attributes
	protected Image floorWood;
	protected Image floor;
		
	//Constructor
	public FloorView(){
		////////// Initialisation image
		try {this.floorWood = ImageIO.read(new File("Floor/FloorWood.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.floor = ImageIO.read(new File("Floor/floor.jpg"));} 
		catch (IOException e) {e.printStackTrace();}
	}
}
