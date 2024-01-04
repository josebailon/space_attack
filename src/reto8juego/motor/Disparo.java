/**
 * 
 */
package reto8juego.motor;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Disparo. Contiene una imagen posicion, velocida y fuerza.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Disparo extends Dibujo {
 
	/**
	 * Imagen del disparo
	 */
	private BufferedImage img;
	
	/**
	 * Fuerza del disparo
	 */
	private int fuerza=0;
	
	
	/**
	 * Constructor
	 * @param img Imagen que se dibujara del disparo
	 * @param x Posicion x 
	 * @param y Posicion y
	 * @param vx Velocidad en x
	 * @param vy Velocidad en y
	 * @param fuerza Fuerza ejerciza al impactar
	 */
	public Disparo(BufferedImage img, double x,double y, double vx,double vy ,int fuerza) {
		super(x,y);
		this.img = img;
		ancho = img.getWidth();
		alto = img.getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		this.vX=vx;
		this.vY=vy;
		this.fuerza=fuerza;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(img, at, null);
	}

	/**
	 * Calcula la nueva posicion en funcion del vector velocidad. Si se sale de la pantalla muere
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		x+=deltaSegundo*vX;
		y+=deltaSegundo*vY;
		if (x<0||y<0||x>motor.getAncho()||y>motor.getAlto())
			this.vivo=false;
	}

	/**
	 * Devuelve la fuerza
	 * 
	 * @return La fuerza
	 */
	public int getFuerza() {
		return fuerza;
	}
	
	/**
	 * Efecto a hacer sobre el disparo en si cuando haya impactado.
	 * Por defecto el disparo muere.
	 */
	public void impactado() {
		this.vivo=false;
	}

}
