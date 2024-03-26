package chess.board;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JButton;

import chess.UI.MatchUI;
import chess.controller.Mouse;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.Bauer;
import chess.piece.Dame;
import chess.piece.König;
import chess.piece.Läufer;
import chess.piece.Piece;
import chess.piece.Springer;
import chess.piece.Turm;
import chess.previousplay.PreviousPlay;
import chess.previousplay.PreviousPlayManager;
import chess.sound.SoundManager;


public class Match {
	
	public Board board;
	public GameProgress progress;
	public PreviousPlayManager previousPlayManager;
	
	public MatchUI matchUI;
	
	public boolean isMatchRunning = true;
	
	public Match(Board board) {
		
		this.board = board;
		
		this.setUpGame();
		
		this.progress = new GameProgress(this);
		
		this.matchUI = new MatchUI(this);
		
		previousPlayManager = new PreviousPlayManager(this);
		
		
	}
	
	
	public void drawMatchRelatedUI(Graphics2D g2) {
		
		matchUI.drawUIItems(g2);
		
	}
	
	
	public void drawBoard(Graphics2D g2) {  // Zeichnet das Board
		
		board.drawBoard(g2);
		
		
		
	}
	
	public void drawBoardGraphics(Graphics2D g2) {
		
		board.drawGraphics(g2);
		
	}
	
	public void drawPieces(Graphics2D g2) { // Zeichnet alle Pieces
		
		board.drawPieces(g2);
		
		board.drawMovingPiece(g2);
		
		
	}
	
	public void update(Mouse mouse) {
		
		this.progress.updateTimer();
	
		
		if(this.progress.getTurn() == 'w' && this.isMatchRunning) {
			
			if(this.progress.timerWhite.min == 0 && this.progress.timerWhite.sec == 0) {
				
				if(this.board.selectedPiece!=null) {
					
					board.selectedPiece = null;
					
				}
				
				
				
				this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
				this.isMatchRunning = false;
				Game.getInstance().gameState = GameState.onWinningScreen;
				
			}
			
		}
		
		if(this.progress.getTurn() == 'b' && this.isMatchRunning) {
			
			
     if(this.progress.timerBlack.min == 0 && this.progress.timerBlack.sec == 0) {
				
				this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
				this.isMatchRunning = false;
				Game.getInstance().gameState = GameState.onWinningScreen;
				
			}
			
			
			
		}
		
		
		
		if(mouse.pressed && Game.getInstance().gameState == GameState.INMATCH) {
		
			
			if(board.selectedPiece == null) {
				
				for(Piece piece : board.pieces) {
					
	                  if(piece.drawY == mouse.mouseY/board.feldSize && piece.drawX == mouse.mouseX/board.feldSize  && progress.getTurn() == piece.color) {
						
	                	 
						this.board.selectedPiece = piece;  }
						

				
				}
		
				
			}else if(board.selectedPiece!=null) {
				
				this.doPieceMovementAnimation(mouse);
		
				if(board.checker.mouseOutOfFeld(mouse.mouseY, mouse.mouseX))  {
					
					
					board.selectedPiece.drawY = board.selectedPiece.y;
					board.selectedPiece.drawX = board.selectedPiece.x;
					
					board.selectedPiece = null;
					
				}
			
			}
			
				
		} else {
			
			if(board.selectedPiece== null) 
				return;
			
			// Ab hier wird der Zug ausgewertet 1.Normal
			
			
			
			if(board.checker.isMoveValid(board.selectedPiece,board.selectedPiece.drawY,board.selectedPiece.drawX) 
				
				|| board.checker.checkRochade(board.selectedPiece, board.selectedPiece.drawY, board.selectedPiece.drawX)) {
				
				   this.board.checker.checkBauerDoubleMove(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX);

				   board.checker.doRochade(board.selectedPiece);
				   board.checker.doEnpassant(board.selectedPiece, board.selectedPiece.drawY, board.selectedPiece.drawX);
				
				   PreviousPlay prevPlay = new PreviousPlay(board.selectedPiece, board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX));
				   
				   if(board.checker.rochadePiece!=null) {
					   
					   prevPlay.button.setText("k-T");
					   prevPlay.button.setBackground(Color.WHITE);
					   
					   if(!SoundManager.soundAlreadyPlayed)
					   SoundManager.setSound(2);
					   SoundManager.soundAlreadyPlayed = true;
					   
				   }
				   
				   previousPlayManager.addNewPlay(prevPlay);
				
				
				if(board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX)!=null && board.checker.rochadePiece==null) { // piece gets Removed
					
					board.pieces.removeIf(m -> m.y == board.selectedPiece.drawY && m.x == board.selectedPiece.drawX);
					
					  if(!SoundManager.soundAlreadyPlayed)
					SoundManager.setSound(3); // Takes piece
					SoundManager.soundAlreadyPlayed = true;
				}
		
				if(board.checker.rochadePiece== null && board.checker.enPassantPiece  == null) {
				
				board.selectedPiece.y = board.selectedPiece.drawY;
				board.selectedPiece.x = board.selectedPiece.drawX;
				
				}
				
				if(board.checker.enPassantPiece!=null) {
					
					board.checker.finalEnPassant(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX);
					
				}
				
				 if(!SoundManager.soundAlreadyPlayed)
					SoundManager.setSound(1);
				 SoundManager.soundAlreadyPlayed = true;
				
				
				board.selectedPiece.hasMoved = true;
				board.selectedPiece.moveCounter++;
				
				board.checker.swapBauerToQueen(board.selectedPiece);
				
				this.progress.increaseTotalMoves();
				
				prevPlay.savePieces(this.board.pieces);
				
				
				
				if(board.checker.isCheckMate(board.selectedPiece) || board.checker.cantDoAnyMoveAnymore('w') || board.checker.cantDoAnyMoveAnymore('b')) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);

