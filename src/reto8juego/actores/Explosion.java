/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * Animacion de explosion. Muestra todos los fotogramas de la animacion y muere.
 * Hay dos tipos, uno para los meteoritos y otro para las naves
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Explosion extends Dibujo {

	/**
	 * Prefijo de imagenes de los fotogramas de la animacion de explosion para el
	 * meteorito
	 */
	public static final String METEORITO = "expmeteo";

	/**
	 * Prefijo de imagenes de los fotogramas de la animacion de explosion para las
	 * naves
	 */
	public static final String NAVE = "expnave";

	/**
	 * Lista de fotogramas de la animacion
	 */
	private BufferedImage[] fotogramas = new BufferedImage[9];
	
	/**
	 * Indice del fotograma actual
	 */
	private int fotogramaActual = 0;

	
	/**
	 * Constructor
	 * 
	 * @param x Posicion X
	 * @param y Posicion Y
	 * @param nombre nombre de las imaenes de la animacion en el gestor de Recursos
	 */
	public Explosion(double x, double y, String nombre) {
		super(x, y);
		
		//recoger fotogramas
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg(nombre + i);
		}
		
		//calcular dimensiones
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
	}

	/**
	 * Dibuja la imagen del fotograma actual
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual], at, null);
		at.setToIdentity();
	}

	/*
	 * Avanza el fotograma e la animacion y si no quedan fotogramas mata el actor
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		fotogramaActual++;
		if (fotogramaActual >= fotogramas.length) {
			fotogramaActual = fotogramas.length - 1;
			vivo = false;
		}
	}

}
