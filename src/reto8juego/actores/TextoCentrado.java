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

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TextoCentrado extends Dibujo {
	String texto;
	Font fuente;
	long tiempoInicio;
	int duracion = 0;
	Funcion funcionSalida;
	boolean saliendo = false;

	
	public TextoCentrado() {
	}

	public TextoCentrado(String texto, boolean animada, int duracion, Funcion funcionSalida) {
		super((double) Config.CENTRO_ANCHO, (double) Config.CENTRO_ALTO);
		this.texto = texto;
		this.duracion = duracion;
		opacidad =254;

		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		this.funcionSalida = funcionSalida;
		tiempoInicio = motor.getTiempo();
		if (animada) {
			opacidad=0;
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad = null);
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION, Config.CENTRO_ALTO - 100, Config.CENTRO_ALTO,
				() -> animacionY = null);
		}
	}

	public void animacionSalida() {
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 254, 0, () -> {
			if (funcionSalida != null)
				funcionSalida.apply();
			vivo = false;
		});
		animacionY = new AnimacionFrenada(Config.DURACION_TRANSICION, Config.CENTRO_ALTO, Config.CENTRO_ALTO + 100,
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
		if (animacionOpacidad != null)
			opacidad = animacionOpacidad.getValor();
		if (duracion!=0 && !saliendo && motor.getTiempo() > tiempoInicio + Config.DURACION_TRANSICION + duracion) {
			saliendo = true;
			animacionSalida();
		}
	}

}
