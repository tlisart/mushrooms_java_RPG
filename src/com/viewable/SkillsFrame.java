package com.viewable;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.ia.Pauser;
import com.model.Armor;
import com.model.Buff;
import com.model.Player;
import com.model.Spell;
import com.model.Weapon;


public class SkillsFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7147632941625182548L;
	
	//Attributs
	Player player;
	Pauser pauser;
	
	//ToBeUsed to build the Mouse Listener
	Weapon weapon;
	Armor armor;
	Buff buff;
	Spell spell;
	
	
	//Constructor
	public SkillsFrame(Player player, Pauser pauser){
		
		this.pauser = pauser;
		
		this.player = player;
				
	    this.setTitle("SKILL POINTS AND STAGE NUMBER");
	    this.setSize(400, 300);
	    this.setLocationRelativeTo(null);
	    
	    // Layout to be used
	    this.setLayout(new GridLayout(5, 2 ));
	   

	    //Buttons
	    ///////////FORCE
	    //Label
	    JLabel force = new JLabel("FORCE", SwingConstants.CENTER);
	    this.getContentPane().add(force);
		force.setFont(new Font("Serif", Font.PLAIN, 20));
	    //Buttons
	    JButton force1 = new JButton(Integer.toString(player.getSkills()[0]));
	    this.getContentPane().add(force1);
	    
	    ///////////SPEED
	    //Label
	    JLabel speed = new JLabel("SPEED", SwingConstants.CENTER);
	    this.getContentPane().add(speed);
		speed.setFont(new Font("Serif", Font.PLAIN, 20));
	    //Buttons
	    JButton speed1 = new JButton(Integer.toString(player.getSkills()[1]));
	    this.getContentPane().add(speed1);

	    ///////////DESTRUCTION
	    //Label
	    JLabel destruction = new JLabel("DESTRUCTION", SwingConstants.CENTER);
	    this.getContentPane().add(destruction);
	    destruction.setFont(new Font("Serif", Font.PLAIN, 20));
	    //Buttons
	    JButton destruction1 = new JButton(Integer.toString(player.getSkills()[2]));
	    this.getContentPane().add(destruction1);

	    ///////////HEALING
	    //Label
	    JLabel healing = new JLabel("HEALING", SwingConstants.CENTER);
	    this.getContentPane().add(healing);
	    healing.setFont(new Font("Serif", Font.PLAIN, 20));
	    //Buttons
	    JButton healing1 = new JButton(Integer.toString(player.getSkills()[3]));
	    this.getContentPane().add(healing1);
	    
	    ///////////STAGE
	    //Label
	    JLabel stage = new JLabel("STAGE", SwingConstants.CENTER);
	    this.getContentPane().add(stage);
	    stage.setFont(new Font("Serif", Font.PLAIN, 20));
	    //Buttons
	    JButton stage1 = new JButton(Integer.toString(player.getStage()));
	    this.getContentPane().add(stage1);
	    

	    this.setVisible(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
        	 public void windowClosing(java.awt.event.WindowEvent e) {
        		pauser.resume();
        		dispose();             }
         });
	}
}
