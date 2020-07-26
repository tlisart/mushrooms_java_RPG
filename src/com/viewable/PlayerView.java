package com.viewable;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

import javax.imageio.ImageIO;

import com.model.Map;
import com.model.Player;

public class PlayerView extends Observable{ //à changer en CharacterView

	//Attributs
	protected Image appearance;
	protected Image[][] appearanceMoove = new Image[4][4];
	protected Image[][] appearanceAttack = new Image [4][4];
	protected Image[][] triForce = new Image[4][4];
	protected Image[] thunderView = new Image[6];
	protected Image[] thunderPlayer = new Image[4];


	protected int state;           //0 = resting; 1 = attacking; 2 = moving; 3= dead; 4 = using TriForce; 5 using thunder
	
	protected int posViewX;
	protected int posViewY;
	
	Player player;
	
	//Constructeur
	public PlayerView(Player player){
		
		this.player = player;
		
		setPosViewX(player.getGridPosX()*Map.unit + Map.unit/10);
		setPosViewY(player.getGridPosY()*Map.unit);
		
		setState(0);
		
		//Sephiroth
		//////////Initialisation des appearancesMoove
		///////////////////////////RIGHT
		try {this.appearanceMoove[0][0] = ImageIO.read(new File("Player/Right1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[0][1] = ImageIO.read(new File("Player/Right.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[0][2] = ImageIO.read(new File("Player/Right3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[0][3] = ImageIO.read(new File("Player/Right.png"));} 
		catch (IOException e) {e.printStackTrace();}
	
		///////////////////////////DOWN
		try {this.appearanceMoove[1][0] = ImageIO.read(new File("Player/Down1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[1][1] = ImageIO.read(new File("Player/Down.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[1][2] = ImageIO.read(new File("Player/Down3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[1][3] = ImageIO.read(new File("Player/Down.png"));} 
		catch (IOException e) {e.printStackTrace();}

		///////////////////////////LEFT
		try {this.appearanceMoove[2][0] = ImageIO.read(new File("Player/Left1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[2][1] = ImageIO.read(new File("Player/Left.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[2][2] = ImageIO.read(new File("Player/Left3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[2][3] = ImageIO.read(new File("Player/Left.png"));} 
		catch (IOException e) {e.printStackTrace();}

		///////////////////////////UP
		try {this.appearanceMoove[3][0] = ImageIO.read(new File("Player/Up1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[3][1] = ImageIO.read(new File("Player/Up.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[3][2] = ImageIO.read(new File("Player/Up3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceMoove[3][3] = ImageIO.read(new File("Player/Up.png"));} 
		catch (IOException e) {e.printStackTrace();}

				
		////////// Initialisation des appearancesAttack
		///////////////////////////RIGHT
		try {this.appearanceAttack[0][0] = ImageIO.read(new File("Attack/A1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[0][1] = ImageIO.read(new File("Attack/A2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[0][2] = ImageIO.read(new File("Attack/A3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[0][3] = ImageIO.read(new File("Attack/A4.png"));} 
		catch (IOException e) {e.printStackTrace();}		
		///////////////////////////DOWN
		try {this.appearanceAttack[1][0] = ImageIO.read(new File("Attack/D1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[1][1] = ImageIO.read(new File("Attack/D2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[1][2] = ImageIO.read(new File("Attack/D3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[1][3] = ImageIO.read(new File("Attack/D4.png"));} 
		catch (IOException e) {e.printStackTrace();}	
		///////////////////////////LEFT
		try {this.appearanceAttack[2][0] = ImageIO.read(new File("Attack/C1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[2][1] = ImageIO.read(new File("Attack/C2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[2][2] = ImageIO.read(new File("Attack/C3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[2][3] = ImageIO.read(new File("Attack/C4.png"));} 
		catch (IOException e) {e.printStackTrace();}	
		///////////////////////////UP
		try {this.appearanceAttack[3][0] = ImageIO.read(new File("Attack/B1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[3][1] = ImageIO.read(new File("Attack/B2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[3][2] = ImageIO.read(new File("Attack/B3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.appearanceAttack[3][3] = ImageIO.read(new File("Attack/B3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		///////TRIFORCE
		///////////////////////////RIGHT
		try {this.triForce[0][0] = ImageIO.read(new File("Attack/TriForce1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[0][1] = ImageIO.read(new File("Attack/TriForce2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[0][2] = ImageIO.read(new File("Attack/TriForce3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[0][3] = ImageIO.read(new File("Attack/TriForce4.png"));} 
		catch (IOException e) {e.printStackTrace();}
		///////////////////////////DOWN
		try {this.triForce[1][0] = ImageIO.read(new File("Attack/DTriForce1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[1][1] = ImageIO.read(new File("Attack/DTriForce2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[1][2] = ImageIO.read(new File("Attack/DTriForce3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[1][3] = ImageIO.read(new File("Attack/DTriForce4.png"));} 
		catch (IOException e) {e.printStackTrace();}
		///////////////////////////LEFT
		try {this.triForce[2][0] = ImageIO.read(new File("Attack/LTriForce1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[2][1] = ImageIO.read(new File("Attack/LTriForce2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[2][2] = ImageIO.read(new File("Attack/LTriForce3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[2][3] = ImageIO.read(new File("Attack/LTriForce4.png"));} 
		catch (IOException e) {e.printStackTrace();}
		///////////////////////////UP
		try {this.triForce[3][0] = ImageIO.read(new File("Attack/UTriForce1.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[3][1] = ImageIO.read(new File("Attack/UTriForce2.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[3][2] = ImageIO.read(new File("Attack/UTriForce3.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.triForce[3][3] = ImageIO.read(new File("Attack/UTriForce4.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		//////Thunder
		try {this.thunderView[0] = ImageIO.read(new File("Attack/Lightning1.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderView[1] = ImageIO.read(new File("Attack/Lightning2.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderView[2] = ImageIO.read(new File("Attack/Lightning3.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderView[3] = ImageIO.read(new File("Attack/Lightning4.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderView[4] = ImageIO.read(new File("Attack/Lightning5.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderView[5] = ImageIO.read(new File("Attack/Lightning6.gif"));} 
		catch (IOException e) {e.printStackTrace();}
		
		///////ThunderPlayer
		try {this.thunderPlayer[0] = ImageIO.read(new File("Attack/RightL.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderPlayer[1] = ImageIO.read(new File("Attack/DownL.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderPlayer[2] = ImageIO.read(new File("Attack/LeftL.png"));} 
		catch (IOException e) {e.printStackTrace();}
		try {this.thunderPlayer[3] = ImageIO.read(new File("Attack/UpL.png"));} 
		catch (IOException e) {e.printStackTrace();}
		
		setAppearance(appearanceMoove[player.getOrientation()][1]);

	}
	//Methodes
	
	
	public void setPosViewX(int posX) {this.posViewX = posX;}
	public void setPosViewY(int posY) {this.posViewY = posY;}
	
	public int getPosViewX() {return posViewX;}
	public int getPosViewY() {return posViewY;}
	
	public Image getAppearanceAttack(int orientation, int i) {
		return appearanceAttack[orientation][i];
	}
	public Image getTriForce(int orientation, int i) {
		return triForce[orientation][i];
	}
	public Image getThunderView(int i) {
		return thunderView[i];
	}
	public Image getThunderPlayer(int i) {
		return thunderPlayer[i];
	}
	public Image getAppearance() {
		return appearance;
	}
	public Image getAppearanceMoove(int orientation, int i) {
		return appearanceMoove[orientation][i];
	}
	public void setAppearance(Image appearance) {
		this.appearance = appearance;
		setChanged();
		notifyObservers();
	}
	public void setState(int state){
		this.state = state;
	}
	public int getState(){
		return this.state;
	}	  	
}
