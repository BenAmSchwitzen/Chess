package chess.sound;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {

	public static Clip clip;
	public static File startSound = new File("chess.res/sounds/startSound.wav");
	public static File moveSound = new File("chess.res/sounds/moveSound.wav");
	public static File rochadeSound = new File("chess.res/sounds/rochadeSound.wav");
	public static File takeSound = new File("chess.res/sounds/takeSound.wav");
	
	public  static boolean soundAlreadyPlayed = false;
	
	public SoundManager() {
	
		
		
	}
	
         public static void setSound(int index) {
        	 
        	 try {
        		 
        		 AudioInputStream inputStream = AudioSystem.getAudioInputStream(getSelectedSound(index));
        		 Clip clip = AudioSystem.getClip();
        		 clip.open(inputStream);
        		 playSound(clip);
        	 }catch(Exception e) {
        		 
        		 e.printStackTrace();
        		 
        	 }
        	 
        	 
        	 
         }
         
         public static void playSound(Clip clip) {
        	 
        	 clip.start();
        	 
         }
         
         public static File getSelectedSound(int index) {
        	 
        	 switch(index) {
        	 
        	 case 0 : return startSound;
        	 case 1 : return moveSound;
        	 case 2 : return rochadeSound;
        	 case 3 : return takeSound;
        	 
        	 }
        	 
        	 return null;
         }
         
	
}