					SoundManager.setSound(0);
					
					isMatchRunning = false;
					
					
					
					Game.getInstance().gameState = GameState.onWinningScreen;
                     
				}
				
				// Check but works
				else if(board.checker.staleMate()) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);

					SoundManager.setSound(0);
					
					isMatchRunning = false;
					
					Game.getInstance().gameState = GameState.onWinningScreen;
					
				}
				
				 this.progress.changeTurn();
				
			}else {
				
				board.selectedPiece.drawY = board.selectedPiece.y;
				board.selectedPiece.drawX = board.selectedPiece.x;
				
				
			}
			
			
			board.checker.rochadePiece = null;
			board.selectedPiece = null;
			
			SoundManager.soundAlreadyPlayed = false;
			
		}
		
		
	}
	
	
	public void doPieceMovementAnimation(Mouse mouse) {
		
		board.selectedPiece.drawX = (mouse.mouseX/board.feldSize);
		board.selectedPiece.drawY = (mouse.mouseY/board.feldSize);
		
	
		
		
	}
	

        
        private void setUpGame() {  // Makes the game ready to play
        	
        	board.pieces.clear();
        	
        	board.pieces.add(new Bauer('w', 6, 0));
        	board.pieces.add(new Bauer('w', 6, 1));
        	board.pieces.add(new Bauer('w', 6, 2));
        	board.pieces.add(new Bauer('w', 6, 3));
        	board.pieces.add(new Bauer('w', 6, 4));
        	board.pieces.add(new Bauer('w', 6, 5));
        	board.pieces.add(new Bauer('w', 6, 6));
        	board.pieces.add(new Bauer('w', 6, 7));
        	
        	board.pieces.add(new Bauer('b', 1, 0));
        	board.pieces.add(new Bauer('b', 1, 1));
        	board.pieces.add(new Bauer('b', 1, 2));
        	board.pieces.add(new Bauer('b', 1, 3));
        	board.pieces.add(new Bauer('b', 1, 4));
        	board.pieces.add(new Bauer('b', 1, 5));
        	board.pieces.add(new Bauer('b', 1, 6));
        	board.pieces.add(new Bauer('b', 1, 7));
        	
        	board.pieces.add(new Dame('w', 7, 3));
        	board.pieces.add(new Dame('b', 0, 3));
        	
        	board.pieces.add(new König('w', 7, 4));
        	board.pieces.add(new König('b', 0, 4));
        	
        	board.pieces.add(new Turm('w', 7, 0));
        	board.pieces.add(new Turm('w', 7, 7));
        	board.pieces.add(new Turm('b', 0, 0));
        	board.pieces.add(new Turm('b', 0, 7));
        	
        	
        	board.pieces.add(new Springer('w', 7, 1));
        	board.pieces.add(new Springer('w', 7, 6));
        	board.pieces.add(new Springer('b', 0, 1));
        	board.pieces.add(new Springer('b', 0, 6));
        	
        	board.pieces.add(new Läufer('w', 7, 5));
            board.pieces.add(new Läufer('w', 7, 2));
            board.pieces.add(new Läufer('b', 0, 5));
        	board.pieces.add(new Läufer('b', 0, 2));
        	
        	
        	
        	
        	
        }

        
      
        
        
        
        
}
