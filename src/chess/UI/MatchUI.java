package chess.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import chess.board.Match;
import chess.gameSave.SaveGameManager;
import chess.main.Game;
import chess.main.GameState;
import chess.util.Button;
import chess.util.FontManager;

public class MatchUI {

	public Match match;
	
	public Button leaveButton;
	public Button saveButton;
	
	
	
	public boolean hasGameBeenSaved = false;

	public MatchUI(Match match) {

		this.match = match;
		
		leaveButton = new Button("  Leave", 700, 880, 50, 135, Color.BLACK, Color.WHITE);
		leaveButton.setIcon(new ImageIcon("chess.res/icons/door.png"));
		
		saveButton = new Button("  Save", 700, 1040, 50, 135, Color.BLACK, Color.WHITE);
		saveButton.setIcon(new ImageIcon("chess.res/icons/save.png"));
		
		getButtonActionLeave();
		getButtonActionSave();

	}

	public void drawUIItems(Graphics2D g2) {

		
		
		drawBlackPlayerUI(g2);
		drawWhitePlayerUI(g2);
		
		drawValueAdvantage(g2);

	}

	private void drawWhitePlayerUI(Graphics2D g2) {

		g2.setColor(Color.WHITE);
		g2.fillRect(880, 700, 300,50);
		
		
		drawWhiteTimer(g2);

		g2.setColor(Color.ORANGE);
		g2.fillRect(1130, 700, 50, 50);
		
		g2.setColor(Color.WHITE);
		
		if(match.progress.colorTurn == 'w')
		this.match.progress.timerWhite.drawTimeAnimation(g2, 1155, 725,23,0);
		
		
	}

	private void drawBlackPlayerUI(Graphics2D g2) {

		g2.setColor(Color.WHITE);
		g2.fillRect(880, 50, 300,50);
		
		drawBlackTimer(g2);
		
		g2.setColor(Color.ORANGE);
		g2.fillRect(1130, 50, 50, 50);
		
		g2.setColor(Color.WHITE);
		
		if(match.progress.colorTurn == 'b')
		this.match.progress.timerBlack.drawTimeAnimation(g2, 1155, 75,23,0);
		
	}
	
	private void drawWhiteTimer(Graphics2D g2) {
		
		g2.setColor(Color.BLACK);
		g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,20));
		g2.drawString(this.match.progress.timerWhite.timerString,1200,730);
		
	}
	
	private void drawBlackTimer(Graphics2D g2) {
		
		
		g2.setColor(Color.BLACK);
		g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,20));
		g2.drawString(this.match.progress.timerBlack.timerString,1200,80);
		
	}
	
	
	private void drawValueAdvantage(Graphics2D g2) {
		
		int getValueDiff = match.progress.calculateAdvantage();
		
		if(getValueDiff>0) {
			
			Math.abs(getValueDiff);
			
			g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,18));
			g2.setColor(Color.BLACK);
			g2.drawString("+ "+Integer.toString(getValueDiff), 920, 732);
			
		}else if(getValueDiff<0) {
			
			getValueDiff = -getValueDiff;
			
			g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,18));
			g2.setColor(Color.BLACK);
			g2.drawString("+ "+Integer.toString(getValueDiff), 920, 81);
			
		}
		
	}

	
	public void drawAfterMatchUIButtons(Game game) {
		
		game.add(leaveButton);
		game.add(saveButton);
		
	}
	
	private void getButtonActionLeave() {
		
		leaveButton.addActionListener(m -> {
			
			Game.getInstance().gameState = GameState.INMENU;
			
	   for(Component c : Game.getInstance().getComponents()) {
				
				Game.getInstance().remove(c);
				
			}
			
			Game.getInstance().add(Game.getInstance().menuScreen.b1);
			Game.getInstance().add(Game.getInstance().menuScreen.b2);
			Game.getInstance().add(Game.getInstance().menuScreen.b3);
			Game.getInstance().add(Game.getInstance().menuScreen.b4);
			
			Game.getInstance().match = new Match(Game.getInstance().board);
			
		});
		
	}
	
	
	private void getButtonActionSave() {
		
		saveButton.addActionListener(m -> {

			if(!hasGameBeenSaved) {
				
				
				saveButton.setBackground(Color.getHSBColor(222, 120, 122));
				saveButton.setForeground(Color.BLACK);
				
				SaveGameManager.safeGame(match);
				
				hasGameBeenSaved = true;
				
			}
			
			
			
			
			
		});
		
		
		
		
	}
	
	
}
