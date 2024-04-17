package chess.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;


import javax.swing.JPanel;

import chess.UI.MenuScreen;
import chess.board.Board;
import chess.board.Match;
import chess.controller.Mouse;
import chess.gameSave.SaveGameManager;
import chess.puzzle.PuzzleManager;


@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {

	 private static final Game game = new Game();
	 
	 public GameState gameState = GameState.INMENU;
	

	//______Sizing_______________________________
	
	private int windowWith = 1300; 
	private int windowHeight = 800;
	//______________________________________
	
	//_____ loop and FPS______________________________
	
	public double updateSpeed =144; // FPS
	private Thread gameThread; // The thread running the game
	//_________________________________________________________
	
	
    public Mouse mouse = new Mouse();
    
	public Board board = new Board();
	
	public MenuScreen menuScreen = new MenuScreen(this);
	
	public Match match = new Match(this.board);
	
	public PuzzleManager puzzleManager = new PuzzleManager(new Board());
	
	
	public Game() {
		
		  this.setDoubleBuffered(true);
		  this.setFocusable(true);
	      this.setLayout(null);
		  this.setPreferredSize(new Dimension(this.windowWith, this.windowHeight));
		  this.addMouseListener(mouse);
		  this.addMouseMotionListener(mouse);
		
		  
		
		    
		  
		  
		  gameThread = new Thread(this);
		  gameThread.start();
		
		  
	}
	
	public static Game getInstance() {
		
		return game;
		
		
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		
		 
		
		Graphics2D g2 = (Graphics2D)g;
		
		super.paintComponent(g);
		
		
		
		
		if(this.gameState == GameState.INMATCH) {
		
		this.match.drawBoard(g2);
		this.match.drawBoardGraphics(g2);
		this.match.drawPieces(g2); 
		
		this.match.previousPlayManager.drawPlayButtons(g2);
		
		this.match.drawMatchRelatedUI(g2);
		
		}
		
		
		 
		else if(this.gameState == GameState.onWinningScreen) {
			
			g2.setColor(Color.getHSBColor(100, 200, 200));
			g2.fillRect(800, 0, 500, 800);
			
			this.match.drawBoard(g2);
			this.match.drawBoardGraphics(g2);
			this.match.previousPlayManager.drawPreviousPlayPieces(g2);
			this.match.previousPlayManager.drawPlayButtons(g2);
			
			board.drawCheckMateFelder(g2);
			
			this.match.matchUI.drawAfterMatchUIButtons(game);
			
		}else if(this.gameState == GameState.INMENU) {
			
			this.match.drawBoard(g2);
			this.menuScreen.drawMenuPieces(g2);
			
			
			
			g2.setColor(Color.getHSBColor(100, 200, 200));
			g2.fillRect(800, 0, 500, 800);
			
			SaveGameManager.drawMatches(g2);
			
			
		}else if(this.gameState == GameState.INWATCH) {
			
			this.match.drawBoard(g2);
			this.match.drawBoardGraphics(g2);
			this.match.previousPlayManager.drawPreviousPlayPieces(g2);
			this.match.previousPlayManager.drawPlayButtons(g2);
			
			this.match.drawMatchRelatedUI(g2);
			
			board.drawCheckMateFelder(g2);
			
			
			
	}else if(this.gameState == GameState.INPUZZLE) {
		
		
		
		this.match.drawBoard(g2);
		this.puzzleManager.drawCurrentPuzzlePieces(g2);
		
		g2.setColor(Color.getHSBColor(100, 200, 200));
		g2.fillRect(800, 0, 500, 800);
		
	}
		
		
		
		
	}
	
	private void update() {
		
		
	     if(this.gameState == GameState.INMATCH) {
	    	
	    	 match.update(mouse);
	    	 
	     }else if(this.gameState == GameState.INPUZZLE) {
	    	 
	    	 puzzleManager.update(mouse);
	    	 
	     }
		
		
	
		
	}

	
	
		
	
	

	@Override
	public void run() {  
		
		double drawInterval = 1000_000_000/this.updateSpeed;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread!=null) {
			
			currentTime = System.nanoTime();
			
			if(currentTime-lastTime > drawInterval) {
				
				this.update();
				this.repaint();
				
				
				
				
				
				
				lastTime = currentTime;
				
			}
			
			
		}
		
		
	}
	
	
	public void updateGameState(GameState newGameState) {
		
		
		this.gameState = newGameState;
		
	}
	
	
}
