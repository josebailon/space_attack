/**
 * 
 */
package reto8juego.actores;

import java.awt.Graphics2D;

import reto8juego.config.Colores;
import reto8juego.config.Config;
import reto8juego.recursos.Recursos;
import reto8juego.recursos.Strings;

/**
 * Texto mostrado durante la pausa. Se trata de un texto centrado sin animacion
 * y un fondo negro semitrasparente.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TextoPausa extends TextoCentrado {

	/**
	 * Constructor
	 */
	public TextoPausa() {
		super(Strings.TEXTO_PAUSA, false, 0, null);
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_2);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		//fondo negro
		g2d.setColor(Colores.FONDO_NEGRO_TRANSP);
		g2d.fillRect(0, 0, motor.getAncho(), motor.getAlto());
		
		//texto
		super.dibujar(g2d);
	}

}
