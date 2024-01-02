/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.escenas.Partida;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class VisorSaludNivel extends Dibujo {
	Partida partida;
	float salud=100;
	int vidas;
	int nivel=1;
	int ancho=200;
	int alto=20;
	Font fuente;
	Motor motor;
	BufferedImage imgVidas;
	public VisorSaludNivel(Partida partida) {
		this.partida=partida;
		motor=Motor.getInstancia();
		this.x=motor.getAncho()-ancho-Config.MARGEN_GUI;
		this.y=motor.getAlto();
		imgVidas=Recursos.getInstancia().getImg("nave7");
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_0);
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION,(float)this.y,(float)this.y-alto-Config.MARGEN_GUI,()->animacionY=null);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
 		g2d.setColor(Colores.FONDO_VIDA);
 		g2d.fillRoundRect((int)x, (int)y, ancho, alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		g2d.setColor(Colores.VIDA);
 		g2d.fillRoundRect((int)x, (int)y, (int) (ancho*salud/Config.SALUD_MAXIMA), alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		g2d.setStroke(Config.BORDE_GUI);
 		g2d.setColor(Colores.BORDE_GUI);
 		g2d.drawRoundRect((int)x, (int)y, ancho, alto,Config.ESQUINA_GUI,Config.ESQUINA_GUI);
 		for (int i =0;i<vidas-1;i++) {
 			g2d.drawImage(imgVidas, (int)x-((i+1)*40), (int)y, 30, 20, null); 
 		}
 		g2d.setFont(fuente);
 		g2d.setColor(Color.BLACK);
 		g2d.drawString("Nivel "+nivel, Config.MARGEN_GUI+1, (int)y+21);
 		g2d.setColor(Colores.BORDE_GUI);
 		g2d.drawString("Nivel "+nivel, Config.MARGEN_GUI, (int)y+20);
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
			salud=partida.getNave().getSalud();
			vidas=partida.getVidas();
			nivel = partida.getNivel();
	}

}
