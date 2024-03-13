package chess.gameSave;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import chess.board.Match;
import chess.main.Game;
import chess.main.GameState;
import chess.util.FontManager;

public class SaveGameManager {

	public static ArrayList<SaveGame> matches = new ArrayList<>();
	
	public SaveGameManager() {
		
	}
	
	
	public static void safeGame(Match match) {
		
		matches.add(new SaveGame(match));
		
	}
	
	public static void getMatchCount() {
		
		for(SaveGame match : matches) {
			
			
			
		}
		
	}
	
	public static void drawMatches(Graphics2D g2) {
		
		g2.setColor(Color.WHITE);
		g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,18));
		g2.drawString("Previous Games", 950, 450);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(940, 460, 170, 5);
		
		drawSingleMatch(g2);
		
	}
	
	public static void drawSingleMatch(Graphics2D g2) {
		
		
		addButtonListener();
		
		int startY = 520;
		int startX = 830;
		int counter = 0;

		for(SaveGame saveGame : matches) {
			
			
		
			
			
			saveGame.rewatchButton.y = startY;
			saveGame.rewatchButton.setBounds(startX, startY, 80, 50);
			Game.getInstance().add(saveGame.rewatchButton);
			saveGame.rewatchButton.y = startY;
			
			
			counter++;
			
	    if(counter == 4) {
				
				startY+=80;
				counter = 0;
			    startX = 830;	
			}else {
				startX+=120;
			}
			
			
			
			
		}
		
		
	}
	
	public static  void addButtonListener() {
		
		matches.forEach(m -> m.rewatchButton.addActionListener(n -> {
			
         if(SaveGameManager.matches.size()>0 &&  SaveGameManager.matches.get(0)!=null) {
				

        	 Game.getInstance().match = m.match;
        	 Game.getInstance().gameState = GameState.onWinningScreen;
        	 
			}
			
		}));
			
		
		
		
		
	}
	
	
}
