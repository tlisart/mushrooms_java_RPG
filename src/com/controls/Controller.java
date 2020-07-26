// Import Statements

package com.controls;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Observable;

import com.engine.ExitNewDungeon;
import com.model.AbstractItem;
import com.model.Chest;
import com.model.Exit;
import com.model.Inventory;
import com.model.Loot;
import com.model.Map;
import com.model.Mushroom;
import com.model.Player;
import com.model.Spell;
import com.model.Weapon;
import com.utils.Serializer;
import com.utils.TrackLog;
import com.viewable.InventoryFrame;
import com.viewable.PlayerView;
import com.viewable.SkillsFrame;
import com.viewable.View;
import com.viewable.Window;

/**
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * @version     0.1.4                
 * @category:   Controller section of the program
 * 
 * Controller implements the class "KeyListener", in the default JRE(awt), links an action to an input
 * 
 */

public class Controller extends Observable implements KeyListener{

	// Attributs
	Map map;
	Player player;
	PlayerView playerView;
	Window window;
	View pan;
	
	//Constructor (permet d'importer map)
	public Controller(View pan, Map map, Player player, PlayerView playerView, Window window){
		this.playerView = playerView;
		this.map = map;
		this.player = player;
		this.window = window;
		this.pan = pan;
		
		window.addKeyListener(this);
	}

