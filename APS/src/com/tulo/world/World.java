package com.tulo.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.tulo.entities.Battery;
import com.tulo.entities.Cardboard;
import com.tulo.entities.Enemy;
import com.tulo.entities.Entity;
import com.tulo.entities.Glass;
import com.tulo.entities.Paper;
import com.tulo.entities.Plastic;
import com.tulo.entities.Player;
import com.tulo.entities.Secret;
import com.tulo.main.Game;

public class World {

	public static Tile[] tiles;//array dos tiles
	public static int WIDTH,HEIGHT;//Variaveis de altura e largura
	public static final int TILE_SIZE = 16; //Variavel do tamanho tile
	
	public BufferedImage sprite;
	
	public World(String path){
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];//Array com a quantidade de pixels do mapa
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tile[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(),pixels, 0, map.getWidth());
			for(int xx = 0; xx < map.getWidth(); xx++){
				
				for(int yy = 0; yy < map.getHeight(); yy++){
					
					//Variavel do pixelatual onde é usado para ver a cor do pixel do mapa e colocar o sprite certo naquele pixel
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					
					//Validação dos pixels do Chao da cidade
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);					
					if(pixelAtual == 0xFF000000) {
						tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16,yy*16,Tile.TILE_FLOOR);
					
					//Validação dos pixels das paredes
					}else if(pixelAtual == 0xFFffffff) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Tile.TILE_WALL);
						
						if(yy-1 >= 0 && pixels[xx+( (yy-1) * map.getWidth())] == 0xFFffffff) {
							tiles[xx + (yy * WIDTH)] = new WallTile(xx*16,yy*16,Game.spritesheet.getSprite(16, 0, 16, 16));
						}
						
					//Validação dos pixels do fundo invisivel q n pd passar
					}else if(pixelAtual == 0xFF808080) {
						tiles[xx + (yy * WIDTH)] = new WallBlock(xx*16,yy*16,Tile.TILE_SKYBLOCK);
							
						if(yy-1 >= 0 && pixels[xx+( (yy-1) * map.getWidth())] == 0xFF808080) {
							tiles[xx + (yy * WIDTH)] = new WallBlock(xx*16,yy*16,Game.spritesheet.getSprite(0,0,16,16));
						}	
						
					//Validação dos pixels da Areia
					}else if(pixelAtual == 0xFFf7ff68) {
						tiles[xx + (yy * WIDTH)] = new FloorSand(xx*16,yy*16,Tile.TILE_SAND);
							
						if(yy-1 >= 0 && pixels[xx+( (yy-1) * map.getWidth())] == 0xFFf7ff68) {
							tiles[xx + (yy * WIDTH)] = new FloorSand(xx*16,yy*16,Game.spritesheet.getSprite(32,16,16,16));
						}
					
					//Validação dos pixels da Grama
					}else if(pixelAtual == 0xFF00ff04) {
						tiles[xx + (yy * WIDTH)] = new FloorGrass(xx*16,yy*16,Tile.TILE_GRASS);
							
						if(yy-1 >= 0 && pixels[xx+( (yy-1) * map.getWidth())] == 0xFF00ff04) {
							tiles[xx + (yy * WIDTH)] = new FloorGrass(xx*16,yy*16,Game.spritesheet.getSprite(48,16,16,16));
						}
						
					//Validação dos pixels de spawn do Jogador
					}else if(pixelAtual == 0xFF0026FF) {
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						
					//Validação dos pixels de spawn dos Inimigos
					}else if(pixelAtual == 0xFFFF0000) {
						Enemy enemy = new Enemy(xx*16,yy*16,16,16,1,Entity.ENEMY1_RIGHT[1]) ;
						Game.entities.add(enemy);
						Player.maxEnemies++;
					
					//Validação dos pixels de spawn das Reciclagem
					}else if(pixelAtual == 0xFFFFD800) {
						Paper paper = new Paper(xx*16,yy*16,16,16,1,Entity.PAPER[1]);
						Game.entities.add(paper);
						Player.maxCoins++;
					} else if(pixelAtual == 0xFF00ff21) {
						Glass glass = new Glass(xx*16,yy*16,16,16,1,Entity.GLASS[1]);
						Game.entities.add(glass);
						Player.maxCoins++;
					} else if(pixelAtual == 0xFF00f6ff) {
						Cardboard cardboard = new Cardboard(xx*16,yy*16,16,16,1,Entity.CARDBOARD[1]);
						Game.entities.add(cardboard);
						Player.maxCoins++;
					} else if(pixelAtual == 0xFFff00e9) {
						Plastic plastic = new Plastic(xx*16,yy*16,16,16,1,Entity.PLASTIC[1]);
						Game.entities.add(plastic);
						Player.maxCoins++;
					} else if(pixelAtual == 0xFF5d00ff) {
						Battery battery = new Battery(xx*16,yy*16,16,16,1,Entity.BATTERY[1]);
						Game.entities.add(battery);
						Player.maxCoins++;					
					
					//Validação do pixels do secreto
					} else if(pixelAtual == 0xFFff927f) {
						Secret secret = new Secret(xx*16,yy*16,16,16,1,Entity.SECRET[1]);
						Game.entities.add(secret);
					}					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Sistema de colisão dos tiles
	public static boolean isFree(int xnext,int ynext){
		
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		//Validação dos tiles colidindo
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile) ||
				
				(tiles[x1 + (y1*World.WIDTH)] instanceof WallBlock) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallBlock) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallBlock) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallBlock) ||
				
				(tiles[x1 + (y1*World.WIDTH)] instanceof FloorSand) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof FloorSand) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof FloorSand) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof FloorSand) ||
				
				(tiles[x1 + (y1*World.WIDTH)] instanceof FloorGrass) ||
				(tiles[x2 + (y2*World.WIDTH)] instanceof FloorGrass) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof FloorGrass) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof FloorGrass));
	}
	
	
	//Metodo de renderização do mapa/nivel e comandos da camera
	public void render(Graphics g){
		
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT)
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
}
