package com.tulo.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.tulo.entities.Entity;
import com.tulo.entities.Player;
import com.tulo.graficos.Spritesheet;
import com.tulo.graficos.UI;
import com.tulo.world.Backgrounds;
import com.tulo.world.Camera;
import com.tulo.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	
	//Variaveis do tamanho da janela
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3; 
	
	private BufferedImage image;
	
	//Variaveis para mudança de nivel
	private int CUR_LEVEL = 1, MAX_LEVEL = 4;
	
	//Variaveis do game over
	public static String GameState = "MENU";
	private boolean showMessageGameover = true, restart = false;
	private int framesGameover = 0;

	//Variaveis do background
	public static Spritesheet background1;
	public static Spritesheet background2;
	public static Spritesheet background3;
	public static Spritesheet backgroundMENU;
	
	//Variaveis/arrays do menu
	public static Menu menu;
	public static int [] pixels;
	public static int [] lightMap;
	
	//Variaveis para instanciar classes
	public static World world;
	public static List<Entity> entities;
	public static Spritesheet spritesheet;
	public static Spritesheet bonecodomenu;
	public static Player player;
	public static Sound soundTrack;
	
	public UI ui;
	
	//Variaveis para inserir novas fontes
	public InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("pixel.ttf");
	public InputStream stream2 = ClassLoader.getSystemClassLoader().getResourceAsStream("pixel.ttf");
	public InputStream stream3 = ClassLoader.getSystemClassLoader().getResourceAsStream("pixel.ttf");
	public static Font newFont;
	public static Font newFont2;
	public static Font newFont3;
	//obs: foi feita mais de uma fonte igual mudando apenas o tamanho nos codigos um  pouco a baixo
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//comando para definir o tamanho da janela.
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Comandos para iniciar os objetos.
		spritesheet = new Spritesheet("/spritesheet.png");
		background1 = new Spritesheet("/level1BCK.png");
		background2 = new Spritesheet("/level2BCK.png");
		background3 = new Spritesheet("/level3BCK.png");
		backgroundMENU = new Spritesheet("/backgroundMENU.png");
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2 - 30,HEIGHT/2,16,16,1.4,Entity.PLAYER_SPRITE_RIGHT[0]);

		world = new World("/level"+CUR_LEVEL+".png");
		ui = new UI();
		
		lightMap = new int[WIDTH*HEIGHT];
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		menu = new Menu();//inicia menu
		
		soundTrack = new Sound("/sound/country banjo", true);
		
		//Comandos para inserir a nova fonte em diferentes tamanhos
		try {
			newFont = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(45f);
			newFont2 = Font.createFont(Font.TRUETYPE_FONT, stream2).deriveFont(60f);
			newFont3 = Font.createFont(Font.TRUETYPE_FONT, stream3).deriveFont(150f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		entities.add(player);
		
	}
	
	//Comandos de configuração da janela
	public void initFrame(){
		frame = new JFrame("Plataforma Reciclagem");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Comando q finaliza o programa sem deixar ele rodando fechado
		frame.setVisible(true);
	}
	
	//Metodo de start do jogo
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	//Metodo de stop do jogo
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Metodo principal do java
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	//Metodo que controla o update e a logica do jogo
	public void tick(){
		
		if (GameState == "MENU") {
			menu.tick();
		}
		
		if(GameState == "NORMAL") {
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				this.restart = false;
				e.tick();
			} 
		
		//Else if para fazer animação do game over
		} else if(GameState == "GAME_OVER") {
			this.framesGameover++;
			if(framesGameover == 25) {
				this.framesGameover = 0;
				if(this.showMessageGameover) {
					this.showMessageGameover = false;
				} else {
					this.showMessageGameover = true;
				}
			}
		}
		
		//if para passar de nivel
		if(Player.currentCoins == Player.maxCoins) {
			this.CUR_LEVEL++;
			Game.entities.clear();
			entities.add(player);
			Player.currentCoins = 0;
			Player.maxCoins = 0;
			Player.currentSecret = 0;
			Player.currentEnemies = 0;
			Player.maxEnemies = 0;
			Player.life = 3;
			world = new World("/level"+CUR_LEVEL+".png");
			
		}else if(Player.currentSecret == 1) {
			this.CUR_LEVEL=3;
			Game.entities.clear();
			entities.add(player);
			Player.currentCoins = 0;
			Player.maxCoins = 0;
			Player.currentSecret = 0;
			Player.currentEnemies = 0;
			Player.maxEnemies = 0;
			Player.life = 3;
			world = new World("/level3.png");
			
		} else if(CUR_LEVEL >= MAX_LEVEL) {
				this.CUR_LEVEL = 1;
				Game.entities.clear();
				entities.add(player);
				Player.currentCoins = 0;
				Player.maxCoins = 0;
				Player.currentSecret = 0;
				Player.currentEnemies = 0;
				Player.maxEnemies = 0;
				world = new World("/level1.png");
		
				}			
	}
	


	//Metodo de renderização dos graficos
	public void render(){
		
		//Codigos que são usados para retornar o grafico do jogo
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}		
		Graphics g = image.getGraphics();	
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		
		if(CUR_LEVEL == 1) {
			g.drawImage(Backgrounds.BC1, -Camera.x, -Camera.y, 3840, 160, null);			
		} else if(CUR_LEVEL == 2) {
			g.drawImage(Backgrounds.BC2, -Camera.x, -Camera.y, 3840, 160, null);
		} else if(CUR_LEVEL == 3) {
			g.drawImage(Backgrounds.BC3, -Camera.x, -Camera.y, 512, 768, null);
		}
	
		//Comandos focados na renderização do jogo

		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		
		//If para executar menu
		if(GameState == "MENU") {
			menu.render(g);				
		}
		
		//If para executar o game over e aparecer a mensagem de game over e restarta o jogo
		if(GameState == "GAME_OVER") {
						
			Graphics2D g2 = (Graphics2D) g;
			
			//Comandos da escrita do Game Over
			g.setFont(Game.newFont3);
			g2.setColor(new Color(0,0,0,220));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setColor(Color.red);
			g.drawString("GAME OVER",(Game.WIDTH*Game.SCALE) - 550 ,210);
		
			g.setFont(Game.newFont2);
			g.setColor(Color.red);
			
			//if da animação do ">Pressine Enter..."
			if(showMessageGameover)
				g.drawString(">Pressione Enter para reiniciar o jogo<",(Game.WIDTH*Game.SCALE) - 680 ,260);
			
			//Comandos para restarta o level
			if(restart == true) {
				GameState = "NORMAL";
				Game.entities.clear();
				entities.add(player);
				world = new World("/level"+CUR_LEVEL+".png");
				Player.currentCoins = 0;
				Player.life = 3;
				Player.maxCoins = Player.maxCoins/2;
				Player.currentEnemies = 0;
				Player.maxEnemies = Player.maxEnemies/2;
				Game.player.updateCamera();
			}
		}
		
		bs.show();
		
	}
	
	//Metodo de controle do loop do jogo
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0; //FPS
		
		 //variavel q vai ser usada para saber o momento certo q o jogo tera q fazer um update
		double ns = 1000000000 / amountOfTicks;
		
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			
			//IF que controla o FPS
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			//If para mostrar no console se realmente esta rodando na quantidade de FPS informado na variavel amountOfTicks
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	//Comandos para controlar o jogador
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = true;
		}
		
		//Foi usado dois if para o jogador poder pular e ir para uma direção ao mesmo tempo
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
		
		//MENU - up
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(GameState == "MENU") {
				menu.UP = true;
			}
		//MENU - down
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(GameState == "MENU") {
				menu.DOWN = true;
			}
			
		}
		//Enter do reinicio
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			restart = true;
			//MENU - ENTER
			if (GameState == "MENU") {
				menu.OK = true;
			}
		}
	}
		

	
	//Comandos para detectar quando o ususario parou de aperta as teclas para andar
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.right = false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.left = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
