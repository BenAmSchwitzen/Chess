package chess.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mouse extends MouseAdapter {

	public boolean pressed = false;
	public boolean clicked = false;
	
	public int mouseY = 0;
	public int mouseX = 0;
	
	@Override
	public void mousePressed(MouseEvent e) {
		
		this.pressed = true;
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		this.clicked = true;
		
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
		
		this.pressed = false;
		this.clicked = false;
		
		
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
		this.mouseY = e.getY();
		this.mouseX = e.getX();
		
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		
		this.mouseY = e.getY();
		this.mouseX = e.getX();
		
	}
	
	

	
}
