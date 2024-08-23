package chess.previousplay;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import chess.piece.Bauer;
import chess.piece.Dame;
import chess.piece.König;
import chess.piece.Läufer;
import chess.piece.Piece;
import chess.piece.Springer;
import chess.piece.Turm;
import chess.util.FontManager;

public class PreviousPlay {
	
	
	public Piece movingPiece;
	public Piece capturedPiece;
	
	public JButton button;
	public ArrayList<Piece> prevPieces;
	
	public int oldY,oldX;
	public int newY,newX;
	
	public boolean lastPlay = false;
	public boolean isRochade = false;
	public boolean isCheck = false;
	public boolean isPromoting = false;
	
	
	public int rochadeTX = 0;
	public int rochadeTY = 0;
	public int rochadeKX = 0;
	public int rochadeKY = 0;
	public boolean longRochade = false;
	
	public int number = 0;
	
	public PreviousPlay(Piece movingPiece,Piece capturedPiece) {
		
		this.movingPiece = movingPiece;
		this.capturedPiece = capturedPiece;
		
		this.oldY = movingPiece.y;
		this.oldX = movingPiece.x;
		
		this.newY = movingPiece.drawY;
		this.newX = movingPiece.drawX;
		
		
		prevPieces = new ArrayList<>();
		
		if(capturedPiece!=null && !isPromoting) {
			
			this.button = new JButton(getName(movingPiece, capturedPiece));
			
		}else {
			
			this.button = new JButton(getFeld(movingPiece, newY, newX));
		}
		
		
		this.button.setBackground(capturedPiece!=null ? Color.RED:Color.WHITE);
		this.button.setSize(50, 50);
		this.button.setFont(FontManager.getFont("Arial Bold",Font.BOLD,20));
		this.button.setBorder(null);
		this.button.setFocusable(false);
		this.button.setVisible(false);
		
		setPlayButtonIcon();
		
	}
	
	public void savePieces(LinkedList<Piece> pieces) {
		
		for(Piece piece : pieces) {
			
			
			switch(piece.getClass().getName()) {
			
			case "chess.piece.Dame" :  prevPieces.add(new Dame(piece.color, piece.y, piece.x));   break;
			
			case "chess.piece.Bauer" :  prevPieces.add(new Bauer(piece.color, piece.y, piece.x));   break;
			
			case "chess.piece.Turm" :  prevPieces.add(new Turm(piece.color, piece.y, piece.x));   break;
			
			case "chess.piece.Springer" :  prevPieces.add(new Springer(piece.color, piece.y, piece.x));   break;

			case "chess.piece.Läufer" :  prevPieces.add(new Läufer(piece.color, piece.y, piece.x));   break;

			case "chess.piece.König" :  prevPieces.add(new König(piece.color, piece.y, piece.x));   break;

			}
			
			
			
			
		}
		
	}
	
	private void setPlayButtonIcon()  {
		
		String finalPath = "";
		
		switch(movingPiece.name) {
		
		case BAUER : finalPath = "chess.res/playIcons/pawnPlay.png"; break;
		case DAME : finalPath = "chess.res/playIcons/damePlay.png"; break;
		case KING : finalPath = "chess.res/playIcons/königPlay.png"; break;
		case LÄUFER : finalPath = "chess.res/playIcons/bishopPlay.png"; break;
		case TURM : finalPath = "chess.res/playIcons/rookPlay.png"; break;
		case SPRINGER : finalPath = "chess.res/playIcons/springerPlay.png"; break; }
		
		this.button.setIcon(new ImageIcon(finalPath));
		
	}
	
	public String getName(Piece piece,Piece capturedPiece) {
		
	     String name = "";
	     
	     name += piece.name.toString().charAt(0);
		 
	     if(capturedPiece!=null) name+= "X"+capturedPiece.name.toString().charAt(0);
		
	
	     
	     
	     
	     return name;
		
		
	}
	
	
	public String getFeld(Piece piece,int y,int x) {
		
		char pieceLetter = piece.name.toString().charAt(0);
		
		char buchstabe = 0;
		
		     switch(x) {
		     
		     case 0 : buchstabe ='A'; break;
		     case 1 : buchstabe ='B'; break;
		     case 2 : buchstabe ='C'; break;
		     case 3 : buchstabe ='D'; break;
		     case 4 : buchstabe ='E'; break;
		     case 5 : buchstabe ='F'; break;
		     case 6 : buchstabe ='G'; break;
		     case 7 : buchstabe ='G'; break;  }
			

		
		return Character.toString(pieceLetter)+Character.toString(buchstabe).toUpperCase()+Integer.toString(y);
	}
	

}
