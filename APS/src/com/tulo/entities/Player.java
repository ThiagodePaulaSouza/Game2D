package com.tulo.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.tulo.main.Game;
import com.tulo.world.Camera;
import com.tulo.world.World;


public class Player extends Entity{

	
	public boolean right,left;
	
	public static double life = 3;//Variavel da vida
	
	public static int currentCoins = 0;//Variavel da quantidade atual de moedas que o jogador pegou
	public static int maxCoins = 0;//Variavel do maximo de moedas existente
	
	public static int currentEnemies = 0;
	public static int maxEnemies = 0;
	
	public static int currentSecret = 0;
	
	public int dir = 1;
	private double gravity = 2;//Varivel da gravidade
	
	//Variaveis do pulo
	public boolean jump = false;
	public boolean isJumping = false;
	public int jumpHeight = 40;//Altura do pulo
	public int jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrames = 15;//Variavel da velocidade da animação
	
	private int maxSprite = 3;//Variavel da quantia maxima de sprite usados em cada direção
	private int curSprite = 0;
	
	//Metodo com variaveis de coordenadas, altura, largura, velocidade e sprite importando da classe Entity
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	//Metodo onde é feito a logica do player
	public void tick(){
		depth = 2;
		if(World.isFree((int)x,(int)(y+gravity)) && isJumping == false) {
			y+=gravity;
			for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					
					//Comandos de pulo do jogador
					isJumping = true;
					//jumpHeight = 36;
					
					//Comandos de causar dano ao inimigo
					((Enemy) e).vida--;
					if(((Enemy) e).vida == 0) {
						
						//Comando para matar o inimigo
						Game.entities.remove(i);
						Player.currentEnemies++;
						break;
					}
				}
			}
			
			}
		}
		//If para verificar se é possivel andar para a direita
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=speed;
			dir = 1;
		}
		//Else if para verificar se é possivel andar para a esquerda
		else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=speed;
			dir = -1;
		}
		
		//If para verificar se é possivel pular
		if(jump) {
			if(!World.isFree(this.getX(),this.getY()+1)) {
				isJumping = true;
			}
			else {
				jump = false;
			}
		}
		
		//If para realizar o pulo
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		
		//Comandos para detectar dano
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Enemy) {
				if(Entity.isColidding(this, e)) {
					if(Entity.rand.nextInt(100) < 5)
						life--;
						
						//If para detectar quando a vida chegar 0 e mudar a variavel para executar o game over
						if(life <= 0) {
							Game.GameState = "GAME_OVER";
						}
				}
			}
			
		}
		
		//Comandos de colisao das moedas e aumento da contagem da mesmas
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity e = Game.entities.get(i);
			if(e instanceof Plastic || e instanceof Cardboard || e instanceof Glass 
									|| e instanceof Paper || e instanceof Battery) {
				
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(i);
					Player.currentCoins++;
					break;
				}
			}
			if(e instanceof Secret) {
				if(Entity.isColidding(this, e)) {
					Game.entities.remove(i);
					Player.currentSecret++;
					break;
				}
			}
		}
		
		//Comandos que controlam a camera
		Camera.x = Camera.clamp((int)x - Game.WIDTH / 2, 0, World.WIDTH * 16 - Game.WIDTH);
		Camera.y = Camera.clamp((int)y - Game.HEIGHT / 2, 0, World.HEIGHT * 16 - Game.HEIGHT);
		
		
	}
	
	//Metodo para renderizar o player
	public void render(Graphics g){
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		//If e else if que atualiza o sprite atual do player
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		}else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		
		super.render(g);
	}
	

	


}
