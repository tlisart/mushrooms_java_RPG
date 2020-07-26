package com.engine;
import java.util.ArrayList;

/**
 * This class has 3 categories of attributes, the dimensions of the new Room, the size of the main Grid, the level of the player (needed to ensure the amount of Enemies (2) increases with the Stage
 * Then the Attributes relative to the Chests and Enemies positioning.
 * 
 * Determines all positions for one Room, randomly select the size of the room, how many Chests and how many enemies (relative to the stage)/ 
 * However does the verifications for all to be coherent and playable (no obstructions, no more enemies than possible, etc...) 
 * 
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Theo<theo@lisart.be>
 * @version     11.2               
 * @since       17-04-2016          
 * 
 */

public class Room {
	
// Variables
	
	private int[] upperCorner;                    //[y, x] coordinates of the upper left corner of the rectangular room, it is randomly generated (Same convention as the main matrix)
	private int width;                             
	private int height;
	
	private int area;;
	
	private int mapSize;                           
	private int stage;                             // Taken from the player, the stage sets the amount of enemies (and their force/resistance) 

	


// Getters&Setters
	
	
	public int getWidth() {return this.width;}
	public int getHeight() {return this.height;}
	public int getArea() { return this.area;}
	public int[] getUpperCorner() { return this.upperCorner;}
	
	public void setWidth(int newWidth) { this.width = newWidth;}
	public void setHeight(int newHeight) { this.height = newHeight;}

	
	
	
//Room generator
	
	/**
	 * Generates a random upperCorner, which can be anywhere except the right and down Grid boundaries
	 * @return upper (int[]) the coordinates of the tried upper corner, is recalled if doesn't fit the requirements
	 */
	
	private int[] generateUpper() {                     //Creates the random position of the upper-left corner of the new room
		int y = Util.randomNumberRanged(0, mapSize -1);
		int x = Util.randomNumberRanged(0, mapSize -1);
		int[] upper  = {y, x};
		
		return upper;		
	}
	

	/**
	 * It generates the dimensions of the Room, randomly, then checks if the room is contained in the available space (defined by the main Grid).
	 * If the conditions are not fulfilled, the process starts again, recursively until a match is found
	 * 
	 * @see Room#dimention()
	 */
	private void generateSize(){
		this.height = Util.randomNumberRanged(5, 7);       
		this.width = Util.randomNumberRanged(5, 7);                  

		
		// Checking the range 
		if(upperCorner[0] + height > this.mapSize){
			this.height = mapSize - upperCorner[0];
			if(mapSize - upperCorner[0] == 0 || mapSize - upperCorner[0] == 1){    // Room out of bound
				dimention(); 
			}
		}
		
		//Checking the range
		if(upperCorner[1] + width > this.mapSize){
			this.width = mapSize - upperCorner[1];
			if(mapSize - upperCorner[1] == 0 || mapSize - upperCorner[1] == 1){    //Room out of bound
				dimention();
			}
		}
	}
	
	
	/**
	 * Generates upper_corner, this is called as long as the rooms are out of the Map boundaries or intersect
	 * @see Room#generateSize()
	 * @see Room#generateUpper()
	 */
	
	private void dimention(){
		this.upperCorner = generateUpper();
		generateSize();
	}
	
	
// Chests Positioning -----------------------------------------------------------------------
	
/// Values
	private int[] chestCoordinates = new int[2];
	
///Getters
	public int[] getChestCoordinates(){return this.chestCoordinates;}
	
	
	/**
	 * It can only be either one Chest, or none. 
	 * Randomly set a number between 0 and 2 (so three numbers), if this number ==1 or ==2, calls positionningChest, and else there will be no chests
	 * @see Room#chestPosition() 
	 */
	private void setRandomChests(){
		int chestInThisRoom;    // YES = 1, NO = 0
		
		chestInThisRoom = Util.randomNumberRanged(0, 2);
		if(chestInThisRoom == 1 || chestInThisRoom == 2){   // Gives 2/3 chance to get a chest by room
			chestPosition();
		}
		else{                                               // Returns a {-1, -1} coordinates to Algo.java ==> There's no chest in the room
			chestCoordinates[0] = -1;
			chestCoordinates[1] = -1;

		}
	}
	
