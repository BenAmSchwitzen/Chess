package chess.board;




import java.util.List;
import chess.piece.*;
import chess.previousplay.PreviousPlay;


public class Checker {

	
	private Board board;
	
	public Piece rochadePiece = null;
	public Piece enPassantPiece = null;
	
    public Piece promotePiece = null;
    public boolean isPromoting = false;
	
	
	public int possibleMoves [][] = new int [63][2];
	public boolean hasBeenCheckedForMoves = false;
	
	public Checker(Board board) {
		
		this.board = board;
	
	}
	

	public void getAllPossibleMoves(Piece piece) {
	
		int count = 0;
		
		for(int i = 0;i<8;i++) {
			
			for(int j = 0;j<8;j++) {
				
				if(isMoveValid(piece, i, j)) {
					
					possibleMoves[count][0] = i;
					possibleMoves[count][1] = j;
							
					   count++;
					
				}else {
					possibleMoves[count][0] = -99;
					possibleMoves[count][1] = -99;
					
				}
				
			}
			
		}
	
		
	}
	
	public boolean isMoveInListe(int y,int x,Piece piece) {
		
		for(int i = 0;i<possibleMoves.length;i++) {
			
			if(possibleMoves[i][0] == y && possibleMoves[i][1] == x) {
				
				return true;
				
			}
			
		}
		
		
		
		return false;
		
	}
		  
	
	
	 /**
	 * checks if one <b>specific field</b> is available for a piece of a color
     */	
	  public boolean isFeldAvailable(Piece piece,int y,int x) {
	       
		 
		 
		  
	      	return ((this.board.getPiece(y, x)!=null && this.board.getPiece(y, x).color != piece.color && !(board.getPiece(y, x) instanceof König)) || board.getPiece(y, x)==null);
	      	 
                                                                  
	      }
	  
			 
          public boolean squareIsPossible(int y,int x) {
				  
				  return y<8 && x<8;
				  
			  }
			  
			  
			  /**
				 * checks if the <b>specific piece</b> can enter a <b>field y,x</b>
				 * without breaking the rules
			     */	
			  public boolean isMoveValid(Piece piece,int y,int x) { 
					
				if(piece==null)return false;
				  
				  return  (piece.canMove(y, x) || bauerAdditionalMovement(piece, y, x) || enPassant(piece, y, x))
							
                            && !this.board.checker.isBlocked(piece, y, x)
							
							&& board.checker.isFeldAvailable(piece, y, x)
							
							&& !this.isCheckAfterMove(piece, y, x)
							
							&& squareIsPossible(y, x);
							
							
							
							
						    
							
							
							
					
							
				}
			  
			  
			  public boolean isKingCheck(char color) {
				  
				  Piece king = getKing(color);
				  
				return  board.pieces.stream().anyMatch(p ->  p.color != king.color && (p.canMove(king.y,king.x) || bauerAdditionalMovement(p,king.y, king.x)) && !isBlocked(p,king.y,king.x));
				  
					
				  
				  
			  }
			  
			  /**
				 * checks if the <b>pawn</b> moved two squares.
				 * If so he gets marked.
			     */	
			  public void checkBauerDoubleMove(Piece piece,int y,int x) {
				  
				  if(!(piece instanceof Bauer)) {
					  
					  return;
					  
				  }
				  
				  if(Math.abs(piece.y-y) == 2) {
					  
					  piece.hasDoubleMoved = true;
					  
				  }
				  
			  }
			  
