/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.config.Niveles;
import reto8juego.escenas.Partida;
import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.motor.Movimiento;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Enemigo extends Dibujo implements Disparable, Colisionable {
	Random r = new Random();
	BufferedImage img;
	Movimiento movimiento;
	AtomicInteger salud = new AtomicInteger(1);
	int radio = 0;
	int fuerza = 1;
	int nivel = 1;
	int cantidadDisparos;
	Partida partida;
	/**
	 * @param x
	 * @param y
	 * @param codigo
	 * @param nFotogramas
	 */
	public Enemigo(Partida partida, double x, int mov, int tipo, int nivel) {
		super(x, -50);
		this.partida=partida;
		this.nivel = nivel;
		setTipo(tipo);
		setMovimiento(mov);
		cantidadDisparos=1+nivel%3;
	}

	/**
	 * @param mov
	 */
	private void setMovimiento(int mov) {
		switch (mov) {
			case Niveles.MOV_RECTO -> {
				movimiento = (d) -> {
					y += 200 * d;
				};
			}
			case Niveles.MOV_ONDULADO -> {
				int xorig=(int) x;
				movimiento = (d) -> {
					y += 200 * d;
					x=xorig+Math.sin(y/50)*100;
				};
			}
			
		}
	}

	/**
	 * @param tipo
	 */
	private void setTipo(int tipo) {
		img = Recursos.getInstancia().getImg("enemigo" + tipo);
		ancho = img.getWidth();
		alto = img.getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		this.radio = mitadAncho;
		this.salud.set(nivel);
		this.fuerza = 50 + this.nivel;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		at.translate(x - mitadAncho, y - mitadAlto);
		g2d.drawImage(img, at, null);
		at.setToIdentity();

	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		movimiento.apply(deltaSegundo);
		if (y>motor.getAlto()+100)
			this.vivo=false;
		
		if (cantidadDisparos>0)
			intentarDisparar();
	}

	/**
	 * 
	 */
	private void intentarDisparar() {
		Nave nave = partida.getNave();
		if (cantidadDisparos>0&&nave!=null&&y>0&&r.nextFloat()<0.01) {
			double dx=nave.getX()-x;
			double dy=nave.getY()-y;
			double longitud=Math.sqrt(dx*dx+dy*dy);
			dx=dx/longitud*200;
			dy=dy/longitud*200;
			motor.agregarCapaDisparosEnemigos(new DisparoEnemigo(x, y, dx, dy, nivel*10));
			cantidadDisparos--;
		}
			
			
		
	}

	@Override
	public void matar() {
		salud.set(0);
		// generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.NAVE));
		vivo = false;
		
		motor.agregarCapaPremios(new PremioPuntos(x, y, nivel));

	}

	@Override
	public int getFuerza() {
		return fuerza;
	}

	@Override
	public int getRadio() {
		return mitadAlto;
	}

	@Override
	public boolean colisiona(Colisionable otro) {
		boolean colisionado = false;
		// componentes de vector de posicion relativa
		double xt = Math.abs(otro.getX()) - x;
		double yt = Math.abs(otro.getY()) - y;
		// comprobacion de que se solapan las cajas contenedoras
		if (xt - otro.getRadio() > radio || yt - otro.getRadio() > radio)
			return false;

		// comprobacion por distancia
		double distancia = Math.sqrt(xt * xt + yt * yt);

		// calcular efectos de impacto
		if (distancia - otro.getRadio() < radio) {
			// efectos propioes
			this.golpear(otro.getFuerza());
			// efectos ajenos
			otro.golpear(fuerza);
			colisionado = true;
		}
		// resultado del impacto
		return colisionado;
	}

	@Override
	public void golpear(int fuerza) {
		salud.addAndGet(-fuerza);
		// comprobar si sigue vivo
		if (salud.get() <= 0)
			matar();

	}

	@Override
	public boolean impactoDisparo(Disparo disparo) {
		boolean impactado = false;
		// componentes de vector de posicion relativa
		double xt = Math.abs(disparo.getX()) - x;
		double yt = Math.abs(disparo.getY()) - y;
		// comprobacion de que el disparo esta en la caja contenedora
		if (xt > radio || yt > radio)
			return false;

		// comprobacion por distancia
		double distancia = Math.sqrt(xt * xt + yt * yt);

		// calcular efectos de impacto
		if (distancia < mitadAncho) {
			golpear(disparo.getFuerza());
			impactado = true;
		}

		// resultado del impacto
		return impactado;
	}

}
