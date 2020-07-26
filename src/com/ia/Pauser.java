package com.ia;

public class Pauser{

	private boolean isPaused;

	public Pauser(){
		isPaused = false;	
	}

	public synchronized void pause(){
		isPaused=true;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public synchronized void resume(){
		isPaused=false;
		notifyAll();
	}

	public synchronized void look(){
		while(isPaused){
			try {wait();} 
			catch (InterruptedException e) {e.printStackTrace();}
		}
	}
}