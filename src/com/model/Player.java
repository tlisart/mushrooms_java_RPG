//Package statements
package com.model;
import java.io.IOException;

//Import statements
import com.utils.TrackLog;


/**
 * The Player class inherits from Character class; which inherits from Cell. 
 * It has a Name (String), a Stage (int), a Skill array (int[4]), XP points (int) and an inventory (inventory)
 * 
 * It sets the default values for a new game, it is obviously the dynamic cell that the user plays.
 * 
 * @author      Lamas Neil<nlamas@ulb.ac.be>
 * @author      Lisart Théo<theo@lisart.be>
 * 
 */

public class Player extends Character {
	
	private static final long serialVersionUID = 1L;

	// Player's Attributes
	private String name;
	protected int level;            // Level == 1 for a new game
	private int stage;
	private int XP;                                    // Experience points		
	private Inventory inventory;                       // Inventory Instance
	private int capacity = 2;                          // Capacity of the inventory depending on the STRENGTH level (Skills[0] == 0 by default for a new game)
	protected int[] skills = new int[4];               // Skills table {int FORCE(W), int SPEED(W), int DESTRUCTION(M), int HEALING(M)} (W = Warior, M = Mage))


	///Getters
	public int getXP() {return this.XP;}
	public Inventory getInventory() {return inventory;}
	public String getName(){return name;}
	public int getStage(){return stage;}        // Defines the difficulty level of the new map
	public int getLevel(){return level;}
	public int getArmorValue(){return inventory.Earmor.getProtection();}
	public int[] getSkills() {return skills;}
	public int getCapacity() {return capacity;}
	

	
	///Setters ----------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Sets the name of the Player.
	 * @param name
	 */
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Initiate the default values for the attributes
	 */
	private void setStart(){
		this.setState(0);
		this.orientation = 1;       
		this.XP = 0;                   		
		this.capacity = 2;
		this.level = 1;
		setSkills(1, 1, 1, 1);
	}
	
	public void setSkills(int i, int j, int k, int l) {
		skills[0] = i;
		skills[1] = j;
		skills[2] = k;
		skills[3] = l;
	}

	/**
	 * 
	 * @param xpBrought quantity of XP's
	 * @throws IOException
	 */
	public void setXP(int xpBrought) throws IOException {                                       //Can be called outside of Player, add XP to the player
		newLevel(xpBrought);
		TrackLog.writeLog("You earned "+ xpBrought + "XP");
	}      
	
	
	/**
	  * Set a new HPMax level depending on the STRENGTH skill and the level of the player
	  * The default value is 50 for the first level
	  * The HPMax increases linearly with the PlayerLevel and the STRENGTH level, STRENGTH being the first skill in the Skills Array
	  */
		
	protected void setHPMax() {
		this.hpMax =  100 + 10*(this.level - 1) + 30*(skills[0] - 1);     // HPMAx formula
	}
	
	/**
	  * Recursive algorithm which determines how many XP' the player need to level up, and set the HP of the player to the new max
	  * The formula {@code neededToNextLevel = 50 + 10*(level-1);}     Gives the amount of XP needed, increases with the level
	  * @throws IOException needed in case of missing files @see {@link TrackLog#readLog()}
	  *
	  */
	
	public void newLevel(int xpBrought) throws IOException{

		int neededToNextLevel = 50 + 30*(this.level);            // XP required to level-up, increases with the level of the player
		int newXP = this.XP + xpBrought;
		
		if(newXP >= neededToNextLevel) {
			this.level ++;                                       // increments the level counter
			TrackLog.writeLog(("I have reached level :  " + this.level));			
			int xpLeft = (XP + xpBrought) - neededToNextLevel  ;  // Keeps the remaining XP
			this.XP = 0;
			
			setHPMax();                                          //Change the HPMax
			healAll();                                           //The player is fully healed
			newLevel(xpLeft);                                    //Reloop if a lot of XP has been earned	
			setChanged();
			notifyObservers();
		}

		else{
			this.XP = (XP + xpBrought);                             //Simple addition of XP's 
		}
	}
	
	/**
	 * Gives a certain amount of health points
	 * @param healthpoints
	 */
	public void heal(int healthpoints){                 //Partially heals the character
		if(this.hp + healthpoints > this.hpMax){
			healAll();
		}
		else{
			this.hp += healthpoints;
		}
	}
	
	/**
	 * Gives full health to the player
	 */
	public void healAll() {                           //Completely heals the character
		this.hp = this.hpMax;
	}

//upDates ---------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Update the Capactity value into the inventory, relative to the STRENGTH level
	 */
	private void upDateCapacity(){
		this.capacity = 2 + (skills[0]-1);        // Arbitrary formula
		this.inventory.setCapacity(this.capacity);
	}
	
	/**
	 * Calls all upDateSkills methods
	 * @throws IOException needed in case of missing files @see {@link TrackLog#readLog()}
	 */
	private void upDateAllSkills() throws IOException{
		upDateStrengthSkill();
		upDateSpeedSkill();
		inventory.upDateSpells(this.skills[2],this.skills[3]);
	}

