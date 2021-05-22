package com.tulo.world;

public class Camera {

	public static int x = 0;
	public static int y = 1;//alterado
	
	//Metodo que vai ser usado para camera n ultrapassar o tamanho do mapa
	public static int clamp(int Atual,int Min,int Max){
		if(Atual < Min){
			Atual = Min;
		}
		
		if(Atual > Max) {
			Atual = Max;
		}
		
		return Atual;
	}
	
}
