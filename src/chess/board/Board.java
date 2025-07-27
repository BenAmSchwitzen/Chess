package chess.board;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.LinkedList;

import chess.graphics.ReachableFeldDrawer;
import chess.main.Game;
import chess.piece.*;



public class Board{

	
	//________standard values_______________
	
	public int width;
	public int height;
	public int feldSize;
	private int anzahlFelder;
	
	private Color white = new Color(238, 238, 210);
	private Color black = new Color(118, 150, 86);
	
	private int selectTimer = 0;
	private int selectSize = 0;
	
//_______________________________________________
	

//____________storing the pieces on the board_________________________________
	
	
	public LinkedList<Piece> pieces;
	
	public Piece selectedPiece = null;  // The piece selected by Mouse
         
//_________________________________________________________________
	
	
//___________Rules the game______________________________
	

	
	public Checker checker = new Checker(this);
	
	
//___________________________________________
	
	
//_________Graphics______________________________________
	
	
	Path2D.Double p = new Path2D.Double();
	
	public ReachableFeldDrawer reachableFeldDrawer;
	
	
	public int perspectiveValue = 1; // Adapts the movement values of black and white peaces according to the direction of the board
	
	
//___________________________________________
	
	public Game game;
	
	
	public Board() {
		
		this.game = Game.getInstance();
	
		
		
		
		this.anzahlFelder = 8;
		
		this.width = 800;
		this.height = 800;
		
		this.feldSize = this.width/this.anzahlFelder;
		
		
		this.pieces = new LinkedList<>();
		
		
		this.reachableFeldDrawer = new ReachableFeldDrawer(this); // could be turned into an event
		
		
		
	}
	
	
	public void drawBoard(Graphics2D g2) {
		
		for(int i = 0;i<anzahlFelder;i++) {
			
			for(int j = 0;j<anzahlFelder;j++) {
				
				g2.setColor((i+j)%2 == 0 ?  white : black );
				
				g2.fillRect(j*feldSize,i*feldSize, feldSize,feldSize);
				
				beschrifteBoard(g2, i, j);
				
			}
			
		}
		
		
		
	}
	
	

	
	
   private void beschrifteBoard(Graphics2D g2,int i,int j) {
		
	   
	   
	   if(i == 7) {
		   
		   g2.setColor((i+j)%2 == 0 ?  Color.BLACK : white);
		   g2.drawString(Character.toString(getBoardLetter(j)),(j*feldSize)+(feldSize)-20, (i*feldSize)+(feldSize)-10);
		   
	   }
	   
	   if(j == 0) {
		   g2.setColor((i+j)%2 == 0 ?  white : Color.BLACK);
		   g2.drawString(Integer.toString(Math.abs(i-9)), (j*feldSize)+5, (i*feldSize)-feldSize+20);
		   
	   }
	   
      if(j == 0 && i == 7) {
		   
    	  g2.setColor(white);
		  g2.drawString("1",(j*feldSize)+5, (i*feldSize)+20);   
      }
	   
	}

   
   @SuppressWarnings("null")
public char getBoardLetter(int j) {
		

		     switch(j) {
		     
		     case 0 : return 'a'; 
		     case 1 : return 'b';
		     case 2 : return 'c';
		     case 3 : return 'd';
		     case 4 : return 'e';
		     case 5 : return 'f';
		     case 6 : return 'g';
		     case 7 : return 'h';  
		     
		     }
		 return (Character) null;
	}
   
	
	public void drawMovingPiece(Graphics2D g2) {
		
		if(selectedPiece!=null) {
		
			
			
			if(selectTimer<60) {
				
				selectTimer++;
				
				if(selectTimer%10 == 0)selectSize+=1;
				
				
			
			}
		
		
		
		
			
		g2.drawImage(selectedPiece.image, Game.getInstance().mouse.mouseX-feldSize/2, Game.getInstance().mouse.mouseY-feldSize/2, 100+selectSize, 100-10+10+selectSize, null);

		
		
		
		
		}else {
			
			selectTimer = 0;
			selectSize =0 ;
			
		}
		
		
		
		
	}
	
	
	public void drawPieces(Graphics2D g2) {
		
	
		if(Game.getInstance().smallPieces) {
			
			pieces.stream().filter(m-> m.drawPiece && m != selectedPiece && !m.standOut && m!=null).forEach(m -> g2.drawImage(m.image,( m.drawX*this.feldSize)+15,m.drawY*feldSize+14, this.feldSize-30,this.feldSize-30, null));

		}else {
			
			pieces.stream().filter(m-> m.drawPiece && m != selectedPiece && !m.standOut && m!=null).forEach(m -> g2.drawImage(m.image, m.drawX*this.feldSize,m.drawY*feldSize+5, this.feldSize,this.feldSize-10, null));

			
		}


		
	}
	
