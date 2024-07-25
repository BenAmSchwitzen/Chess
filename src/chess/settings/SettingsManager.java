package chess.settings;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import chess.main.Game;
import chess.main.GameState;
import chess.piece.*;

import chess.sound.SoundManager;
import chess.util.Button;

public class SettingsManager {

	
	private Game game;
	
	public ArrayList<Setting> settings = new ArrayList<>();
	
	private Button leaveButton;
	
	
	//-----Drawings--------
	private Color white = new Color(238, 238, 210);
	private Color black = new Color(118, 150, 86);
	private int startX = 500;
	private int startY = 100;
	
	private Dame dPiece;
	private König kPiece;
	private Läufer lPiece;
	//--------------------
	
	public SettingsManager(Game game) {
		
		this.game = game;
		
		
	}
	
	
	
	public void addSettings() {
		
		dPiece = new Dame('b',4,5);
		kPiece = new König('w',0,1);
		lPiece = new Läufer('w',0,6);
		
		Setting s1 = new Setting("board-graphics") {
			
			
			
			@Override
			public void onClick() {
				
				game.boardgraphics = !game.boardgraphics;
				
			    this.getButton().setBackground(game.boardgraphics ? new Color(118, 150, 86) : new Color(238, 238, 210));
			    this.onToggle(game.boardgraphics);
				
			}
			
		
			
		};
		
	Setting s2 = new Setting("sounds") {
			
			@Override
			public void onClick() {
				
				game.sound = !game.sound;
				
			    this.getButton().setBackground(game.sound ? new Color(118, 150, 86) : new Color(238, 238, 210));
			    
			    this.onToggle(game.sound);
			    
			    if(game.sound)SoundManager.setSound(3);
			    	
				
			}
		};
		
             Setting s3 = new Setting("Arrows") {
			
			@Override
			public void onClick() {
				
				game.arrows = !game.arrows;
				
			    this.getButton().setBackground(game.arrows ? new Color(118, 150, 86) : new Color(238, 238, 210));
			    this.onToggle(game.arrows);
				
			}
		};
		
		
		Setting s4 = new Setting("Random puzzles") {
			
			@Override
			public void onClick() {
				
			game.randomPuzzles = !game.randomPuzzles;
				
			this.getButton().setBackground(game.randomPuzzles ? new Color(118, 150, 86) : new Color(238, 238, 210));
			 this.onToggle(game.randomPuzzles);
			
			}
		};
		
		
    Setting s5 = new Setting("Animations") {
			
			@Override
			public void onClick() {
				
			game.animations = !game.animations;
				
			this.getButton().setBackground(game.animations ? new Color(118, 150, 86) : new Color(238, 238, 210));
			 this.onToggle(game.animations);
			
			}
		};
		
		
		 Setting s6 = new Setting("Computer") {
				
				@Override
				public void onClick() {
					
				game.computer = !game.computer;
					
				this.getButton().setBackground(game.computer ? new Color(118, 150, 86) : new Color(238, 238, 210));
				 this.onToggle(game.computer);
				
				}
			};
			
		
		
		
		 s1.getButton().setBackground(game.boardgraphics ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s2.getButton().setBackground(game.sound ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s3.getButton().setBackground(game.arrows ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s4.getButton().setBackground(game.randomPuzzles ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s5.getButton().setBackground(game.animations ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 s6.getButton().setBackground(game.computer ? new Color(118, 150, 86) : new Color(238, 238, 210));
		 
		settings.add(s1);
		settings.add(s2);
		settings.add(s3);
		settings.add(s4);
		settings.add(s5);
		settings.add(s6);
		
		
	}
	
	
	
   public void addButtons() {
	   
	   int startX = 120;
	   int startY = 110;
	   int abstand = 100;
	   int count = 0;
	   
	   for(Setting s : settings) {
		   
		   s.getButton().setText(s.getName());
		   
		   s.getButton().setBounds(startX, startY+(abstand*count), 140, 50);
		   
		   game.add(s.getButton());
		   
		   count++;
		  
	   }
	   
	   initLeaveButton();
	   setLeaveButtonAction();
	   
   }
	
   
   private void initLeaveButton() {
	   
	  leaveButton = new Button(" Leave", 600, 1050, 50, 130,Color.white, Color.BLACK);
   	leaveButton.setIcon(new ImageIcon("chess.res/icons/quitIcon.png"));
   	
   	this.setLeaveButtonAction();
   	
   	game.add(leaveButton);
	   
   }
   
   private void setLeaveButtonAction() {
	   
	   leaveButton.addActionListener(e  ->  {
		   
		   
		   onLeave();
		   
	   });
		
	   
   }
   
   
	   
   
   
   private void onLeave() {
	   
	   for(Component c : game.getComponents()) {
		   game.remove(c);
	   }
	   
		if(leaveButton!=null)game.remove(leaveButton);
    	leaveButton = null;
    	
    	settings.clear();
	   
	     game.gameState = GameState.INMENU;

		game.add(Game.getInstance().menuScreen.b1);
		game.add(Game.getInstance().menuScreen.b2);
		game.add(Game.getInstance().menuScreen.b3);
		game.add(Game.getInstance().menuScreen.b4);
	   
   }
	
   public void drawSettingsFeld(Graphics2D g2) {
	   
	for(int i = 0;i<8;i++) {
			
			for(int j = 0;j<8;j++) {
				
				g2.setColor((i+j)%2 == 0 ?  white : black );
				
				g2.fillRect(startX+(j*50),startY+(i*50), 50,50);
				
				
			}
			
		}
	
           drawSettingsInterActive(g2);
	
	
   
   }
   
   public void drawBackGrounnd(Graphics2D g2) {
	   
	  
	   g2.fillRect(0, 0,game.getWidth(), game.getHeight());
	   
   }
   
   public void drawPiece(Graphics2D g2) {
	   
	   g2.drawImage(dPiece.image, startX+dPiece.drawX*50, startY+dPiece.drawY*50, 50, 50, null);
	   g2.drawImage(kPiece.image, startX+kPiece.drawX*50, startY+kPiece.drawY*50, 50, 50, null);
	   g2.drawImage(lPiece.image, startX+lPiece.drawX*50, (startY+lPiece.drawY*50)-3, 50, 50, null);
   }
   
   private void drawSettingsInterActive(Graphics2D g2) {
	   
	
	   drawSettingsInterActiveMarkedFelder(g2);
	   
	   drawSettinsgsInteractiveArrows(g2);
	   
   }
   
   
   private void drawSettinsgsInteractiveArrows(Graphics2D g2) {
	   
	   if(game.arrows) {
		   
		   g2.setColor(Color.ORANGE);
		   g2.drawLine(startX+(5*50)+(25), startY+(4*50), startX+(6*50)+25, startY+(1*50));
		   
		   
		   
	   }
	   
	   
   }
   
   private void drawSettingsInterActiveMarkedFelder(Graphics2D g2) {
	   
	if(game.boardgraphics) {
			
			int yMinus = 3;
			int xMinus = 4;
			
			
			for(int i = yMinus;i>0;i--) {
			
		g2.setColor(Color.ORANGE);
		g2.fillRect(startX+(xMinus*50), startY+(i*50), 50, 50);
		
			xMinus--;
			
			}
		}
	   
   }
	
}
