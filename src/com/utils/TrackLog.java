package com.utils;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

public class TrackLog extends Observable{

// General Log Game notification) ----------------------------------------------------------------
	
    /**
     * @param line
     * @throws IOException
     */
	
	private static final String TRACKLOG_PATH = "TrackLog.txt";	
	private static final String LISTNAME_PATH = "listName.txt";	


    public static void writeLog(String line) throws IOException{
    	
        FileWriter fw = new FileWriter(TRACKLOG_PATH, true);     // true ensure the file in written, not the copy in the buffer
        BufferedWriter bw = new BufferedWriter(fw);
         
        bw.write(line);     
        bw.newLine();
        bw.close();
    }
    
    /**
     * 
     * @return
     * @throws IOException
     */
    
    public static ArrayList<String> readLog() throws IOException{
        String line = null;
        ArrayList<String> allLines = new ArrayList<String>();
        
    	FileReader fr = new FileReader(TRACKLOG_PATH);
    	BufferedReader br = new BufferedReader(fr);
    	
    	 while((line = br.readLine()) != null) {
    		 allLines.add(line);
         }   
     	
     	 br.close();
    	 return allLines;
    }
    

 // Various constructors ---------------------------------------------------------------------------------------------------------------------------
    
    //Constructors
	/**
	 * This is the main trackLog, it records in a text file what actions we'd want to display in a text form
	 * @param args
	 * @throws IOException
	 */
	
    
    public TrackLog() throws IOException {
	    	File log = new File(TRACKLOG_PATH);
            log.createNewFile();
            eraseLog();
    }
    
    /**
     * The second trackLog records the names of the players (all of them) to make easier to manage saveData
     * @param listName
     * @throws IOException
     */
    
    public TrackLog(String NewName) throws IOException {
    	String newName = NewName;

    	ArrayList<String> allNames = getAllNames();
    	boolean alreadyTaken = false;
    
	    for(int i =0; i < allNames.size(); i ++){
	    	if (allNames.get(i) == NewName){
	    		alreadyTaken = true;
	    	}
	    }
	   
	    if(alreadyTaken == false){
	        FileWriter fw = new FileWriter(LISTNAME_PATH, true);     // true ensure the file in written, not the copy in the buffer
	        BufferedWriter bw = new BufferedWriter(fw);
	            
	        bw.write(newName);     
	        bw.newLine();
	        bw.close();
	    }
	    
	    if(alreadyTaken == true){
	    	System.out.println("Sorry, name already taken!");
	    }
    }


    public static ArrayList<String> getAllNames() throws IOException{
    	ArrayList<String> allNames = new ArrayList<String>();
    	
    	//First, a list of all saves is made
    	String fileName = "listName.txt";
        String line = null;
                
    	FileReader fr = new FileReader(fileName);
    	BufferedReader br = new BufferedReader(fr);
    	
    	 while((line = br.readLine()) != null) {
    		 allNames.add(line);
         }   
     	 br.close();
     	 return allNames;
    }


    public void eraseLog() throws IOException{
    	String fileName = "TrackLog.txt";
        PrintWriter eraser = new PrintWriter(fileName);
        eraser.write("");
        eraser.close();
    }
    
    public static boolean alreadyTaken(String NewName) throws IOException{
    	ArrayList<String> allNames = getAllNames();
    	
    	boolean bool = false;
    	if(allNames.contains(NewName)){
    		bool =true;
    	}
    	return bool;
	}
    
 }

