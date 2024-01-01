/**
 * 
 */
package reto8juego.actores;

 import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import reto8juego.motor.Colisionable;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.motor.Disparo;
import reto8juego.motor.Premio;
import reto8juego.recursos.Recursos;
 
/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Meteorito extends Dibujo implements Disparable, Colisionable{
	
	Random r;
	BufferedImage img;
	AtomicInteger salud=new AtomicInteger(1);
	int nivel=1;
	int fuerza=1;
	int radio=0;
	
	public Meteorito(double x,double y, double vx,double vy ,int nivel) {
		super(x,y);
		r = new Random();
		this.img = Recursos.getInstancia().getImg("meteorito"+r.nextInt(3));
		this.vAngulo = r.nextFloat(-3,3);
		ancho = img.getWidth();
		alto = img.getHeight();
		mitadAlto = alto / 2;
		mitadAncho = ancho / 2;
		this.vX=vx;
		this.vY=vy;
		this.nivel=nivel;
		this.salud.set(nivel);
		this.fuerza=10+this.nivel;
		this.radio=mitadAlto;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		AffineTransform at = new AffineTransform();
		
		at.translate(x, y);
		at.rotate(angulo);
		at.translate(-mitadAncho,-mitadAlto);
		g2d.drawImage(img, at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		x+=deltaSegundo*vX*nivel*100;
		y+=deltaSegundo*vY*nivel*100;
		if (x<-100||x>motor.getAncho()+100||y>motor.getAlto()+100)
			this.vivo=false;
		angulo+=deltaSegundo*vAngulo;
	}

	@Override
	public boolean impactoDisparo(Disparo disparo) {
		boolean impactado = false;
		//componentes de vector de posicion relativa
		double xt=Math.abs(disparo.getX())-x;
		double yt=Math.abs(disparo.getY())-y;
		//comprobacion de que el disparo esta en la caja contenedora
		if (xt>radio||yt>radio)
			return false;
		
		//comprobacion por distancia
		double distancia = Math.sqrt(xt*xt+yt*yt);
		
		//calcular efectos de impacto
		if (distancia<mitadAncho) {
			golpear(disparo.getFuerza());
			impactado=true;
		}

		//resultado del impacto
		 return impactado;
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
		//componentes de vector de posicion relativa
		double xt=Math.abs(otro.getX())-x;
		double yt=Math.abs(otro.getY())-y;
		//comprobacion de que se solapan las cajas contenedoras
		if (xt-otro.getRadio()>radio||yt-otro.getRadio()>radio)
			return false;
		
		//comprobacion por distancia
		double distancia = Math.sqrt(xt*xt+yt*yt);
		
		//calcular efectos de impacto
		if (distancia-otro.getRadio()<radio) {
			//efectos propioes
			this.golpear(otro.getFuerza());
			//efectos ajenos
			otro.golpear(fuerza);
			colisionado=true;
		}
		//resultado del impacto
		 return colisionado;
	}
	
	@Override
	public void matar() {
		salud.set(0);
		//generar explosion y morir
		motor.agregarCapaFx(new Explosion(x, y, Explosion.METEORITO));
		vivo=false;
		Premio premio;
		int azar=r.nextInt(100);
 		if (azar>30)
			premio =  new PremioPuntos(x, y,nivel);
		else
			premio = new PremioSalud(x, y);
//		else if (azar>15)
//		else if (azar>50)
//			premio = new PremioEscudo(x, y);
//		else
//			premio =  new PremioVida(x, y);
		motor.agregarCapaPremios(premio);
	}


	@Override
	public void golpear(int fuerza) {
		salud.addAndGet(-fuerza);
		//comprobar si sigue vivo
		if (salud.get()<=0)
			matar();
	}
 
}
