package chess.graphics;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import chess.board.Board;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.König;
import chess.piece.Piece;
import chess.util.Vektor2D;



public class ReachableFeldDrawer {

	public Board board;
	
	public Piece movingPieceOverThisPiece = null;
	
	public ArrayList<Vektor2D> arrows = new ArrayList<>();
	public ArrayList<Vektor2D> markedFelder = new ArrayList<>();
	
	
	public Vektor2D rVektor = null;
	
	public boolean markedOneArrowFeld = false;
	
	public Piece checkedKing = null;
	private int angle = 0;
	private int radius = 35;
	private int animationTimer = 0;
	

	
	
	
	Path2D.Double p = new Path2D.Double();
	
	public ReachableFeldDrawer(Board board) {
		
		this.board = board;
		
	}
	
	
	
  public void draw(Graphics2D g2) {  // Zeichnet die möglichen Felder,die eine Figur betreten kann
		
		
		g2.setColor(Color.getHSBColor(24, 24, 40));
		
		if(this.board.selectedPiece!=null) {
			
			
			this.board.selectedPiece.possibleMoves.forEach(m -> {
				
				g2.fillRect((m[1]*board.feldSize)+30, (m[0]*board.feldSize)+30,40, 40);
				
				
				
			});
			
			drawRochade(g2);
			
			
		}
		
	
		
	}
	
  
  private void drawRochade(Graphics2D g2) {
	  
	  if(board.selectedPiece instanceof König) {
		  
		  if(board.selectedPiece.color == 'w' || (board.selectedPiece.color == 'b' && board.perspectiveValue == -1)) {
			  
			  if(board.checker.checkRochade(board.selectedPiece, 7, 7)) {
				  
				  g2.fillRect((7*board.feldSize)+30, (7*board.feldSize)+30,40, 40);
				  
				  
			  } 
			  
			  if(board.checker.checkRochade(board.selectedPiece, 7, 0)) {
				  g2.fillRect((0*board.feldSize)+30, (7*board.feldSize)+30,40, 40);
			  }
			  
			  
			  
			  
			  
		  } else if(board.selectedPiece.color == 'b') {
			  
	       if(board.checker.checkRochade(board.selectedPiece, 0, 7)) {
				  
				  g2.fillRect((7*board.feldSize)+30, (0*board.feldSize)+30,40, 40);
				  
				  
			  } 
			  
			  if(board.checker.checkRochade(board.selectedPiece, 0, 0)) {
				  g2.fillRect((0*board.feldSize)+30, (0*board.feldSize)+30,40, 40);
			  }
			  
		  }
		  
		  
	  }
	  
	  
  }





	@Deprecated
	public void drawOld(Graphics2D g2) {  // Zeichnet die möglichen Felder,die eine Figur betreten kann
		
		
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
	
	
	
	
	public void addNewArrow(int startX,int startY,int endX,int endY) {
		
		arrows.add(new Vektor2D(startX, startY,endX,endY));
		
		
	}
	
	
	public void drawArrows(Graphics2D g2) {
		
		for(Vektor2D v : arrows) {
			
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(5));
			if(v.startY<v.endY) {
			g2.drawLine(v.startX*100+50, v.startY*100+100,v.endX*100+50,v.endY*100+50);
			
			}else {
				g2.drawLine(v.startX*100+50, v.startY*100,v.endX*100+50,v.endY*100+50);
			}
		}
		
		
	}
		
		public void clearArrows() {
			
			arrows.clear();
			
		}
		
