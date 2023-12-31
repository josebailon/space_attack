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
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class ExplosionMeteorito extends Dibujo{
	
	 

	BufferedImage[] fotogramas = new BufferedImage[6];
	int fotogramaActual=0;
	
	public ExplosionMeteorito(double x,double y) {
		super(x,y);
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg("expmeteo" + i);
		}
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual], at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		fotogramaActual++;
		if (fotogramaActual>=fotogramas.length) {
			fotogramaActual=fotogramas.length-1;
			vivo=false;
		}
	}

 

}
