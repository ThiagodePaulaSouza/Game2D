package com.tulo.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tulo.world.Backgrounds;
import com.tulo.world.Camera;

public class Menu {
	
	public String[] options = {"Start", "Exit"};
	public int currentOptions = 0;
	public int maxOptions = options.length - 1;
	public boolean UP, DOWN, OK;
	public BufferedImage player;
	public BufferedImage inimigo;
	public static Sound soundTrack;
	
	public boolean pause = false;
	
	public Menu() {
		player = Game.spritesheet.getSprite(0, 128, 16, 16);
		inimigo = Game.spritesheet.getSprite(48, 0, 16, 16);
	}
	
	public void tick() {
		
		if (DOWN==true) {
			DOWN = false;
			currentOptions ++;
			if(currentOptions > maxOptions) {
				currentOptions = 0;
			}
		}
		
		if (UP==true) {
			UP = false;
			currentOptions --;
			if (currentOptions < 0) {
				currentOptions = maxOptions;
			}
		}
		
		
		
		if (OK) {
			OK = false;
			if (currentOptions == 0) { //inicia jogo
				Game.GameState = "NORMAL";
			}else if (currentOptions == 1) { //sai do jogo
				System.exit(1);
			}
		}
	}
	public void render(Graphics g) {
		
		
		g.setColor(new Color(30,88,66)); //cor do fundo
		g.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
		g.drawImage(Backgrounds.BCmenu, -Camera.x, -Camera.y, 720, 480, null);
		g.setColor(new Color(255,255,255)); //cor da fonte
		g.drawString("Start", 330, 280);
		g.drawString("Exit", 337, 350);
		g.drawImage(player.getScaledInstance(200, 200, 0), 50, 240, null);
		g.drawImage(inimigo.getScaledInstance(200, 200, 0), 510, 250, null);
		if(options[currentOptions] == "Start") { g.drawImage(player.getScaledInstance(50, 50, 0), 280, 240, null);
		}else if(options[currentOptions] == "Exit") { g.drawImage(player.getScaledInstance(50, 50, 0), 286, 310, null);
		}
	}
}

