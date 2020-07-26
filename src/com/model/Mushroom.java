// Package statements
package com.model;
//Import Statements
import java.io.IOException;

/**
 * The mushroom class is the enemy class, the movements are managed by the com.ia
 * Extends Character, has as attributes the vision (range of the view of the monster) and reloadTime
 * 
 * @see com.ia
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @category:   Dynamic Cell
 * 
 */  

public class Mushroom extends Character{

	private static final long serialVersionUID = 1L;
	
	//Attributes
	int vision;              // Range of vision of the Monster, within the player is seen and can be attacked
	public int reloadTime;   // Internal clock, time between two Mushrooms attacks
	

// Attacks the player ------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Attack function, for any Player in game, it checks the position of it and attacks if in range.
	 * Deals damages relative to the armor value of the player.
	 * Calls check(0), check(1), check(2), check(3) 
	 * @throws IOException 
	 */
	
	public void rawAttack() throws IOException{ 
		
  		//the arg allows to know what the target is,  and to reinitialize the position after hit
		
		Player player = null;
		int RawDamages;
		RawDamages = this.rawAttackPoints;                    // Checks the power value of the monster
		
		// Attacks if the player is within range (1 block aside)
		if(this.check(0) == 3){
			//Attack player if he's on the right
			try{player = (Player) map.grid[this.getGridPosY()][this.getGridPosX()+1];
				int ArmorValue = player.getArmorValue();
				int realDamages = RawDamages - ArmorValue;
				player.sufferDamage(realDamages);}	
				catch(ClassCastException e){}
		}
		
		if(this.check(1) == 3){
			try{player = (Player)map.grid[this.getGridPosY()+1][this.getGridPosX()];
			int ArmorValue = player.getArmorValue();
			int realDamages = RawDamages - ArmorValue;
			player.sufferDamage(realDamages);}	
			catch(ClassCastException e){}
		}
		
		if(this.check(2) == 3){
			try{player = (Player) map.grid[this.getGridPosY()][this.getGridPosX()-1];
			int ArmorValue = player.getArmorValue();
			int realDamages = RawDamages - ArmorValue;
			player.sufferDamage(realDamages);}	
			catch(ClassCastException e){}
		}
		
		if(this.check(3) == 3){
			try{player = (Player) map.grid[this.getGridPosY()-1][this.getGridPosX()];
			int ArmorValue = player.getArmorValue();
			int realDamages = RawDamages - ArmorValue;
			player.sufferDamage(realDamages);}	
			catch(ClassCastException e){}
		}
	}
	
	
	/**
	 * Checks around the mushroom if the player is within range, if so returns the player.
	 * @see {@link com.ia.GoToThePlayer}
	 * @return player (Player)
	 */
	
	public synchronized Player searchPlayer(){

		Player player = null; 		
		int quitter = 0;
		for (int i = -vision; quitter ==0 && i<=vision; i++){
			for(int j = -vision; quitter ==0 && j<=vision; j++){
				if (i+ this.getGridPosY() < map.getSize() &
					i+ this.getGridPosY() > 0 &
					j+ this.getGridPosX() < map.getSize() &
					j+ this.getGridPosX() > 0){
										
					if(map.grid[this.getGridPosY() + i][this.getGridPosX() + j] != null){ 						//Avoid potential error
						if(map.grid[this.getGridPosY() + i][this.getGridPosX() + j].whoIsThere(map) == 3){ 		//player
							player = (Player) map.grid[this.getGridPosY() + i][this.getGridPosX() + j];
							quitter = 1;
						}
					}
				}
			}
		}
		return player;
	}
	
		
// Suffer damages on Mushrooms -----------------------------------------------------------------------------------------------------------
	
	/**
	 * public damage system, a mob(Monster) would call this function if it is in range of attacking
	 * Calls the characterDeath(x, y), which is the death sequence of the dynamic object;
	 * @throws IOException 
	 * @param RealDamages
	 * @param inventory
	 * @param player
	 */
	
		protected void sufferDamage(int RealDamages, Inventory inventory, Player player) throws IOException {          
			if(this.hp - RealDamages > 0){
				setHP(this.hp - RealDamages);
			}
			else{
				AbstractItem newLoot = Loot.NewLoot();
				this.characterDeath(this.gridPosY, this.gridPosX, newLoot);                       //Launch death sequence
				player.setXP(20);           //Adds XP's
			}
		}
		
	//Constructor ----------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * Enemies constructor, sets the initial position, orientation, the movingTime, reloadTime and attackPoints.
	 * 
	 * @see com.ia for the moving methods
	 * @param gridPosY
	 * @param gridPosX
	 * @param map
	 */
	public Mushroom(int gridPosY, int gridPosX, Map map){
		this.map = map;                        //Needed for positions checking
		
		this.movingTime = 400;
		this.reloadTime = 1000 - map.Stage;    // Mushrooms move faster the higher the Stage
		this.rawAttackPoints = 10 + map.Stage*2;
		
		this.hpMax = 50 + (map.Stage)*10;
		this.hp = this.hpMax;                  //Starting with full health
		
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		map.grid[this.gridPosY][this.gridPosX] = this;				
		this.setOrientation(1);
		
		this.alive = true;
		this.vision = 9;
	}
}