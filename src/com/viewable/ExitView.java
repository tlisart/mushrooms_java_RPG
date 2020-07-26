package com.viewable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ExitView{

	
	//Attributs
	protected Image totem;

	public ExitView() {
		try {totem = ImageIO.read(new File("Exit/totem.gif"));} 
		catch (IOException e) {System.out.println("je reconnais pas l'image totem");}
	}
}