import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import com.controls.Controller;
import com.engine.Algo;
import com.ia.Pauser;
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
 *  This class is a whole stage, instanciate what is to be displayed (Panels for UI, panels for the game, sets the Algorithm to generate the map, and then plugs the map into 
 *  the dynamic constructor
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 */
public class Dungeon extends Observable {

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
	Pauser pauser;
	
	//Controller
	Controller commande;
	
	
	/**
	 * Gives to the window the needed panels
	 * @param panGame
	 * @param panUI
	 * @return window
	 */
	public Window getWindow(View panGame, PlayerConsol panUI ){
		
		Window window = new Window(panGame, panUI);
		window.setLocationRelativeTo(null);
		return window;
	}
	
	/**
	 * Constructor 1, starts the game
	 * Called in NewGame
	 * @param playerName
	 * @throws IOException
	 */
	
	public Dungeon(String playerName) throws IOException{
			
		ArrayList<Integer> allSettings = EditSettings.getSettings();
		int size = allSettings.get(0);
		int dungeonStyle = allSettings.get(1);
			
		//Init the building algorithm
		algo = new Algo(size, 1);   // First stage
		map = new Map(algo);
		player = map.getPlayer(); 
		player.setName(playerName);
			
		//Creating a new save reference and a trackLog file
		TrackLog log = new TrackLog();       // Removes the previous tracklog
		TrackLog newReference = new TrackLog(player.getName());  
		
		//Creating the view
		playerView = new PlayerView(player); 
		panUI = new PlayerConsol();
		View panGame = new View(playerView, map, dungeonStyle);
			
		//Add respective observers
		playerView.addObserver(panGame);
		playerView.addObserver(panUI);
			
		for (int i = 1; i < map.getSize(); i++){
			for (int j = 1; j < map.getSize(); j++){
				if(map.grid[i][j].whoIsThere(map) == 2){
					map.grid[i][j].addObserver(panGame);
					map.grid[i][j].addObserver(map);					
				}
					
				if(map.grid[i][j].whoIsThere(map) == 3){
					Player player = (Player) map.grid[i][j];
					player.addObserver(panGame);
					player.addObserver(map);
					player.addObserver(panUI);
					player.getInventory().addObserver(panUI);
				}
					
				if(map.grid[i][j].whoIsThere(map) == 4)
					map.grid[i][j].addObserver(panGame);
				}
		}
		//Init of the Window and oOntroller
		window = getWindow(panGame, panUI);
		Controller command = new Controller(panGame, map, player, playerView, window);		
	}
	
	/**
	 * Constructor 2 called when we deserialize the map
	 * Called in Continue
	 * @param mapSaved
	 * @throws IOException
	 */
	public Dungeon(Map mapSaved) throws IOException{

		ArrayList<Integer> allSettings = EditSettings.getSettings();
		int dungeonStyle = allSettings.get(1);
		
		// Construction of the Player and map from Deserialized objects
		map = new Map(mapSaved.getGrid());
		this.player  = map.getPlayer();

		//Creating a new save reference and a trackLog file
		TrackLog log = new TrackLog();       // Removes the previous tracklog
		TrackLog newReference = new TrackLog(player.getName());  
		
		//Creating the view
		playerView = new PlayerView(player); 
		panUI = new PlayerConsol();
		View panGame = new View(playerView, map, dungeonStyle);
		
		//Add respective observers
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
				if(map.grid[i][j].whoIsThere(map) == 4){
					map.grid[i][j].addObserver(panGame);
				}
			}
		}
				
		//Init of the window and controller
		window = getWindow(panGame, panUI);
		Controller command = new Controller(panGame, map, this.player, playerView, window);
	}
}