	//KeyListeners
	public void keyPressed(KeyEvent e) {
		//Position of the player on the grid
		int previousGridPosY = player.getGridPosY();
		int previousGridPosX = player.getGridPosX();
		
		if (playerView.getState() != 0){}					//Avoid interrupting an action 
		
		else{
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.setOrientation(0);
			if (player.check(0) <= 5 && player.check(0) != 0){						//If is not equal to floor or gift
					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(), 1));
			}
			else{
				playerView.setState(2);
				if(player.check(0) == 6){					//Item on the floor
					AbstractItem item = null;
					item = (AbstractItem) map.grid[player.getGridPosY()][player.getGridPosX() + 1];
					try {player.getInventory().addInInventory(item);} 
					catch (IOException e1) {e1.printStackTrace();}
				}
				player.setGridPosX(player.getGridPosX() + 1);	//New player's position
				pan.moveTo(player.getOrientation());
			}
			setChanged();
			notifyObservers(player);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			player.setOrientation(1);
			if (player.check(1) <= 5 && player.check(1) != 0){						//If is not equal to floor or gift

					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(), 1));
			}
			else{
				playerView.setState(2);
				if(player.check(1) == 6){					
					AbstractItem item = null;
					item = (AbstractItem) map.grid[player.getGridPosY()+1][player.getGridPosX()];
					try {player.getInventory().addInInventory(item);} 
					catch (IOException e1) {e1.printStackTrace();}
				}
				player.setGridPosY(player.getGridPosY() + 1); 
				pan.moveTo(player.getOrientation());
			}
			setChanged();
			notifyObservers(player);

		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			player.setOrientation(2);
			if (player.check(2) <= 5 && player.check(2) != 0){						//If is not equal to floor or gift

					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(), 1));
			}
			else{
				playerView.setState(2);
				if(player.check(2) == 6){ 					
					AbstractItem item = null;
					item = (AbstractItem) map.grid[player.getGridPosY()][player.getGridPosX() - 1];
					try {player.getInventory().addInInventory(item);} 
					catch (IOException e1) {e1.printStackTrace();}
				}
				player.setGridPosX(player.getGridPosX() - 1);	
				pan.moveTo(player.getOrientation());
			}
			setChanged();
			notifyObservers(player);

		}	
		else if (e.getKeyCode() == KeyEvent.VK_UP){
			player.setOrientation(3);
			if (player.check(3) <= 5 && player.check(3) != 0){						//If is not equal to floor or gift
					playerView.setAppearance(playerView.getAppearanceMoove(player.getOrientation(), 1));
			}
			else{
				playerView.setState(2);
				if(player.check(3) == 6){
					AbstractItem item = null;
					item = (AbstractItem) map.grid[player.getGridPosY()-1][player.getGridPosX()];
					try {player.getInventory().addInInventory(item);} 
					catch (IOException e1) {e1.printStackTrace();}
				}
				player.setGridPosY(player.getGridPosY() - 1);
				pan.moveTo(player.getOrientation());
			}
			setChanged();
			notifyObservers(player);

		}
		
		// ATTACK
		else if (e.getKeyCode() == KeyEvent.VK_SPACE){
			Weapon weapon = player.getInventory().getEweapon();
			if(weapon != null){
				if(weapon.getID() == 1){
					playerView.setState(4);
				}
				else{
					playerView.setState(1);
				}
			
			try {player.meleeAttack();} 
			catch (InterruptedException e1) {e1.printStackTrace();} catch (IOException e1) {e1.printStackTrace();}
			try {pan.rawAttackAnimation();} //pan nécessaire pour le repaint
			catch (InterruptedException e1) {e1.printStackTrace();}
			} 
			else{
				try {TrackLog.writeLog("I don't have any spell equiped!");} 
				catch (IOException e1) {e1.printStackTrace();}
			}
		}
		
		// USE
		else if (e.getKeyCode() == KeyEvent.VK_E){
			int ori = player.getOrientation();
			int X = player.getGridPosX();
			int Y = player.getGridPosY();
			Chest chest = null;
			Exit exit = null;
			
			//Action for E, if there's a Chest
			if(player.check(ori) == 4){	//Chest
				if(ori == 0){chest = (Chest) map.grid[Y][X+1];}
				else if(ori == 1){chest = (Chest) map.grid[Y+1][X];}
				else if(ori == 2){chest = (Chest) map.grid[Y][X-1];}
				else if(ori == 3){chest = (Chest) map.grid[Y-1][X];}
				
				if(chest.getState() == 0){							//If chest is closed
					chest.setState(1);
					AbstractItem newLoot = Loot.NewLoot();   			
					try {player.getInventory().addInInventory(newLoot);} 
					catch (IOException e1) {e1.printStackTrace();}
				}
			}
			if(player.check(ori) == 5){ //Exit
				if(ori == 0){exit = (Exit) map.grid[Y][X+1];}
				else if(ori == 1){exit = (Exit) map.grid[Y+1][X];}
				else if(ori == 2){exit = (Exit) map.grid[Y][X-1];}
				else if(ori == 3){exit = (Exit) map.grid[Y-1][X];}
				
				try {
					
					ExitNewDungeon newStage= new ExitNewDungeon(window, player);
					window.dispose();

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		// INVENTORY
		else if (e.getKeyCode() == KeyEvent.VK_I){ 		
			//Beginning of the pause
			map.pauser.pause();
			//Inventory
			Inventory inv = player.getInventory();
			InventoryFrame invView = new InventoryFrame(inv, map.pauser);
		}
		
		// SKILLS
		else if (e.getKeyCode() == KeyEvent.VK_O){ 		
			//Beginning of the pause
			map.pauser.pause();
			//Inventory
			SkillsFrame skillView = new SkillsFrame(player, map.pauser);
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_P){
			try {
				player.getInventory().usePotion();
			} catch (IOException e1) {
				e1.printStackTrace();
			};
		}
		
		// SPELLS
		else if (e.getKeyCode() == KeyEvent.VK_S){
				Inventory inv = player.getInventory();
				Spell spell = inv.getEspell();
				
				if(spell != null){
					try {if (player.useSpellOnSelf() == 0);} 
					catch (IOException e2) {e2.printStackTrace();}
						playerView.setState(5);
						Mushroom ghost = null;
						try {ghost = (Mushroom) player.rangedAttack();}
						catch (IOException e1) {e1.printStackTrace();} 
						if(ghost != null){pan.rangedAttackAnimation(ghost);}
						else playerView.setState(0);
					try {if (player.useSpellOnSelf() == 1);} 
					catch (IOException e2) {e2.printStackTrace();}
						System.out.println(player.getHP() + " HP sur " + player.getHpMax());
						player.setHP(player.getHP() + spell.getValue());
						
				}
				else{
					try {TrackLog.writeLog("I don't have any spell equiped!");} 
					catch (IOException e1) {e1.printStackTrace();}
				}
		}
		
		// PAUSE SYSTEM
		else if (e.getKeyCode() == KeyEvent.VK_A){
			map.pauser.pause();

		}
		else if (e.getKeyCode() == KeyEvent.VK_Z){
			map.pauser.resume();
		}
		else if (e.getKeyCode() == KeyEvent.VK_L){
			Serializer.serializeMap(map);
		}
		
		///////////Applique les changements sur les Observers
		int allPos[] = {previousGridPosX, previousGridPosY, player.getGridPosX(), player.getGridPosY()};
		player.notifyObservers(allPos); 
		this.map.notifyObservers(player);
		}
}
	
	@Override
	public void keyReleased(KeyEvent e) {
			
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
