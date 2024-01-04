/**
 * 
 */
package reto8juego.actores;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.Animacion;
import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;

/**
 * Visor de puntos superior durante el desarrollo de la partida
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class VisorPuntos extends Dibujo {
	
	/**
	 * Referencia a la partida
	 */
	private Partida partida;
	
	/**
	 * Puntos actuales
	 */
	private int puntos=0;
	
	
	/**
	 * Fuente a usar
	 */
	private Font fuente;
	
	
	/**
	 * Constructor
	 * 
	 * @param partida Referencia a la partida
	 */
	public VisorPuntos(Partida partida) {
		this.partida=partida;
		
		//fuente
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		
		//dimensiones del panel
		ancho=200;
		alto=40;
		mitadAncho=ancho/2;
		mitadAlto=alto/2;
		
		//esquina superior izquierda del panel antes de la animacion de entrada
		this.x=Config.CENTRO_ANCHO-mitadAncho;
		this.y=-alto;
		
		//animacion de entrada
		animacionY = new Animacion(Config.DURACION_TRANSICION,(float)this.y,0,()->animacionY=null);
	}

	
	/**
	 * El dibujo de este visor consiste en un panel y un texto central
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		
		//puntos x del poligono del panel
		int[]xp= {(int)x,(int) (x+mitadAlto),(int) (x+ancho-mitadAlto),(int) (x+ancho)};
		
		//puntos y del poligono del panel
		int[]yp= {(int) y,(int) (y+alto),(int) (y+alto),(int) y};
		
		//dibujar panel
 		g2d.setColor(Colores.FONDO_GUI);
 		g2d.fillPolygon(xp, yp, 4);
 		
 		//dibujar borde del panel
 		g2d.setStroke(Config.BORDE_GUI);
 		g2d.setColor(Colores.BORDE_GUI);
 		g2d.drawPolyline(xp, yp, 4);
 		
 		//medir y dibujar texto
 		String texto=""+puntos;
 		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int anchoTexto = metrics.stringWidth(texto);

		g2d.setColor(Colores.CARA_TEXTO);
		g2d.drawString(texto, (int) (Config.CENTRO_ANCHO - (anchoTexto / 2)), (int) (y+32));
 		
 		
	}

	/**
	 * Actualiza el atributo puntos con los puntos registrados en la partida
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
			puntos=partida.getPuntos();
	}

}