			  /**
				 * checks if the <b>specific piece</b> can enter a <b>field y,x</b>
				 * without causing being checked
			     */	
			  public boolean isCheckAfterMove(Piece piece,int y,int x) {
				  

				  
				  Piece king = this.getKing(piece.color);
                
				  int oldPieceY = piece.y;
				  int oldPieceX = piece.x;
				  
				  Piece cP = board.getPiece(y,x);
				  
				  piece.y = y;
				  piece.x = x;
				  
				
				  
				  if(cP!=null)
				  cP.considerPiece = false;
				  
				  
				  
				  for(Piece p : board.pieces) {
					  
					  if(p.color!=king.color) {
					  
					  if(!isBlocked(p, king.y, king.x) && (p.canMove(king.y, king.x) || bauerAdditionalMovement(p,king.y,king.x)) && p.considerPiece) {

						  if(cP!=null)
						  cP.considerPiece = true;
						  piece.y = oldPieceY;
						  piece.x = oldPieceX;
						 return true;
						  
					  }
					  
					  }
					  
				  }
				  

				  
				  piece.y = oldPieceY;
				  piece.x = oldPieceX;
				  if(cP!=null)
				  cP.considerPiece = true;
				  
				  return false;
			  }
			  
		  
			 
			
			  
			  /**
				 * checks if the king can move without being in check
			     */	
			  public boolean canKingMove(Piece king)  {
				  
				  
				  if(!(king instanceof König)) {
					  
					  return true;
				  }
				  
				  if(
						    
					   isMoveValid(king, king.y, king.x-1)
						  
				    || isMoveValid(king, king.y-1, king.x-1)
				    
				    || isMoveValid(king, king.y-1, king.x)
				    
				    || isMoveValid(king, king.y-1, king.x+1)
				    
				    || isMoveValid(king, king.y, king.x+1)
				    
				    || isMoveValid(king, king.y+1, king.x+1)
				    
				    || isMoveValid(king, king.y+1, king.x)
				    
				    || isMoveValid(king, king.y+1, king.x-1)
				    
						  ) {
					  
					  return true;
					  
				  }
				
				  
				  return false;
			  }
			  
			  
			  
			  /**
			   * a pawn will be replaced with a queen when being on the last square 
			   */
			  public void swapBauerToQueen(Piece piece) {
				  
				  

				  if(piece instanceof Bauer) {
					  
					  if((piece.color == 'w' && piece.y == 0) || (piece.color == 'b' && piece.y == 7)) {
						  
						  board.pieces.remove(piece);
						  
						  Piece newDame = new Dame(piece.color, piece.color == 'w' ? 0:7, piece.x);
						  board.pieces.add(newDame);
						  board.selectedPiece = newDame;
						  
						  piece = null;
						
						
						 
						
						 
						
						  
						  
						  
						
						 
						  
					  }
					  
				  } }
			  
			  
			  public boolean isBauerPromoting(Piece piece) {
				  

				  if(piece instanceof Bauer) {
					  
					  if(((piece.color == 'w' && piece.y == 0)  || (board.perspectiveValue == 1 && piece.color == 'w' && piece.y == 7)) || ((piece.color == 'b' && piece.y == 7)) || (board.perspectiveValue == -1 && piece.color == 'b' && piece.y == 0)) {
				    
						  return true;
				  
					  }}
				  
				  return false;
				  
				  }
				  
			  /**
			   * 
			   *  checks for an enPassant event
			   */
			  
			  public boolean enPassant(Piece piece,int y,int x) {
				  
				  
				  
				  // links neben bauer ein bauer,der im letzten Zug zwei Felder gemacht hat
				  
				  if(board.getPiece(y, x)!=null && board.getPiece(y, x) instanceof Bauer && piece instanceof Bauer && board.getPiece(y, x).hasDoubleMoved == true  && board.getPiece(y, x).y == piece.y && Math.abs(piece.x-x) == 1) {
					  
					 
					  return true;
					  
				  }
				  
				  
				  return false;
				  
			  }
			  
			  public void finalEnPassant(Piece piece,int y,int x) {
				  
				  if(enPassantPiece==null)return;
				  
				  if( piece.color == 'w') {
					  
					
					  
					  piece.y = y-1*board.perspectiveValue;
					  piece.x = x;
					  board.selectedPiece.drawY = piece.y;
					  board.selectedPiece.drawX = piece.x;

				  
				  }else {
					  
					  
						  piece.y = y+1*board.perspectiveValue;
						  
					 
					  
					  
					  piece.x = x;
					  board.selectedPiece.drawY = piece.y;
					  board.selectedPiece.drawX = piece.x;
					  
				  }
				  
			  }
			  
