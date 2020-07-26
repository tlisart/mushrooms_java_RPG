//Package Statements
package com.model;
//import statements
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This class is the superclass of all dynamic cells, Player and Mushrooms inherits from it. 
 * Has as attributes : hpMax, movingTime, hp, orientation, state alive, map, gridPosX, grosPosY and attributes for the attack rawAttackPoints
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @category:   Dynamic Cell
 * 
 */ 


public abstract class Character extends Cell { 
	
	private static final long serialVersionUID = 1L;


	protected int hpMax;            // HP Max, relative to player level and skills
	public int movingTime; 		    // The mushroom internal clock
	protected int hp;
	protected int orientation;      // 0 = right, 1 = down, 2= left, 3=up
	protected int state;            //0 = resting; 1 = Attacking; 2 = moving; 3 = dead; 4 = Using tri-force; 5 = lit by a lighting
	public boolean alive;

	transient public Map map;	    // The map doesn't need to be serialized twice
	
	protected int gridPosX; 
	protected int gridPosY;
	
	// Attributes relative the equipment
	protected int rawAttackPoints;              // Damages per hits (raw)
	
	// Getters 
	public int getHpMax() {return this.hpMax;}
	public int getHP() {return this.hp;}
	public int getGridPosX() {return gridPosX;}
	public int getGridPosY() {return gridPosY;}
	public int getOrientation() {return orientation;}
	public int getState() {return state;}
	public int getMovingTime() {return movingTime;}
	
	//Setters
	public void setGridPosX(int gridPosX) {
		this.gridPosX = gridPosX;
		setChanged();
	}
	public void setGridPosY(int gridPosY) {
		this.gridPosY = gridPosY;
		setChanged();
		
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
		setChanged();
		notifyObservers();

	}
	public void setState(int state) {
		this.state = state;
		setChanged();
		notifyObservers();
	}
	public void setMoovingTime(int movingTime) {
		this.movingTime = movingTime;
	}
	public void setMap(Map map) {
		this.map = map;
	}

	
//Position checkers -------------------------------------------------------------------------------
	
	/**
	 * Checks what instance of Cell is beside the Character, checking in the direction the Character is looking towards.
	 * @param orientation (int)
	 * @return type (Cell type)
	 */
	
	public int check(int orientation){
		int type = 0;									//Init to type to floor
		int X = this.getGridPosX();
		int Y = this.getGridPosY();
		
		if (orientation == 0){							// Right side
			if (X == map.getSize()-1){ 	// BORDER
				return 1;								// as a Wall 
			}
			type = map.grid[Y][X+1].whoIsThere(map);
		}
		else if(orientation == 1){						//Down side
			if (Y == map.getSize()-1){
				return 1; 
			}
			type = map.grid[Y+1][X].whoIsThere(map);
		}
		else if(orientation == 2){						//Left side
			if (X == 0){
				return 1; 
			}
			type = map.grid[Y][X-1].whoIsThere(map);
		}
		else if(orientation == 3){						//Up side
			if (Y == 0){
				return 1; 
			}
			type = map.grid[Y-1][X].whoIsThere(map);
		}
		return type;
	}

	/**
	 * Sets hp for Player and Mushrooms, checks if the amount given is over the hpMax, then apply changes to the stored values
	 * @param hP  (int)
	 */
	
	public void setHP(int hP) {
		if(hP>hp){hp = this.hpMax;}
		else{hp = hP;}
		setChanged();
		notifyObservers();
	}
	
	/**
	  * Dynamic object death sequence.
	  * Calls for a timer, when the sequence is launched the Character is replaced by a 'floor', and so it disappears
	  * @param line
	  * @param column
	  */
	
	public void characterDeath(int line, int column, AbstractItem newLoot) throws IOException{
		//constructor TimerTask
  		TimerTask task2 = new TimerTask(){//SIMPLIFIABLE via thread
  			//Definition de la tâche
			int i = 0;
  			public void run(){
				i++;
				if (i > 1) { //When the timer plays for the fourth time it stops and the charcacter's cell = floor 
	  				alive = false;
	  				if (newLoot == null){map.grid[line][column] = new Floor();}
	  				else{map.grid[line][column] = newLoot;}
	  				setChanged();
	  				notifyObservers();
					cancel();
				}
			}	
		};
		Timer timer = new Timer();
		timer.schedule(task2, 0, 400);
	}
}