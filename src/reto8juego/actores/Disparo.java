/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Disparo extends Dibujo {
 
	BufferedImage img;
	int fuerza=0;
	
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

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		x+=deltaSegundo*vX;
		y+=deltaSegundo*vY;
		if (x<0||y<0||x>motor.getAncho()||y>motor.getAlto())
			this.vivo=false;
	}

	/**
	 * @return
	 */
	public int getFuerza() {
		return fuerza;
	}

}