			  public void getEnPassantPiece(Piece piece,int y,int x) {
				  
				  if(enPassant(piece,y, x)) {
					  
					  this.enPassantPiece = board.getPiece(y, x);
					 
					  
				  }else {
					  this.enPassantPiece = null;
				  }
				  
			  }
			  
			
		
			  
			  @Deprecated
			  public void doRochadeFix(Piece piece) {
				  
				  if(rochadePiece!=null) {
					  System.out.println("d");
					  if(piece.x>rochadePiece.x) {
						  
						  rochadePiece.drawX += 3;
						  rochadePiece.x += 3;
						 
						  piece.x = piece.x-2;
						  piece.drawX = piece.x;
						  System.out.println("da");
						 
						  return;
					  }
					  
                         if(piece.x<rochadePiece.x) {
						  
                        	
                        		 
                        		rochadePiece.drawX -= 2;
       						  rochadePiece.x -= 2;
       						  
       						  piece.x += 2;
       						  piece.drawX = piece.x;
                        		 
                        
                        	 
						 
						  
						  
						  return;
					  }
					  
				  }
				  
			  }
			  
			  
			  public void doRochade(Piece piece,PreviousPlay play) {
				  
				  if(rochadePiece!=null) {
					 if(piece.color == 'w') {
						 
						 whiteRochade(piece,play);
					 }else {
						 
						 blackRochade(piece,play);
 
					 }
	
					 play.rochadeTY = rochadePiece.drawY;
					 play.rochadeTX = rochadePiece.drawX;
					 play.rochadeKX = piece.drawX;
					 play.rochadeKY = piece.drawY;
					 
					 
				  }
				  
				  
			  }
			  
			  
			  private void blackRochade(Piece piece,PreviousPlay play) {
				
				  
				 if(board.perspectiveValue == 1) {
					 
					  if(piece.x< rochadePiece.x) {
						  piece.drawX = 6;
						  rochadePiece.drawX = 5;
						  piece.x = 6;
						  rochadePiece.x = 5;
						  
						  play.longRochade = false;
						  
					  }else {
						  
						  piece.drawX = 2;
						  rochadePiece.drawX = 3;
						  piece.x = 2;
						  rochadePiece.x = 3;
						  
						  play.longRochade = true;
						  
						  
					  }
 
					  
				  }else if(board.perspectiveValue == -1) {
					  if(piece.x< rochadePiece.x) {
						  piece.drawX = 5;
						  rochadePiece.drawX = 4;
						  piece.x = 5;
						  rochadePiece.x = 4;
						  
						  play.longRochade = true;
						  
					  }else {
						  
						  
						  
						  
						  piece.drawX = 1;
						  rochadePiece.drawX = 2;
						  piece.x = 1;
						  rochadePiece.x = 2;
						  
						  play.longRochade = false;
						  
						  
					  }
					  
				  }
				  
				  
			  }
			  
