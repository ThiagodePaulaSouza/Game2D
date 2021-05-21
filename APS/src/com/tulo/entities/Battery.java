package com.tulo.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Battery extends Entity{
	
	//Variaveis de animação das reciclagens
	private int framesAnimationR = 0;
	private int maxFramesR = 10;
	private int curSpriteR = 0;
	private int maxSpriteR = 5;

	//Metodo com variaveis de coordenadas, altura, largura, velocidade e sprite importando da classe Entity
	public Battery(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	//Metodo onde renderiza os papelao
	public void render(Graphics g) {
		
		framesAnimationR++;
		if(framesAnimationR == maxFramesR) {
			curSpriteR++;
			framesAnimationR = 0;
			if(curSpriteR == maxSpriteR) {
				curSpriteR = 0;
			}
		}
				
		sprite = Entity.BATTERY[curSpriteR];
		
		super.render(g);
	}

}