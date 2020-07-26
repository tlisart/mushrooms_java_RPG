
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.utils.TrackLog;

public class NewGame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JFrame mainMenu;
	
	public NewGame (JFrame mainMenu){
		this.mainMenu =  mainMenu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel pan = new JPanel();
		JLabel labelBox = new JLabel("Player's name (press ENTER)");
		JTextField enterName = new JTextField("Sephiroth", 20);
		
		enterName.addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String enteredName = enterName.getText();
					
					// First we check if the name is already taken
					try {
						Boolean tryTheName;
						tryTheName = TrackLog.alreadyTaken(enteredName);
						
						if(tryTheName == false){
							try {
								Dungeon newDungeon = new Dungeon(enteredName);
								dispose();
								mainMenu.dispose();
							} 
							catch (IOException e1) {
								e1.printStackTrace();
							}
						}
						else{
							System.out.println("Name already taken, try again!");
						}
					} catch (IOException e2) {
						e2.printStackTrace();
					}

				}
			}
			
		});	
		
		pan.add(labelBox);
		pan.add(enterName);
		
		this.setTitle("MushRooms : NewGame");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.getContentPane().add(pan);

	    this.pack();
		this.setVisible(true);
	}
	
}