package chess.util;

import chess.main.Game;

public class Timer {

	public int counter = 0;
	
	public int sec = 0;
	public int min = 10;
	
	public String timerString = "10:00";
	
	public Timer() {
		
	}
	
	
	public void updateTime() {
		
		
		if(counter>=Game.getInstance().updateSpeed) {
			
			sec--;
			
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
	
	
	
}
