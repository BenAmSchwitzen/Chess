package chess.puzzle;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import chess.board.Board;
import chess.controller.Mouse;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.Bauer;
import chess.piece.Dame;
import chess.piece.König;
import chess.piece.Läufer;
import chess.piece.Piece;
import chess.piece.Springer;
import chess.piece.Turm;
import chess.sound.SoundManager;

public class PuzzleManager {

    
	public Board board;
	
   
	
	private File turnsFile = new File("chess.res/puzzles/Turns.txt");
	private File piecesFile = new File("chess.res/puzzles/PiecesForTurns.txt");
	
	private ArrayList<String> puzzles = new ArrayList<>();
	private ArrayList<String> pieces = new ArrayList<>();
	
    private int currentPuzzle = 0;
	private int currentPuzzleTurn = 0;
	private int amountOfPuzzles = 0;
	
	private boolean computerTurn = false;
	private int computerTimer = 0;
	
	public PuzzleManager(Board board) {
		
		this.board = board;
		
        this.amountOfPuzzles =  this.getPuzzles(turnsFile);
        
        getPiecesForPuzzles(piecesFile);
        
        setUpPiecesForPuzzle(0);
		
		
		
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
         
 

    private String getPieceString(Piece piece) {


		
		char alias = piece.name.toString().charAt(0);
		int  pieceY = piece.y;
		int pieceX = piece.x;
		
		return Character.toString(alias)+pieceY+pieceX;
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
    
    
    private boolean doComputerTurn(int startY,int startX,int endY,int endX) {
    	
    	if(computerTimer>=60) {
    		
    		Piece piece = board.getPiece(startY,startX);
    		
    		
    	        piece.y = endY;
    	        piece.x = endX;
    	        piece.drawY = piece.y;
    	        piece.drawX = piece.x;
    	        computerTimer = 0;
    	        
    	        board.pieces.removeIf(m -> m.y == piece.drawY && m.x == piece.drawX && m!=piece);
    			
    	        computerTurn = false;
    	        
    			currentPuzzleTurn++;
    			return true;
    		
    	}
    	return false;
    }
    
    
    public void update(Mouse mouse) {
    	
    	
    	
    	if(currentPuzzleTurn>= getSinglePuzzleLength(currentPuzzle) && amountOfPuzzles > currentPuzzle) {
    		
    		
    		currentPuzzleTurn = 0;
    		currentPuzzle++;
    		
    		this.computerTurn = false;
    		this.computerTimer = 0;
    		
    		if(pieces.size() > currentPuzzle)
    		setUpPiecesForPuzzle(currentPuzzle);
    		
    		SoundManager.setSound(0);
    		
    	}
    	
    	
       if(currentPuzzle>=amountOfPuzzles) {
    		
    		this.currentPuzzle = 0;
    		this.currentPuzzleTurn = 0;
    		this.computerTurn = false;
    		this.computerTimer = 0;
    		
    		SoundManager.setSound(0);
    		Game.getInstance().gameState = GameState.INMENU;
    		;
    		
    		Game.getInstance().add(Game.getInstance().menuScreen.b1);
			Game.getInstance().add(Game.getInstance().menuScreen.b2);
			Game.getInstance().add(Game.getInstance().menuScreen.b3);
			Game.getInstance().add(Game.getInstance().menuScreen.b4);
    		
    	}
       
    	// (0.0)->(1.0)
        
       
        //-------------------------------------------------
        
    	
       String nextMove = getMoveFromPuzzle(currentPuzzle, currentPuzzleTurn);  //  (0.0)->(1.0)
       
    	String start = nextMove.split("\\->")[0]; //(0.0)
    	
    	String end = nextMove.split("\\->")[1];   //(1.0)
    	
    	String [] startyx = start.replace("(", "").replace(")", "").split("\\."); // 00
    	
    	String [] endyx = end.replace("(", "").replace(")", "").split("\\.");    // 10
    	
    	int startY = Integer.parseInt(startyx[0]);
    	int startX = Integer.parseInt(startyx[1]);
    	
    	int endY = Integer.parseInt(endyx[0]);
    	int endX = Integer.parseInt(endyx[1]);
    	
          
    	if(computerTurn) {
    		
    		
    		if(doComputerTurn(startY, startX, endY, endX)) {
    			
    			SoundManager.setSound(1);
    			
    		}else {
    			this.computerTimer++;
    		}
    		
    	}
    	
    	
    	if(board.selectedPiece==null && mouse.pressed && !computerTurn) {
			
			
			board.selectedPiece = board.getPiece(mouse.mouseY/100, mouse.mouseX/100);

			
		}else if(board.selectedPiece!=null && mouse.pressed) {
			
			this.doPieceMovementAnimation(mouse);
			
		
		} else if(board.selectedPiece!=null && !mouse.pressed)  {
			
			if(board.selectedPiece.drawY == endY && board.selectedPiece.drawX == endX && board.selectedPiece.y == startY && board.selectedPiece.x == startX ) {
			
			
				
			if(board.pieces.removeIf(m -> m.y == board.selectedPiece.drawY && m.x == board.selectedPiece.drawX && m!=board.selectedPiece)) {
				
				SoundManager.setSound(3);
				
			}else {
				
				SoundManager.setSound(1);
				
			}
			

			
			board.selectedPiece.y = board.selectedPiece.drawY;
			board.selectedPiece.x = board.selectedPiece.drawX;
			
			board.pieces.removeIf(m -> m.y == board.selectedPiece.drawY && m.x == board.selectedPiece.drawX && m!=board.selectedPiece);
			
			currentPuzzleTurn++;
    	
			computerTurn = true;
			
			
			}else {
				
			
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}


