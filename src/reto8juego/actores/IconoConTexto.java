/**
 * 
 */
package reto8juego.actores;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * Linea con icono y texto. Usado para generar las lineas de las instrucciones.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class IconoConTexto extends Dibujo {
	
	/*
	 * Imagen del icono
	 */
	private BufferedImage icono = null;
	
	/**
	 * Texto de la linea
	 */
	private String texto;
	
	/**
	 * Fuente usada
	 */
	private Font fuente;
	
	/**
	 * dimension del icono
	 */
	private final int tamIcono = 66;
	
	/**
	 * Define si el texto esta centrado o no en pantalla
	 */
	private boolean centrado = false;

	/**
	 * Constructor 
	 * 
	 * @param texto Texto de la linea
	 * @param icono Identificador de la imagen del icono en el gestor de recursos
	 * @param x Posicion x del inicio de la linea(o del centro si centrado es true)
	 * @param y Posicion y de la linea
	 * @param centrado True si el texto debe estar centrado en la coordenada x
	 * 
	 * @see Recursos
	 * @see Instrucciones
	 */
	public IconoConTexto(String texto, String icono, double x, double y, boolean centrado) {
		super();
		this.texto = texto;
		
		//recoger bufferedimage del icono desde los recursos
		if (icono != null)
			this.icono = Recursos.getInstancia().getImg(icono);
		
		this.x = x;
		this.y = y;
		this.centrado = centrado;
		
		//construir la fuente
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_0);
	}

	/**
	 * Dibuja la linea
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		//definir fuente y calcular tamano del texto
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int desfaseAlto = metrics.getHeight() / 2;
		int desfaseAncho = 0;
		
		//si es centrado calcular el desfase
		if (centrado)
			desfaseAncho = metrics.stringWidth(texto) / 2;
		
		//si hay icono dibujarlo
		if (icono != null)
			g2d.drawImage(icono, (int) x - desfaseAncho - 10 - tamIcono, (int) y - icono.getHeight(), tamIcono,
					tamIcono, null);
		
		//dibujar el texto
		g2d.setColor(Colores.CARA_TEXTO);
		g2d.drawString(texto, (int) x - desfaseAncho, (int) y - desfaseAlto);

	}

}
