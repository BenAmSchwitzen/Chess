package chess.graphics;

import java.awt.Color;
import java.awt.Graphics2D;

import chess.gameSave.SaveGameManager;
import chess.main.Game;
import chess.main.GameState;



   // This class renders all game-scenes

public class Renderer {

	
	
	private Game game;
	
	
	public Renderer() {
		
		this.game = game;
		
		
	}
	
	
	public void render(Graphics2D g2) {
		
		

		if(this.game.gameState == GameState.INMATCH) {
		
			if(this.game.match.firstVisited) {
				this.game.board.updatePossibleMoves('w');
				
				this.game.match.firstVisited = false;
			}
			
			
			this.game.match.drawBoard(g2);
			this.game.match.drawBoardGraphics(g2);
		
	
		
		this.game.match.drawPieces(g2); 
 	
		this.game.match.previousPlayManager.drawPlayButtons(g2);
		
		this.game.match.drawMatchRelatedUI(g2);
		
		this.game.board.reachableFeldDrawer.drawArrows(g2);
		
		
		
		}
		
		
		 
		else if(this.game.gameState == GameState.onWinningScreen) {
			
			g2.setColor(Color.getHSBColor(100, 200, 200));
			g2.fillRect(800, 0, 500, 800);
			
			this.game.match.drawBoard(g2);
			this.game.match.drawBoardGraphics(g2);
			
			game.board.drawCheckMateFelder(g2);
			
			this.game.match.previousPlayManager.drawPreviousPlayPieces(g2);
			
			this.game.match.previousPlayManager.drawPlayButtons(g2);
			
			
			
			this.game.match.matchUI.drawAfterMatchUIButtons(game);
			
		}else if(this.game.gameState == GameState.INMENU) {
			
			this.game.match.drawBoard(g2);
			this.game.menuScreen.drawMenuPieces(g2);
			
			
			
			Color color = new Color(3,2,2,182);
			 
			 g2.setColor(color);
			g2.fillRect(800, 0, 500, 800);
			
			
			this.game.menuScreen.drawLogo(g2);
			
			SaveGameManager.drawMatches(g2);
			
			
		}else if(this.game.gameState == GameState.INWATCH) {
			
			this.game.match.drawBoard(g2);
			this.game.match.drawBoardGraphics(g2);
			this.game.match.previousPlayManager.drawPreviousPlayPieces(g2);
			this.game.match.previousPlayManager.drawPlayButtons(g2);
			
			this.game.match.drawMatchRelatedUI(g2);
		
			
		
			
			
	}else if(this.game.gameState == GameState.INPUZZLE) {
		

		
		this.game.match.drawBoard(g2);
		
	
		
		g2.setColor(Color.getHSBColor(100, 200, 200));
		g2.fillRect(800, 0, 500, 800);
		
	    this.game.puzzleManager.onDrawEvent(g2);
		this.game.puzzleManager.drawCurrentPuzzlePieces(g2);
		
		
		
	}else if(this.game.gameState == GameState.SETTINGS) {
		
		game.settingsManager.drawBackGrounnd(g2);
		game.settingsManager.drawSettingsFeld(g2);
		game.settingsManager.drawPiece(g2);
	
		
	}else if(this.game.gameState == GameState.INPROMOTING) {
		
	
		 
		this.game.match.drawBoard(g2);
		this.game.match.drawBoardGraphics(g2);
		
		this.game.match.drawMatchRelatedUI(g2);
		g2.setColor(Color.getHSBColor(100, 200, 200));
		g2.fillRect(800, 0, 500, 800);
 		
		this.game.board.drawPieces(g2);
			
		this.game.match.matchUI.promoteScreen.hideSelectedPieceWhilePromotingOption();
		
	}
		
		
		
		
		
		
		
	}
	
	
}
