package chess.util;

import java.awt.BasicStroke;
import java.awt.Graphics2D;


import chess.main.Game;

public class Timer {

	public int counter = 0;
	private int animationCounter = 0;
	public int sec = 0;
	public int min = 10;
	
	public String timerString = "10:00";
	
	public Timer() {
		
	}
	
	
	public void updateTime() {
		
		
		if(counter>=Game.getInstance().updateSpeed) {
			
			sec--;
			animationCounter++;
		
			
			if(this.animationCounter>3) 
				animationCounter = 0;
				
				
			
			
			
			if(sec<0) {
				
				this.min--;
				this.sec = 59;
				
			}
			
			this.counter = 0;

		}
		
		
		
		if(this.sec>9) {
		
		this.timerString = "0"+min+":"+sec;
		
		}else {
			
			this.timerString = "0"+min+":0"+sec;
		}
		
		this.counter++;
	
		if(min>=10)
		this.timerString = min+":0"+sec;
		
	}
	
	public void drawTimeAnimation(Graphics2D g2,int startX,int startY,int width,int height) {
		
	 switch(animationCounter) {
	 
	 case 0 : width = 0; height = -25;break;
	 case 1 : width = 25; height = 0;break;
	 case 2 : width = 0; height = 25;break;
	 case 3 : width = -25; height = 0;break;
	 }
	 
	 g2.setStroke(new BasicStroke(4));
	 g2.drawLine(startX, startY, startX+width, startY+height);
		
	}
	
	
}