	/**
	 * Resets HPmax and upDate the capacity of the inventory (necessary for the bonus to take effect right away when the Skill point is spend
	 */
	private void upDateStrengthSkill(){        // Update the capacity of the inventory and other STRENGTH related skill
		setHPMax();                            // Update HPMax
		upDateCapacity();                      // Update the max Inventory capacity
		this.rawAttackPoints = 20 + ((skills[0]*10) - 10);   // Attack power without any equipment, depends on the STRENGTH

	}
	
	/**
	 * Apply the modification of the speed skill, the player goes faster.
	 */
	private void upDateSpeedSkill(){          // Update the Speed skills
		this.movingTime = 60 - (skills[1]-1)*10;
	}
	
	
	public void increaseSkill(int pos, int amount){
		this.skills[pos] += amount;
		try {this.upDateAllSkills();} 
		catch (IOException e) {e.printStackTrace();}
		setChanged();
	}
	
	
	
//Attack Mechanism and Player Death mechanism---------------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * It deals damages to the monster if there's one following the conditions.
	 * The monster must be one cell around the player, and following the right orientation
	 * 
	 * @see com.model.Monster#sufferDamage(int, Inventory, Player)
	 * 
	 * @see {@link com.model.Character#check(int)
	 * @see {@link com.model.Character#getOrientation()
	 * @throws IOException, InterruptedException
	 */
	
	public void meleeAttack() throws InterruptedException, IOException{ 
		Mushroom mushroom = null;
		
		int weaponValue = inventory.Eweapon.getValue();
		int realDamages =  this.rawAttackPoints + weaponValue;          // Calculate the damages according to what's equipped (by default, it is a rusty sword)
		
		if (this.getOrientation() == 0 && this.check(0) == 2){
				mushroom = (Mushroom) map.grid[this.getGridPosY()][this.getGridPosX()+1];  // Cast on Monster
		}
		
		if(this.getOrientation() == 1 && this.check(1) == 2){
				mushroom = (Mushroom)map.grid[this.getGridPosY()+1][this.getGridPosX()];
				}

		if(this.getOrientation() == 2 && this.check(2) == 2){
				mushroom = (Mushroom) map.grid[this.getGridPosY()][this.getGridPosX()-1];
				}

		if(this.getOrientation() == 3 && this.check(3) == 2){
				mushroom = (Mushroom) map.grid[this.getGridPosY()-1][this.getGridPosX()];
				}
		if (mushroom != null) {mushroom.sufferDamage(realDamages, inventory, this);}
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * DESTRUCTION spells method, ranged attack. Deals damages to the monster within range.
	 * The range changes relatively to the DESTRUCTION skill level (skill[2])
	 * @see com.model.Monster#sufferDamage(int, Inventory, Player)
	 * @see {@link com.model.Character#check(int)
	 * @see {@link com.model.Character#getOrientation()
	 * 
	 * @return monster (Cell class object)
	 * @throws IOException needed in case of missing files @see {@link TrackLog#readLog()}
	 */
	
	public Cell rangedAttack() throws IOException{
		Mushroom ghost = null;
		Spell spell = inventory.Espell;
		
		int range = skills[2] +5;									// The highest the DESTRUCTION skill, the farthest the player can throw a spell
		int realDamages =  spell.getValue() + skills[2];                     // The damages depends also on the DESTRUCTION skill  
		
		while (this.getGridPosX() + range >= map.getSize() ||
				this.getGridPosX() - range < 0 ||
				this.getGridPosY() + range >= map.getSize() ||
				this.getGridPosY() - range < 0
				){
			range --;
		}
		for(int i = 1; i <= range; i++){
			if (this.getOrientation() == 0 && map.grid[this.getGridPosY()][this.getGridPosX()+i].whoIsThere(map) == 1 || //Doesn't work if there is a wall between
					this.getOrientation() == 1 && map.grid[this.getGridPosY()+i][this.getGridPosX()].whoIsThere(map) == 1 ||
					this.getOrientation() == 2 && map.grid[this.getGridPosY()][this.getGridPosX()-i].whoIsThere(map) == 1 ||
					this.getOrientation() == 3 && map.grid[this.getGridPosY()-i][this.getGridPosX()].whoIsThere(map) == 1){
				break;
			}
			if (this.getOrientation() == 0 && map.grid[this.getGridPosY()][this.getGridPosX()+i].whoIsThere(map) == 2){
				ghost = (Mushroom) map.grid[this.getGridPosY()][this.getGridPosX()+i];  // Cast on Ghost
				break;
			}
		
			if(this.getOrientation() == 1 && map.grid[this.getGridPosY()+i][this.getGridPosX()].whoIsThere(map) == 2){
				ghost = (Mushroom)map.grid[this.getGridPosY()+i][this.getGridPosX()];
				break;
			}

			if(this.getOrientation() == 2 && map.grid[this.getGridPosY()][this.getGridPosX()-i].whoIsThere(map) == 2){
				ghost = (Mushroom) map.grid[this.getGridPosY()][this.getGridPosX()-i];
				break;
			}

			if(this.getOrientation() == 3 && map.grid[this.getGridPosY()-i][this.getGridPosX()].whoIsThere(map) == 2){
				ghost = (Mushroom) map.grid[this.getGridPosY()-i][this.getGridPosX()];
				break;
			}
		}
		if (ghost != null) {
			ghost.sufferDamage(realDamages, inventory, this);
		}
		return ghost;
	}
	
	// Reduce the player's HP
	public void sufferDamage(int RealDamages) throws IOException {          
		if(this.hp - RealDamages > 0){
			setHP(this.hp - RealDamages);
		}
		else{
			setHP(0);
			this.characterDeath(this.gridPosY, this.gridPosX);                       //Launch death sequence
		}
	}
	
	
	/**
	 * Overrides the characterDeath of the Character Class, obviously the death sequence of the player means GameOver
	 * though the exact mechanics has still to be decided, as always, we should be threading
	 */
	
	public void characterDeath(int line, int column) throws IOException{

		if(isThereAFeather()[0] == 1){
			healAll();
			inventory.Buffs.remove(isThereAFeather()[1]);
			TrackLog.writeLog("The phoenix feather resurrected you");
		}
		else{
			setState(3);
			TrackLog.writeLog("YOU DIED");
			setChanged();
			notifyObservers();
		}
	}	
	
	
	//Miscellaneous (Special effects) -------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * Checks the Buffs section in inventory if there's a "Phoenix Feather", Heals all HP when the characterDeath() sequence is triggered
	 * @see Player#characterDeath(int, int)
	 * @return int[], where {a, b} is the value returned, if a == 1, there's a "Phoenix Feather" in the position b into the Buffs section of the inventory
	 */
	
	private int[] isThereAFeather(){
		int[] info = {0, 0};
		for(int i = 0; i < inventory.Buffs.size(); i++){
			if(inventory.Buffs.get(i).getName() == "Phoenix feather" ){
				info[0] = 1;
				info[1] = i;
			}			
		}
		return info;
	}

	/**
	 * Use spell on self method, takes the spell equipped, and returns a value depending on the effect
	 * Heals right away if it is a Heal spell
	 * @return
	 * @throws IOException needed in case of missing files @see {@link TrackLog#readLog()}
	 */
	
	public int useSpellOnSelf() throws IOException{
		Spell spell = inventory.getEspell();
		if(spell.getEffect() == "ATTACK"){
			return 0;
		}
		if(spell.getEffect() == "HEAL"){
			setHP(spell.getValue());
			return 1;
		}
		else return 2; //Bugfixing return, if it returns 2 there's a problem
	}
	
	
	
// Copy tools, needed after Deserialization ------------------------------------------------------------------------------------------------------------

	
	public void setStage(int Stage) throws IOException{
		this.stage = Stage;
	}
	private void setInventory(Inventory inv){
		this.inventory = inv;
	}
	private void setCopyStage(int Stage){
		this.stage = Stage;
	}
	private void setCopyMovingTime(int movingTime){
		this.movingTime = movingTime;
	}
	private void setCopySkills(int[] Skills){
		this.skills = Skills;
	}
	private void setCopyHP(int HP){
		this.hp = HP;
	}
	private void setCopyXP(int XP){
		this.XP = XP;
	}
	private void setCopyLevel(int level){
		this.level = level;
	}
	
	/**
	 * Calls 8 Update methods for creating a new instance of Player from map. Needed when passing to the next stage, it copies all attributes from the old player to the new instance
	 * 
	 * @see Map#Map(Cell[][], Player)
	 * @see Map#Map(com.engine.Algo, Player)
	 * 
	 * @param inv
	 * @param Skills
	 * @param HP
	 * @param XP
	 * @param level
	 * @param Stage
	 * @param movingTime
	 * @param movingTime
	 */
	
	public void setCopyAll(String name, Inventory inv, int[] Skills, int HP, int XP, int level, int Stage, int movingTime){
		setInventory(inv);
		setCopySkills(Skills);
		setCopyHP(HP);
		setCopyXP(XP);
		setCopyLevel(level);
		setCopyStage(Stage);
		setCopyMovingTime(movingTime);
		setName(name);
	}
	
	
	// Constructor ---------------------------------------------------------------------------------------------------------------
	
	/**
	 * Instantiate the player (called in instance of Map), initiate all variables.
	 * @param gridPosY 
	 * @param gridPosX
	 * @param map
	 * @throws IOException
	 */
	
	public Player(int gridPosY, int gridPosX, Map map) throws IOException {
		this.map = map;                   
		setStart();
		
		this.inventory = new Inventory(this);
		upDateAllSkills();
		
		this.gridPosX = gridPosX;
		this.gridPosY = gridPosY;
		
		this.hp = this.hpMax;               //Starting with full health
	}
}