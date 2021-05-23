package com.tulo.main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound{
		
	//Construtor para rodar som. Recebe 2 parametros o nome do arquivo e se é loop ou não
	public Sound(final String url, final Boolean loopContinuo) {
		//thread inicializada com o metodo run
		  new Thread(new Runnable() {
		    public void run() {
		    //try catch, tenta pegar o som da classe chamada, caso contrario mostra erro.
		      try {
		        Clip clip = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(Game.class.getResourceAsStream(url + ".wav"));
		        clip.open(inputStream);
		        
		        //if caso o loop continuo do construtor seja verdadeiro, caso contrario inicia normalmente
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