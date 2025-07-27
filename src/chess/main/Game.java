package chess.main;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import chess.UI.MenuScreen;
import chess.board.Board;
import chess.board.Match;
import chess.controller.Keyboard;
import chess.controller.Mouse;
import chess.gameSave.SaveGameManager;
import chess.puzzle.PuzzleManager;
import chess.settings.SettingsManager;



@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable {

	 private static final Game game = new Game();
	 
	
	 
	 public GameState gameState = GameState.INMENU;
	 
	

	//______Sizing_______________________________
	
	private int windowWith = 1300; 
	private int windowHeight = 800;
	//______________________________________
	
	//_____ loop and FPS______________________________
	
	public double updateSpeed = 144; // FPS
	private Thread gameThread; // The thread running the game
	//_________________________________________________________
	
	
	//_____ settings______________________________
	public boolean sound = true;
	public boolean boardgraphics = true;
	public boolean arrows =  true;
	public boolean randomPuzzles = true;
	public boolean animations = true;
	public boolean computer = false;
	public boolean turnBoard = false;
	public boolean smallPieces = false;
		
	//_________________________________________________________
		
	
	
    public Mouse mouse = new Mouse();
    public Keyboard keyBoard = new Keyboard();
	public Board board = new Board();
	public MenuScreen menuScreen = new MenuScreen(this);
	public Match match = new Match(this.board);
	public PuzzleManager puzzleManager = new PuzzleManager(new Board());
	public SettingsManager settingsManager = new SettingsManager(this);
	

	

	
	public Game() {
		
		  this.setDoubleBuffered(true);
		  this.setFocusable(true);
	      this.setLayout(null);
		  this.setPreferredSize(new Dimension(this.windowWith, this.windowHeight));
		  this.addMouseListener(mouse);
		  this.addMouseMotionListener(mouse);
		  
		  this.addKeyListener(this.keyBoard);
		  
		

		  gameThread = new Thread(this);
		  gameThread.start();
		  
		  
		
		  
	}
	

	
	private void update(double deltaTime) {
		
		
		switch(gameState) {
		
		
		case INMATCH :   match.update(mouse);  this.match.previousPlayManager.onKeyClick(keyBoard.currentKeyNumber);  break;
		
		
		case INPUZZLE :  puzzleManager.update(mouse);  break;
		

		case INMENU:     menuScreen.updateMenuGame(); break;
		
		
		case SETTINGS :  settingsManager.onLeave(27); break;
		
		
		case INWATCH  :  this.match.previousPlayManager.onKeyClick(keyBoard.currentKeyNumber); this.match.previousPlayManager.update(); break;
		
		
		case onWinningScreen :  this.match.previousPlayManager.onKeyClick(keyBoard.currentKeyNumber); this.match.previousPlayManager.update(); break;
		
		
		default:  break;
		
		
		}
		
		
		
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
		this.board.reachableFeldDrawer.drawArrows(g2);
		
		this.match.drawAnimations(g2);
		

		}
		
		
		else if(this.gameState == GameState.onWinningScreen) {
			
			this.match.matchUI.drawBackGround(g2);
			this.match.drawBoard(g2);
			this.match.drawBoardGraphics(g2);
			board.drawCheckMateFelder(g2);
			this.match.previousPlayManager.drawPreviousPlayPieces(g2);
			this.match.previousPlayManager.drawPlayButtons(g2);
			this.match.matchUI.drawAfterMatchUIButtons(game);
			
			
		
		}else if(this.gameState == GameState.INMENU) {
			
			this.match.drawBoard(g2);
			this.menuScreen.drawMenuPieces(g2);
			this.menuScreen.drawBackGround(g2);
			this.menuScreen.drawLogo(g2);
			SaveGameManager.drawMatches(g2);
			
			
		}else if(this.gameState == GameState.INWATCH) {
			
			this.match.drawBoard(g2);
			this.match.drawBoardGraphics(g2);
			this.match.previousPlayManager.drawPreviousPlayPieces(g2);
			this.match.previousPlayManager.drawPlayButtons(g2);
			this.match.drawMatchRelatedUI(g2);
		
			
		
			
			
	}else if(this.gameState == GameState.INPUZZLE) {
		

		
		this.match.drawBoard(g2);
		this.puzzleManager.drawBackGround(g2);
		this.puzzleManager.onDrawEvent(g2);
		this.puzzleManager.drawCurrentPuzzlePieces(g2);
		this.puzzleManager.jiggleAnimation.drawJiggleAnimation(g2);
		this.puzzleManager.drawSuccessImage(g2);
		

	}else if(this.gameState == GameState.SETTINGS) {
		
		settingsManager.drawBackGrounnd(g2);
		settingsManager.drawSettingsFeld(g2);
		settingsManager.drawPiece(g2);
	
		
	}else if(this.gameState == GameState.INPROMOTING) {

		this.match.drawBoard(g2);
		this.match.drawBoardGraphics(g2);
		this.match.drawMatchRelatedUI(g2);
		this.match.matchUI.promoteScreen.drawBackGround(g2);
		this.board.drawPieces(g2);
 		this.match.matchUI.promoteScreen.hideSelectedPieceWhilePromotingOption();
		
	}
		

		
	}
	

	@Override
	public void run() {  
		
		double drawInterval = 1000_000_000/this.updateSpeed;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread!=null) {
			
			currentTime = System.nanoTime();
			double deltaTime = (currentTime - lastTime) / 1_000_000_000.0; // Sekunden
			
			if(currentTime-lastTime > drawInterval) {
				
				this.update(deltaTime);
				this.repaint();
				
				
				
				
				
				
				lastTime = currentTime;
				
			}
			
			
		}
		
		
	}
	
	

	
}
