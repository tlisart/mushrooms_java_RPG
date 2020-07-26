//Package Statements
package com.engine;
import java.io.Serializable;
// Import statements
import java.util.ArrayList;



/**
 * <b> Algo is the class which generates the randomized map.</b>
 * 
 * It starts with an evenly filled n*n matrix, then carves the rooms and pathways.
 * 
 * The used system will be as followed, where :
 * 
 * 0 = Floor
 * 1 = Wall
 * 2 = Enemies
 * 3 = Player   
 * 4 = Chest    
 * 5 = Event to the next stage
 *  
 * STEPS
 * 
 * 1) Starts by filling the grid with one's
 * 2) Places a fixed amount of rooms and apply it to the grid
 * 3) Places path between rooms, each room is linked to another
 * 4) Adds Chests and Monsters
 * 5) Determine the Player's position
 * 6) Determines the endStage position relatively to the player's, it is set to be further possible
 * 
 * The Algo class instantiates the Path and Room classes, that creates various checks to ensure the coherence of the random map. However it is the Algo class which 
 * apply the changes to the Grid. 
 * 
 * The grid will be used by the Map class, to initiate further dynamic processes.
 * 
 * It musts implement the java.io.Serializable interface for the save mechanism to work.
 * 
 * Convention : all coordinates for a location on the grid are always given in the order {y, x}.
 * 
 * @see com.model.Map
 * @see {@link com.utils.Serializer#serializeMap(com.model.Map, com.model.Player)}
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Theo<theo@lisart.be>
 * @version     11.2                
 * 
 */


public class Algo implements Serializable {

	private static final long serialVersionUID = 1L;

	

// Constant and declaration of variables

private int size;                          
private int Stage = 1;         // Default
private int availableArea;    

public int[][] Grid;           //Self explanatory


	/**
	 * 
	 * Declaration of the array for the player coordinates relative to the upper left corner, starting {0, 0}.
	 * 
	 */

private int[] PlayerPos = new int[2];    

//Getters&Setters
public int getSize() {return this.size;}
public void setSize(int size) {this.size = size;}
public int[][] getGrid() {return Grid;}
public void setGrid(int[][] grid) {Grid = grid;}

//Grid Modifiers

/**
 * Set the given coordinates to "Floor" (0). 
 * @param y
 * @param x
 */

private void setToFloor(int y, int x){    
	this.Grid[y][x] = 0;
}

/**
 * Set the given coordinates to "Enemy" (2). 
 * @param y
 * @param x
 */
private void setToEnemy(int y, int x){  
	this.Grid[y][x] = 2;
}

/**
 * Set the given coordinates to "Player" (3). 
 * @param y
 * @param x
 */
private void setToPlayer(int y, int x){ 
	this.Grid[y][x] = 3;
}

/**
 * Set the given coordinates to "Chest" (4). 
 * @param y
 * @param x
 */
private void setToChest(int y, int x){   
	this.Grid[y][x] = 4;
}

/**
 * Set the given coordinates to "Exit" (5). 
 * @param y
 * @param x
 */
private void setToExit(int y, int x){  
	this.Grid[y][x] = 5;
}

/**
 *Called first in the Constructor, two for loops which fill the Grid of (1) = "Wall";
 *  @see {@link com.engine.Algo#Algo(int, int)}.
 */
private void fullGrid(){                    

	for(int i = 0; i <= size -1 ; i++){
		for(int j = 0; j<= size -1 ; j++){
			this.Grid[i][j] = 1;			
		}
	}
}




// ROOM GENERATOR ---------------------------------------------------------------------------------------------------------------------------


/// Variables, specific to Rooms

private int HowMany;            // How many rooms needed relative to the size of the map
private transient Room[] listRooms;       // This table will be used to access all the rooms available

/**
 * It determines how many rooms should appear relative to the size of the Grid.
 * The formula (its coefficients) have been determined empirically.
 * Rounded to int by casting (area/(4*size)
 * @return
 */

private int howManyRooms(){             
	return (int) this.availableArea/(4*this.size);   
}

/**
 * Instantiate the Rooms and put them into a table
 * @see {@link com.engine.Algo#listRooms}.
 */

private void CreateRooms(){
	for(int i = 0; i <= HowMany - 1; i++){
		if(i ==0){
			Room NewRoom = new Room(this.size, this.Stage);
			roomApply(NewRoom);
			listRooms[i] = NewRoom;
		}
		else{
			Room NewRoom = new Room(this.size, this.Grid, this.Stage);
			listRooms[i] = NewRoom;
			roomApply(NewRoom);
		}
	}
}


/**
 * Carves the rooms with (0), enemies (2) and  Chests (4), all of which have been procedurally placed in the Room class
 * Calls the methods 
 * 
 * @see Algo#setToNone()
 * @see Algo#setToChest(int, int))
 * @see Algo#setToEnemy(int, int)
 * @param anyRoom element of the listRooms
 */

private void roomApply(Room anyRoom){
	
	//Room variables
	int room_width = anyRoom.getWidth();
	int room_height = anyRoom.getHeight();
	int[] upper_corner = anyRoom.getUpperCorner();
	
	//Apply Changes, Add Floors
	
	for(int i = 0; i <= room_height - 1; i ++){
		for(int j = 0;  j <= room_width - 1; j ++){
			setToFloor(i + upper_corner[0] , j + upper_corner[1] ); 
		}
	}
	
	//Apply Changes, Add Chests -- One or none by rooms
	
	int[] chestCoordinates = anyRoom.getChestCoordinates();
	if(chestCoordinates[0] != -1){
		setToChest(chestCoordinates[0], chestCoordinates[1]);
	}
	
	//Apply Changes, Add Enemies -- Amount depending on the size of the room
	ArrayList<int[]> foePosition = anyRoom.getFoePosition();
	for(int i = 0; i <= foePosition.size() - 1; i++){
		if(foePosition.get(i)[0] != -1){
			int Yenemy = foePosition.get(i)[0];
			int Xenemy = foePosition.get(i)[1];
			setToEnemy(Yenemy, Xenemy);
		}
	}
}


//PathWay Generator ------------------------------------------------------------------------------------------------------------------------


