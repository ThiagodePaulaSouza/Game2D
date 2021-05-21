package com.tulo.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.Random;

import com.tulo.main.Game;
import com.tulo.world.Camera;
import com.tulo.world.World;

public class Entity {
	

	//Comandos que carregam o sprite do jogador
	public static BufferedImage[] PLAYER_SPRITE_RIGHT = {Game.spritesheet.getSprite(0, 128, 16, 16),Game.spritesheet.getSprite(16, 128, 16, 16),Game.spritesheet.getSprite(32, 128, 16, 16)};
	public static BufferedImage[] PLAYER_SPRITE_LEFT = {Game.spritesheet.getSprite(0, 144, 16, 16),Game.spritesheet.getSprite(16, 144, 16, 16),Game.spritesheet.getSprite(32, 144, 16, 16)};
	
	//Comandos que carregam o sprite dos inimigos
	public static BufferedImage[] ENEMY1_RIGHT = {Game.spritesheet.getSprite(32, 0, 16, 16), Game.spritesheet.getSprite(48, 0, 16, 16), Game.spritesheet.getSprite(64, 0, 16, 16), Game.spritesheet.getSprite(80, 0, 16, 16)};
	public static BufferedImage[] ENEMY1_LEFT = {Game.spritesheet.getSprite(32, 0, 16, 16), Game.spritesheet.getSprite(48, 0, 16, 16), Game.spritesheet.getSprite(64, 0, 16, 16), Game.spritesheet.getSprite(80, 0, 16, 16)};
	
	//Comandos que carregam os sprites das reciclagens
	public static BufferedImage[] PAPER = {Game.spritesheet.getSprite(48,32,16,16), Game.spritesheet.getSprite(48,48,16,16), Game.spritesheet.getSprite(48,64,16,16), 
											Game.spritesheet.getSprite(96,80,16,16), Game.spritesheet.getSprite(96,96,16,16), Game.spritesheet.getSprite(48,32,16,16)};
	
	public static BufferedImage[] GLASS = {Game.spritesheet.getSprite(64,32,16,16), Game.spritesheet.getSprite(64,48,16,16), Game.spritesheet.getSprite(64,64,16,16), 
											Game.spritesheet.getSprite(96,80,16,16), Game.spritesheet.getSprite(96,96,16,16), Game.spritesheet.getSprite(64,32,16,16)};
	
	public static BufferedImage[] CARDBOARD = {Game.spritesheet.getSprite(80,32,16,16), Game.spritesheet.getSprite(80,48,16,16), Game.spritesheet.getSprite(80,64,16,16), 
												Game.spritesheet.getSprite(96,80,16,16), Game.spritesheet.getSprite(96,96,16,16), Game.spritesheet.getSprite(80,32,16,16)};
	
	public static BufferedImage[] PLASTIC = {Game.spritesheet.getSprite(96,32,16,16), Game.spritesheet.getSprite(96,48,16,16), Game.spritesheet.getSprite(96,64,16,16), 
												Game.spritesheet.getSprite(96,80,16,16), Game.spritesheet.getSprite(96,96,16,16), Game.spritesheet.getSprite(96,32,16,16)};
	
	public static BufferedImage[] SECRET = {Game.spritesheet.getSprite(96,32,16,16), Game.spritesheet.getSprite(96,48,16,16), Game.spritesheet.getSprite(96,64,16,16), 
												Game.spritesheet.getSprite(96,80,16,16), Game.spritesheet.getSprite(96,96,16,16), Game.spritesheet.getSprite(96,32,16,16)};
	
	public static BufferedImage[] BATTERY = {Game.spritesheet.getSprite(108,32,16,16), Game.spritesheet.getSprite(108,48,16,16), Game.spritesheet.getSprite(108,64,16,16), 
												Game.spritesheet.getSprite(108,80,16,16), Game.spritesheet.getSprite(108,96,16,16), Game.spritesheet.getSprite(108,32,16,16)};
	
	protected double x;
	protected double y;
	protected int width;
	protected int height;
	protected double speed;
	
	public int depth;
	
	public boolean debug = false;
	
	public BufferedImage sprite;
		
	public static Random rand = new Random();
	
	//Metodo com variaveis de coordenadas, altura, largura, velocidade e sprite
	public Entity(double x,double y,int width,int height,double speed,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
	}
	
	//Codigos usados para comparar dois objetos e organizalos em um arraylist
	public static Comparator<Entity> nodeSorter = new Comparator<Entity>() {
		
		@Override
		public int compare(Entity n0,Entity n1) {
			if(n1.depth < n0.depth)
				return +1;
			if(n1.depth > n0.depth)
				return -1;
			return 0;
		}
		
	};
	
	
	//Metodo que atualiza a camera
	public void updateCamera() {
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2),0,World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2),0,World.HEIGHT*16 - Game.HEIGHT);
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public int getX() {
		return (int)this.x;
	}
	
	public int getY() {
		return (int)this.y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void tick(){}
	
	//Metodo para ver se as entidades estão colidindo
	public static boolean isColidding(Entity e1,Entity e2){
		Rectangle e1Mask = new Rectangle(e1.getX(),e1.getY(),e1.getWidth(),e1.getHeight());
		Rectangle e2Mask = new Rectangle(e2.getX(),e2.getY(),e2.getWidth(),e2.getHeight());
		
		return e1Mask.intersects(e2Mask);
	}
	
	//Metodo de renderização da classe Entity
	public void render(Graphics g) {
		g.drawImage(sprite,this.getX() - Camera.x,this.getY() - Camera.y,null);
	}
	
}
