/**
 * 
 */
package reto8juego.actores;

 import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import reto8juego.motor.Dibujo;
import reto8juego.motor.Disparable;
import reto8juego.recursos.Recursos;
 
/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Meteorito extends Dibujo implements Disparable{
	
 
	BufferedImage img;
	int fuerza=0;

	
	public Meteorito(double x,double y, double vx,double vy ,int fuerza) {
		super(x,y);
		Random r = new Random();
		this.img = Recursos.getInstancia().getImg("meteorito"+r.nextInt(3));
		this.vAngulo = r.nextFloat(-3,3);
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
		
		at.translate(x, y);
		at.rotate(angulo);
		at.translate(-mitadAncho,-mitadAlto);
		g2d.drawImage(img, at, null);
		at.setToIdentity();
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		x+=deltaSegundo*vX*fuerza*100;
		y+=deltaSegundo*vY*fuerza*100;
		if (x<-100||x>motor.getAncho()+100||y>motor.getAlto()+100)
			this.vivo=false;
		angulo+=deltaSegundo*vAngulo;
	}

	@Override
	public boolean impacto(double xD, double yD, int fuerzaD) {
		boolean impactado = false;
		double xt=xD-x;
		double yt=yD-y;
		//comprobacion de la caja contenedora
		if (Math.abs(xt)>mitadAncho||Math.abs(yt)>mitadAlto)
			return false;
		
		//comprobacion por distancia
		double distancia = Math.sqrt(xt*xt+yt*yt);
		
		//calcular efectos de impacto
		if (distancia<mitadAncho) {
			fuerza-=fuerzaD;
			impactado=true;
		}
		
		//calcular que sigue con vida o no
		if (fuerza<=0) {
			//generar explosion y morir
			motor.agregarCapaFx(new ExplosionMeteorito(x, y));
			vivo=false;
 		}
		
		//resultado del impacto
		 return impactado;
 	}

}
