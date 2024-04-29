package chess.settings;

import java.awt.Color;

import chess.util.Button;

public abstract class Setting {

	private String name;
	
	private Button button = new Button();
	
	
	
	public Setting(String name) {
		
		this.name = name;
		
	    this.button.setForeground(Color.WHITE);
		
		button.addActionListener(e -> {
			
			onClick();
			
		});
		
	}
	
	
	public abstract void onClick();
	
		
	public Button getButton() {
		
		return button;
		
	}


public String getName() {
		
		return name;
		
	}
	
}
