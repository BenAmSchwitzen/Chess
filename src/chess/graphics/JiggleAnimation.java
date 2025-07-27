package chess.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import chess.piece.Piece;

public class JiggleAnimation {
	
	public Piece jigglePiece = null;
	private int size = 0;
	private int timer = 0;
	private int flagValue = 1;
	private int steps = 2;
	private int turns = 0;

	
	public void drawJiggleAnimation(Graphics2D g2) {
		if(this.jigglePiece==null) return;
		
		int x = this.jigglePiece.drawX*100;
		int y = this.jigglePiece.drawY*100;
		
		int finalY = (y+50) - ((100+this.size)/2);
		int finalX = (x+50) - ((100+this.size)/2);
		
		g2.setColor(Color.RED);
		g2.fillRect(x, y, 100, 100);
		
		g2.drawImage(this.jigglePiece.image,finalX,finalY,100+size, 100+size, null);
		
		
		
	}
	
	public void updateAnimation() {
		if(this.jigglePiece==null) return;
		
		this.jigglePiece.standOut = true;
		
		timer+=1;
		
		if(timer >= 3) {
			this.size+=1*this.flagValue; 
			timer = 0;
		}
		
		if(this.size >= 15 || (this.size == -5 && this.flagValue ==-1)) {
			if(turns == steps -1) {
			   this.endAnimation();
			}
			else {
				this.flagValue *= -1;
				this.timer = 15;
				this.turns +=1;
			}
			
				
		}	
		
	}
	
	public void setJigglePiece(Piece piece) {
		this.jigglePiece = piece;
		
		
		
	}
	
	public void endAnimation() {
		
		this.size = 0;
		this.timer = 0;
		this.flagValue = 1;
		this.turns = 0;
		if(this.jigglePiece!=null)
		this.jigglePiece.standOut = false;
		
		this.jigglePiece = null;
		
		
	}

}