	/**
	 * Takes a random number for the Y and X position of the Chest, in the available area given by the room dimensions
	 * Chests do not appear on the boundaries of the room, to avoid the Path to be obstructed
	 * Called by setRandomChests()
	 * 
	 * @see Room#setRandomChests()
	 */
		private void chestPosition(){
			chestCoordinates[0] = Util.randomNumberRanged(this.upperCorner[0] + 1, this.upperCorner[0] + this.height -1);
			chestCoordinates[1] = Util.randomNumberRanged(this.upperCorner[1] + 1, this.upperCorner[1] + this.width -1);
			
		}

	
// Enemies Positioning -----------------------------------------------------------------------
		
		
/// Values and lists
	private int HowManyFoes;

	private ArrayList<int[]> enemiesCoordinates = new ArrayList<int[]>();
	
/// Getters
	public ArrayList<int[]> getFoePosition(){ return enemiesCoordinates;}
	public int getHowManyFoes(){return HowManyFoes;}
	
///Methods
	
	/**
	 * Same than the chestPositionning, however enemies can obstruct the way (so appear on the borders of the room
	 * @see {@link Room#chestPosition()
	 * @return foePosition (int[]), foe coordinates
	 */
	
	private int[] foePosition(){              //Gives a random position for the enemy inside of the room
		int[] foePosition = new int[2];
		foePosition[0] = Util.randomNumberRanged(this.upperCorner[0], this.upperCorner[0] + this.height -1);   //-1 avoid to be out of bound
		foePosition[1] = Util.randomNumberRanged(this.upperCorner[1], this.upperCorner[1] + this.width - 1);
		
		return foePosition;
	}
	
	/**
	 * Adds all foePositions into a list for further use, determines how many enemies to create (randomly, by increasing with the stage)
	 * @see Room#foePosition
	 */
	
	private void foeCoordinates(){          // Checks the position given by foePosition(), and write the results into a list
		this.HowManyFoes = Util.randomNumberRanged((stage - 1), (stage + 1));                   // The amount of enemies increases with the Stage, but to avoid conflict cannot be bigger than the available area
		this.area = width*height;
		
		if(HowManyFoes >= area - 2){       //Makes sure there isn't too much enemies relative to the size of the room
			HowManyFoes = area -3;
		}
		for(int i = 0; i <= HowManyFoes-1; i++){
			int [] foePosition = foePosition();
			if(i ==0){
				if(foePosition != chestCoordinates){
					enemiesCoordinates.add(foePosition);
					foePosition(); //Gives a new position to add next
			}
				
				else{
					int[] nullPosition = {-1, -1};       // Assures the list is big enough, so adds the coordinates which means no enemies
					enemiesCoordinates.add(nullPosition);
					foePosition(); //Gives a new position to add next
				}
			}
			else{                                  
				if(foePosition != chestCoordinates){   
					enemiesCoordinates.add(foePosition);
					foePosition();
				}
				else{
					int[] nullPosition = {-1, -1};
					enemiesCoordinates.add(nullPosition);
					foePosition();
				}
			} 
		} 
	}


//Constructor --------------------------------------------------------------------------------
	
	
	/**
	 * First Room constructor, calls dimention(), is called when the first room is being build, it doesn't have to check if the room intersects with another
	 * 
	 * @param map_height
	 * @param map_width
	 */
	
	public Room(int mapSize, int Stage){        // Constructor first room
		this.stage = Stage;
		this.mapSize = mapSize; 
		//Set the position and dimensions
		dimention();            
		//Adds foes and chests
		setRandomChests();
		foeCoordinates();
	}
	
/**
  * All other rooms checks if it intersect with another.
  * If it does, retry with other dimension and position.
 * @param mapSize
 * @param Grid
 * @param Stage
 */
	
	public Room(int mapSize, int[][] Grid, int Stage){    // Constructor all other rooms
		this.stage = Stage;
		this.mapSize = mapSize; 

		dimention();     
		
		for(int i = 0; i <= this.height -1 ; i++){
			for(int j =0; j <= this.width - 1; j++){
				if(Grid[i][j] == 0){
					dimention();                                 // Retry if the rooms intersect
				}
			}
		}
		
		//Set the position and dimensions
		dimention();            
		//Adds foes and chests
		setRandomChests();
		foeCoordinates();
	}

}

