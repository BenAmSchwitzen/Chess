package chess.main;


import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {

	private String iconPath ="chess.res/icons/chessIcon.png";
	
	public Window() {
		
		this.setTitle("Chess");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(Game.getInstance());
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setIconImage(new ImageIcon(iconPath).getImage());
		
		
		
		
		
		
		this.setVisible(true);
		
		
	}
	
}
