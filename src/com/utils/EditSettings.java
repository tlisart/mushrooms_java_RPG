package com.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class EditSettings {
	
    /**
     * Write into the settings.txt file, the first line is a int and is the height value, the second line is also an int and is the width value, finally the last line is a String giving the graphic skin to be displayed
     * @param height
     * @param width
     * @param skin
     * @throws IOException 
     */
    
	private static final String SETTINGS_PATH = "Settings.txt";	
	
    public static void upDateSettings(int size, int skin) throws IOException{
    	    	
    	String heightString = Integer.toString(size);
    	String newSkin = Integer.toString(skin);
    	
    	
        FileWriter fw = new FileWriter(SETTINGS_PATH, true);     // true ensure the file in written, not the copy in the buffer
        BufferedWriter bw = new BufferedWriter(fw);
         
        //Removes the previous Settings
        PrintWriter writer = new PrintWriter(SETTINGS_PATH);
        writer.print("");
        writer.close();
        
        // Writes the new Settings
        
        bw.write(heightString);     
        bw.newLine();
        bw.write(newSkin);
        bw.close();
    }
    
    /**
     * The allSettings list (ArrayList) is structured as:
     * index == 0 (String Height), index == 1 (String Width), index == 2 (String GameStyle)
     * @return allSettings (ArrayList<String>)
     * @throws IOException 
     */
    
    public static ArrayList<Integer> getSettings() throws IOException{
    	ArrayList<String> allSettings = new ArrayList<String>();
    	ArrayList<Integer> allSettingsInt = new ArrayList<Integer>();
    	
      	String fileName = "Settings.txt";
        String line = null;
                    
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        	
        	 while((line = br.readLine()) != null) {
        		 allSettings.add(line);
             }   
        	 
         	 br.close();
         	 
        int size = Integer.parseInt(allSettings.get(0));
        int skin = Integer.parseInt(allSettings.get(1));
        
        allSettingsInt.add(size);
        allSettingsInt.add(skin);
        
    	return allSettingsInt;
    }
}
