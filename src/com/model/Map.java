//Package statements
package com.model;

import java.io.IOException;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import com.engine.Algo;
import com.ia.GoToThePlayer;
import com.ia.MonsterAttack;
import com.ia.Pauser;

/**
 * The Map class initialize the main game grid, this one is being randomly build with help of the gameEngine, into com.engine
 * 
 */

public class Map extends Observable implements Observer, Serializable{
	
		
		private static final long serialVersionUID = 1L;
	
		
	//Map Values
		
	public int Stage = 1;  // Default Stage, the player has the same value, which is updated every time a Stage is completed 
	private int size;
	public Cell[][] grid;
	public static final int unit = 60;  //(Each case is a square of 80px/80px)
	Player player;
	
	public transient Pauser pauser = new Pauser();

	
	
	//Getters&&Setters
	
	public Player getPlayer() {return player;}
	public int getSize(){return this.size;}
	public Cell[][] getGrid() {return grid;}
	public void setPlayer(Player player){
	this.player = player;
	}
	

	//Constructor
	public Map(Algo newGrid) throws IOException{	
		
		this.size = newGrid.getSize();
		this.grid = new Cell[size][size];
		
		for (int i = 0; i < newGrid.getSize(); i++){
			for (int j = 0; j < newGrid.getSize(); j++){
				if (newGrid.Grid[i][j] == 0){
					grid[i][j] = new Floor();}
				if (newGrid.Grid[i][j] == 1){
					grid[i][j] = new Wall();}
				if (newGrid.Grid[i][j] == 3){
					grid[i][j] = new Player(i,j, this);
					player = (Player) grid[i][j];
					player.setStage(1); 
				}
				if (newGrid.Grid[i][j] == 2){
					grid[i][j] = new Mushroom(i, j, this);    
					///////////Threads for each Ghost
					Thread ghostAttack = new Thread(new MonsterAttack((Mushroom) grid[i][j], pauser));
					ghostAttack.start();
					Thread ghostSearch = new Thread(new GoToThePlayer((Mushroom) grid[i][j], pauser)) ;
					ghostSearch.start();
				
				}
				if (newGrid.Grid[i][j] == 4){
					grid[i][j] = new Chest();
				}
				if (newGrid.Grid[i][j] == 5){
					grid[i][j] = new Exit();
				}
			}
		}
	}
	
	// Called for loading
	public Map(Cell[][] grid) throws IOException{	
		
		this.size = grid.length;
		this.grid = new Cell[size][size];
		
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				if (grid[i][j] instanceof Floor){
					this.grid[i][j] = new Floor();
				}
				if (grid[i][j] instanceof Wall){
					this.grid[i][j] = new Wall();}
				
				if (grid[i][j] instanceof Player){
					this.grid[i][j] = new Player(i,j, this);
					this.player = (Player) grid[i][j];
					this.player.setMap(this);
				}
				
				if (grid[i][j] instanceof Mushroom){
					this.grid[i][j] = new Mushroom(i, j, this);    
					///////////Threads for each Ghost
					Thread ghostAttack = new Thread(new MonsterAttack((Mushroom) this.grid[i][j], pauser));
					ghostAttack.start();
					Thread ghostSearch = new Thread(new GoToThePlayer((Mushroom) this.grid[i][j], pauser)) ;
					ghostSearch.start();
				
				}
				if (grid[i][j] instanceof Chest){
					this.grid[i][j] = new Chest();
				}
				if (grid[i][j] instanceof Exit){
					this.grid[i][j] = new Exit();
				}
			}
		}
	}
	//Called for Exit
	public Map(Algo newGrid, Player player) throws IOException{		
		
		this.size = newGrid.getSize();
		this.grid = new Cell[size][size];
		
		Player playerSaved = player;
		
		for (int i = 0; i < newGrid.getSize(); i++){
			for (int j = 0; j < newGrid.getSize(); j++){
				if (newGrid.Grid[i][j] == 0){
					grid[i][j] = new Floor();}
				if (newGrid.Grid[i][j] == 1){
					grid[i][j] = new Wall();}
				if (newGrid.Grid[i][j] == 3){
					grid[i][j] = new Player(i,j, this);
					this.player = (Player) grid[i][j];
					this.player.setCopyAll(playerSaved.getName(), playerSaved.getInventory(), playerSaved.getSkills(), playerSaved.getHP(), playerSaved.getXP(), playerSaved.getLevel(), playerSaved.getStage() , playerSaved.getMovingTime());
					this.player.setMap(this);
				}
				if (newGrid.Grid[i][j] == 2){
					grid[i][j] = new Mushroom(i, j, this);    
					///////////Threads for each ghosts
					Thread ghostAttack = new Thread(new MonsterAttack((Mushroom) grid[i][j], pauser));
					ghostAttack.start();
					Thread ghostSearch = new Thread(new GoToThePlayer((Mushroom) grid[i][j], pauser)) ;
					ghostSearch.start();
				}
				
				if (newGrid.Grid[i][j] == 4){
					grid[i][j] = new Chest();
				}
				if (newGrid.Grid[i][j] == 5){
					grid[i][j] = new Exit();
				}
			}
		}
	}

	public void update(Observable character, Object arg1) {
		if (arg1 == null){
		setChanged();
		notifyObservers(this);
	}	
		else{
			int[]allPos = (int[]) arg1;
			grid[allPos[3]][allPos[2]] = (Cell) character;		
			grid[allPos[1]][allPos[0]] = new Floor();
			setChanged();
			notifyObservers(this);
		}
	}
	
}
