package chess.piece;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Piece {

 //__________main(standard) attributes_____________________
	
	public Category name;
	public int value;
	public char color;
	
//____________________________________________
	
	
	
//_____________game attributes__________________________
	
	public int y,x;
	public int drawY,drawX;
	public boolean hasMoved;
    public int moveCounter;
    public boolean hasDoubleMoved = false;
	
//_____________________________________________________
	
	
	//____________image of the piece____________________
	
	private String whiteImage;
	private String blackImage;
	public BufferedImage image;
	
	public boolean drawPiece = true;
	
	//___________________________________________________
	
	public Piece(char color,int y,int x) {
	
		PieceInfo pieceInfo =  getClass().getAnnotation(PieceInfo.class);
		
		this.name = pieceInfo.name();
		this.value = pieceInfo.value();
		this.color = color;
		
		this.whiteImage = pieceInfo.whiteImagePath();
		this.blackImage = pieceInfo.blackImagePath();
		
		try {this.image = ImageIO.read(getClass().getResourceAsStream(this.color == 'w' ? this.whiteImage:this.blackImage));
		} catch (IOException e) {e.printStackTrace();}
		
		
		this.y = y;
		this.x = x;
		
		
		
		this.drawY = y;
		this.drawX = x;
		
		this.hasMoved = false;
		this.moveCounter = 0;
		
		
	}
	
	public abstract boolean canMove(int y,int x);   //Kann sich diese Figur laut Regeln zu y und x bewgen
		
	

	
	
}
