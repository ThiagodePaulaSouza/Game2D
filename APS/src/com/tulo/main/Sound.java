package com.tulo.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound{
		
	//metodo para rodar som. Sound("/som", true)
	public Sound(final String url, final Boolean loopContinuo) {
		  new Thread(new Runnable() {
		    public void run() {
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Game.class.getResourceAsStream(url + ".wav"));
		        clip.open(inputStream);
		        if (loopContinuo == true) {
		        	clip.loop(Clip.LOOP_CONTINUOUSLY);
		        	Thread.sleep(10000);					
				}else {
					clip.start();
				}
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
}