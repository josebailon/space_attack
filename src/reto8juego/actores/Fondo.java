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
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Fondo extends Dibujo {
	BufferedImage[] fondos = new BufferedImage[4];

	
	public Fondo() {

		ancho=1024;
		alto=1024;
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fondos.length; i++) {
			fondos[i] = r.getImg("fondo" + i);
		}
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		//dibujar capas paralax
		g2d.setPaint(new TexturePaint(fondos[0], new Rectangle2D.Double(0, y, ancho, alto)));
		g2d.fillRect(0, 0, ancho, alto);
		g2d.setPaint(new TexturePaint(fondos[1],new Rectangle2D.Double(100, y * 2.2 + 500, ancho, alto)));
		g2d.fillRect(0, 0, ancho, alto);
		g2d.setPaint(new TexturePaint(fondos[2],new Rectangle2D.Double(200, y * 3 + 800,  ancho, alto)));
		g2d.fillRect(0, 0, ancho, alto);
		g2d.setPaint(new TexturePaint(fondos[3],new Rectangle2D.Double(300, y * 4.3, ancho, alto)));
		g2d.fillRect(0, 0, ancho, alto);
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
			y+= deltaSegundo * Config.VELOCIDAD_FONDO;
	}

}
