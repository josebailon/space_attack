/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import reto8juego.config.Config;
import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * Fondo estrellado del juego. Esta compuesto por un pase inferior que genera la
 * nebulosa que se desplaza a la velocidad mas lenta y luego tres pases mas con
 * estrellas y transparencia que se mueve cada uno a una velocidad cada vez mas
 * rapida generando la ilusion de paralax
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Fondo extends Dibujo {

	/**
	 * Lista de imagenes usadas por el fondo
	 */
	private BufferedImage[] fondos = new BufferedImage[4];

	/**
	 * Constructor
	 */
	public Fondo() {
		ancho = 1024;
		alto = 1024;
		// carga de imagenes
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fondos.length; i++) {
			fondos[i] = r.getImg("fondo" + i);
		}
	}

	/**
	 * Dibuja 4 pases cada uno con una imagen de estrellas diferente y una velocidad
	 * diferente creando una ilusion de paralax. La velocidad es en proporcion al parametro y del actor
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		// dibujar capas paralax
		
		//textura de nebulosas
		g2d.setPaint(new TexturePaint(fondos[0], new Rectangle2D.Double(0, y, ancho, alto)));
		//dibujar la textura
		g2d.fillRect(0, 0, ancho, alto);
		
		//textura de estrellas 1
		g2d.setPaint(new TexturePaint(fondos[1], new Rectangle2D.Double(100, y * 2.2 + 500, ancho, alto)));
		//dibujar la textura
		g2d.fillRect(0, 0, ancho, alto);
		
		//textura de estrellas 2
		g2d.setPaint(new TexturePaint(fondos[2], new Rectangle2D.Double(200, y * 3 + 800, ancho, alto)));
		//dibujar la textura
		g2d.fillRect(0, 0, ancho, alto);
		
		//textura de estrellas 2
		g2d.setPaint(new TexturePaint(fondos[3], new Rectangle2D.Double(300, y * 4.3, ancho, alto)));
		//dibujar la textura
		g2d.fillRect(0, 0, ancho, alto);
	}

	/**
	 * Incrementa el parametro Y del actor segun la velocidad definida en la configuracion
	 * @see Config
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		y += deltaSegundo * Config.VELOCIDAD_FONDO;
		
	}

}
