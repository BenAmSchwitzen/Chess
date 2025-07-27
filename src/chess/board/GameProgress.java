package chess.board;

import java.util.HashMap;
import java.util.Map;

import chess.main.Game;
import chess.main.GameState;
import chess.piece.Piece;
import chess.util.Timer;

public class GameProgress {

	public char colorTurn = 'w';
	
	private Match match;
	
	public int totalMoves = 0;
	
	public Timer timerWhite;
	public Timer timerBlack;
	
	public int zugCounter = 0;  // 50-Züge-Regel
	
	Map<String, Integer> positionCounts = new HashMap<>();
	
	public GameProgress(Match match) {
		
		this.match = match;
		
		timerWhite = new Timer();
		timerBlack = new Timer();
		
	}
	
	public void changeTurn() {
		

		for(Piece piece : match.board.pieces) {
			
			if(piece.color != colorTurn) {
				
				piece.hasDoubleMoved = false;
				
			}
			
		}
		
		colorTurn = colorTurn == 'w' ? 'b':'w';
		
		
		
	}
	
	public char getTurn() {
		
		return colorTurn;
		
	}
	
	public void increaseTotalMoves() {
		
		this.totalMoves++;
		
	}
	
	public void updateTimer() {
		
		if(Game.getInstance().gameState != GameState.INMATCH) {
			return;
		}
		
		if(colorTurn == 'w') {
			
			timerWhite.updateTime();
			
		}else {
			
			timerBlack.updateTime();
			
		}
		
		
		
	}
	
	public int calculateAdvantage() {
		
		int white = (int) match.board.pieces.stream().filter(m-> m.color == 'w').mapToDouble(m -> m.value).sum();
		
		int black = (int) match.board.pieces.stream().filter(m-> m.color == 'b').mapToDouble(m -> m.value).sum();
		
		return white-black;
		
	}
	
	
}
