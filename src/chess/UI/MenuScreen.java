package chess.UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import chess.board.Checker;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.Bauer;
import chess.piece.Piece;
import chess.piece.Springer;
import chess.sound.SoundManager;
import chess.util.Button;

public class MenuScreen {

	 private Game game;
	 
	 public Button b1;
	 public Button b2;
	 public Button b3;
	 public Button b4;
	 
	 ArrayList<Piece> pieces;
	 
	 public Piece menuPiece1 = new Springer('b', 4, 4);
	 boolean springerTurn = false;
	 private int updateCounter = 0;
	 
	 private String playIconPath ="chess.res/icons/playIcon.png";
	 private String achievementIconPath ="chess.res/icons/achievementIcon.png";
	 private String quitIconPath ="chess.res/icons/quitIcon.png";
	 private String settingsIconPath ="chess.res/icons/settingsIcon.png";
	 
	 private ArrayList<Button> buttons;
	
	public MenuScreen(Game game) {
		
		 this.game = game;
		
		this.buttons = new ArrayList<>();
		
		  pieces = new ArrayList<>();
		  addMenuPieces();

	      instantiateButtons();
		
	}
	

	
	private void instantiateButtons() {
		
		b1 = new Button("  Play", 150, 900, 50, 135, Color.WHITE, Color.BLACK);
		b1.setIcon(new ImageIcon(playIconPath));
		
		 b2 = new Button("Puzzles", 150, 1080, 50, 170, Color.WHITE, Color.BLACK);
		b2.setIcon(new ImageIcon(achievementIconPath));
		
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
        	
        	  Game.getInstance().puzzleManager.setUpPiecesForPuzzle(0);
        	  
        	  game.gameState = GameState.INPUZZLE;
        	  SoundManager.setSound(0);
        	  
        	  
		});
          
          
          b3.addActionListener(a -> {
  			
  			System.exit(1);
  			
  			
  		});
		
		
	}
	
    public void drawMenuPieces(Graphics2D g2) {
    	
    	g2.drawImage(menuPiece1.image,menuPiece1.x*game.board.feldSize,menuPiece1.y*game.board.feldSize+5, game.board.feldSize, game.board.feldSize-10, null);
    	
    	
    	for(Piece piece : pieces) {
    	g2.drawImage(piece.image,piece.x*game.board.feldSize,piece.y*game.board.feldSize+5, game.board.feldSize, game.board.feldSize-10, null);
    	}
    	
    	updateMenuGame();
    	
    }
	

    private void addMenuPieces() {
    	
     	pieces.add(new Bauer('w', 6, 0));
    	pieces.add(new Bauer('w', 6, 1));
    	pieces.add(new Bauer('w', 6, 2));
    	pieces.add(new Bauer('w', 6, 3));
    	pieces.add(new Bauer('w', 6, 4));
    	pieces.add(new Bauer('w', 6, 5));
    	pieces.add(new Bauer('w', 6, 6));
    	pieces.add(new Bauer('w', 6, 7));
    	
    	
    }
    
    public void updatePawns() {
    	
    	
    	
    	for(Piece piece : pieces) {
    	
    		if(piece.y == 0) {
    			
    			piece.y = 7;
    			piece.drawY = 7;
    			return;
    			
    		}
    		
    		if(piece.y-1!= menuPiece1.y && piece.drawX!=menuPiece1.drawX) {
    			
    			piece.drawY--;
    			piece.y--;
    		
    		
    	}
    	
    }}
    
    private int[] getRandomMove(Piece piece) {
    	
    	
    	
    	int [][] allPossibleMoves  = Checker.getPossibleFields(piece);
    	
    	int randomMove = (int) Math.floor(Math.random()*allPossibleMoves.length);
    	
    	int i = allPossibleMoves[randomMove][0];
    	int j = allPossibleMoves[randomMove][1];
    	
    	
    	
			return new int[] {i,j};
		
    	
    
    
    }

    private void updateMenuGame() {
    	
    	
    	updateCounter++;
	    
    	if(updateCounter == 144) {
    		
    		if(springerTurn) {
    			
    			int randY = getRandomMove(menuPiece1)[0];
    			int randX = getRandomMove(menuPiece1)[1];
    			
    			menuPiece1.y = randY;
    			menuPiece1.x = randX;
    			menuPiece1.drawY = menuPiece1.y;
    			menuPiece1.drawX = menuPiece1.x;
    			
    			removeUpdateGamePiece(menuPiece1);
    			
    			springerTurn = false;
    		}else {
    			
    			updatePawns();
    			springerTurn = true;
    		}
    		
    		
    		updateCounter = 0;
    		
    		
    		
    	    }
    	
    }

    private void removeUpdateGamePiece(Piece attackingPiece) {
    	
    	for(Piece piece : pieces) {
    	
    		if(attackingPiece.drawY == piece.drawY && attackingPiece.drawX == piece.drawX) {
    			
    			pieces.remove(piece);
    			return;
    			
    		}
    		
    	}
    	
    }

    private void clearGameComponents() {
    	
    	for(Component c :  game.getComponents()) {
    		
    		game.remove(c);
    		
    	}
    	
    }
    
}