	public void drawGraphics(Graphics2D g2) {
		
		this.reachableFeldDrawer.draw(g2);
		this.reachableFeldDrawer.drawCurrentMovingSquare(g2);
		
		
		
	}
	
	
        	
		
	

	public void drawCheckMateFelder(Graphics2D g2) {
		
		this.reachableFeldDrawer.drawCheckMateFelder(g2);
		
	}
	

	  public Piece getPiece(int y,int x) {
		  
		  for(Piece piece : pieces) {
			  
			  if(piece.y == y && piece.x == x) {
				  
				  return piece;
				  
			  }
			  
		  }
		  
		  return null;
	  }
	
	
		  
		public void turnBoardAround(char turn) {
			
			if(!Game.getInstance().turnBoard || Game.getInstance().computer)return;
			
			
			perspectiveValue *=-1;

			for(Piece piece : pieces) {
				
				
				piece.drawY = 7-piece.drawY;
				piece.drawX = 7-piece.drawX;
				
			    piece.y = piece.drawY;
			    piece.x = piece.drawX;
			
			}
			
		

		
		}
		
		
	
		
		
		//Updates all possible moves for each piece
		
		public void updatePossibleMoves(char color) {
			
			
			
				pieces.stream().filter(m -> m.color == color).forEach(m -> {
					
					m.possibleMoves.clear();
					
					
                     for(int i = 0;i< 8;i++) {
						
						for(int j = 0;j< 8;j++) {
					
							
							if(checker.isMoveValid(m, i,j))
							    m.possibleMoves.add(new int[]{i,j});
					
						}

					}
						
                     
                     updateMovesWithRochade(m);

					
				});
					

					
					}
		
		 // Fügt gegebenfalls die Rochade-Züge noch extra hinzu, falls eben möglich
		private void updateMovesWithRochade(Piece piece) {
			
			
			if(piece instanceof König && piece.color == 'w' || (piece.color == 'b' && this.perspectiveValue == -1) ) {
           	 
           	 if(checker.checkRochade(piece, 7, 7)) 
  				   piece.possibleMoves.add(new int[] {7,7});

  			  if(checker.checkRochade(piece, 7, 0)) 
  				piece.possibleMoves.add(new int[] {7,0});
  			  
  			  
           	 }	else if(piece instanceof König && piece.color == 'b') {
           		 
           		 if(checker.checkRochade(piece, 0, 7)) 
    				   piece.possibleMoves.add(new int[] {0,7});

    			  if(checker.checkRochade(piece, 0, 0)) 
    				piece.possibleMoves.add(new int[] {0,0});
           		 
           		 
           		 
           		 
           	 }
			
			
		}
						
					
		             //Uses the new possibleMoves list in order to find out whether a square is possible to enter
 		public boolean canPieceGoToSpecificFeld(Piece piece,int y,int x) {
 			return piece.possibleMoves.stream().anyMatch(m -> y == m[0] && x == m[1]);
						
						
					}




}
		  
		 
	

