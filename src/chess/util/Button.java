package chess.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

@SuppressWarnings("serial")
public class Button extends JButton {

	

	public String text;
	public int width;
	public int height;
	public int y,x;
	
	
	public Color backColor;
	public Color fontColor;
	
	public Font mainFont = new Font("MonoLisa", Font.ITALIC, 20);
	
	public Button(String text,int y,int x,int height,int width,Color backColor,Color fontColor) {
		
		this.text = text;
		this.width = width;
		this.height = height;
		
		this.y = y;
		this.x = x;
		
		this.backColor = backColor;
		this.fontColor = fontColor;
		
		this.setText(text);
		
		this.setBounds(x, y, width, height);
		this.setBackground(backColor);
		this.setForeground(fontColor);
		this.setFocusable(false);
		this.setBorder(null);
		
		
		this.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				setBackground(Color.lightGray);
			
				repaint();
			
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
				setBackground(backColor);
				repaint();
			}	
			
			
			@Override
			public void mouseReleased(MouseEvent e) {
			
				
				setBackground(backColor);
				
			}
			
		});
		
	  
		
	}
	
	public Button() {
		
		this.setBackground(null);
		
		this.setFocusable(false);
		this.setBorder(null);
		
	}

	
	
	
}
