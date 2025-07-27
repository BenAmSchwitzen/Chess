package chess.util;

public class Vektor2D {

	
	public int startX,startY,endX,endY;
	public int y,x;
	public double doubleY,doubleX;
	
	
	public Vektor2D(int startX,int startY,int endX,int endY) {
		
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
	}
	
    public Vektor2D(int y,int x) {
		
		this.y = y;
		this.x = x;
		
	}
    
    public Vektor2D(double doubleY,double doubleX) {
		
		this.doubleY = doubleY;
		this.doubleX = doubleX;
		
	}
	
	public Vektor2D() {
		
		
		
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
