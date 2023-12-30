/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import reto8juego.motor.AnimacionFrenada;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Nave extends Dibujo {
	BufferedImage[] fotogramas = new BufferedImage[15];
	int ancho=1;
	int alto=1;
	int mitadAncho=1;
	int mitadAlto=1;
	AnimacionFrenada a ;
	public Nave() {
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg("nave" + i);
		}
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto=alto/2;
		mitadAncho=ancho/2;
		x=400d;
		y=1124d;
		a = new AnimacionFrenada(3000, 1124, 500, null);
		a.start();
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x-mitadAncho, y-mitadAlto);
		g2d.drawImage(fotogramas[7],at, null);
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		y=(double) a.getValor();
	}

}
