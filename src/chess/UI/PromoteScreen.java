package chess.UI;

import java.awt.Color;
import javax.swing.ImageIcon;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.Dame;
import chess.piece.Läufer;
import chess.piece.Springer;
import chess.piece.Turm;
import chess.util.Button;

public class PromoteScreen {
	
	private MatchUI matchUI;
	
	
	
	public PromoteScreen(MatchUI matchUI) {
		
		this.matchUI = matchUI;
		
		
	}
	
	
	public void hideSelectedPieceWhilePromotingOption() {
		
		
		matchUI.match.board.selectedPiece.drawPiece = false;
		
	}
	
	
	
	
	
	public void addPromoteButtons() {
		
	   Button turmB = new Button("Turm",matchUI.match.board.feldSize*2,matchUI.match.board.feldSize*3,100, 200, Color.WHITE, Color.BLACK);
		turmB.setIcon(new ImageIcon("chess.res/icons/pTurm.png"));
		
	   Button läuferB = new Button("Läufer",matchUI.match.board.feldSize*3,matchUI.match.board.feldSize*3,100, 200, Color.WHITE, Color.BLACK);
	   läuferB.setIcon(new ImageIcon("chess.res/icons/pLäufer.png"));
	   
	   Button springerB = new Button("Springer",matchUI.match.board.feldSize*4,matchUI.match.board.feldSize*3,100, 200,  Color.WHITE, Color.BLACK);
	   springerB.setIcon(new ImageIcon("chess.res/icons/pSpringer.png"));
	   
	   Button dameB = new Button("Dame",matchUI.match.board.feldSize*5,matchUI.match.board.feldSize*3,100, 200, Color.WHITE, Color.BLACK);
	   dameB.setIcon(new ImageIcon("chess.res/icons/pQueen.png"));
	 
	   turmB.addActionListener(e -> {
		   Turm turm = new Turm(matchUI.match.progress.colorTurn, matchUI.match.board.selectedPiece.y, matchUI.match.board.selectedPiece.x);
		   matchUI.match.board.checker.promotePiece = turm;
		   Game.getInstance().gameState = GameState.INMATCH;
	   });
	   
   läuferB.addActionListener(e -> {
		   
		   matchUI.match.board.checker.promotePiece = new Läufer(matchUI.match.progress.colorTurn, matchUI.match.board.selectedPiece.y, matchUI.match.board.selectedPiece.x);
		   Game.getInstance().gameState = GameState.INMATCH;
	   });
   
   springerB.addActionListener(e -> {
	   
	   matchUI.match.board.checker.promotePiece = new Springer(matchUI.match.progress.colorTurn, matchUI.match.board.selectedPiece.y, matchUI.match.board.selectedPiece.x);
	   Game.getInstance().gameState = GameState.INMATCH;
   });
   
   dameB.addActionListener(e -> {
	   
	   matchUI.match.board.checker.promotePiece = new Dame(matchUI.match.progress.colorTurn, matchUI.match.board.selectedPiece.y, matchUI.match.board.selectedPiece.x);
	   Game.getInstance().gameState = GameState.INMATCH;
   });
	   
	   
		Game.getInstance().add(turmB);
		Game.getInstance().add(läuferB);
		Game.getInstance().add(springerB);
		Game.getInstance().add(dameB);
		
	
		
		
	}
	
	
			
			
		
		
		
		
	}
	
	

