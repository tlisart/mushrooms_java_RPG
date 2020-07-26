package com.engine;
import java.io.IOException;
import java.util.ArrayList;

import com.controls.Controller;
import com.model.Map;
import com.model.Player;
import com.utils.EditSettings;
import com.utils.TrackLog;
import com.viewable.ChestView;
import com.viewable.ExitView;
import com.viewable.FloorView;
import com.viewable.MushroomView;
import com.viewable.PlayerConsol;
import com.viewable.PlayerView;
import com.viewable.View;
import com.viewable.WallView;
import com.viewable.Window;

/**
 * This class is a copy of the Dungeon class in the default directory, except it differs in the arguments given to the constructor, this class builds the dungeon with an already build player
 */
public class ExitNewDungeon {
	
	// Model
	/////////Initialization -- Whole map
	Algo algo;
	Map map;
		
	//View
	/////////Initialization -- model and View elements
	
	ExitView exitView;
	PlayerView playerView;
	MushroomView ghostView;
	WallView wallView;
	FloorView floorView;
	ChestView chestView;
	Player player;
	View panGame;
	PlayerConsol panUI;
	Window window;
	
	
	public Window getWindow(View panGame, PlayerConsol panUI ){
		
		Window window = new Window(panGame, panUI);
		window.setLocationRelativeTo(null);
		return window;
	}
	
	
	//Constructor
	public ExitNewDungeon(Window window, Player player) throws IOException{

		ArrayList<Integer> allSettings = EditSettings.getSettings();
		int size = allSettings.get(0);
		int dungeonStyle = allSettings.get(1);
		player.setStage(player.getStage() +1);
				
		algo = new Algo(size, player.getStage() + 1);   // First stage
		map = new Map(algo, player);
		Player newPlayer = map.getPlayer();
		TrackLog.writeLog("We must go deeper...");
		this.player = newPlayer;

		this.window = window;
				
		playerView = new PlayerView(this.player); 
		panUI = new PlayerConsol();

		View panGame = new View(playerView, map, dungeonStyle);
		
		//AddObservers
		playerView.addObserver(panGame);
		playerView.addObserver(panUI);
		
		this.player.addObserver(panGame);
		this.player.addObserver(map);
		this.player.addObserver(panUI);
		this.player.getInventory().addObserver(panUI);

		for (int i = 1; i < map.getSize(); i++){
			for (int j = 1; j < map.getSize(); j++){
				if(map.grid[i][j].whoIsThere(map) == 2){
					map.grid[i][j].addObserver(panGame);
					map.grid[i][j].addObserver(map);
				}
				if(map.grid[i][j].whoIsThere(map) == 4)
					map.grid[i][j].addObserver(panGame);
			}
		}
		
		window = getWindow(panGame, panUI);
		//Controller
		@SuppressWarnings("unused")
		Controller command = new Controller(panGame, map, this.player, playerView, window);
	}
}
