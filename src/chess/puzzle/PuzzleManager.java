package chess.puzzle;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import chess.board.Board;
import chess.controller.Mouse;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.*;
import chess.sound.SoundManager;
import chess.util.Button;
import chess.util.FontManager;

public class PuzzleManager {

    
	public Board board;
	
   
	
	private File turnsFile = new File("chess.res/puzzles/Turns.txt");
	private File piecesFile = new File("chess.res/puzzles/PiecesForTurns.txt");
	
	private BufferedImage correctIconImage;
	private String correctIconImagePath = "/icons/correctMoveIcon.png";
	
	private ArrayList<String> puzzles = new ArrayList<>();
	private ArrayList<String> pieces = new ArrayList<>();
	
    private int currentPuzzle = 0;
	private int currentPuzzleTurn = 0;
	private int amountOfPuzzles = 0;
	
	private boolean computerTurn = false;
	private boolean playerHelp = false;
	private int computerDelay = 60;
	private int computerTimer = 0;
	
	
	private int newPuzzleTimer = 0;
	private boolean waitForNextPuzzle = false;
	
	private int animationCounter = 0;
	public boolean waitForAnimationEnding = false;
	private int animationTimes = 0;
	public boolean animationsAllowed = true;
	
	private Button leaveButton;
	private Button reloadButton;
	private Button helpButton;
	
	private int score = 0;
    private boolean updateScore = false;
    private int nextScore = 0;
	
	private int mistakes = 0;
	
	
	private int successY = -99;
	private int successX = -99;
	
