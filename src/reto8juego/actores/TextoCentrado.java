/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TextoCentrado extends Dibujo {
	String texto;
	Font fuente;
	AnimacionFrenada animacion;
	public TextoCentrado(String texto) {
		this.texto=texto;
		fuente=Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		animacion=new AnimacionFrenada(Config.DURACION_TRANSICION, 0, 254, null);
		animacion.start();
	}

	public void animacionSalida(Funcion f) {
		animacion=new AnimacionFrenada(Config.DURACION_TRANSICION, 254, 0, () -> f.apply());
		animacion.start();
	}
	@Override
	public void dibujar(Graphics2D g2d) {
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int ancho = metrics.stringWidth(texto);
		int alto = metrics.getHeight();
		g2d.setColor(new Color(0,0,0,(int)opacidad));
		g2d.drawString(texto, Config.ANCHO/2-(ancho/2)+2, (int) (y-(alto/2)+2));
		g2d.setColor(Colores.caraTexto);
		Color c = Colores.caraTexto;
		try {
		g2d.setPaint(new Color((int)c.getRed(),(int)c.getGreen(),(int)c.getBlue(),(int)opacidad));
		}
		catch (Exception e) {
			System.out.println(e.getMessage()); 
			}
		
		g2d.drawString(texto, Config.ANCHO/2-(ancho/2), (int) (y-(alto/2)));
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		y=555d;//(double) animacion.getValor();
		if (animacion!=null)
		opacidad=animacion.getValor();
	}

}
