/**
 * 
 */
package reto8juego.actores;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import reto8juego.Controlador;
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
public class Menu extends Dibujo {
	Font fuente;
	int opcionSeleccionada = 0;
	Controlador control;
	BufferedImage[] imgSeleccionado=new BufferedImage[6];
	int fotogramaActual=0;
	public Menu(Controlador control) {
		super((double) Config.CENTRO_ANCHO, (double) Config.ALTURA_TITULO);
		this.control=control;
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		for (int i=0;i<imgSeleccionado.length;i++)
			imgSeleccionado[i]=Recursos.getInstancia().getImg("menu"+i);
		// animar opacidad a la entrada
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad = null);

	}

	public void animacionSalida() {
		Funcion f=null;
		switch (opcionSeleccionada) {
		case 0 ->{f=() -> control.iniciarPartida();}
		case 1 ->{f=() -> control.verInstrucciones();}
		case 2 ->{f=() -> control.salir();}
		}
		// animar opacidad en la salida
		animacionOpacidad = new AnimacionFrenada(Config.DURACION_TRANSICION, 254, 0, f);
	}

	public void subirOpcionSeleccionada() {
		int tmp = opcionSeleccionada - 1;
		if (tmp == -1)
			tmp = 2;
		opcionSeleccionada = tmp;
	}

	public void bajarOpcionSeleccionada() {
		opcionSeleccionada = (1 + opcionSeleccionada) % 3;
	}

	public int getOpcionSeleccionada() {
		return opcionSeleccionada;
	}

	@Override
	public void dibujar(Graphics2D g2d) {
		int yt = Config.CENTRO_ALTO;
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		String[]textos= {Strings.INICIA_PARTIDA,Strings.VER_INSTRUCCIONES,Strings.SALIR};
		for (int i = 0; i < 3; i++) {
			int ancho = metrics.stringWidth(textos[i]);
			int alto = metrics.getHeight();
			g2d.setColor(new Color(0, 0, 0, (int) opacidad));
			g2d.drawString(textos[i], (int) (x - (ancho / 2) + 2), (int) (yt - (alto / 2) + 2));
			g2d.setColor(Colores.CARA_TEXTO);
			Color c = Colores.CARA_TEXTO;
			try {
				g2d.setPaint(new Color((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue(), (int) opacidad));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			g2d.drawString(textos[i], (int) (x - (ancho / 2)), (int) (yt - (alto / 2)));
			if (i==opcionSeleccionada) {
				BufferedImage img=imgSeleccionado[fotogramaActual];
				g2d.drawImage(img, (int) (x - (ancho / 2)-img.getWidth()-10),yt - alto/2 - img.getHeight()+5, (int)(img.getWidth()*opacidad/254),(int)(img.getHeight()*opacidad/254),null );
			}
			yt+=alto*2;
		}
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		fotogramaActual=(1+fotogramaActual)%6;
	}

}
