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
import reto8juego.motor.Animacion;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

/**
 * Elementos inferiores de la GUI durante la partida. Consiste de izquierda a
 * derecha en: texto del nivel actual, iconos de naves tantas como vidas extra,
 * barra de vida restante.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class VisorSaludNivel extends Dibujo {
	/**
	 * Referencia a la partida
	 */
	private Partida partida;

	/**
	 * Salud actual
	 */
	private float salud = 100;

	/**
	 * Vidas actuales
	 */
	private int vidas;

	/**
	 * Nivel actual
	 */
	private int nivel = 1;

	/**
	 * Fuente a usar
	 */
	private Font fuente;

	/**
	 * Icono usado para las vidas extra
	 */
	private BufferedImage imgVidas;

	/**
	 * Constructor
	 * 
	 * @param partida
	 */
	public VisorSaludNivel(Partida partida) {

		this.partida = partida;
		motor = Motor.getInstancia();

		// calcular dimensiones
		ancho = 200;
		alto = 20;
		this.x = motor.getAncho() - ancho - Config.MARGEN_GUI;
		this.y = motor.getAlto();

		// cargar icono de vidas
		imgVidas = Recursos.getInstancia().getImg("nave7");

		// crear fuente
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_0);

		// animar entrada
		animacionY = new Animacion(Config.DURACION_TRANSICION, (float) this.y,
				(float) this.y - alto - Config.MARGEN_GUI, () -> animacionY = null);
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		// Sombra del texto de nivel
		g2d.setFont(fuente);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Nivel " + nivel, Config.MARGEN_GUI + 1, (int) y + 21);
		// cara del texto de nivel
		g2d.setColor(Colores.BORDE_GUI);
		g2d.drawString("Nivel " + nivel, Config.MARGEN_GUI, (int) y + 20);

		// iconos de vida extra
		for (int i = 0; i < vidas - 1; i++)
			g2d.drawImage(imgVidas, (int) x - ((i + 1) * 40), (int) y, 30, 20, null);

		// fondo de la barra de vida
		g2d.setColor(Colores.FONDO_VIDA);
		g2d.fillRoundRect((int) x, (int) y, ancho, alto, Config.ESQUINA_GUI, Config.ESQUINA_GUI);

		// zona rellena de la barra
		g2d.setColor(Colores.VIDA);
		g2d.fillRoundRect((int) x, (int) y, (int) (ancho * salud / Config.SALUD_MAXIMA), alto, Config.ESQUINA_GUI,
				Config.ESQUINA_GUI);

		// borde de la barra
		g2d.setStroke(Config.BORDE_GUI);
		g2d.setColor(Colores.BORDE_GUI);
		g2d.drawRoundRect((int) x, (int) y, ancho, alto, Config.ESQUINA_GUI, Config.ESQUINA_GUI);

	}

	/**
	 * En cada fotograma actualiza las posiciones con la animacion autonoma y
	 * refresca los datos de salud, vidas extra y nivel
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		salud = partida.getNave().getSalud();
		vidas = partida.getVidas();
		nivel = partida.getNivel();
	}

}
