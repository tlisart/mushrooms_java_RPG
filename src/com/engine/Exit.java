package com.engine;

/**
 * This class has ont attributes, ExitPos (int[]), it finds the farthest point from the already placed player
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Theo<theo@lisart.be>
 * @version     11.2                
 * 
 */
public class Exit{


	private int[] ExitPos = {0, 0};
	
	
	//Getters
	public int[] getExitPos(){return ExitPos;}
	
	/**
	 * Constructor, checks for all positions possible (amongst the (0) of the Grid the farthest away from the Player (3)
	 * For loop, compare each distances with calculteDistance(double, double)
	 * @see {@link com.engine.Util#calculateDistance(double, double)
	 * 
	 * @param Grid  The carved Grid up to this point, (Rooms, Paths applied, Mobs and Chests applied)
	 * @param PlayerPos
	 * @param size
	 */
	
	public Exit(int[][] Grid, int[] PlayerPos, int size){
		double maxDistance = 0;
		double distancePlayer = Util.calculateDistance(PlayerPos[0], PlayerPos[1]);
		
		for(int i = 0; i <= size -1; i++){
			for(int j = 0; j <= size -1; j++){
				if(Grid[i][j] == 0){
					double distanceCheck = Util.calculateDistance((double) i,(double) j);   
					double distance = Math.abs(distancePlayer - distanceCheck);				
					if(distance > maxDistance){
						maxDistance = distance;
						this.ExitPos[0] = i;
						this.ExitPos[1] = j;
					}
				}
			}
		}
	}
}