              private void whiteRochade(Piece piece,PreviousPlay play) {
				  
            	  if(piece.x< rochadePiece.x) {
					  piece.drawX = 6;
					  rochadePiece.drawX = 5;
					  piece.x =6;
					  rochadePiece.x = 5;
					  
					  play.longRochade = false;
					  
					  
					  
				  }else {
					  
					  piece.drawX = 2;
					  rochadePiece.drawX = 3;
					  piece.x = 2;
					  rochadePiece.x = 3;
					  
					  play.longRochade = true;
					  
				  }
			  }
			  
			  
			  @Deprecated
			  public boolean drawRochade(Piece piece,int y,int x) {
				  
				  if(board.getPiece(y, x)==null || piece==null)return false;
				      if(!(board.getPiece(y, x) instanceof Turm) || !(piece instanceof König))return false;
				  
				      Turm turm = (Turm) board.getPiece(y, x);
				      
				      if(turm.color != piece.color || piece.hasMoved || turm.hasMoved)return false;
				      
				      if(isFeldAttackedByEnemy(piece.y, piece.x, piece.color) || isFeldAttackedByEnemy(turm.y, turm.x, turm.color) || piece.y !=turm.y)return false;
				      
				      
				       // Ab hier folgen die Checks bezüglich der Zwischenräume
				      
				      // rechts
				      if(piece.x<turm.x) {
				      for(int i = piece.x+1;i<turm.x;i++) {
				    	  
				    	  if(isFeldAttackedByEnemy(y, i, piece.color)|| board.getPiece(piece.y, i)!=null)return false;
				    	  
				      }
				      
				      }else if(piece.x>turm.x) {
				      // links
                          for(int i = piece.x-1;i>turm.x;i--) {
				    	  
				    	  if(isFeldAttackedByEnemy(y, i, piece.color) || board.getPiece(piece.y, i)!=null)return false;
				    	  
				      }
				      }

				  this.rochadePiece = turm;   
				  return true;
			  }
			  
			  
			  public boolean checkRochade(Piece piece,int y,int x) {
				  
				  this.rochadePiece = null;
				  
				  if(piece instanceof König && board.getPiece(y, x)!=null && board.getPiece(y, x).color == piece.color && board.getPiece(y, x) instanceof Turm) {
					  
					  if(!piece.hasMoved && !board.getPiece(y, x).hasMoved) {
						  
						  if(!isAttacked((König) piece)  && !isAttacked(board.getPiece(y, x))) {
						  
							
							  
						  if(piece.x<x) {
								
								for(int i = piece.x+1;i<x;i++) {
									if(board.checker.isFeldAttackedByEnemy(piece.y, i, piece.color) || board.getPiece(piece.y, i)!=null) {
										return false;
										
									}
									
								}
								
							}else if(piece.x>x) {
								
								for(int i = piece.x-1;i>x;i--) {
									if(board.checker.isFeldAttackedByEnemy(piece.y, i, piece.color)  || board.getPiece(piece.y, i)!=null) {
										return false;
										
									}
									
								}
								
							}
						  
						  
							 
						  
						  
							if(board.getPiece(y, x).x == piece.drawX &&  board.getPiece(y, x).y == piece.drawY)
							this.rochadePiece = board.getPiece(y, x);
							
							return true;
							
						}
					  }
				  }
					
		
				
				 return false;
				  
				  }
			  
			 public boolean isAttacked(Piece king) {
				 
				 if(king==null)return false;
				 
				 for(Piece piece : board.pieces) {
					 
					 if(piece.color != king.color && !isBlocked(piece, king.y, king.x) && piece.canMove(king.y, king.x)) {
						 return true;
					 }
					 
				 }
				 
				 return false;
			 }
			 
			
			
		
			 
	
			 public boolean staleMate(char color) {
				 
				 return cantDoAnyMoveAnymore(color) && !isAttacked(getKing(color));
				 
			 }
		
		public boolean staleMateReal() {
			
			// King not attacked and cantdoany moveanymore
			
			// board.pieces.size == 2
			
			if(board.pieces.size() == 2) {
				
				return true;
				
			}
			
			Piece wK = board.checker.getKing('w');
			Piece bK = board.checker.getKing('b');
			
			if(!board.checker.isFeldAttackedByEnemy(wK.y, wK.x, wK.color)) {
				
				if(cantDoAnyMoveAnymore(wK.color)) {
					
					return true;
					
				}
				
			}
			
			else  if(!board.checker.isFeldAttackedByEnemy(bK.y, bK.x, bK.color)) {
				
				if(cantDoAnyMoveAnymore(bK.color)) {
					
					return true;
					
				}
				
			}
			
			
			
			return false;
		}
		
