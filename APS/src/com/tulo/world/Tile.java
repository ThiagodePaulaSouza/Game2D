package com.tulo.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tulo.main.Game;

public class Tile {
	
	//Comando que carregam os sprites dos blocos e objetos estaticos
	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0,0,16,16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16,0,16,16);
	public static BufferedImage TILE_SAND = Game.spritesheet.getSprite(32,16,16,16);
	public static BufferedImage TILE_GRASS = Game.spritesheet.getSprite(48,16,16,16);
	public static BufferedImage TILE_SKYBLOCK = Game.spritesheet.getSprite(0,0,16,16);

	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x,int y,BufferedImage sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	//Metodo de renderização dos tiles
	public void render(Graphics g){
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}

}
