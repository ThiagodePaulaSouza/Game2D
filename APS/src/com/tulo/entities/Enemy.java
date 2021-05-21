package com.tulo.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tulo.world.World;

public class Enemy extends Entity{
	
	private int framesAnimationE = 0;
	private int maxFramesE = 30;
	private int curSpriteE = 0;
	private int maxSpriteE = 4;
	
	public boolean right = true,left = false;
	
	public int vida = 1;//Variavel da vida do inimigo

	//Metodo com variaveis de coordenadas, altura, largura e sprite importando da classe Entity
	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	//Metodo onde é feito a logica do inimigo
	public void tick() {
		if(World.isFree((int)x,(int)(y+1))) {
			y+=1;
		}else {
		
		//Comandos para o inimigo mover para direita
		if(right) {
			if(World.isFree((int)(x+speed), (int)y)) {
			x+=speed/2;
			if(World.isFree((int)(x+16),(int)y+1)) {
				right = false;
				left = true;
			}
			}else {
				right = false;
				left = true;
			}
		}
		
		//Comandos para o inimigo mover para esquerda
		if(left) {
			if(World.isFree((int)(x-speed), (int)y)) {
				x-=speed/2;
			if(World.isFree((int)(x-16),(int)y+1)) {
				right = true;
				left = false;
			}
			}else {
				right = true;
				left = false;
			}
		}
		
		
		}
	}
	
	//Metodo onde renderiza o inimigo
	public void render(Graphics g) {
		
		framesAnimationE++;
		if(framesAnimationE == maxFramesE) {
			curSpriteE++;
			framesAnimationE = 0;
			if(curSpriteE == maxSpriteE) {
				curSpriteE = 0;
			}
		}
		
		if(right)
		sprite = Entity.ENEMY1_RIGHT[curSpriteE];
		else if(left)
		sprite = Entity.ENEMY1_LEFT[curSpriteE];
		
		super.render(g);
	}

}
