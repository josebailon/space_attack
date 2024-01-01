/**
 * 
 */
package reto8juego.config;

import java.awt.BasicStroke;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Config {
	//LIENZO Y MOTOR
	public static final int FPS=50;
	public static final int ANCHO=800;
	public static final int CENTRO_ANCHO=ANCHO/2;
	public static final int ALTO=1024;
	public static final int CENTRO_ALTO=ALTO/2;
	
	//TEXTO
	public static final int ALTURA_TITULO = 400;
	public static final float T_LETRA_0=100f;
	public static final float T_LETRA_1=30f;
	public static final float T_LETRA_2=50f;
	
	public static final int VELOCIDAD_FONDO = 10;
	public static final int VELOCIDAD_FONDO_RAPIDA = 50;
	
	public static final int DURACION_TRANSICION = 1000;
	
	public static final int INTERVALO_METEORITOS = 5000;

	public static final BasicStroke BORDE_GUI=new BasicStroke(2f);
	public static final int ESQUINA_GUI = 8;
	public static final float MARGEN_GUI = 40;
}

