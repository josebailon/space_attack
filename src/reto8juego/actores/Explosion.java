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
public class Explosion extends Dibujo{
	public static final  String METEORITO="expmeteo";
	public static final  String NAVE="expnave";
	

	BufferedImage[] fotogramas = new BufferedImage[9];
	int fotogramaActual=0;
	
	public Explosion(double x,double y,String nombre) {
		super(x,y);
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg(nombre + i);
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
