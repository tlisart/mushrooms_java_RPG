package com.model;

import java.io.IOException;

import com.engine.Util;
import com.utils.TrackLog;

public interface Loot {
		
	public static AbstractItem NewLoot(){
		int type = Util.randomNumberRanged(0, 2); // 0 = weapon, 1 = Spell; 2 = Armor; 3 = Buff

		if (type == 0){
			return new Weapon();
		}
		if (type == 1){
			return new Armor();
		}
		if (type == 2){
			return new Buff();
		}
		else {
			try {TrackLog.writeLog("There is nothing there ...");}
			catch (IOException e) {e.printStackTrace();}
			return null;
		}
	}
}
