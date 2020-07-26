import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import com.model.Map;
import com.utils.Deserializer;


public class Continue extends JFrame implements ActionListener{

	
	private static final long serialVersionUID = 1L;

	JFrame mainMenu;
	
	public Continue (JFrame mainMenu){
		this.mainMenu =  mainMenu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JPanel pan = new JPanel();
		JLabel newLabel = new JLabel("Choose a player from the list then press any key");
		
		// Creating an array of Player's data
		ArrayList<String> allPlayers = null;
		try {
			allPlayers = Deserializer.getAllPlayers();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String[] arrayPlayer = new String[allPlayers.size()];
		
		for(int i =0; i < allPlayers.size(); i++){
			arrayPlayer[i] = allPlayers.get(i);
		}
		
		// Creating a clickable list
		
		JList listName = new JList(arrayPlayer);
		listName.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		listName.addKeyListener(new KeyAdapter(){
			
		// Call a new Dungeon with the previous attributes of the map
			public void keyPressed(KeyEvent e){
				String nameSelected = (String) listName.getSelectedValue();
				Map map = null;

				try {map = Deserializer.deserializeMap(nameSelected);} 
				catch (IOException e1) {e1.printStackTrace();}	
				
				try {
					Dungeon newDungeon = new Dungeon(map);
					dispose();
					mainMenu.dispose();
				} 
				catch (IOException e2) {System.out.println("ligne 62 Continue");}
				catch (NullPointerException e3) {}
			}

		});
		
		pan.add(newLabel);
		pan.add(listName);

		this.setTitle("MushRooms : Settings");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.getContentPane().add(pan);
	    this.pack();
		this.setVisible(true);
	}
}