		 public boolean cantDoAnyMoveAnymore(char color) {
			 
			 for(int i = 0;i<8;i++) {
				 
				 for(int j = 0;j<8;j++) {
					 
					 for(Piece piece : board.pieces) {
						 
						 if(piece.color == color && board.checker.isMoveValid(piece, i, j)) {
							 
							 return false;
							 
						 }
						 
					 }
					 
				 }
				 
			 }
	 		 
			 return true;
		 }
			  
		
		public boolean isFeldAttackedByEnemy(int y,int x,char color) {
			
			return board.pieces.stream().filter(m -> m.color != color).anyMatch(m -> board.checker.isMoveValid(m, y, x));
			
		}
			  
		 public boolean isCheckMate(char color) {
			 
			 return cantDoAnyMoveAnymore(color) && isAttacked(getKing(color));
		 }
		
			
			  
			  
				public Piece getAttackingPiece(Piece king) {
					
					
					for(Piece piece : board.pieces) {
						
						if(piece.color != king.color && (piece.canMove(king.y, king.x)||board.checker.bauerAdditionalMovement(piece, king.y, king.x))
								
								&& !board.checker.isBlocked(piece, king.y, king.x)
								
								) {
							
							return piece;
						}
						
					}
					
					return null;
					
				}
			
			
		
		 
	
			public boolean canTheAttackingPieceBeTaken(Piece attackingPiece) {
				
				
				List<Piece> kingPieces = board.pieces.stream().filter(m -> m.color !=attackingPiece.color).toList();
				
				
				
				for(Piece piece : kingPieces) {
					
					if(piece.canMove(attackingPiece.y, attackingPiece.x)||board.checker.bauerAdditionalMovement(piece, attackingPiece.y, attackingPiece.x)
	                       && !board.checker.isBlocked(piece, attackingPiece.y,attackingPiece.x) && !isCheckAfterMove(piece, attackingPiece.y,attackingPiece.x)) {
						
						
						return true;
					}
					
				}
				
				
				return false;
			}
			
			
	
			public boolean canPieceAttackAttackingPiece(Piece piece,Piece attackingPiece,int y,int x) {
				
				
				return y == attackingPiece.y && x == attackingPiece.x;
				
			}
			
	  /**
		 * checks if the selected piece is blocked while moving to y,x
	     */	
	 public boolean isBlocked(Piece piece,int y,int x) {
		  
		  if(piece instanceof Turm) {
			  
			  return vertAndHorBlocked(piece, y, x);
			  
		  }else if(piece instanceof Dame) {
			  
			  return diagonalBlocked(piece, y, x) || vertAndHorBlocked(piece, y, x);
			  
		  }else if(piece instanceof Läufer) {
			  
			  return diagonalBlocked(piece, y, x);
			  
		  }else if(piece instanceof Bauer) {
			  
			  return  isBauerBlocked(piece, y, x);
			
		  }else if(piece instanceof König) {
			  
			  return vertAndHorBlocked(piece, y, x);
			  
		  }
		  
		  return false;
		  
	  }
	 
	 
	 
	 public boolean vertAndHorBlocked(Piece piece,int y,int x) {
		 
		 
		  if(piece.x> x && Math.abs(piece.y-y)== 0) {
			  
			  for(int j = piece.x-1;j>x;j--) {
				  
				  
				  if(board.getPiece(y, j)!=null) {
					  
					  return true;
					  
				  }
				  
				  
			  }
            // left to right
		  } else if(piece.x< x && Math.abs(piece.y-y)== 0) {
			  
         for(int j = piece.x+1;j<x;j++) {
				  
				  
				  if(board.getPiece(y, j)!=null) {
					  
					  return true;
					  
				  }
		  }
         // top to down => 0 - 10
		  
		  }else if(piece.y< y && Math.abs(piece.x-x)== 0) {
			  
	           for(int i = piece.y+1;i<y;i++) {
					  
					  
					  if(board.getPiece(i, x)!=null) {
						  
						  return true;
						  
					  }
			  }
	           
	           // down to top => 10 - 0
	           
		  }else if(piece.y> y && Math.abs(piece.x-x)== 0) {
			  
	           for(int i = piece.y-1;i>y;i--) {
					  
					  
					  if(board.getPiece(i, x)!=null) {
						  
						  return true;
						  
					  }
			  }
	           
		  }
		  
		 
		 
		 
		 return false;
	 }
	 
