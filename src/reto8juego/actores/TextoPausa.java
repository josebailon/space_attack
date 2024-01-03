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
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TextoPausa extends TextoCentrado {

	/**
	 * @param texto
	 * @param animada
	 * @param duracion
	 * @param funcionSalida
	 */
	public TextoPausa() {
 		super(Strings.TEXTO_PAUSA, false, 0, null);
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_2);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		g2d.setColor(Colores.FONDO_NEGRO_TRANSP);
		g2d.fillRect(0,0,motor.getAncho(),motor.getAlto());
		super.dibujar(g2d);
	}

	
}
