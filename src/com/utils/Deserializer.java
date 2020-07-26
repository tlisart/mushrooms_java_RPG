package com.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import com.model.Map;

public class Deserializer{
	
	private static final String LISTNAME_PATH = "listName.txt";	

    public static ArrayList<String> getAllPlayers() throws IOException{
    	ArrayList<String> allNames = new ArrayList<String>();
    	
    	//First, a list of all saves is made
    	
        String line = null;
    	FileReader fr = new FileReader(LISTNAME_PATH);
    	BufferedReader br = new BufferedReader(fr);
    	
    	 while((line = br.readLine()) != null) {
    		 allNames.add(line);
         }   
     	 br.close();
     	 
    	return allNames;
    }
    
    public static Map deserializeMap(String Name) throws IOException{
    	Map map = null;
		
		try{
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Name+".ser"));
			map = (Map) ois.readObject();
			ois.close();
		}
			
		catch(Exception e){
			System.out.println("La sauvegarde n'existe pas dans le workspace");
		}
    	return map;
    }
}
