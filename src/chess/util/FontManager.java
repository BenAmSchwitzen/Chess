package chess.util;

import java.awt.Font;

public class FontManager {

	
	public FontManager() {
		
	}
	
	public static Font getFont(String name,int style,int size) {
		
		return new Font("Arial Bold", style, size);
		
	}
	
}
