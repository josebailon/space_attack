/**
 * 
 */
package reto8juego.config;

import java.awt.Color;

/**
 * Configuracion de colores usados en el juego
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Colores {
	
	/**
	 * Color del texto
	 */
	public static final Color CARA_TEXTO=new Color(0.565f, 0.827f, 1);
	
	/**
	 * Color de los bordes de los paneles
	 */
	public static final Color BORDE_GUI = new Color(0.6f, 0.6f, 0.6f);
	
	/**
	 * Color de la barra de vida
	 */
	public static final Color VIDA = new Color(0.0f, .4f, .8f);
	
	/**
	 * Color de la zona de la barra de vida gastada
	 */
	public static final Color FONDO_VIDA = new Color(.6f, 0f, 0f,0.2f);
	
	/**
	 * Color de fondo de los paneles
	 */
	public static final Color FONDO_GUI = new Color(0.09f, 0.38f, 0.569f, 0.412f);
	
	/**
	 * Color de fondo de panel modal(usado durante el mensaje de pausa)
	 */
	public static final Color FONDO_NEGRO_TRANSP = new Color(0f, 0f, 0f, 0.8f);
}
