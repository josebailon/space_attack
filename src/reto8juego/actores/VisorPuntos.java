/**
 * 
 */
package reto8juego.actores;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Animacion;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class VisorPuntos extends Dibujo {
	Partida partida;
	int puntos=0;
	int ancho=200;
	int alto=40;
	Motor motor;
	Font fuente;
	public VisorPuntos(Partida partida) {
		this.partida=partida;
		motor=Motor.getInstancia();
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		mitadAncho=ancho/2;
		mitadAlto=alto/2;
		this.x=Config.CENTRO_ANCHO-mitadAncho;
		this.y=-alto;
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION,(float)this.y,0,()->animacionY=null);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		int[]xp= {(int)x,(int) (x+mitadAlto),(int) (x+ancho-mitadAlto),(int) (x+ancho)};
		int[]yp= {(int) y,(int) (y+alto),(int) (y+alto),(int) y};
		
 		g2d.setColor(Colores.FONDO_GUI);
 		g2d.fillPolygon(xp, yp, 4);
 		g2d.setStroke(Config.BORDE_GUI);
 		g2d.setColor(Colores.BORDE_GUI);
 		g2d.drawPolyline(xp, yp, 4);
 		
 		String texto=""+puntos;
 		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int anchot = metrics.stringWidth(texto);
		int altot = metrics.getHeight();

		g2d.setColor(Colores.CARA_TEXTO);
		g2d.drawString(texto, (int) (Config.CENTRO_ANCHO - (anchot / 2)), (int) (y+32));
 		
 		
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
			puntos=partida.getPuntos();
	}

}

