/**
 * 
 */
package reto8juego.actores;

import java.awt.BasicStroke;
import java.awt.Color;
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
public class VisorSalud extends Dibujo {
	Partida partida;
	float salud=100;
	int vidas;
	int ancho=200;
	int alto=20;
	Motor motor;
	BufferedImage imgVidas;
	public VisorSalud(Partida partida) {
		this.partida=partida;
		motor=Motor.getInstancia();
		this.x=motor.getAncho()-ancho-Config.MARGEN_GUI;
		this.y=motor.getAlto();
		imgVidas=Recursos.getInstancia().getImg("nave7");
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION,(float)this.y,(float)this.y-alto-Config.MARGEN_GUI,()->animacionY=null);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
 		g2d.setColor(Colores.FONDO_VIDA);
 		g2d.fillRoundRect((int)x, (int)y, ancho, alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		g2d.setColor(Colores.VIDA);
 		g2d.fillRoundRect((int)x, (int)y, (int) (ancho*salud/100), alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		g2d.setStroke(Config.BORDE_GUI);
 		g2d.setColor(Colores.BORDE_GUI);
 		g2d.drawRoundRect((int)x, (int)y, ancho, alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		for (int i =0;i<vidas-1;i++) {
 			g2d.drawImage(imgVidas, (int)x-((i+1)*40), (int)y, 30, 20, null); 
 		}
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
			salud=partida.getNave().getSalud();
			vidas=partida.getVidas();
	}

}