	private int mistakesRow = 0;
	private int helpY = 0;
	private int helpX = 0;
	private int hintTimer = 0;
	private int hintStroke = 0;
	private int hintSizeChange = 1;
	
	
	
	
	public PuzzleManager(Board board) {
		
		this.board = board;
		
        this.amountOfPuzzles =  this.getPuzzles(turnsFile);
        
        getPiecesForPuzzles(piecesFile);
        
        setUpPiecesForPuzzle(0);
		
	
        try {
			this.correctIconImage = ImageIO.read(getClass().getResourceAsStream(correctIconImagePath));
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	
	
	
     public void doPieceMovementAnimation(Mouse mouse) {
		
		board.selectedPiece.drawX = (mouse.mouseX/board.feldSize);
		board.selectedPiece.drawY = (mouse.mouseY/board.feldSize);
		
		if(board.checker.mouseOutOfFeld(mouse.mouseY, mouse.mouseX))  {
			
			
			board.selectedPiece.drawY = board.selectedPiece.y;
			board.selectedPiece.drawX = board.selectedPiece.x;
			
			board.selectedPiece = null;
			
		}
		
		
	}
	
	public void drawCurrentPuzzlePieces(Graphics2D g2) {
		
		
		//g2.setColor(new Color(50, 0, 0,tier));
		//g2.fillRect(0, 0, Game.getInstance().getWidth(), Game.getInstance().getHeight());
		
		board.drawPieces(g2);
		board.drawMovingPiece(g2);
		
	}
	
	private int getPuzzles(File file)  {  // loads all the puzzles in the file
	
		   String line = null;
		   int count  = 0;
		      try (BufferedReader br = new BufferedReader(new FileReader(file))){

		         while ((line = br.readLine()) != null) {
		           puzzles.add(line);
		           count++;
		         }       
		      } catch(Exception e) {
		         e.printStackTrace();
		      }
		   
		      return count;
	}
	
	private void getPiecesForPuzzles(File file) { // Loads all the pieces for the specific puzzles
		
		 String line = null;
		  
		      try(BufferedReader br = new BufferedReader(new FileReader(file))) {

		         while ((line = br.readLine()) != null) {
		           pieces.add(line);
		          
		         }       
		      } catch(Exception e) {
		         e.printStackTrace();
		      }
		   
		 
	}
         
 
    
    public String [] getPuzzleMoves(int puzzle) {
    	

    	
    	String [] singleTurns = puzzles.get(puzzle).split(" ");
    	
    	
    	return singleTurns;
    }
    
    public String getMoveFromPuzzle(int puzzle,int turn) {
    	
    	
    	return getPuzzleMoves(puzzle)[turn];
    	
    	
    }
    
    public int getSinglePuzzleLength(int puzzle) {
    	
    	return puzzles.get(puzzle).split(" ").length;
    	
    }
    
    
    public void setRandomPuzzleOrOrder() {
    	
    	if(!Game.getInstance().randomPuzzles) {
			
			
            this.currentPuzzle++;
    		
    		
		}else {
			
			int tempCurrent = currentPuzzle;
			
			this.currentPuzzle = (int) Math.floor(Math.random()*(amountOfPuzzles-1));
			
			while(tempCurrent== currentPuzzle) {
				this.currentPuzzle = (int) Math.floor(Math.random()*(amountOfPuzzles-1));
			}
			
		}
    	
    }
    
   private void updateScore() {
	   
	   
	 
	   
	   if(updateScore && this.score < nextScore) {
	    	
   		this.score+=10;
   		
   	}else {
   		
   		
   		
   		this.updateScore = false;
   		this.nextScore = 0;
   		
   		
   	}
	   
   }
    
    
    private boolean doComputerTurn(int startY,int startX,int endY,int endX) {
    	
    	if(computerTimer>=computerDelay) {
    		
    		Piece piece = board.getPiece(startY,startX);
    		
    		if(piece==null)return false;
    		
    		piece.drawY = endY;
    		piece.drawX = endX;
    		
    		
    		
    		board.checker.checkRochade(piece,piece.drawY,piece.drawX);
			board.checker.doRochade(piece, null);
			
    		
    		
    	        piece.y = piece.drawY;
    	        piece.x = piece.drawX;
    	        
    	        System.out.println("davor");
    	        board.pieces.forEach(m -> System.out.println(m.name));
    	        System.out.println("-------------------------------");
    	        board.checker.swapBauerToQueen(piece);
    	        System.out.println("danach");
    	        board.pieces.forEach(m -> System.out.println(m.name));
    	        System.out.println("-------------------------------");
    	        computerTimer = 0;
    	        
    	        
    	        //board.selectedPiece = piece;
    	        
    	        if(board.checker.rochadePiece==null) {
    				successY = endY;
    				successX = endX;
    				
    				}else {
    					
    					successY = piece.drawY;
    					successX = piece.drawX;
    					
    				}
    				
    	        
    	     
    	        
    	        
    	       if(board.pieces.removeIf(m -> m.y == piece.y && m.x == piece.x && piece!=m)) {
    	    	   
    	    	   SoundManager.setSound(3);
    	    	   
    	       }else if(doesPuzzlePieceCheckTheKing(piece, piece.y,piece.x) || (board.checker.rochadePiece!=null && doesPuzzlePieceCheckTheKing(board.checker.rochadePiece, board.checker.rochadePiece.drawY,board.checker.rochadePiece.drawX))) {
    	    	   
    	    	   SoundManager.setSound(5);
    	    	   
    	       }else if(board.checker.rochadePiece!=null){
    	    	   
    	    	   SoundManager.setSound(2);
    	    	   
    	       }else {
    	    	   
    	    	   SoundManager.setSound(1);
    	       }
    	     
    	       
    	       
    	       

    	       
    	       
    	        if(!playerHelp) {
    	        computerTurn = false;
    	        }else {
    	        	playerHelp = false;
    	        	this.mistakes++;
    	        	computerTurn = true;
    	        	mistakesRow = 0;
    	        	
    	        }
    	        
    	       
    			currentPuzzleTurn++;
    			return true;
    		
    	}
    	return false;
    }
    
    
    public void update(Mouse mouse) {
    	
    	
    	updateScore();
    	
    	

            if(Game.getInstance().keyBoard.currentKeyNumber == 27)
            	onLeave();
       
         
        
    	
    	
    	if(currentPuzzleTurn>= getSinglePuzzleLength(currentPuzzle) && amountOfPuzzles > currentPuzzle) {
    		
    		
    		if(!waitForNextPuzzle)SoundManager.setSound(4);
    		
    		this.waitForNextPuzzle = true;
    		
    	    this.newPuzzleTimer += waitForNextPuzzle ? 1 : 0;
    	    
    		
    	}
    	
    	if(waitForNextPuzzle && newPuzzleTimer >= 80) {
    		
    		
    		currentPuzzleTurn = 0;
    		
    		
    		setRandomPuzzleOrOrder();
    		
    		
    		
    		
    		this.computerTurn = false;
    		this.computerTimer = 0;
    		
    		if(pieces.size() > currentPuzzle)
    		setUpPiecesForPuzzle(currentPuzzle);
    		
    		this.nextScore = (int) (Math.random()*3180)+ this.score;
    		this.updateScore = true;
    		
    		waitForNextPuzzle = false;
    		newPuzzleTimer = 0;
    		
    		waitForAnimationEnding = true;
    		
    	}
    	
    	
       if(currentPuzzle>=amountOfPuzzles) {
    		
    		this.currentPuzzle = 0;
    		this.currentPuzzleTurn = 0;
    		this.computerTurn = false;
    		this.computerTimer = 0;
    		
    		SoundManager.setSound(0);
    		Game.getInstance().gameState = GameState.INMENU;
    		
    		this.onLeave();
    		
    		Game.getInstance().add(Game.getInstance().menuScreen.b1);
			Game.getInstance().add(Game.getInstance().menuScreen.b2);
			Game.getInstance().add(Game.getInstance().menuScreen.b3);
			Game.getInstance().add(Game.getInstance().menuScreen.b4);
    		
    	}
       
    	// (0.0)->(1.0)
        
       
        //-------------------------------------------------
        
        if(waitForNextPuzzle)return;
       
        
        if(waitForAnimationEnding && animationsAllowed) {
       	 
       	 doNewPuzzleAnimation();
       	 return;
        }
        	 
        	 
        	 
         
        
        
    	
       String nextMove = getMoveFromPuzzle(currentPuzzle, currentPuzzleTurn);  //  (0.0)->(1.0)
       
    	String start = nextMove.split("\\->")[0]; //(0.0)
    	
    	String end = nextMove.split("\\->")[1];   //(1.0)
    	
    	String [] startyx = start.replace("(", "").replace(")", "").split("\\."); // 00
    	
    	String [] endyx = end.replace("(", "").replace(")", "").split("\\.");    // 10
    	
    	int startY = Integer.parseInt(startyx[0]);
    	int startX = Integer.parseInt(startyx[1]);
    	
    	int endY = Integer.parseInt(endyx[0]);
    	int endX = Integer.parseInt(endyx[1]);
    	
    	// in case the player needs help
    	this.helpY = startY;
    	this.helpX = startX;
    	
          
    	if(computerTurn || playerHelp) {
    		
    		
    		
    		if(!doComputerTurn(startY, startX, endY, endX)) {
    			
    			this.computerTimer++;
    			
    		}
    		
    	}
    	
    	
    	if(board.selectedPiece==null && mouse.pressed && !computerTurn) {
			
			
			board.selectedPiece = board.getPiece(mouse.mouseY/100, mouse.mouseX/100);
			
			
		if(board.selectedPiece!=null && board.getPiece(startY, startX)!=null) {
			if(board.selectedPiece.color != board.getPiece(startY, startX).color) {
				board.selectedPiece = null;}}

		}else if(board.selectedPiece!=null && mouse.pressed) {
			

			this.doPieceMovementAnimation(mouse);
			
		
		} else if(board.selectedPiece!=null && !mouse.pressed)  {
			
			if(board.selectedPiece.drawY == endY && board.selectedPiece.drawX == endX && board.selectedPiece.y == startY && board.selectedPiece.x == startX ) {
			
				board.checker.checkRochade(board.selectedPiece, endY, endX);
				board.checker.doRochade(board.selectedPiece, null);
				
				
				board.selectedPiece.y = board.selectedPiece.drawY;
				board.selectedPiece.x = board.selectedPiece.drawX;
				
				
				
				board.checker.swapBauerToQueen(board.selectedPiece);
				
			if(board.pieces.removeIf(m -> m.y == board.selectedPiece.drawY && m.x == board.selectedPiece.drawX && m!=board.selectedPiece)) {
				
				SoundManager.setSound(3);
				
			}else if(doesPuzzlePieceCheckTheKing(board.selectedPiece,board.selectedPiece.drawY, board.selectedPiece.drawX) ||(board.checker.rochadePiece!=null && doesPuzzlePieceCheckTheKing(board.checker.rochadePiece,board.checker.rochadePiece.drawY, board.checker.rochadePiece.drawX))) {
				SoundManager.setSound(5);
				
			} else if(board.checker.rochadePiece!=null){
				
				SoundManager.setSound(2);
				
			}else {
				
				SoundManager.setSound(1);
			}
			

			
			
			
			
			board.pieces.removeIf(m -> m.y == board.selectedPiece.drawY && m.x == board.selectedPiece.drawX && m!=board.selectedPiece);

			
			if(board.checker.rochadePiece==null) {
			successY = endY;
			successX = endX;
			
			}else {
				
				successY = board.selectedPiece.drawY;
				successX = board.selectedPiece.drawX;
				
			}
			
			mistakesRow = 0;
			
			currentPuzzleTurn++;
    	
			computerTurn = true;
			
			
			
			
			
			}else {
				
				if(board.selectedPiece.drawY!= board.selectedPiece.y || board.selectedPiece.drawX != board.selectedPiece.x) {
					
					this.mistakes++;
				
					if(mistakesRow < 3)mistakesRow++; // Giving the player a hint if he is not about to find out the right move
					
				}
			
				board.selectedPiece.drawY = board.selectedPiece.y;
				board.selectedPiece.drawX = board.selectedPiece.x;
				
				
				
				
			}
			
		
			
			
			
    	
			
		
    	 
			
			board.selectedPiece = null;
			
				}
    	
    	
    
			
    }
 
	

    
    
    
    
     public void setUpPiecesForPuzzle(int puzzle) {
    	 
    	 
    	 board.pieces.clear();
    	 
    	 String piecesString = pieces.get(puzzle);
    	 
    	 
    	 
    	 // B(0.0)|D(7.0)
    	 
    	 String [] singlePieces = piecesString.split("\\|");
    	 
    	 
    	 int y = 0;
    	 int x = 0;
    	 char color = 'n';
    	 
    	 for(String s : singlePieces) {
    		 
    		 
      		 String pieceWorkString = s.replace("(", "").replace(")", "").replace(".", "");
    		 
      		  y = Character.getNumericValue(pieceWorkString.charAt(2));
      		  x = Character.getNumericValue(pieceWorkString.charAt(3));
    		  color = pieceWorkString.charAt(1);
      	      
    		  board.pieces.add(createNewPiece(pieceWorkString.charAt(0),y, x, color));
      		  
    	 }
    	 
    	 
     }
    
    
    private void doNewPuzzleAnimation() {
    	
    	if(animationCounter == 6) {
    		
    		animationCounter = 0;
    		
    		for(Piece p : board.pieces) {
    			
    			p.drawY--;
    			p.drawX--;
    			
    		}
    		
    		animationTimes++;
    		
    		
    	}else {
    		
    		animationCounter++;
    	}
    	
    	if(animationTimes == 10) {
    		
    		for(Piece p : board.pieces) {
    			
    			p.drawY+=animationTimes;
    			p.drawX+=animationTimes;
    			
    		}
    		
    		animationTimes = 0;
    		waitForAnimationEnding = false;
    		
    	}
    	
    	
    }
    
    private Piece createNewPiece(char startSymbol,int y,int x,char color) {
    	
        switch(startSymbol) {
        
        case 'B' : return new Bauer(color, y, x);
        case 'S' : return new Springer(color, y, x);
        case 'L' : return new Läufer(color, y, x);
        case 'T' : return new Turm(color, y, x);
        case 'D' : return new Dame(color, y, x);
        case 'K' : return new König(color, y, x);
        
        
        
        }
    	
    	return null;
    }
    
    

    	
	
    	
    
    
    
    
    
    public void initButtons() {
	
    	leaveButton = new Button(" Leave", 600, 1100, 50, 130,Color.white, Color.BLACK);
    	leaveButton.setIcon(new ImageIcon("chess.res/icons/quitIcon.png"));
    	
    	reloadButton = new Button(" Reload", 600, 900, 50, 130,Color.white, Color.BLACK);
    	reloadButton.setIcon(new ImageIcon("chess.res/icons/reloadIcon.png"));
    	
    	helpButton = new Button(" Help", 500,1000, 50, 130,Color.white, Color.BLACK);
    	helpButton.setIcon(new ImageIcon("chess.res/icons/helpIcon.png"));
    	
    	this.setButtonAction();
    	
    	Game.getInstance().add(leaveButton);
    	Game.getInstance().add(reloadButton);
    	Game.getInstance().add(helpButton);
    	
    	
    }
    
    public void onLeave() {
    	
    	Game.getInstance().remove(leaveButton);
    	leaveButton = null;
    	Game.getInstance().remove(reloadButton);
    	reloadButton = null;
    	Game.getInstance().remove(helpButton);
    	helpButton = null;
    	
         
    
    	
    	this.mistakes = 0;
    	this.score = 0;
    	
    	this.mistakesRow = 0;
    	
    	
    	this.updateScore = false;
    	this.nextScore = 0;
    	
    	
    	this.currentPuzzle = 0;
		this.currentPuzzleTurn = 0;
		this.computerTurn = false;
		this.computerTimer = 0;
		
		this.playerHelp = false;
		this.newPuzzleTimer = 0;
		this.waitForNextPuzzle = false;
		
		board.pieces.clear();
		
		SoundManager.setSound(0);
		
		Game.getInstance().gameState = GameState.INMENU;
		Game.getInstance().add(Game.getInstance().menuScreen.b1);
		Game.getInstance().add(Game.getInstance().menuScreen.b2);
		Game.getInstance().add(Game.getInstance().menuScreen.b3);
		Game.getInstance().add(Game.getInstance().menuScreen.b4);
    	
		
    }
    
    
   
    
   public void setButtonAction() {
	   
	   if(leaveButton==null || reloadButton==null)return;
	   
	   leaveButton.addActionListener(e -> {
		   
		   if(!waitForAnimationEnding || !animationsAllowed) {
		   
		
    		
    		onLeave();
    	
			
		   }
		   
	   });
	 
	   reloadButton.addActionListener(e -> {
		   
		   if(!waitForNextPuzzle  && (!waitForAnimationEnding || !animationsAllowed)) {
		   setUpPiecesForPuzzle(currentPuzzle);
		   this.computerTurn = false;
		   this.computerTimer = 0;
		   this.mistakesRow = 0;
		   this.currentPuzzleTurn = 0; }
		   
		    
		   
	   });
	   
     helpButton.addActionListener(e -> {
		   
		   if(!waitForNextPuzzle && (!waitForAnimationEnding || !animationsAllowed) && !computerTurn) {
		   
			   this.playerHelp = true;;}
		   
		    
		   
	   });
	   
	   
   
   }
    
    
    public void onDrawEvent(Graphics2D g2) {
    	
    	g2.setFont(FontManager.getFont("Arial Bold",Font.BOLD,18));
    	g2.setColor(Color.ORANGE);
    	
    	g2.fillRect(1100,300, 120, 10);
    	g2.fillRect(900,300, 120, 10);
    	

    	g2.setColor(Color.WHITE);
    	
    	g2.drawString("Score", 1130, 350);
    	
    	if(updateScore) {
    		g2.setColor(Color.ORANGE);
    	}else {
    		g2.setColor(Color.WHITE);
    	}
    	
    	
    	g2.drawString(Integer.toString(score), 1145, 280);
    	
    	g2.setColor(Color.WHITE);
    	
    	g2.drawString("Score", 1130, 350);
    	
    	g2.drawString("Mistakes", 920, 350);
    	g2.drawString(Integer.toString(mistakes), 955, 280);
    	
    	drawSuccess(g2);
    
    	board.reachableFeldDrawer.makePiecesInvisible(g2);
    	
    	this.drawHint(g2);
    	
    
    }
    
    private void drawSuccess(Graphics2D g2) {
    	
    	if(computerTurn) {
    		
    		g2.setColor(Color.GREEN);
    		g2.fillRect(successX*100, successY*100, 100,100);
    		
    		
    		
    	}
    	
    	
    }
    
     // Has to be separated from the drawSuccess method because of drawing-order
    public void drawSuccessImage(Graphics2D g2) {
    	
    	if(computerTurn) {
    		
    		g2.drawImage(correctIconImage,successX*100+75,successY*100-16, 32, 32, null);
    		
    	}
    	
    }
    
    public int startWithRandomPuzzle() {
    	
    	int puzzleInt = (int) (Math.random()*amountOfPuzzles);
    	
    	currentPuzzle = puzzleInt;
    	
    	return puzzleInt;
    	
    }
    
    
    public boolean doesPuzzlePieceCheckTheKing(Piece piece,int y,int x) {
		
    	
    	char color = piece.color == 'w' ? 'b' : 'w';
    	
    	König king = (König) board.checker.getKing(color);
    	
    	if(king==null)return false;
    	
    	
        int oldY = piece.y;
        int oldX = piece.x;
        
        piece.y = y;
        piece.x = x;
    	
    	
    	if(!board.checker.isBlocked(piece, king.y, king.x) && (piece.canMove(king.y, king.x) ||board.checker.bauerAdditionalMovement(piece,king.y,king.x))) {
    		
    		piece.y = oldY;
    		piece.x = oldX;
    		
    		return true;
    		
    		
    	}
    	
    	
    	piece.y = oldY;
		piece.x = oldX;
    	return false;
    	

}
    
     // Only draws if the player makes 3 mistakes in a row    
    private void drawHint(Graphics2D g2) {
    	
    	if(mistakesRow < 3)return;
    		

    	
    		g2.setColor(Color.YELLOW);
    		g2.setStroke(new BasicStroke(doHintAnimation()));
    		g2.drawRect(helpX*board.feldSize,helpY*board.feldSize,board.feldSize, board.feldSize);
    		
    
    	
    
    
    
   }
    
    
    private int doHintAnimation() {
    	
    	if(hintTimer<100) {
 		   
 		   if(hintTimer % 20 == 0) {
 			   
 			   hintStroke+=hintSizeChange;
 			   
 		   }
 		   hintTimer++;
 		   
 		   
 	   }else {
 		   
 		   hintTimer = 0;
 		   hintSizeChange *= -1;
 		   
 	   }
    	
    	return hintStroke;
    	
    }
    
   
}

    	
    	
    
   
   


