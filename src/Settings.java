import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.action.SkinSettingsClassic;
import com.action.SkinSettingsHolidays;
import com.utils.EditSettings;


public class Settings extends JFrame implements ActionListener  {
    
	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//Init JPanel
		JPanel pan = new JPanel();
		
		//Init the size config
		JTextField size = new JTextField("50", 20);
		size.addKeyListener(new KeyAdapter(){
			
			public void keyTyped(KeyEvent e){
				
				ArrayList<Integer> allSettings = null;
				try {
					allSettings = EditSettings.getSettings();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				String stringHeigth = size.getText();
				int skin = allSettings.get(1);
								
				try{
					
				int Intsize = Integer.parseInt(stringHeigth);
				EditSettings.upDateSettings(Intsize, skin);
					
				}catch(Exception ex){
					System.out.println("I need an integer!");
				}
			}
		});
		
		JLabel labelSize = new JLabel("Recomended : between 20 and 150");
		
		//Init the buttonGroup in which we can choose what skin we want to play
		ButtonGroup checkBoxes = new ButtonGroup();
		
		///Creating buttons
		JRadioButton classicSkin = new JRadioButton("Classic Dungeon Skin");
		classicSkin.setSelected(true);
		SkinSettingsClassic classic = new SkinSettingsClassic();
		classicSkin.addActionListener(classic);
		
		///classicSkin.addActionListener();
		
		JRadioButton hawaiiSkin = new JRadioButton("Sephiroth's Holidays Skin");
		SkinSettingsHolidays holidays = new SkinSettingsHolidays();
		hawaiiSkin.addActionListener(holidays);
		
		//Creating the radioButtons
		checkBoxes.add(classicSkin);
		checkBoxes.add(hawaiiSkin);
		
		
		//Adding everything into the Panel
		pan.add(labelSize);

		pan.add(size);
		pan.add(classicSkin);
		pan.add(hawaiiSkin);
		
		
		this.setTitle("MushRooms : Settings");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.getContentPane().add(pan);

	    this.pack();
		this.setVisible(true);
	}
}
