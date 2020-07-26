package com.viewable;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import com.model.Character;

public class LifeBar implements Observer {
	
	//Values
	Character character;
	int HP;
	int HPmax;
	float HPpercentage;
	public Image background;
	public Image HPView;
	
	//Constructor
	public LifeBar(Character character){
		this.character = character;
		this.HP = character.getHP();
		this.HPmax = character.getHpMax();
		this.HPpercentage = (float) HP/HPmax;
		
		try {this.background = ImageIO.read(new File("LifeBar/Red.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.HPView = ImageIO.read(new File("LifeBar/Green.gif"));} 
		catch (IOException e) {e.printStackTrace();}
	}
	
	public void setHPpercentage(int HP, int HPmax){
		this.HP = HP;
		this.HPmax = HPmax;
		this.HPpercentage = (float)HP/HPmax; //Attention HPMAX != 0 
	}

	public void update(Observable o, Object arg) {
		this.setHPpercentage(character.getHP(), character.getHpMax());		
	}
}
