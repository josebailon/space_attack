/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Nave extends Dibujo implements Colisionable, Disparable{
	final int FOTOGRAMA_CENTRO=7;
	final int FOTOGRAMA_IZQUIERDA=0;
	final int FOTOGRAMA_DERECHA=14;
	
	
	BufferedImage[] fotogramas = new BufferedImage[15];
	BufferedImage imgEscudo;
	Partida partida;
	AtomicInteger velX = new AtomicInteger();
	AtomicInteger velY = new AtomicInteger();
	AtomicInteger velocidad = new AtomicInteger(400);
	AtomicInteger fotogramaDestino=new AtomicInteger(FOTOGRAMA_CENTRO);
	AtomicInteger fotogramaActual=new AtomicInteger(FOTOGRAMA_CENTRO);
	int fuerzaDisparo=1;
	AtomicInteger salud = new AtomicInteger(Config.SALUD_MAXIMA);
	AtomicInteger nivelEscudo=new AtomicInteger(1);
	
	
	
	public Nave(Partida partida) {
		super(400d, 1124d);
		Recursos r = Recursos.getInstancia();
		for (int i = 0; i < fotogramas.length; i++) {
			fotogramas[i] = r.getImg("nave" + i);
		}
		imgEscudo = r.getImg("escudo");
		ancho = fotogramas[0].getWidth();
		alto = fotogramas[0].getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		this.partida=partida;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(fotogramas[fotogramaActual.get()], at, null);
		at.setToIdentity();
		if (nivelEscudo.get()>0) {
			at.translate(x - mitadAncho, y - mitadAncho);
			g2d.drawImage(imgEscudo, at, null);
			at.setToIdentity();
		}
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

	@Override
	public void matar() {
		salud.set(0);
		//generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.NAVE));
		vivo=false;
		partida.naveDestruida();
	}

	@Override
	public int getFuerza() {
		return salud.get();
	}

	@Override
	public int getRadio() {
		return mitadAlto;
	}

	@Override
	public boolean colisiona(Colisionable otro) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void golpear(int fuerza) {
		if (nivelEscudo.get()>0)
			return;
		salud.addAndGet(-fuerza);
		if (salud.get()<=0)
				matar();
	}

	/**
	 * @param b
	 * @return
	 */
	public void setEscudoActivo(boolean activo) {
		if (activo)
		nivelEscudo.incrementAndGet();
		else
		nivelEscudo.decrementAndGet();
	}

	/**
	 * @return
	 */
	public float getSalud() {
		return salud.get();
	}

	/**
	 * @param i
	 */
	public void agregarSalud(int i) {
			if (salud.addAndGet(i)>Config.SALUD_MAXIMA)
				salud.set(Config.SALUD_MAXIMA);
	}

	public void aumentarFuerzaDisparo() {
		fuerzaDisparo++;
	}
	
	@Override
	public boolean impactoDisparo(Disparo disparo) {
		boolean impactado = false;
		//componentes de vector de posicion relativa
		double xt=Math.abs(disparo.getX())-x;
		double yt=Math.abs(disparo.getY())-y;
		//comprobacion de que el disparo esta en la caja contenedora
		if (xt>mitadAlto||yt>mitadAlto)
			return false;
		
		//comprobacion por distancia
		double distancia = Math.sqrt(xt*xt+yt*yt);
		
		//calcular efectos de impacto
		if (distancia<mitadAlto) {
			golpear(disparo.getFuerza());
			impactado=true;
		}

		//resultado del impacto
		 return impactado;
	}

}
