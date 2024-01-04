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
import reto8juego.motor.Animacion;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Recursos;

/**
 * Texto centrado en la pantalla al cual se le puede activar animacion de entrada y de salida.
 *  
 * @author Jose Javier Bailon Ortiz
 */
public class TextoCentrado extends Dibujo {
	
	/*
	 * Texto a escribir
	 */
	private String texto;
	
	/**
	 * Fuente a usar
	 */
	protected Font fuente;
	
	/**
	 * Tiempo actual al crearse el texto
	 */
	private long tiempoInicio;
	
	/**
	 * Duracion del texto si esta animado
	 */
	private int duracion = 0;
	
	/**
	 * Funcion a ejecutar al terminar el texto animado
	 */
	private Funcion funcionSalida;
	
	/**
	 * True si esta haciendo la animacion de salida
	 */
	private boolean saliendo = false;

	
	/**
	 * Constructor
	 * 
	 * @param texto Texto a escribir
	 * @param animado True si esta animado
	 * @param duracion Duracion si esta animado
	 * @param funcionSalida Funcion a ejecutar tras la animacion de salida
	 */
	public TextoCentrado(String texto, boolean animado, int duracion, Funcion funcionSalida) {
		//colocar en el centro de la pantalla
		super((double) Config.CENTRO_ANCHO, (double) Config.CENTRO_ALTO);
		this.texto = texto;
		this.duracion = duracion;
		this.funcionSalida = funcionSalida;

		opacidad = 254;
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		tiempoInicio = motor.getTiempo();
		
		//si esta animado se activa una animacion de entrada para la opacidad y para su posicion vertical
		if (animado) {
			opacidad = 0;
			animacionOpacidad = new Animacion(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad = null);
			animacionY = new Animacion(Config.DURACION_TRANSICION, Config.CENTRO_ALTO - 100, Config.CENTRO_ALTO,
					() -> animacionY = null);
		}
	}

	/**
	 * Activa la animacion de salida
	 */
	private void animacionSalida() {
		
		//animacion de opacidad con callback de aplicar la funcion de salida
		animacionOpacidad = new Animacion(Config.DURACION_TRANSICION, 254, 0, () -> {
			if (funcionSalida != null)
				funcionSalida.apply();
			vivo = false;
		});
		animacionY = new Animacion(Config.DURACION_TRANSICION, Config.CENTRO_ALTO, Config.CENTRO_ALTO + 100, null);
	}

	/**
	 * Dibuja el texto centrado en pantalla con sombra negra
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		//fuente
		g2d.setFont(fuente);
		
		//medir texto
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int ancho = metrics.stringWidth(texto);
		int alto = metrics.getHeight();
		
		//pintar sombra del texto
		g2d.setColor(new Color(0, 0, 0, (int) opacidad));
		g2d.drawString(texto, (int) (x - (ancho / 2) + 2), (int) (y - (alto / 2) + 2));
		
		//pintar cara del texto
		g2d.setColor(Colores.CARA_TEXTO);
		Color c = Colores.CARA_TEXTO;
		try {
			g2d.setPaint(new Color((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue(), (int) opacidad));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		g2d.drawString(texto, (int) (x - (ancho / 2)), (int) (y - (alto / 2)));
	}

	
	/**
	 * Calcular el nuevo fotograma
	 */
	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		//aplicacion de animaciones si las hubiere
		super.nuevoFotograma(frame, delta, deltaSegundo);
		//si ha pasado el tiempo de duracion activar la animacion de salida
		if (duracion != 0 && !saliendo && motor.getTiempo() > tiempoInicio + Config.DURACION_TRANSICION + duracion) {
			saliendo = true;
			animacionSalida();
		}
	}

}
