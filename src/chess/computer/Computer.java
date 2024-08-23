package chess.computer;

import chess.board.Board;
import chess.piece.Piece;

public class Computer {
	
	
	
	 
	 
	
	public Computer() {
		
		
	}
	
	

	
     public int[][] getAllMoves(Board board,char color)  {
		
		int [][] array = new int[50][3];
		int moveCount = 0;
		
		
		
		    	 for(int i = 0;i<board.pieces.size();i++) {
		    		 
		    		 if(board.pieces.get(i).color != color)continue;
		    		
		    		  Piece p = board.pieces.get(i);
		    		 
		    		 
		    		 for(int m = 0; m<p.possibleMoves.size();m++) {
		    			 
		    			 
		    			 array[moveCount][0] = i;
		    			 array[moveCount][1] = p.possibleMoves.get(m)[0];
		    			 array[moveCount][2] =  p.possibleMoves.get(m)[1];
		    			 
		    			 moveCount++;
		    			 
		    			 
		    		 }
 
		    		 
		    	 }
		    		

		 
		  int [][]finalArray = new int [moveCount][3];
		  
		   for(int i = 0;i<moveCount;i++) {
			   
			   finalArray[i][0] = array[i][0];
			   finalArray[i][1] = array[i][1];
			   finalArray[i][2] = array[i][2];
			   
			   
		   }
		
		
		return finalArray;
		 
		  
		
		
	}
		
		 
		
		
	public int[][] getAllMovesOld(Board board,char color)  {
		
		int [][] array = new int[50][3];
		int count = 0;
		
		
		for(int p = 0;p< board.pieces.size();p++) {
			
			
			if(board.pieces.get(p).color == color) {
				
				
				for(int i = 0;i<8;i++) {
			    	
		    		for(int j = 0;j<8;j++) {
		    			
		    			
		    			if(board.checker.isMoveValid(board.pieces.get(p), i, j)) {
		    				
		    				array[count][0] = p;
		    				array[count][1] = i;
		    				array[count][2] = j;
		    				
		    				count++;
		    				
		    			}
		    			
		    			
		    		}
		    		
		    	}
				
				
				
			}
			
			
		}
		
		 
		  int [][]finalArray = new int [count][3];
		  
		   for(int i = 0;i<count;i++) {
			   
			   finalArray[i][0] = array[i][0];
			   finalArray[i][1] = array[i][1];
			   finalArray[i][2] = array[i][2];
			   
			   
		   }
		
		
		return finalArray;
		 
		  
		
		
	}
		
	

	public int[] getCompletelyRandomMove(int [][] a) {
		
		int randomZahl = (int) (Math.random()*a.length-1);
		
		return new int [] {a[randomZahl][0],a[randomZahl][1],a[randomZahl][2]};
	}
	
	public int[] getBestValueMove(int [][] a,Board board) {
		
		int bestValue = 0;
		int index = 0;
		
		Piece mightPiece = null;
		
		for(int i = 0;i< a.length;i++) {
			
			
			mightPiece = board.getPiece(a[i][1],a[i][2]);
			
			if(mightPiece!=null) {
				
				if(mightPiece.value>= bestValue) {
					
					index = i;
					bestValue = mightPiece.value;
				}
				
			}
			
			
		}
		
		
		return new int[] {a[index][0],a[index][1],a[index][2]};
	}
	
	
	
		
	public int[] getFinalMove(Board board,char color, int type) {
		
		// 1: Alle Moves
		// 2. Wie sollen die bewertet werden
		
		
		int moves [][] = getAllMoves(board, color);
		
		
	
		
		if(type == 0) {
			
			return getCompletelyRandomMove(moves);
		
		}else if(type == 1) {
			
			return getBestValueMove(moves, board);
			
		}
		
		
		
		
		
		
		return null;
	}
		
		
	
	
	
		
	
	
	
	
	
}
