package com.utils;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;

import com.model.Map;
import com.model.Player;

public class Serializer implements Observer{
	
	Map map;
	Player player;
	
	public static void serializeMap(Map map){
		try{
			String nameSave = map.getPlayer().getName();
			FileOutputStream fout = new FileOutputStream(nameSave+".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			
			oos.writeObject(map);
			oos.flush();
			oos.close();
			TrackLog.writeLog("Game Saved!");
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {	
	}
}
