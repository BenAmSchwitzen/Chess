package chess.graphics;


import java.awt.Graphics2D;

import chess.piece.Piece;



public class MovementAnimation extends Animation{
	
	private Piece movingPiece = null;
	private double startY, startX, endY, endX = -99.0;
	private double currentY, currentX = -99.0;
	
	private double timeSinceLastStep = 0.0;
	private final double stepInterval = 0.1; 
	
	

	@Override
	public void drawAnimation(Graphics2D g2) {
		if(this.movingPiece!=null) {
			g2.drawImage(movingPiece.image, (int) this.currentX*100, (int) this.currentY*100, 100, 100, null);
			
		}
		
	}
	
	// Calculates the animation-steps
	public void update(double deltaTime) {
		
		if(this.movingPiece==null || !isAnimating ) return;
		
		   timeSinceLastStep += deltaTime;

		    // Wenn 0.2 Sekunden vergangen sind, mache einen Schritt
		    if (timeSinceLastStep >= stepInterval) {
		        // Reset Timer
		        timeSinceLastStep = 0.0;
		        
		     // Deine Animation
		        double dx = endX - startX;
		        double dy = endY - startY;
		        double distance = Math.sqrt(dx * dx + dy * dy);

		        

		        // Schrittgröße festlegen (z. B. 50px pro Schritt)
		        double step = 2;
		        double stepX = (dx / distance) * step;
		        double stepY = (dy / distance) * step;

		        currentX += stepX;
		        currentY += stepY;
		        
		        if ((stepX > 0 && currentX >= endX) || (stepX < 0 && currentX <= endX) ||
		                (stepY > 0 && currentY >= endY) || (stepY < 0 && currentY <= endY)) {
		                currentX = endX;
		                currentY = endY;
		                endAnimation();
		            }

		        
		        
		    }
		 
		
		
		    }
		
		
	
	// Called before the first animation-step
	public void prepareAnimation(Piece movingPiece, int startY, int startX, int endY, int endX) {
		this.movingPiece = movingPiece;
		this.startY = startY*100;
		this.startX = startX*100;
		this.endY = endY*100;
		this.endX = endX*100;
		this.currentX = startX;
        this.currentY = startY;
		this.movingPiece.standOut = true;
		
		this.isAnimating = true;
			
	}
	 
	// Called after the animation is completed
	public void endAnimation() {
		this.startY = -99.0;
		this.startX = -99.0;
		this.endY = -99.0;
		this.endX = -99.0;
		this.movingPiece.standOut = false;
		this.movingPiece = null;
		
		this.isAnimating = false;
		
		
	}
	


	
	
	

	

}
