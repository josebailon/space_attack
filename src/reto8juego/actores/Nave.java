/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Nave extends Dibujo {
	final int FOTOGRAMA_CENTRO=7;
	final int FOTOGRAMA_IZQUIERDA=0;
	final int FOTOGRAMA_DERECHA=14;
	BufferedImage[] fotogramas = new BufferedImage[15];
	AtomicInteger velX = new AtomicInteger();
	AtomicInteger velY = new AtomicInteger();
	AtomicInteger velocidad = new AtomicInteger(400);
	AtomicInteger fotogramaDestino=new AtomicInteger(FOTOGRAMA_CENTRO);
	AtomicInteger fotogramaActual=new AtomicInteger(FOTOGRAMA_CENTRO);
	
	int fuerzaDisparo=1;

	public Nave() {
		super(400d, 1124d);
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg("nave" + i);
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
		g2d.drawImage(fotogramas[fotogramaActual.get()], at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		if (animacionY!=null || animacionX!= null)
			return;
		int fotogramaA=fotogramaActual.get();
		if (fotogramaA<fotogramaDestino.get())
			fotogramaActual.set(++fotogramaA);
		else if (fotogramaA>fotogramaDestino.get())
			fotogramaActual.set(--fotogramaA);
		x+=velocidad.get()*velX.get()*deltaSegundo;
		y+=velocidad.get()*velY.get()*deltaSegundo;
		//limitacion
		if (x<0)
			x=0;
		else if (x>motor.getAncho())
			x=motor.getAncho();
		if (y<0)
			y=0;
		if (y>motor.getAlto())
			y=motor.getAlto();
	}

	/**
	 * @param arriba
	 * @param abajo
	 * @param izquierda
	 * @param derecha
	 */
	public void teclas(boolean arriba, boolean abajo, boolean izquierda, boolean derecha) {
		if (arriba && !abajo) 
			velY.set(-1);
		else if (!arriba && abajo)
			velY.set(1);
		else	velY.set(0);
		if (derecha && !izquierda) {
			velX.set(1);
			fotogramaDestino.set(FOTOGRAMA_DERECHA);
		}
		else if (!derecha && izquierda) {
			velX.set(-1);
			fotogramaDestino.set(FOTOGRAMA_IZQUIERDA);
			}
		
		else {
			velX.set(0);
			fotogramaDestino.set(FOTOGRAMA_CENTRO);
		}
	}

	/**
	 * 
	 */
	public void disparar() {
		motor.agregarCapaDisparosAmigos(new DisparoAmigo(x, y, fuerzaDisparo));
	}

}
