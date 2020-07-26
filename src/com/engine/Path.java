package com.engine;
import java.util.ArrayList;


/**
 * Has 8 attributes, Grid which is the integer int[] up to this point (Rooms applied, Enemies and Chests applied)
 * Room room1 and Room room2 (two rooms to link)
 * The respective coordinates of the upper left corners of the rooms. (integers x1, y1, x2, y2)
 * 
 * and an ArrayList<int[]> pathCoordinates, which stores all coordinates of the path between rooms. 
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Theo<theo@lisart.be>
 * @version     11.2
 * 
 */

public class Path{
	
	Room room1;
	Room room2;
	int y1;
	int y2;
	
	int x1;
	int x2;
	
	ArrayList<int[]> pathCoordinates = new ArrayList<int[]>();;      //Will be read by the Algorithm to link to upper left corners of rooms of the map
	
	//GETTERS
	public ArrayList<int[]> getPathCoordinates(){return pathCoordinates;}

	/**
	 * Adds the coordinates of each block to pathCoordinates list,  making a path between two upper corners.
	 * 
	 * Firstly, it compares the values of y, then loop until is recorded all the coordinates to get to the first y coordinate
	 * to the second one. Then do the same for the x coordinates, making sure it starts where it stopped with the y.
	 */
	private void setPath(){

		if(y1 < y2){      //Starting by comparing the y's
			for(int i = y1; i <= y2 ; i++){
				if(x1 < x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = i; 
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	//Adding the new coordinates
					
					for(int j = x1; j <= x2; j++){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX); //Adding the new coordinates
					}
				}
				
				if(x1 >= x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = i;
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	
					
					for(int j = x1; j >= x2; j--){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX);

					}
				}
			}
		}
		
		//This is the same process, for a different configuration
		
		if(y1 > y2){
			for(int i = y1; i >= y2 ; i--){
				if(x1 < x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = i;
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	
					
					for(int j = x1; j <= x2; j++){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX);	
					}
				}
				
				if(x1 >= x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = i;
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	
					
					for(int j = x1; j >= x2; j--){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX);

					}
				}
			}
			if( y1 == y2){
				if(x1 <= x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = y1;
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	
					
					for(int j = x1; j <= x2; j++){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX);	
					}
				}
				
				if(x1 >= x2){
					int[] toAddY = new int[2];
					
					toAddY[0] = y2;    // Why not change? 
					toAddY[1] = x1;
					
					pathCoordinates.add(toAddY);	
					
					for(int j = x1; j >= x2; j--){
						int[] toAddX = new int[2];
						
						toAddX[0] = y2;
						toAddX[1] = j;
						pathCoordinates.add(toAddX);
					}
				}
			}
		}
	}
	
	// Constructor
	
	/**
	 * Gets the coordinates of the upper corners (left) of the rooms, and calls setPath() accordingly
	 * 
	 * @param Grid
	 * @param room1
	 * @param room2
	 */
	
	public Path(Room room1, Room room2) {
		
		this.room1 = room1;
		this.room2 = room2;
		
		this.y1 = room1.getUpperCorner()[0];
		this.y2 = room2.getUpperCorner()[0];
		this.x1 = room1.getUpperCorner()[1];
		this.x2 = room2.getUpperCorner()[1];
		
		setPath();
	}
	
}
