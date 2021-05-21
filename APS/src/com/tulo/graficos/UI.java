package com.tulo.graficos;


import java.awt.Color;
import java.awt.Graphics;

import com.tulo.entities.Player;
import com.tulo.main.Game;

public class UI {

	//Metodo onde mostrar a vida e a quantidade de reciclagens coletadas
	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(10, 10,150,30);
		g.setColor(Color.green);
		g.fillRect(10, 10,(int)((Player.life/3) * 150), 30);
		g.setColor(Color.white);
		g.drawRect(10, 10, 150, 30);
		g.setColor(Color.red);
		g.setFont(Game.newFont);
		g.drawString("Reciclagens: "+Player.currentCoins+"/"+Player.maxCoins,(Game.WIDTH*Game.SCALE) - 225 ,70);
		g.drawString("Inimigos destruidos: "+Player.currentEnemies+"/"+Player.maxEnemies,(Game.WIDTH*Game.SCALE) - 315 ,35);
	}
	
}
