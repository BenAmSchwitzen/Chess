package chess.piece;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public boolean considerPiece = true;
	
	//___________________________________________________
	
	
	//________ new ( Jedes Piece speichert nun seine möglichen Züge(Zielfelder),damit diese nicht die ganze Zeit neu berechnet werden müssen )
	
	public List<int []> possibleMoves;
	
	//______________
	
	public Piece(char color,int y,int x) {
	
		PieceInfo pieceInfo =  getClass().getAnnotation(PieceInfo.class);
		
		this.name = pieceInfo.name();
		this.value = pieceInfo.value();
		this.color = color;
		
		this.whiteImage = pieceInfo.whiteImagePath();
		this.blackImage = pieceInfo.blackImagePath();
		
		try {this.image = ImageIO.read(getClass().getResourceAsStream(this.color == 'w' ? this.whiteImage:this.blackImage));
		} catch (IOException e) {e.printStackTrace();}
		
		this.scaleImage(image);
		
		this.y = y;
		this.x = x;
		
		
		
		this.drawY = y;
		this.drawX = x;
		
		this.hasMoved = false;
		this.moveCounter = 0;
		
		this.possibleMoves = new ArrayList<>();
		
	}
	
	public abstract boolean canMove(int y,int x);
		
	private void scaleImage(BufferedImage image) {
		
		BufferedImage scaledImage = new BufferedImage(100, 100, image.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(image,0,0,100,100, null);
		image = scaledImage;
		
	}

	public abstract Piece pCopy();
		
		
	
	
	
}
