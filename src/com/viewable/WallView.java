package com.viewable;

//Import statements
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class WallView {
	//Attributs
	protected Image wall;
	protected Image water;
	protected Image lava;
	
	//Constructeur
	public WallView(){
		//Initialisation de l'image
		try {this.lava = ImageIO.read(new File("Wall/Lava.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.water = ImageIO.read(new File("Wall/water.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.wall = ImageIO.read(new File("Wall/WallRock2.gif"));} 
		catch (IOException e) {e.printStackTrace();}
	}
}
