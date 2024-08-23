package chess.board;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import chess.UI.MatchUI;
import chess.computer.Computer;
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
	
	
	private Computer computer = new Computer();
	private boolean computerTurn = false;
	private int computerTimer = 0;
	
	public Piece checkedKing = null;  // Used to animate the checked king after checkmate
	
	public boolean firstVisited = true; // For animation purposes
	
	
    
	


	
	
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
	
	private void updateValuesAfterTurn(Piece piece,PreviousPlay play) {
		
		piece.hasMoved = true;
		piece.moveCounter++;
		
		//board.checker.swapBauerToQueen(piece);
		
		if(this.progress.totalMoves == 0)
		   this.progress.totalMoves++;
			
			
		play.number = this.progress.totalMoves;
		
	     previousPlayManager.currentPlay = play;
		
		
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
		
		 if(board.pieces.removeIf(m -> m.y == piece.drawY && m.x == piece.drawX && m!=piece && board.checker.rochadePiece!=m)) {
			   
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
			   
			   //play.button.setText("k-T");
			 
			 // short and long rochade
			 
			   play.button.setText(play.longRochade ? "0-0-0":"0-0");
			 
			 
			   play.button.setBackground(Color.RED);
			   play.isRochade = true;
			  
			   
		   }else if(board.checker.enPassantPiece!=null) {
			   
			   play.newX = piece.drawX;
			   play.newY = piece.drawY;
			   
		   } else if(play.isPromoting){
			   
			   play.button.setText("B - >");
			   
		   }
		 
		   
		  // The code belows refreshes the move-buttons 
		   
		   if(previousPlayManager.plays.size()% 10 == 0 &&previousPlayManager.plays.size()>0) {
			   
			   previousPlayManager.currentViewPlay+=10;
			   
		   }
		 
		
		  
		   play.savePieces(this.board.pieces); 
		    
		   previousPlayManager.addNewPlay(play); 
		   
		   
		 
		   
		   
		   
		
	}
	
	
	public boolean isThisMoveValid(Piece piece) {
		
	
		
		if(board.canPieceGoToSpecificFeld(piece,piece.drawY,piece.drawX)) {
			
			if(piece instanceof König && board.getPiece(piece.drawY,piece.drawX)!=null && board.getPiece(piece.drawY,piece.drawX) instanceof Turm && piece.color == board.getPiece(piece.drawY,piece.drawX).color) {
				
				
				return board.checker.checkRochade(piece,piece.drawY, piece.drawX);
				
			}else {
				
				return true;
			}
			
			
			
		}else {
			
			return false;
			
		}
		                  
		//Dieser Code ist alt,da nur mit dem Code über diesem Rochade als possibleMove anerkannt werden kann,weil nun checkRochade auf jeden Fall berechnet(ausgeführt) wird und somit rochadePiece zugewiesen werden kann,falls eine Rochade möglich ist
		//return board.canPieceGoToSpecificFeld(piece,piece.drawY,piece.drawX) || board.checker.checkRochade(piece,piece.drawY, piece.drawX);
		
	}
	
	
	private void mouseCheckForPiece(Mouse mouse) {
		
		

		
			
			for(Piece piece : board.pieces) {
				
                  if(piece.drawY == mouse.mouseY/board.feldSize && piece.drawX == mouse.mouseX/board.feldSize  && progress.getTurn() == piece.color) {
					
                	
                	 
                	  
					this.board.selectedPiece = piece;
			
					return;
				
					
                  }
                  
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


	private void handleComputer() {
		
		
		 if(Game.getInstance().computer) {
			    
		    	
			    if(computerTurn) {
			    	
			    	if(computerTimer == 144) {
			    		
			    		
			    		int [] finalMove = computer.getFinalMove(board, 'b',0);

			    		
			    		board.selectedPiece = board.pieces.get(finalMove[0]);
			    		board.selectedPiece.drawY =  finalMove[1];
			    		board.selectedPiece.drawX = finalMove[2];
			    		
			    	
			    		computerTimer = 0;
			    	
			    	
			    	}else {
			    		
			    		computerTimer++;
			    	}

			    	
			    }
			    
			 
			    }
			    
		
		
	}
	
	
	public void update(Mouse mouse) {
		
		
		if(Game.getInstance().keyBoard.currentKeyNumber == 27) {
			
			 matchUI.onLeave();
			 board.selectedPiece = null;
			
		}
		  
		
			
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
		
	    
	    handleComputer();
	   
	    
		
		if(mouse.pressed && mouse.currentKey == 1 && (!computerTurn || !Game.getInstance().computer)) {
		
			this.board.reachableFeldDrawer.clearArrows();
			this.board.reachableFeldDrawer.clearMarkedFelder();
			
			
			if(board.selectedPiece == null) {
				
				board.checker.hasBeenCheckedForMoves = false;
				mouseCheckForPiece(mouse);
				
				
					
				

				
			}else  moveAnimationPiece(mouse);
			
			         
			
			
				
		} else {
			
			
			
			
			if(board.selectedPiece== null)
				return;
			
			
			
			
			
			if(isThisMoveValid(board.selectedPiece) || board.checker.promotePiece !=null) {
				
				

				   PreviousPlay prevPlay = new PreviousPlay(board.selectedPiece, board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX));
				
				   this.board.checker.checkBauerDoubleMove(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX);
 
				   
				  // board.checker.doRochade(board.selectedPiece);
				   board.checker.doRochade(board.selectedPiece,prevPlay);
				   board.checker.getEnPassantPiece(board.selectedPiece, board.selectedPiece.drawY, board.selectedPiece.drawX);
				
				  
				   
				 
				   
                    removePiece(board.selectedPiece);
				   
                   
				   
				
				   
				   if(board.checker.rochadePiece== null && board.checker.enPassantPiece  == null) {
						
						board.selectedPiece.y = board.selectedPiece.drawY;
						board.selectedPiece.x = board.selectedPiece.drawX;
						
						if(!handlePromoting(board.selectedPiece,prevPlay)) {
							
							
							return;
							
						}
						
						
					
						
						}
				   
				   
	                board.checker.finalEnPassant(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX);

				   
				   
				   setUpPreviousPlay(prevPlay,board.selectedPiece);
				  
				
				
				
				 
				   
				   
				  

                 
				 
				  
				   onCheckEvent(prevPlay);
				   
                   updateValuesAfterTurn(board.selectedPiece,prevPlay);
				   
                 
				   
				   
				  if(willThereBeTakeSound && !hasBeenCheckSound) {
					  SoundManager.setSound(3);
				  }
				 
				 
	               // board.checker.finalEnPassant(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX); // Das könnte mit Arbeit in isMoveValid

	               

				
				 if(!hasBeenCheckSound && !willThereBeTakeSound && !prevPlay.isRochade) {
					SoundManager.setSound(1);}
					 
				
				if(prevPlay.isRochade) {
					
					 SoundManager.setSound(2);
					
				}
				
				
				
				 
				
				
				
				
	         if(board.checker.isCheckMate(this.progress.colorTurn == 'w' ? 'b':'w')) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);

					SoundManager.setSound(4);
					
					this.board.perspectiveValue = 1;
				    this.checkedKing = (König) board.checker.getKing(Game.getInstance().match.progress.getTurn() == 'w' ? 'b' : 'w');
					
					isMatchRunning = false;
					
					prevPlay.lastPlay = true;
					prevPlay.button.setIcon(new ImageIcon("chess.res/playIcons/checkmatePlay.png"));
					
					Game.getInstance().gameState = GameState.onWinningScreen;
                     
				}
				
				// Check but works
				else if(board.checker.staleMate(this.progress.colorTurn == 'w' ? 'b':'w')) {
					
					this.previousPlayManager.currentPlay = this.previousPlayManager.plays.get(this.previousPlayManager.plays.size()-1);
					SoundManager.setSound(0);
					
					isMatchRunning = false;
					
					prevPlay.lastPlay = true;
					prevPlay.button.setIcon(new ImageIcon("chess.res/playIcons/stalematePlay.png"));
					
					Game.getInstance().gameState = GameState.onWinningScreen;
					
				}
	         
	         
				
				 this.progress.changeTurn();
				 
				
				 
				 
				 
				 computerTurn = !computerTurn;
				
				 if(this.checkedKing==null)
				 turnBoard();
				
				 
				 this.board.updatePossibleMoves(this.progress.getTurn());
				 
				
				 
				
				 
			}else {
				
				resetInvalidMove(board.selectedPiece);
				
				
				
				//Fail sound to add
			}
			
			
		    resetTurnChangesAndValues();
			
			
			
		}
		
		
		
		
		
		
	}
	
	private void doPromote(Piece piece) {
		
		 board.pieces.remove(piece);
		 board.pieces.add(board.checker.promotePiece);
	}
	
	private boolean handlePromoting(Piece piece,PreviousPlay play) {
		
		if(board.checker.isBauerPromoting(piece)) {
			
			if((computerTurn && Game.getInstance().computer)) {
				
				
				board.checker.swapBauerToQueen(piece); 
				
				return true;
				
			} else {
				
				if(board.checker.promotePiece == null) {
				
					
					board.checker.promotePiece = board.selectedPiece;
					board.selectedPiece.drawPiece = false;
					board.checker.isPromoting = true;
					Game.getInstance().gameState = GameState.INPROMOTING;
					matchUI.promoteScreen.addPromoteButtons();
					return false;
				} else {
					
					
					play.isPromoting = true;
				
					this.doPromote(piece);
					board.checker.promotePiece = null;
					board.checker.isPromoting = false;
					board.selectedPiece.drawPiece = true;
					return true;
				}
				
				
				
			}
			
			
			
		}
		
		
		return true;
	}
	
	
	public void doPieceMovementAnimation(Mouse mouse) {
		
		
		if(!board.checker.isPromoting) {
			
			board.selectedPiece.drawX = (mouse.mouseX/board.feldSize);
			board.selectedPiece.drawY = (mouse.mouseY/board.feldSize);
	
		    
		}
		
	
		
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
        	
        
        	
        	board.pieces.add(new König('w', 7, 4));
        	board.pieces.add(new König('b', 0, 4));
        	
        	board.pieces.add(new Turm('w', 7, 0));
        	board.pieces.add(new Turm('w', 7, 7));
        	board.pieces.add(new Turm('b', 0, 0));
        	board.pieces.add(new Turm('b', 0, 7));
        	
        	
        	
        	
        	
        	
        	
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

        
       
       
        	
        	private void turnBoard() {
        		
        		if(Game.getInstance().turnBoard) {
        			
        			board.turnBoardAround(progress.getTurn());
        			
        		}
        		
        	}
        	
        
       
        
        
}
