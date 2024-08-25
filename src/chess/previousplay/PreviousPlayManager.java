package chess.previousplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import chess.board.Match;
import chess.main.Game;
import chess.main.GameState;
import chess.sound.SoundManager;
import chess.util.FontManager;

public class PreviousPlayManager implements ActionListener {

	public Match match;

	public int currentViewPlay = 0;

	public ArrayList<PreviousPlay> plays;

	public PreviousPlay currentPlay;

	public JButton prevMovesButton;
	public JButton postMoveButton;
	
	
	private int sizeTimer = 0;
	private int size = 0;
	private int sizeChanged = 1;
	



	public PreviousPlayManager(Match match) {

		this.match = match;

		this.plays = new ArrayList<>();

		this.currentPlay = null;

		this.prevMovesButton = new JButton("<");
		this.prevMovesButton.setBackground(Color.WHITE);
		this.prevMovesButton.setBounds(950, 585, 50, 30);
		this.prevMovesButton.setBorder(null);
		this.prevMovesButton.setFont(FontManager.getFont("Arial Bold", Font.BOLD, 20));
		this.prevMovesButton.setFocusable(false);

		this.postMoveButton = new JButton(">");
		this.postMoveButton.setBackground(Color.WHITE);
		this.postMoveButton.setBounds(1050, 585, 50, 30);
		this.postMoveButton.setBorder(null);
		this.postMoveButton.setFont(FontManager.getFont("Arial Bold", Font.BOLD, 20));

		changePreviousPlayPages();

	}

	public void addNewPlay(PreviousPlay play) {

		this.plays.add(play);
		play.button.addActionListener(this);

	}

	public void changePreviousPlayPages() {

		this.prevMovesButton.addActionListener(m -> {

			if (currentViewPlay != 0)
				currentViewPlay -= 10;
		

		});

		this.postMoveButton.addActionListener(m -> {

			if (plays.size() > currentViewPlay + 10)
				currentViewPlay += 10;
		

		});

	}
	
	
	
	
	public void onKeyClick(int key) {
		
		if(this.currentPlay==null)return;
		
		if(key != 37 && key != 39)return;
		
		 int forwardOrBackwardNumber = key == 37 ? -1 : 1;
		
		       if((key == 37 && currentPlay.number-1 == 0) || (key == 39 && currentPlay.number-1 == plays.size()-1  ) )return;

		       Game.getInstance().gameState = GameState.INWATCH;
		       
				actionPerformed(new ActionEvent(plays.get(currentPlay.number+forwardOrBackwardNumber-1).button, currentViewPlay, forwardOrBackwardNumber == -1 ? "backwards" : "forwards"));

				if(currentPlay.number > currentViewPlay+10 && key == 39) {
					
					currentViewPlay+=10;
					
				}else if(currentPlay.number< currentViewPlay+1 && key == 37) {
					currentViewPlay-=10;
				}
				
				
				 Game.getInstance().keyBoard.currentKeyNumber = -1;
				
			}
			
		
		
		
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
         
		for (PreviousPlay play : plays) {

			if (play.button == e.getSource() && match.board.checker.promotePiece == null) {
				
				
				match.board.reachableFeldDrawer.arrows.clear();
				match.board.reachableFeldDrawer.markedFelder.clear();

				if (play == plays.get(plays.size() - 1) && match.isMatchRunning) {

					
					
					currentPlay = play;
					playWatchSound(currentPlay);

					Game.getInstance().gameState = GameState.INMATCH;

				} else {

					if (match.isMatchRunning) {

						Game.getInstance().gameState = GameState.INWATCH;

					} else {
						Game.getInstance().gameState = GameState.onWinningScreen;
					}

					this.currentPlay = play;
					
					playWatchSound(currentPlay);
					
					this.currentPlay.button.setBorder(
							BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
					this.currentPlay.button.setBackground(Color.YELLOW);
			        

				}

			}

			for (PreviousPlay prev : plays) {

				if (prev != currentPlay) {

					prev.button.setBackground(prev.capturedPiece != null ? Color.RED : Color.WHITE);
					prev.button.setBorder(null);

				}
			}
           
		}
	}
	
