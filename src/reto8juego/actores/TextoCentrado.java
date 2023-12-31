/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import reto8juego.config.Config;
import reto8juego.motor.AnimacionFrenada;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Colores;
import reto8juego.recursos.Recursos;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class TextoCentrado extends Dibujo {
	String texto;
	Font fuente;
	
	
	long tiempoInicio;
	int duracion=0;

	public TextoCentrado(String texto, boolean animado,int duracion) {
		super((double) Config.CENTRO_ANCHO, (double) Config.CENTRO_ALTO);
		this.texto = texto;
		this.duracion=duracion;
		opacidad=0;
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		if (animado) {
			tiempoInicio=motor.getTiempo();
			animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad=null);
			animacionOpacidad.start();
			animacionY=new AnimacionFrenada(Config.DURACION_TRANSICION, Config.CENTRO_ALTO-100, Config.CENTRO_ALTO, null);
			animacionY.start();
		}
	}

	public void animacionSalida(Funcion f) {
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 254, 0, () -> f.apply());
		animacionOpacidad.start();
		animacionY=new AnimacionFrenada(Config.DURACION_TRANSICION, Config.CENTRO_ALTO, Config.CENTRO_ALTO+100, null);
		animacionY.start();
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		int ancho = metrics.stringWidth(texto);
		int alto = metrics.getHeight();
		g2d.setColor(new Color(0, 0, 0, (int) opacidad));
		g2d.drawString(texto, (int) (x - (ancho / 2) + 2), (int) (y - (alto / 2) + 2));
		g2d.setColor(Colores.caraTexto);
		Color c = Colores.caraTexto;
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
		if (animacionOpacidad==null && duracion>0)
			if (motor.getTiempo()>tiempoInicio+Config.DURACION_TRANSICION+duracion) {
				animacionSalida(() -> vivo=false);
			}
	}

}
