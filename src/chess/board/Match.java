package chess.board;

import java.awt.Color;
import java.awt.Graphics2D;


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
import chess.util.Vektor2D;


public class Match {
	
	
	public Board board;
	public GameProgress progress;
	public PreviousPlayManager previousPlayManager;
	public MatchUI matchUI;
	
	public boolean isMatchRunning = true;
	
	private boolean hasBeenCheckSound = false;
	private boolean willThereBeTakeSound = false;
	
	private boolean preparationPhase = true;
	private int preparationTimer = 0;
	private int preparationSteps = 0;
	
	
	
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
		
		if(Game.getInstance().boardgraphics)
		board.drawGraphics(g2);
		board.reachableFeldDrawer.drawMarkedFelder(g2);
		
		if(Game.getInstance().arrows)
		board.reachableFeldDrawer.drawArrows(g2);
		
		board.reachableFeldDrawer.makePiecesInvisible(g2);
		
	}
	
	public void drawPieces(Graphics2D g2) { // Zeichnet alle Pieces
		
		
		
		
		board.drawPieces(g2);
		
		board.drawMovingPiece(g2);
		
		
	}
	
	public void resetInvalidMove(Piece piece) {
		
		
		piece.drawY = piece.y;
		piece.drawX = piece.x;
		
	}
	
	private void resetTurnChangesAndValues() {
		
		board.checker.rochadePiece = null;
		board.checker.enPassantPiece = null;
		board.selectedPiece = null;
		
		SoundManager.soundAlreadyPlayed = false;
	    hasBeenCheckSound = false;
		willThereBeTakeSound = false;
		
	}
	
	private void updateValuesAfterTurn(Piece piece) {
		
		piece.hasMoved = true;
		piece.moveCounter++;
		
		//board.checker.swapBauerToQueen(piece);
		
		this.progress.increaseTotalMoves();
		
		
		
		
	}
	
	private void onCheckEvent(PreviousPlay play) {
		
		if(this.board.checker.isKingCheck(this.progress.colorTurn == 'w' ? 'b':'w')) {
			hasBeenCheckSound =  true;
		
			   play.isCheck = true;
			
				SoundManager.setSound(5);
			
				SoundManager.soundAlreadyPlayed = true;
			
		}
		
	}
	
	
	private void removePiece(Piece piece) {
		
		 if(board.pieces.removeIf(m -> m.y == piece.drawY && m.x == piece.drawX && m!=piece)) {
			   
			   if(!SoundManager.soundAlreadyPlayed)
				   willThereBeTakeSound = true;
		   
		   }
		
	}
	
	private void drawArrows(Mouse mouse) {
		
		if(!Game.getInstance().arrows)return;
		
		 if(mouse.currentKey == 2 && mouse.pressed && !board.reachableFeldDrawer.markedOneArrowFeld) {
			 
			 if(board.checker.squareIsPossible(mouse.mouseY/100, mouse.mouseX/100)) {
				 
				 board.reachableFeldDrawer.rVektor = new Vektor2D();
				 board.reachableFeldDrawer.rVektor.startX = mouse.mouseX/100;
				 board.reachableFeldDrawer.rVektor.startY = mouse.mouseY/100;
				 board.reachableFeldDrawer.markedOneArrowFeld = true;
				 
			 }

			 
		 } else {
			 
			 if(board.checker.squareIsPossible(mouse.mouseY/100, mouse.mouseX/100) &&  board.reachableFeldDrawer.rVektor!=null && mouse.currentKey!=2 && board.reachableFeldDrawer.markedOneArrowFeld) {
				 
				 board.reachableFeldDrawer.rVektor.endX = mouse.mouseX/100;
				 board.reachableFeldDrawer.rVektor.endY = mouse.mouseY/100;
				 
				
				if(board.reachableFeldDrawer.rVektor.startX !=board.reachableFeldDrawer.rVektor.endX || board.reachableFeldDrawer.rVektor.startY!=board.reachableFeldDrawer.rVektor.endY )
				 board.reachableFeldDrawer.addNewArrow( board.reachableFeldDrawer.rVektor.startX,  board.reachableFeldDrawer.rVektor.startY,  board.reachableFeldDrawer.rVektor.endX,  board.reachableFeldDrawer.rVektor.endY);
				 
				
				 
				 board.reachableFeldDrawer.rVektor = null;
				 this.board.reachableFeldDrawer.markedOneArrowFeld = false;
				 mouse.currentKey = -99;
				 
			 }
			 
			 
			
		 }
			
		
	}
	
	
	private void drawMarkedFelder(Mouse mouse) {
	
   
		
		if(!Game.getInstance().boardgraphics)return;
		
		if(board.checker.squareIsPossible(mouse.mouseY/100, mouse.mouseX/100) && (mouse.clicked||mouse.pressed) && mouse.currentKey == 3) {
			
			
			Vektor2D vektor = new Vektor2D(mouse.mouseX/100, mouse.mouseY/100, 100, 100);
			
			for(Vektor2D v2 : board.reachableFeldDrawer.markedFelder ) {
				
				if(vektor.startX == v2.startX && vektor.startY == v2.startY)   return;
				
			}
			
			board.reachableFeldDrawer.markedFelder.add(vektor);
			
			
		}
		
		
		
	}
	
	
	private void checkTimerOfWhite() {
		
		
     if(this.progress.getTurn() == 'w' && this.isMatchRunning) {
			
			if(this.progress.timerWhite.min == 0 && this.progress.timerWhite.sec == 0) {
				
				if(this.board.selectedPiece!=null) {
					
					board.selectedPiece = null;
					
				}
				
				
				
				this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
				this.isMatchRunning = false;
				SoundManager.setSound(4);
				Game.getInstance().gameState = GameState.onWinningScreen;
				
			}
			
		}
	}
	
	private void checkTimerOfBlack()  {
		
		
		if(this.progress.getTurn() == 'b' && this.isMatchRunning) {
			
			
     if(this.progress.timerBlack.min == 0 && this.progress.timerBlack.sec == 0) {
				
				this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
				this.isMatchRunning = false;
				SoundManager.setSound(4);
				Game.getInstance().gameState = GameState.onWinningScreen;
				
			}
			
			
			
		}
		
	}
	
	
	private void moveAnimationPiece(Mouse mouse) {
		
		if(board.selectedPiece!=null) {
			
			this.doPieceMovementAnimation(mouse);
	
			if(board.checker.mouseOutOfFeld(mouse.mouseY, mouse.mouseX))  {
				
				
				board.selectedPiece.drawY = board.selectedPiece.y;
				board.selectedPiece.drawX = board.selectedPiece.x;
				
				board.selectedPiece = null;
				
			}
		
		}
		
	}
	
	private void setUpPreviousPlay(PreviousPlay play,Piece piece) {
		
		
		
		 if(board.checker.rochadePiece!=null) {
			   
			   play.button.setText("k-T");
			   play.button.setBackground(Color.WHITE);
			   play.isRochade = true;
			  
			   
		   }else if(board.checker.enPassantPiece!=null) {
			   
			   play.newX = piece.drawX;
			   play.newY = piece.drawY;
			   
		   }
		 
		   
		   
		  
		   play.savePieces(this.board.pieces); 
		    
		   previousPlayManager.addNewPlay(play); 
		
	}
	
	
	private boolean isThisMoveValid(Piece piece) {
		
	return	board.checker.isMoveValid(piece,piece.drawY,piece.drawX)
		
		|| board.checker.checkRochade(piece,piece.drawY, piece.drawX);
		
	}
	
	
	private void mouseCheckForPiece(Mouse mouse) {
		
		

		
			
			for(Piece piece : board.pieces) {
				
                  if(piece.drawY == mouse.mouseY/board.feldSize && piece.drawX == mouse.mouseX/board.feldSize  && progress.getTurn() == piece.color) {
					
                	 
					this.board.selectedPiece = piece;  }
					
			}
			

		
	}
	
	
	private void doPreparation() {
		
	
		
		if(preparationTimer >= 15) {
		
			if(preparationSteps<8) {
			
		for(Piece p : board.pieces) {
			
			p.drawX++;
			
		}
		preparationTimer = 0;
		preparationSteps++;
			
			}else {
				
			preparationPhase = false;	
			
			}
		
		}else {
			preparationTimer++;
		}
		
	
	}


	
	public void update(Mouse mouse) {
		
		   
		
			
			if(preparationPhase  && Game.getInstance().animations) {
				
				doPreparation();
				return;
				
			}else if(preparationPhase && !Game.getInstance().animations){
				
				setUpGameWithoutAnimation();
				preparationPhase = false;
			}
		

		
			
	
		
		

		this.progress.updateTimer();
	
	    this.checkTimerOfWhite();
	    
	    this.checkTimerOfBlack();
		
	    this.drawArrows(mouse);
	    
	    this.drawMarkedFelder(mouse);
		
	    
		
		if(mouse.pressed && mouse.currentKey == 1 && Game.getInstance().gameState == GameState.INMATCH) {
		
			this.board.reachableFeldDrawer.clearArrows();
			this.board.reachableFeldDrawer.clearMarkedFelder();
			
			
			if(board.selectedPiece == null) {
				
				mouseCheckForPiece(mouse);

				
			}else moveAnimationPiece(mouse);
			     
			
				
		} else {
			
			
			
			if(board.selectedPiece== null) 
				return;
			
			// Ab hier wird der Zug ausgewertet 1.Normal
			
			
			
			if(isThisMoveValid(board.selectedPiece)) {
				
				

				   PreviousPlay prevPlay = new PreviousPlay(board.selectedPiece, board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX));
				
				   this.board.checker.checkBauerDoubleMove(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX);
 
				   
				   board.checker.doRochade(board.selectedPiece);
				   board.checker.getEnPassantPiece(board.selectedPiece, board.selectedPiece.drawY, board.selectedPiece.drawX);
				
				  
				   
				 
				   
                    removePiece(board.selectedPiece);
				   
                   
				   
				
				   
				   if(board.checker.rochadePiece== null && board.checker.enPassantPiece  == null) {
						
						board.selectedPiece.y = board.selectedPiece.drawY;
						board.selectedPiece.x = board.selectedPiece.drawX;
						
						board.checker.swapBauerToQueen(board.selectedPiece);
						
						}
				   
				   
	                board.checker.finalEnPassant(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX); // Das könnte mit arbeit in isMoveValid

				   
				   
				   setUpPreviousPlay(prevPlay,board.selectedPiece);
				  
				
				
				
				   
				  

                 
				 
				  
				   onCheckEvent(prevPlay);
				   
                   updateValuesAfterTurn(board.selectedPiece);
				   
				   
				   
				   
				  if(willThereBeTakeSound && !hasBeenCheckSound) {
					  SoundManager.setSound(3);
				  }
				 
				 
	               // board.checker.finalEnPassant(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX); // Das könnte mit arbeit in isMoveValid

	               

				
				 if(!hasBeenCheckSound && !willThereBeTakeSound && !prevPlay.isRochade) {
					SoundManager.setSound(1);}
					 
				
				if(prevPlay.isRochade) {
					
					 SoundManager.setSound(2);
					
				}
				
				
				
				 
				
				
				
				
	         if(board.checker.isCheckMate(this.progress.colorTurn == 'w' ? 'b':'w')) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);

					SoundManager.setSound(4);
					
					isMatchRunning = false;
					
					prevPlay.lastPlay = true;
					
					Game.getInstance().gameState = GameState.onWinningScreen;
                     
				}
				
				// Check but works
				else if(board.checker.staleMate(this.progress.colorTurn == 'w' ? 'b':'w')) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
					SoundManager.setSound(0);
					
					isMatchRunning = false;
					
					prevPlay.lastPlay = true;
					
					Game.getInstance().gameState = GameState.onWinningScreen;
					
				}
	         
	         
				
				 this.progress.changeTurn();
				
			}else {
				
				resetInvalidMove(board.selectedPiece);
				
				//Fail sound to add
			}
			
			
		    resetTurnChangesAndValues();
			
			
			
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
        	
        	
        	for(Piece piece : board.pieces) {
        		
        		piece.drawX-= 8;
        		
        	
		}
        	
        
        }
        	
        	
        	private void setUpGameWithoutAnimation() {
        		
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