 //The same method is applied for the paths than for the rooms, we make a table of Objects Path, then apply them to the main Grid.
 

// Path Variables

transient Path[] allPath;   // Set to transient, it is not needed when loading the save

/**
 * Calls the method setToFloor (0), for each {y,x} coordinates.
 * @param toDraw  int[] table of all coordinates needed to draw the path
 */

private void drawPath(int [] toDraw){
	int y = toDraw[0];
	int x = toDraw[1];
	setToFloor(y, x);
}

/**
 * Instantiate a new Path, giving two rooms in the listRoom.
 * @see {@link com.engine.Algo#listRooms} the array in which all created rooms are stored
 * @see {@link com.engine.Path#Path(Room, Room)}  Path class
 * 
 */

private void pathGenerator(){
		for(int i = 0; i <= this.HowMany -2; i++){
		Path newPath = new Path(listRooms[i], listRooms[i+1]);
		this.allPath[i] = newPath;
	}
}

/**
 * Apply the connections stored in the array allPath
 * @see Algo#pathGenerator()
 * @see Algo#allPath
 * @see Algo#applyAllPath()
 */

private void applyAllPath(){
	for(int i = 0; i <= HowMany - 2; i++){
		ArrayList<int[]> coordinatesList = allPath[i].getPathCoordinates();
		for(int j = 0; j <= allPath[i].getPathCoordinates().size() -1 ; j++){      // For each element in the list of coordinates
			drawPath(coordinatesList.get(j));
		}		
	}
}

/**
 * Calls two functions one after one other to be more concise in the constructor.
 */

private void path(){
	pathGenerator();
	applyAllPath();
}

//Add the Player ------------------------------------------------------------------------------------------------------------------------

/**
  * Gives a pseudo-random coordinate, checks if it is a floor, if so the player is set to this position.
  * @see {@link com.engine.Util#randomNumberRanged(int, int)}.
  * 
  */

private void addPlayer(){
		int YPlayer = Util.randomNumberRanged(0, size -1);
		int XPlayer = Util.randomNumberRanged(0, size -1 );
		
		if(Grid[YPlayer][XPlayer] != 0){
			addPlayer();
		}
		else{
			setToPlayer(YPlayer, XPlayer);
			PlayerPos[0] = YPlayer;
			PlayerPos[1] = XPlayer;
	}
}


//Add the Exit --------------------------------------------------------------------------------------------------------------------------

/**
 * Apply changes for the Exit
 * @see Algo#addExit()
 * @param newExit
 */

private void applyExit(Exit newExit){
	int YPos = newExit.getExitPos()[0];
	int XPos = newExit.getExitPos()[1];
	
	setToExit(YPos, XPos);
}

/**
 * Instantiate the Exit relative to the player's position, see Exit class documentation
 * @see {@link com.engine.Exit}.
 */

private void addExit(){
	Exit newExit = new Exit(Grid, PlayerPos, this.size);    // The exit will be the farthest possible from the player
	applyExit(newExit);
}


//Constructor --------------------------------------------------------------------------------------------------------------------------


/**
 * It is the constructor of the randomizer
 * @param size    taken from the settings, given in the Dungeon class
 * @param Stage   taken from the player, gives an indication of the amount of enemies to place
 */

public Algo(int size,int Stage){
	
	// Set constants
	this.Stage = Stage;
	this.size = size;
	this.availableArea = size*size;
	this.Grid = new int[size][size];
	
	// Set variables
	HowMany = howManyRooms();
	listRooms = new Room[HowMany];
	allPath = new Path[HowMany -1];
	
	//Apply changes
	fullGrid();
	CreateRooms();	
	path();
	addPlayer(); // Places the player
	addExit();
}
}

