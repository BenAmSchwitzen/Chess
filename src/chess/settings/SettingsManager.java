package chess.settings;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import chess.main.Game;
import chess.main.GameState;
import chess.util.Button;

public class SettingsManager {

	
	private Game game;
	
	public ArrayList<Setting> settings = new ArrayList<>();
	
	private Button leaveButton;
	
	
	
	public SettingsManager(Game game) {
		
		this.game = game;
		
		
	}
	
	
	
	public void addSettings() {
		
		
		
		Setting s1 = new Setting("board-graphics") {
			
			
			
			@Override
			public void onClick() {
				
				game.boardgraphics = !game.boardgraphics;
				
			    this.getButton().setBackground(game.boardgraphics ? new Color(118, 150, 86) : new Color(238, 238, 210));
				
			}
			
		
			
		};
		
	Setting s2 = new Setting("sounds") {
			
			@Override
			public void onClick() {
				
				game.sound = !game.sound;
				
			    this.getButton().setBackground(game.sound ? new Color(118, 150, 86) : new Color(238, 238, 210));
				
			}
		};
		
             Setting s3 = new Setting("Arrows") {
			
			@Override
			public void onClick() {
				
				game.arrows = !game.arrows;
				
			    this.getButton().setBackground(game.arrows ? new Color(118, 150, 86) : new Color(238, 238, 210));
				
			}
		};
		
		
		Setting s4 = new Setting("Random puzzles") {
			
			@Override
			public void onClick() {
				
			game.randomPuzzles = !game.randomPuzzles;
				
			this.getButton().setBackground(game.randomPuzzles ? new Color(118, 150, 86) : new Color(238, 238, 210));
			
			}
		};
		
		
		 s1.getButton().setBackground(game.boardgraphics ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s2.getButton().setBackground(game.sound ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s3.getButton().setBackground(game.arrows ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s4.getButton().setBackground(game.randomPuzzles ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 
		settings.add(s1);
		settings.add(s2);
		settings.add(s3);
		settings.add(s4);
		
		
	}
	
	private void removeSettings() {
		
		settings.clear();
		
	}
	
	
   public void addButtons() {
	   
	   int startX = 70;
	   int startY = 100;
	   int abstand = 100;
	   int count = 0;
	   
	   for(Setting s : settings) {
		   
		   s.getButton().setText(s.getName());
		   
		   s.getButton().setBounds(startX, startY+(abstand*count), 140, 50);
		   
		   game.add(s.getButton());
		   
		   count++;
		  
	   }
	   
	   initLeaveButton();
	   setLeaveButtonAction();
	   
   }
	
   
   private void initLeaveButton() {
	   
	  leaveButton = new Button(" Leave", 600, 1050, 50, 130,Color.white, Color.BLACK);
   	leaveButton.setIcon(new ImageIcon("chess.res/icons/quitIcon.png"));
   	
   	this.setLeaveButtonAction();
   	
   	game.add(leaveButton);
	   
   }
   
   private void setLeaveButtonAction() {
	   
	   leaveButton.addActionListener(e  ->  {
		   
		   
		   onLeave();
		   
	   });
		
	   
   }
   
   private void getButtonColor() {
	   
	   for(Setting s : settings) {
		   
		   
		   
		   
	   }
	   
   }
   
   private void onLeave() {
	   
	   for(Component c : game.getComponents()) {
		   game.remove(c);
	   }
	   
		if(leaveButton!=null)game.remove(leaveButton);
    	leaveButton = null;
    	
    	settings.clear();
	   
	     game.gameState = GameState.INMENU;

		game.add(Game.getInstance().menuScreen.b1);
		game.add(Game.getInstance().menuScreen.b2);
		game.add(Game.getInstance().menuScreen.b3);
		game.add(Game.getInstance().menuScreen.b4);
	   
   }
	
	
}
