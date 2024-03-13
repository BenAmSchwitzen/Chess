package chess.gameSave;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import chess.board.Match;
import chess.util.Button;

public class SaveGame {
	
	
	public Match match;
	public Button rewatchButton;
	
	private String blackEyeIcon ="chess.res/icons/blackEyeIcon.png";
	private String whiteEyeIcon ="chess.res/icons/whiteEyeIcon.png";
	
	public SaveGame(Match match) {
		
		this.match = match;
		
		rewatchButton = new Button("", 500, 800, 50, 120, this.match.progress.colorTurn == 'w' ? Color.BLACK: Color.WHITE, this.match.progress.colorTurn == 'w' ? Color.WHITE: Color.BLACK);
		rewatchButton.setIcon(new ImageIcon( this.match.progress.colorTurn == 'w' ? whiteEyeIcon: blackEyeIcon));
		rewatchButton.setText(this.match.progress.colorTurn == 'w' ? "black":"white");
	}

}
