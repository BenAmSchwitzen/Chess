package chess.graphics;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import chess.board.Board;
import chess.main.Game;
import chess.piece.Piece;



public class ReachableFeldDrawer {

	public Board board;
	
	public Piece movingPieceOverThisPiece = null;
	
	public ReachableFeldDrawer(Board board) {
		
		this.board = board;
		
	}
	
	public void draw(Graphics2D g2) {  // Zeichnet die möglichen Felder,die eine Figur betreten kann
		
		
		g2.setColor(Color.getHSBColor(24, 24, 40));
		
		if(this.board.selectedPiece!=null) {
			
			for(int i = 0;i<8;i++) {
				
				
				for(int j = 0;j<8;j++) {
					
					if(board.checker.isMoveValid(board.selectedPiece, i, j) || board.checker.checkRochade(board.selectedPiece, i, j) || board.checker.enPassant(board.selectedPiece, i, j)) 
							
							
							  
						
							
					
					g2.fillRect((j*board.feldSize)+30, (i*board.feldSize)+30,40, 40);
					
					
					
				}
				
				
				
			}
			
			
			
			
			
		}
		
	
		
	}
	
	
	public void makePiecesInvisible(Graphics2D g2) {
		
		
			
		for(Piece piece : board.pieces) {
			
			if(board.selectedPiece!=null&&board.selectedPiece!=piece && board.selectedPiece.drawY == piece.drawY && board.selectedPiece.drawX == piece.drawX) {
				
				piece.drawPiece = false;
				
			}else {
				
				piece.drawPiece = true;
				
			}
		}
		
		
		
	  
}
	
	
	public void drawCurrentMovingSquare(Graphics2D g2) {
		
		if(board.selectedPiece==null)return;
		
		if((board.selectedPiece.drawY!= board.selectedPiece.y || board.selectedPiece.drawX != board.selectedPiece.x))
			
		{
			
			
			if(board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX)!=null && board.getPiece(board.selectedPiece.drawY, board.selectedPiece.drawX).color !=board.selectedPiece.color
					   && board.checker.isMoveValid(board.selectedPiece, board.selectedPiece.drawY, board.selectedPiece.drawX)) {
				
				  g2.setColor(Color.RED);
				
			}
				
		 
			
		g2.fillRect(board.selectedPiece.drawX*board.feldSize,board.selectedPiece.drawY*board.feldSize,board.feldSize, board.feldSize);


		
		
		
		}
		
		
	}
			
	public void drawCheckMateFelder(Graphics2D g2) {
		
		if(!Game.getInstance().match.isMatchRunning) {
			
			Piece attackingPiece = board.checker.getAttackingPiece(board.checker.getKing(Game.getInstance().match.progress.getTurn()));


			
			Piece king = board.checker.getKing(Game.getInstance().match.progress.getTurn());
			
			if(attackingPiece!=null && Game.getInstance().match.previousPlayManager.currentPlay == Game.getInstance().match.previousPlayManager.plays.get(Game.getInstance().match.previousPlayManager.plays.size()-1)) {
				
				g2.setColor(Color.RED);
				
				if(attackingPiece.drawY>king.drawY && attackingPiece.drawX == king.x) {
					
				for(int i = attackingPiece.drawY-1;i>king.y;i--) {
					
					
					g2.fillRect(attackingPiece.drawX*100, i*100, 100, 100);
					
					
					
					
				}
				
				}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX == king.x) {
					
					for(int i = attackingPiece.drawY+1;i<king.y;i++) {
						
						
						g2.fillRect(attackingPiece.drawX*100, i*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY==king.drawY && attackingPiece.drawX< king.x) {
					
					for(int i = attackingPiece.drawX+1;i<king.x;i++) {
						
						
						g2.fillRect(i*100, attackingPiece.drawY*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY==king.drawY && attackingPiece.drawX>king.x) {
					
					for(int i = attackingPiece.drawX-1;i>king.x;i--) {
						
						
						g2.fillRect(i*100, attackingPiece.drawY*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY>king.drawY && attackingPiece.drawX>king.x) {
					
					
                        int whereX = attackingPiece.x;
		        	 
		        	 for(int i = attackingPiece.y-1;i>king.y;i--) {
		        		 
							g2.fillRect((whereX-1)*100, i*100, 100, 100);
							whereX--;
		        			 
		        		 }
		
				}else if(attackingPiece.drawY>king.drawY && attackingPiece.drawX<king.x) {
					
					
                    int whereX = attackingPiece.x;
	        	 
	        	 for(int i = attackingPiece.y-1;i>king.y;i--) {
	        		 
						g2.fillRect((whereX+1)*100, i*100, 100, 100);
						whereX++;
	        			 
	        		 }
	
			}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX<king.x) {
				
				
                int whereX = attackingPiece.x;
        	 
        	 for(int i = attackingPiece.y+1;i<king.y;i++) {
        		 
					g2.fillRect((whereX+1)*100, i*100, 100, 100);
					whereX++;
        			 
        		 }

		}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX>king.x) {
			
			
            int whereX = attackingPiece.x;
    	 
    	 for(int i = attackingPiece.y+1;i<king.y;i++) {
    		 
				g2.fillRect((whereX-1)*100, i*100, 100, 100);
				whereX--;
    			 
    		 }

	}
			
		}
		
		
		
		
		
		
	}
	}
	
	
	
		
}
		
	
	

			
	
		
	


