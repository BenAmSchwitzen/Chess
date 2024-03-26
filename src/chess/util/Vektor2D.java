package chess.util;

public class Vektor2D {

	
	public int startX,startY,endX,endY;
	
	
	public Vektor2D(int startX,int startY,int endX,int endY) {
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
	}


	public int getStartX() {
		return startX;
	}


	public void setStartX(int startX) {
		this.startX = startX;
	}


	public int getStartY() {
		return startY;
	}


	public void setStartY(int startY) {
		this.startY = startY;
	}


	public int getEndX() {
		return endX;
	}


	public void setEndX(int endX) {
		this.endX = endX;
	}


	public int getEndY() {
		return endY;
	}


	public void setEndY(int endY) {
		this.endY = endY;
	}
	
	
}
