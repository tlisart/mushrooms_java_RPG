import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class HowTo extends JFrame implements ActionListener{
	

	private static final long serialVersionUID = 1L;

	//Constructor
	public HowTo(){
		JLabel explainationText = new JLabel();
		explainationText.setText("<html>" + "<div style='text-align: center;'>" + "To play, press the 'NewGame' button and type a name for your player." +  "<br />" + "You can carry on a started adventure by clicking on 'Continue',"
								+ " selecting the name and pressing ENTER."+ "<br />" + "The size and the Skin of the map are editable in the 'Settings' section, write your size and validate by pressin any key "
								+"<br />"+ "<br />"+ "IN GAME"+ "<br />"+"I -- > Opens the inventory" + "<br />" +  "O --> Open the player Card for information (Stage and Skills)"
								+"<br />" + "P --> Uses a potion if there's one available" +"<br />" + "L --> Saves the game "+ "<br />"+ "S --> Use a spell learned (must be equipped in the inventory" 
								+"<br />" + "A --> Pauses the Game, as Z --> Resumes the Game" + "<html>");

		explainationText.setFont(new Font("Serif", Font.PLAIN, 20));
		this.getContentPane().add(explainationText);
	    this.pack();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.setVisible(true);
		
	}

}
