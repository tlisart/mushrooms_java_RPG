//Package Statements
package com.engine;
//Import Statements
import java.util.Random;

/**
 *  Interface in which is written some needed static methods to do basic calculations
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Theo<theo@lisart.be>
 * @version     11.2                
 * @since       17-04-2016          
 * 
 */
 
public interface Util {
	
	/**
	 * Pseudo-random integer generator, takes to boundaries and returns an integer in the boundaries (boundaries taken)
	 * @see {@link java.util.Random}
	 * @param min
	 * @param max
	 * @return RandomNumber
	 */
	
	public static int randomNumberRanged(int min, int max){
		Random generator = new Random();
		int RandomNumber = generator.nextInt((max + 1) - min) + min;    // Cf. Random documentation, Random doesn't do very well with ints
		
		return RandomNumber;
	}
	
	
	/**
	 * Calculates the euclidian distance between two points on the grid
	 * @see java.lang.Math#hypot(double, double)
	 * @param Ypos
	 * @param Xpos
	 * @return double hypot
	 */
	
	public static double calculateDistance(double Ypos,double Xpos){
		return Math.hypot(Ypos, Xpos);
}
}
