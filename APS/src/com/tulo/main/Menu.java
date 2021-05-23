package com.tulo.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tulo.world.Backgrounds;
import com.tulo.world.Camera;

public class Menu {
	
	//inicializa variaveis e atributos
	public String[] options = {"Start", "Exit"};
	public int currentOptions = 0;
	public int maxOptions = options.length - 1;
	public boolean UP, DOWN, OK;
	public BufferedImage player;
	public BufferedImage inimigo;
	public static Sound soundTrack;
	public boolean pause = false;
	
	//Construtor do menu - adiciona as Sprites aos atributos locais
	public Menu() {
		player = Game.spritesheet.getSprite(0, 128, 16, 16);
		inimigo = Game.spritesheet.getSprite(48, 0, 16, 16);
	}
	
	//Metodo Tick - Realiza logica por tras do menu
	public void tick() {
		
		//If que controla a tecla de Descer
		if (DOWN==true) {
			DOWN = false;
			currentOptions ++;
			if(currentOptions > maxOptions) {
				currentOptions = 0;
			}
		}
		
		//If que controla a tecla de Subir
		if (UP==true) {
			UP = false;
			currentOptions --;
			if (currentOptions < 0) {
				currentOptions = maxOptions;
			}
		}
		
		//If que controla a tecla ENTER
		if (OK) {
			OK = false;
			
			//muda o gameState pra normal
			if (currentOptions == 0) { 
				Game.GameState = "NORMAL";
				
			//sai do jogo
			}else if (currentOptions == 1) { 
				System.exit(1);
			}
		}
	}
	public void render(Graphics g) {
		
		//renderiza UI do menu
		g.drawImage(Backgrounds.BCmenu, -Camera.x, -Camera.y, 720, 480, null);
		g.setColor(new Color(255,255,255));
		g.drawString("Start", 330, 280);
		g.drawString("Exit", 337, 350);
		g.drawImage(player.getScaledInstance(200, 200, 0), 50, 240, null);
		g.drawImage(inimigo.getScaledInstance(200, 200, 0), 510, 250, null);
		
		//muda a posição do Sprite da selecao
		if(options[currentOptions] == "Start") { g.drawImage(player.getScaledInstance(50, 50, 0), 280, 240, null);
		}else if(options[currentOptions] == "Exit") { g.drawImage(player.getScaledInstance(50, 50, 0), 286, 310, null);
		}
	}
}