	 public boolean diagonalBlocked(Piece piece,int y,int x) {
		 
		   if(piece.y>y && piece.x<x) {
				 
		    	 int whereX = piece.x;
		    	 
		    	 for(int i = piece.y-1;i>y;i--) {
		    		 
		    		 if(board.getPiece(i,whereX+1)!=null) {
		    			 
		    			 return true;
		    			 
		    		 }
		    		 
		    		 whereX++;
		    		 
		    		
		    		 
		    	 }
		    	 // right bottom to top left
		    		 
		    	 }else if(piece.y>y && piece.x>x)  {
		    		 
		    		 
		    		 int whereX = piece.x;
		        	 
		        	 for(int i = piece.y-1;i>y;i--) {
		        		 
		        		 if(board.getPiece(i,whereX-1)!=null) {
		        			 
		        			 return true;
		        			 
		        		 }
		        		 
		        		 whereX--;
		        		 
		        		
		        		 
		        	 }
		        	 
		        	 //Top left to bottom right
		    		 
		    	 }else if(piece.y<y && piece.x<x) {
		        		 
		        		 int whereX = piece.x;
		        		 
		        		 for(int i = piece.y+1;i<y;i++) {
		        			 
		        			 if(board.getPiece(i, whereX+1)!=null) {
		        				 
		        				 
		        				 return true;
		        				 
		        			 }
		        			 whereX++;
		            		 
		        		 }
		        		 //Top right to bottom left
		    	 }else if(piece.y<y && piece.x>x) {
		    		 
		    		 int whereX = piece.x;
		    		 
		    		 for(int i = piece.y+1;i<y;i++) {
		    			 
		    			 if(board.getPiece(i,whereX-1)!=null) {
		    				 
		    				 
		    				 return true;
		    				 
		    			 }
		    			 whereX--;
		        		 
		    		 }
		 
		    	 }
	
		  return false;
		  
	  }
	 
	 public static int [][] getPossibleFields(Piece piece) {
		 
		 int [][] moves = new int[countPossibleFields(piece)][2];
		 int counter = 0;
		 
            for(int i = 0;i<8;i++) {
			 
			 
			 for(int j = 0;j<8;j++) {
				 
				 if (piece.canMove(i, j)) {
					 
					 moves[counter][0] = i;
					 moves[counter][1] = j;
					 counter++;
					 
				 }
				 
			 }
			 
		 }
		 
		 
		 return moves;
	 }
	 
	 public boolean mouseOutOfFeld(int mouseY,int mouseX) {
		 
		 if(mouseY > 0 && mouseY <800 && mouseX> 0  && mouseX <800) {
			 
			 return false;
			 
		 }else {
			 return true;
		 }
		 
		 
	 }
	 
