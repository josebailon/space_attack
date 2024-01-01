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
import reto8juego.motor.Dibujo;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Recursos;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TituloJuego extends Dibujo {
	String texto;
	Font fuente;
	long inicio;
	int duracion = 0;

	public TituloJuego() {
		super((double) Config.CENTRO_ANCHO, (double) Config.ALTURA_TITULO);
		this.texto = Strings.TITULO_JUEGO;
		fuente = Recursos.getInstancia().getFuente("COMPUTERRobot").deriveFont(Config.T_LETRA_0);
		
		//animar opacidad a la entrada
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad = null);

	}

	public void animacionSalida() {
		//animar opacidad y vertical en la salida
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 254, 0, null);
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION, Config.ALTURA_TITULO, Config.ALTURA_TITULO+ 100,
				null);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int ancho = metrics.stringWidth(texto);
		int alto = metrics.getHeight();
		g2d.setColor(new Color(0, 0, 0, (int) opacidad));
		g2d.drawString(texto, (int) (x - (ancho / 2) + 2), (int) (y - (alto / 2) + 2));
		g2d.setColor(Colores.CARA_TEXTO);
		Color c = Colores.CARA_TEXTO;
		try {
			g2d.setPaint(new Color((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue(), (int) opacidad));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		g2d.drawString(texto, (int) (x - (ancho / 2)), (int) (y - (alto / 2)));
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
	}

}
