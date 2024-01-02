/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.motor.Dibujo;
import reto8juego.recursos.Recursos;
import reto8juego.recursos.Strings;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class IconoConTexto extends Dibujo {
	BufferedImage icono = null;
	String texto;
	Font fuente;
	private final int tamIcono = 66;
	private boolean centrado = false;

	public IconoConTexto(String texto, String icono, double x, double y, boolean centrado) {
		super();
		this.texto = texto;
		if (icono != null)
			this.icono = Recursos.getInstancia().getImg(icono);
		this.x = x;
		this.y = y;
		this.centrado = centrado;
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_0);
	}

	@Override
	public void dibujar(Graphics2D g2d) {

		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int desfaseAlto = metrics.getHeight() / 2;
		int desfaseAncho = 0;
		if (centrado)
			desfaseAncho = metrics.stringWidth(texto) / 2;
		if (icono != null)
			g2d.drawImage(icono, (int) x - desfaseAncho - 10 - tamIcono, (int) y - icono.getHeight(), tamIcono,
					tamIcono, null);
		g2d.setColor(Colores.CARA_TEXTO);
		g2d.drawString(texto, (int) x - desfaseAncho, (int) y - desfaseAlto);

	}

}
