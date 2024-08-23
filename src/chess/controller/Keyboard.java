package chess.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	
	 // links rechts taste für Züge zurückspulen
	
	public char currentKey = '-';
	public int  currentKeyNumber =- 1;
	
	
	public Keyboard() {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		
		currentKey = e.getKeyChar();
		currentKeyNumber = e.getKeyCode();
		
		
		System.out.println(e.getKeyChar());
		System.out.println(e.getKeyCode());
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		currentKey = '-';
		currentKeyNumber = -1;
		
		
	}
	
}
