/**
 * 
 */
package reto8juego.config;

import java.awt.BasicStroke;

/**
 * Configuraciones generales
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Config {
	// LIENZO Y MOTOR
	// ####################################################################
	/**
	 * FPS maximo del juego
	 */
	public static final int FPS = 60;
	
	/**
	 * ancho de la ventana
	 */
	public static final int ANCHO = 800;
	
	/**
	 * Localizacon del centro horizontal
	 */
	public static final int CENTRO_ANCHO = ANCHO / 2;
	
	/**
	 * Alto de la ventana
	 */
	public static final int ALTO = 960;
	
	/**
	 * Localizacion del centro vertical
	 */
	public static final int CENTRO_ALTO = ALTO / 2;

	// TEXTOS
	// ####################################################################
	
	/**
	 * Posicion el titulo del juego
	 */
	public static final int ALTURA_TITULO = 400;
	
	/**
	 * Tamano de letra del titulo
	 */
	public static final float T_LETRA_TITULO = 100f;
	
	/**
	 * Tamano de letra 0
	 */
	public static final float T_LETRA_0 = 20f;

	/**
	 * Tamano de letra 1
	 */
	public static final float T_LETRA_1 = 30f;
	
	/**
	 * Tamano de letra 2
	 */
	public static final float T_LETRA_2 = 50f;
	
	
	
	

	// GUI
	// ####################################################################
	
	/**
	 * Duracion de la transicion por defecto(usado en los efectos de desvanecimiento de texto)
	 */
	public static final int DURACION_TRANSICION = 1000;
	
	/**
	 * Stroke usado en el dibujado de la GUI
	 */
	public static final BasicStroke BORDE_GUI = new BasicStroke(2f);
	
	/**
	 * Radio para ciertas esquinas de lala GUI
	 */
	public static final int ESQUINA_GUI = 8;
	
	/**
	 * Margen de los elementos inferiores de la GUI
	 */
	public static final float MARGEN_GUI = 40;
	
	/**
	 * Velocidad a la que se mueve el fondo
	 */
	public static final int VELOCIDAD_FONDO = 10;
	

	
	
	
	
	// PARTIDA
	// ####################################################################

	/**
	 * Velocidad a la que se mueven los premios
	 */
	public static final int VELOCIDAD_PREMIOS = 100;
	
	/**
	 * Intervalo de creacion de meteoritos
	 */
	public static final int INTERVALO_METEORITOS = 5000;
	
	/**
	 * Salud maxima de la nave
	 */
	public static final int SALUD_MAXIMA = 100;
	
	/**
	 * Vidas iniciales
	 */
	public static final int VIDAS_INICIALES = 3;
}