	public void drawMarkedFelder(Graphics2D g2) {
		
		if(Game.getInstance().gameState!= GameState.INMATCH)return;
		
		for(Vektor2D v : markedFelder) {
			g2.setColor(Color.ORANGE);
		     g2.fillRect(v.startX*Game.getInstance().board.feldSize, v.startY*Game.getInstance().board.feldSize, 100, 100);
		  
			
		}
		
	}
	
	
	public void clearMarkedFelder() {
		
		
		this.markedFelder.clear();
		
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
					  && board.canPieceGoToSpecificFeld(board.selectedPiece, board.selectedPiece.drawY,board.selectedPiece.drawX)) {
				
				  g2.setColor(Color.RED);
				  g2.fillRect(board.selectedPiece.drawX*board.feldSize,board.selectedPiece.drawY*board.feldSize,board.feldSize, board.feldSize);
					
				
			}else {
				g2.fillRect(board.selectedPiece.drawX*board.feldSize,board.selectedPiece.drawY*board.feldSize,board.feldSize, board.feldSize);
				g2.setColor(Color.getHSBColor(24, 24, 40));
				
			}
				
		 
			
			
		
		
		g2.setColor(Color.WHITE);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(board.selectedPiece.drawX*board.feldSize,board.selectedPiece.drawY*board.feldSize,board.feldSize, board.feldSize);
		
		
		
		
		
		}
		
		
	}
	
	
	
	// Deprecated
	@Deprecated
	public void drawCurrentMovingSquareOldVersion(Graphics2D g2) {
		
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
		
		if(!Game.getInstance().match.isMatchRunning && Game.getInstance().boardgraphics) {
			
			Piece attackingPiece = board.checker.getAttackingPiece(board.checker.getKing(Game.getInstance().match.progress.getTurn()));


			
			Piece king = board.checker.getKing(Game.getInstance().match.progress.getTurn());
			
			if(attackingPiece!=null   && Game.getInstance().match.previousPlayManager.currentPlay == Game.getInstance().match.previousPlayManager.plays.get(Game.getInstance().match.previousPlayManager.plays.size()-1)) {
				
				
				
				
				g2.setColor(Color.RED);
				
				
				
			
				if(attackingPiece.drawY>king.drawY && attackingPiece.drawX == king.x) {
					
				for(int i = attackingPiece.drawY-1;i>=king.y;i--) {
					
					
					g2.fillRect(attackingPiece.drawX*100, i*100, 100, 100);
					
					
					
					
				}
				
				}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX == king.x) {
					
					for(int i = attackingPiece.drawY+1;i<=king.y;i++) {
						
						
						g2.fillRect(attackingPiece.drawX*100, i*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY==king.drawY && attackingPiece.drawX< king.x) {
					
					for(int i = attackingPiece.drawX+1;i<=king.x;i++) {
						
					
						g2.fillRect(i*100, attackingPiece.drawY*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY==king.drawY && attackingPiece.drawX>king.x) {
					
					for(int i = attackingPiece.drawX-1;i>=king.x;i--) {
						
						
						g2.fillRect(i*100, attackingPiece.drawY*100, 100, 100);
						
						
						
						
					}
					
				}else if(attackingPiece.drawY>king.drawY && attackingPiece.drawX>king.x) {
					
					
                        int whereX = attackingPiece.x;
		        	 
		        	 for(int i = attackingPiece.y-1;i>=king.y;i--) {
		        		
							g2.fillRect((whereX-1)*100, i*100, 100, 100);
							whereX--;
		        			 
		        		 }
		
				}else if(attackingPiece.drawY>king.drawY && attackingPiece.drawX<king.x) {
					
					
                    int whereX = attackingPiece.x;
	        	 
	        	 for(int i = attackingPiece.y-1;i>=king.y;i--) {
	        		 
						g2.fillRect((whereX+1)*100, i*100, 100, 100);
						whereX++;
	        			 
	        		 }
	
			}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX<king.x) {
				
				
                int whereX = attackingPiece.x;
        	 
        	 for(int i = attackingPiece.y+1;i<=king.y;i++) {
        		
					g2.fillRect((whereX+1)*100, i*100, 100, 100);
					whereX++;
        			 
        		 }

		}else if(attackingPiece.drawY<king.drawY && attackingPiece.drawX>king.x) {
			
			
            int whereX = attackingPiece.x;
    	 
    	 for(int i = attackingPiece.y+1;i<=king.y;i++) {
    	
				g2.fillRect((whereX-1)*100, i*100, 100, 100);
				whereX--;
    			 
    		 }
    	 
 		

	}
			
				
				animateDefeatedKing(g2);		
				
		}
		
	
		
	}
		
		
		
		
	}
	
	
	private void animateDefeatedKing(Graphics2D g2) {
		
		if(Game.getInstance().match.checkedKing==null)return;
		
		if(checkedKing==null)
			checkedKing = Game.getInstance().match.checkedKing;
		     
		
		 
		 
		
		
		
		int y = (int) (40 + this.radius*(Math.sin(angle*Math.PI/600)));
		int x = (int) (40+ this.radius*(Math.sin(angle*Math.PI/150)));
	   
		g2.setColor(Color.GREEN);
		g2.fillRect(checkedKing.drawX*board.feldSize+x,checkedKing.drawY*board.feldSize+y,15,15);
		
		
		
		if(animationTimer>=0) {
	   
		angle+=3;
		animationTimer = 0;
		} else {
			animationTimer++;
		}
		
	
			
			
		}
	
	
		
	}
	
	
	
		

		
	
	

			
	
		
	


