package chess.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.Bauer;
import chess.piece.Piece;
import chess.sound.SoundManager;
import chess.util.Button;

public class MenuScreen {

	 private Game game;
	 
	 public Button b1;
	 public Button b2;
	 public Button b3;
	 public Button b4;
	 
	 ArrayList<Piece> pieces;
	 
	
	 private int updateCounter = 0;
	 
	 private String playIconPath ="chess.res/icons/playIcon.png";
	 private String puzzleIconPath ="chess.res/icons/puzzleIcon.png";
	 private String quitIconPath ="chess.res/icons/quitIcon.png";
	 private String settingsIconPath ="chess.res/icons/settingsIcon.png";
	 
	
	 private BufferedImage logo;
	 
	 private ArrayList<Button> buttons;
	
	public MenuScreen(Game game) {
		
		 this.game = game;
		
		this.buttons = new ArrayList<>();
		
		  pieces = new ArrayList<>();
		  addMenuPieces();

	      instantiateButtons();
	      
	     try {
			this.logo = ImageIO.read(getClass().getResourceAsStream("/icons/ChessLogo.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	

	
	private void instantiateButtons() {
		
		b1 = new Button("  Play", 150, 900, 50, 135, Color.WHITE, Color.BLACK);
		b1.setIcon(new ImageIcon(playIconPath));
		
	

		
		 b2 = new Button(" Puzzles", 150, 1080, 50, 170, Color.WHITE, Color.BLACK);
		b2.setIcon(new ImageIcon(puzzleIconPath));
		
		 b4 = new Button("  Settings", 250, 880, 50, 135, Color.WHITE, Color.BLACK);
		b4.setIcon(new ImageIcon(settingsIconPath));
		
		 b3 = new Button("  Quit", 250, 1060, 50, 135, Color.WHITE, Color.BLACK);
		b3.setIcon(new ImageIcon(quitIconPath));
		
		this.buttons.add(b1);
		this.game.add(b1);
		
        this.buttons.add(b2);
		this.game.add(b2);
		
		this.buttons.add(b3);
		this.game.add(b3);
		
		this.buttons.add(b4);
		this.game.add(b4);
			
		
		
		
		b1.addActionListener(a -> {
			
			clearGameComponents();
			
			
			
			game.gameState = GameState.INMATCH;
			SoundManager.setSound(0);
			
			
			
			
		});
		
          b2.addActionListener(a -> {

        	  clearGameComponents();
        	
        	  Game.getInstance().puzzleManager.initButtons();
        	  
        	  
        	  
        	  
        	  if(!game.randomPuzzles) {
        	  Game.getInstance().puzzleManager.setUpPiecesForPuzzle(0);
        	  
        	  
        	  }else {
        		  
        		  Game.getInstance().puzzleManager.setUpPiecesForPuzzle(Game.getInstance().puzzleManager.startWithRandomPuzzle());
        		  
        		  if(Game.getInstance().animations) {
        		  Game.getInstance().puzzleManager.waitForAnimationEnding = true;
        		  Game.getInstance().puzzleManager.animationsAllowed = true;
        		
        		  
        	  }else {
        		  
        		  Game.getInstance().puzzleManager.waitForAnimationEnding = false;
        		  Game.getInstance().puzzleManager.animationsAllowed = false; 
        		
        		  }
        		  
        	  }
        	  
        	  game.gameState = GameState.INPUZZLE;
        	  SoundManager.setSound(0);
        	  
        	  
		});
          
          
          b3.addActionListener(a -> {
  			
       
  			System.exit(0);
  			
  			
  		});
		
		
          b4.addActionListener(a -> {
        	  
        	  clearGameComponents();
        	  
        	  game.settingsManager.addSettings();
        	  game.settingsManager.addButtons();
        	  
        	  game.gameState = GameState.SETTINGS;
        	  SoundManager.setSound(0);
        	  
        	
        	  
          });
	}
          
          
	
	
	

	public void drawLogo(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		//g2.fillRect(800, 0, 140, 65);
		g2.fillRoundRect(800, 0, 140, 100,20,0);
		
		g2.drawImage(logo, 800, 20, 140, 70, null);
	}

	
	
    public void drawMenuPieces(Graphics2D g2) {
    	
    	
    	
    	for(Piece piece : pieces) {
    	g2.drawImage(piece.image,piece.x*game.board.feldSize,piece.y*game.board.feldSize+5, game.board.feldSize, game.board.feldSize-10, null);
    	}
    	
    	
    	
    }
	

    private void addMenuPieces() {
    	
     	pieces.add(new Bauer('w', 6, 0));
    	pieces.add(new Bauer('w', 3, 1));
    	pieces.add(new Bauer('w', 6, 2));
    	pieces.add(new Bauer('w', 4, 3));
    	pieces.add(new Bauer('w', 6, 4));
    	pieces.add(new Bauer('w', 5, 5));
    	pieces.add(new Bauer('w', 6, 6));
    	pieces.add(new Bauer('w', 4, 7));
    	
    	
    	
    }
    
   
  


    public void updateMenuGame() {
    	
    	
    	updateCounter++;
	    
    	if(updateCounter == 144) {
    	
    		updatePawns();
    	
    		
    		updateCounter = 0;
    		
    	}
    	
    }
    
  public void  updatePawns() {
    	
	 
	  
	  int randomPiece1 = (int) (Math.random()*pieces.size()); 
	  
    	
	  Piece p = pieces.get(randomPiece1);
	  
	  p.y--;
	  
	  if(p.y == -1)
	     p.y = 7;
	  
    }

  
   

    private void clearGameComponents() {
    	
    	for(Component c :  game.getComponents()) {
    		
    		game.remove(c);
    		
    	}
    	
    }
    
    public int getValidMovesCount(Piece piece) {
    	
    	int count = 0;

		for(int i = 0;i<8;i++) {
			
			for(int j = 0;j<8;j++) {
				
				if(piece.canMove(i, j))count++;
				
				
			}
			
		}
			
		  return count;

    }
    

    

  
}