	private void playWatchSound(PreviousPlay play) {
		
		if(play.lastPlay) {
			
			SoundManager.setSound(4);
		}else if(play.isRochade) {
			SoundManager.setSound(2);	
			
		}else if(play.isCheck) {
			SoundManager.setSound(5);
	
		}else if(play.capturedPiece!=null) {
			SoundManager.setSound(3);
		}else {
			SoundManager.setSound(1);
		}
		
	}

	public void drawPlayButtons(Graphics2D g2) {

		int buttonsAlreadyDrawn = 0;

		if (plays.size() >= 15) {
			g2.setColor(Color.BLACK);
			g2.fillRect(880, 150, 300, 400);

		} else {
			g2.setColor(Color.BLACK);
			g2.fillRect(880, 150, 300, 400);

		}
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(880, 550, 300, 100);

		int startX = 925;
		int startY = 200;

		int counter = 0;
		int boostX = 0;
		int boostY = 0;

		for (int i = 0; i < Game.getInstance().getComponents().length; i++) {

			Game.getInstance().remove(Game.getInstance().getComponent(i));

		}

		for (int i = currentViewPlay; i < plays.size() && buttonsAlreadyDrawn < 10; i++) {

			if (counter == 1) {

				boostX = 120;
			}

			if (counter == 2) {

				boostY += 70;
				counter = 0;
			}

			plays.get(i).button.setBounds(startX + boostX, startY + boostY, 80, 40);
			plays.get(i).button.setVisible(true);

			Game.getInstance().add(plays.get(i).button);

			buttonsAlreadyDrawn++;
			counter++;
			boostX = 0;

		}

		Game.getInstance().add(prevMovesButton);
		Game.getInstance().add(postMoveButton);

	}

	public void drawPreviousPlayPieces(Graphics2D g2) {

		drawInvolvedFields(g2);

		
		
		if (this.currentPlay != null) {

			currentPlay.prevPieces.stream().filter(m -> !m.standOut).forEach(m -> g2.drawImage(m.image, m.drawX * this.match.board.feldSize, m.drawY * this.match.board.feldSize+5,this.match.board.feldSize, this.match.board.feldSize-10, null));

		}
		
		
           if(match.mAttacker!=null  && match.mKing!=null && match.previousPlayManager.currentPlay == plays.get(Game.getInstance().match.previousPlayManager.plays.size()-1)) {
			

			
			g2.drawImage(match.mAttacker.image, match.mAttacker.drawX*match.board.feldSize,match.mAttacker.drawY*match.board.feldSize+5, match.board.feldSize+standOutAnimation(),match.board.feldSize-10+standOutAnimation(), null);
			g2.drawImage(match.mKing.image, match.mKing.drawX*match.board.feldSize,match.mKing.drawY*match.board.feldSize+5, match.board.feldSize+standOutAnimation(),match.board.feldSize-10+standOutAnimation(), null);
			
		}
		

	}

	private void drawInvolvedFields(Graphics2D g2) {

		if (this.currentPlay == null || !Game.getInstance().boardgraphics)
			return;
 
		
		if(!currentPlay.isRochade) {

		// From this
		g2.fillRect(currentPlay.oldX * match.board.feldSize, currentPlay.oldY * match.board.feldSize,
				match.board.feldSize, match.board.feldSize);

		// To this
		g2.fillRect(currentPlay.newX * match.board.feldSize, currentPlay.newY * match.board.feldSize,
				match.board.feldSize, match.board.feldSize);
		
		}else {
			
			
			g2.fillRect(currentPlay.rochadeKX * match.board.feldSize, currentPlay.rochadeKY * match.board.feldSize,
					match.board.feldSize, match.board.feldSize);
				
			g2.fillRect(currentPlay.rochadeTX* match.board.feldSize, currentPlay.rochadeTY * match.board.feldSize,
					match.board.feldSize, match.board.feldSize);
			
			
		}
		
		
		

	}
	
	 private int standOutAnimation() {
		 if(sizeTimer<200) {
			   
			   if(sizeTimer % 50 == 0) {
				   
				   size+=sizeChanged;
				   
			   }
			   sizeTimer++;
			   
			   
		   }else {
			   
			   sizeTimer = 0;
			   sizeChanged *= -1;
			   
		   }
	  
	  	return size;
	  	
	  }
		
	
	

}
