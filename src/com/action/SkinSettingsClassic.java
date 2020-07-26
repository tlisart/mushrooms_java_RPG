package com.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import com.utils.EditSettings;

public class SkinSettingsClassic implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		ArrayList<Integer> listSettings = null;  //Init
			
		try {
			listSettings = EditSettings.getSettings();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
			
		int size = listSettings.get(0);
		
		try {
			EditSettings.upDateSettings(size, 0);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}




