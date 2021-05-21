package com.tulo.graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {

	private BufferedImage spritesheet;
	
	public Spritesheet(String path)
	{
		try {
			//Variavel que tras a imagem de um diretorio para si
			spritesheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//Metodo que obtem a imagem de acordo com as coordenas e alturas
	public BufferedImage getSprite(int x,int y,int width,int height){
		return spritesheet.getSubimage(x, y, width, height);
	}
}
