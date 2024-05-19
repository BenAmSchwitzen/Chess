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
	
	
	
//___________________________________________
	
	public Game game;
	
	
	public Board() {
		
		this.game = Game.getInstance();
	
		
		
		
		this.anzahlFelder = 8;
		
		this.width = 800;
		this.height = 800;
		
		this.feldSize = this.width/this.anzahlFelder;
		
		
		this.pieces = new LinkedList<>();
		
		
		this.reachableFeldDrawer = new ReachableFeldDrawer(this); // Event maybe
		
		
		
	}
	
	
	public void drawBoard(Graphics2D g2) {
		
		for(int i = 0;i<anzahlFelder;i++) {
			
			for(int j = 0;j<anzahlFelder;j++) {
				
				g2.setColor((i+j)%2 == 0 ?  white : black );
				
				g2.fillRect(j*feldSize,i*feldSize, feldSize,feldSize);
				
				
			}
			
		}
		
		
		
	}
	
	public void drawMovingPiece(Graphics2D g2) {
		
		if(selectedPiece!=null) {
			
		
			
		g2.drawImage(selectedPiece.image, Game.getInstance().mouse.mouseX-feldSize/2, Game.getInstance().mouse.mouseY-feldSize/2, 100+10, 100-10+10, null);

		
		
		
		
		}
		
		
		
		
	}

	public void drawPieces(Graphics2D g2) {
		
		
		pieces.stream().filter(m-> m.drawPiece && m != selectedPiece).forEach(m -> g2.drawImage(m.image, m.drawX*this.feldSize,m.drawY*feldSize+5, this.feldSize,this.feldSize-10, null));
		

		
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
	
	
		  
		
		  
		  
	  
	
}