	 public static int countPossibleFields(Piece piece) {
		
		 int counter = 0;
		 
		 for(int i = 0;i<8;i++) {
			 
			 
			 for(int j = 0;j<8;j++) {
				 
				 if(piece.canMove(i, j) && (i != piece.y || j != piece.x)) {
					 
					 
					 counter++;
					 
				 }
				 
			 }
			 
			 
		 }
		 
		 return counter;
		 
	 }

	 

	 
	 
	
	 public boolean isBauerBlocked(Piece piece,int y,int x) {
		 
		 

			 int diffY = 0;
			 int diffX = 0;
		
			 if(piece.color == 'w') {
			
				 diffY =  piece.y-y;
				 diffX =  piece.x-x;
				 
				 if(board.getPiece(piece.y-1*board.perspectiveValue,piece.x)!=null && diffY == 1*board.perspectiveValue && diffX ==  0) {
					 
					 return true;
					 
				 }
				 
			 }
			 
			 if(piece.color == 'b') {
				 
				 diffY =  piece.y-y;
				 diffX =  piece.x-x;
				 
				 if(board.getPiece(piece.y+1*board.perspectiveValue, piece.x)!=null && diffY == -1*board.perspectiveValue && diffX ==  0) {
					 
					 return true;
					 
				 }
				 
				 
			 }

		 
		 return false;
		 
	 }
	 
	 
	 public boolean bauerAdditionalMovement(Piece piece,int y,int x) {
		 
		 if(!(piece instanceof Bauer)) {
			 
			 return  false;
			 
		 }
		 
		 return piece.color == 'w'?bauerAdditionalMovementWhite(piece, y, x):bauerAdditionalMovementBlack(piece, y, x);

	 }
	 
    public boolean bauerAdditionalMovementWhite(Piece piece,int y,int x) {
		 
		
		 if((board.getPiece(piece.y-1*board.perspectiveValue, piece.x-1)!=null && piece.y-y == 1*board.perspectiveValue && piece.x-x == 1)
				 || (board.getPiece(piece.y-1*board.perspectiveValue, piece.x+1)!=null && piece.y-y == 1*board.perspectiveValue && piece.x-x == -1)
				 ) {
			 return true;
		 }
		 
		 if((board.getPiece(piece.y-2*board.perspectiveValue, piece.x)== null && piece.y-y == 2*board.perspectiveValue && piece.x-x == 0) &&
				 (board.getPiece(piece.y-1*board.perspectiveValue, piece.x)== null && !piece.hasMoved)
				 
				 ) {
			 
			 return true;
			 
		 }
		 
		 
		 return false;
		 
	 }
 
 
 
   public boolean bauerAdditionalMovementBlack(Piece piece,int y,int x) {
	 
	   
	   
	   
	 
	   if((board.getPiece(piece.y+1*board.perspectiveValue, piece.x-1)!=null && piece.y-y == -1*board.perspectiveValue && piece.x-x == 1)
				 || (board.getPiece(piece.y+1*board.perspectiveValue, piece.x+1)!=null && piece.y-y == -1*board.perspectiveValue && piece.x-x == -1)
				 ) {
			 return true;
		 }
		 
	   
		 if((board.getPiece(piece.y+2*board.perspectiveValue, piece.x)== null && piece.y-y == -2*board.perspectiveValue && piece.x-x == 0) &&
				 (board.getPiece(piece.y+1*board.perspectiveValue, piece.x)== null && !piece.hasMoved)
				 
				 ) {
			 
			 return true;
			 
		 }
	   
	    
		 
		 return false;
		 
	 }
	 
 
	 
	 
	 public boolean isPieceOnStraightLine(Piece piece,int y,int x) {
		
		 if(piece.x < x && piece.y == y) {
			 
			 for(int i = piece.x+1;i<x;i++) {if(board.getPiece(y,i )!=null) {return true;}}}
		 
		 if(piece.x> x && piece.y == y) {
			 
			 for(int i = piece.x-1;i>x;i--) {if(board.getPiece(y,i )!=null) {return true;}}}
			 
         if(piece.x== x && piece.y > y) {
			 
			 for(int i = piece.y-1;i>y;i--) {if(board.getPiece(i,x )!=null) {return true;}}}
         
     if(piece.x== x && piece.y < y) {
			 
			 for(int i = piece.y+1;i<y;i++) {if(board.getPiece(i,x )!=null) {return true;}}}
		 
		 return false;
	 }



     public Piece getKing(char color) {
  	   
  	   for(Piece piece : board.pieces) {
  		   
  		   if(piece.color == color && piece instanceof König ) {
  			   
  			   return piece;
  		   }
  		   
  	   }
  	   return null;
     }
	
 
	
	
	
	
	

	

    
       
     
}
