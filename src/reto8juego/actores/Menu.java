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
import reto8juego.motor.Animacion;
import reto8juego.motor.Dibujo;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Recursos;
import reto8juego.recursos.Strings;

/**
 * <p>
 * Menu principal. Muestra tres lineas de texto:jugar partida, instrucciones y
 * salir. Ademas muestra un icono marcando la opcion seleccionada. Cuando se
 * activa la opcion seleccionada con {@link Menu#activarOpcionSeleccionada()} se
 * ejecuta una animacion de opacidad del menu y tras terminar se ejecuta la
 * opcion del controlador correspondiente a la opcion seleccionada.
 * </p>
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Menu extends Dibujo {

	/**
	 * Fuente usada por el menu
	 */
	private Font fuente;

	/**
	 * Opcion seleccionada actualmente
	 */
	private int opcionSeleccionada = 0;

	/**
	 * Referencia al controlador
	 */
	private Controlador control;

	/**
	 * Fotogramas del icono que se coloca al lado de la opcion seleccionada
	 */
	private BufferedImage[] imgSeleccionado = new BufferedImage[6];

	/**
	 * Fotograma actual del icono
	 */
	private int fotogramaActual = 0;

	/**
	 * Constructor
	 * 
	 * @param control Referencia al controlador
	 */
	public Menu(Controlador control) {
		//Cordenadas del menu centrado horizontal y a partir del centro vertical
		super((double) Config.CENTRO_ANCHO, (double) Config.CENTRO_ALTO);
		
		this.control = control;
		

		// cargar fotogramas del icono
		for (int i = 0; i < imgSeleccionado.length; i++)
			imgSeleccionado[i] = Recursos.getInstancia().getImg("menu" + i);

		// animar opacidad a la entrada
		animacionOpacidad = new Animacion(Config.DURACION_TRANSICION, 0, 254, () -> animacionOpacidad = null);
		
		//crear fuente
		fuente = Recursos.getInstancia().getFuente("plasmati").deriveFont(Config.T_LETRA_1);
		
	}

	/**
	 * Activa una accion dependiendo de la accion seleccionada actualmente. Ejecuta
	 * una animacion en la opacidad del menu y al terminar ejecuta la accion
	 * correspondiente
	 */
	public void activarOpcionSeleccionada() {

		// seleccionar la acction que se realizara
		Funcion f = null;
		switch (opcionSeleccionada) {
		case 0 -> {
			f = () -> control.iniciarPartida();
		}
		case 1 -> {
			f = () -> control.verInstrucciones();
		}
		case 2 -> {
			f = () -> control.salir();
		}
		}

		// animar opacidad y ejecutar la accion al terminar la animacion
		animacionOpacidad = new Animacion(Config.DURACION_TRANSICION, 254, 0, f);
	}

	/**
	 * Cambia la opcion seleccionada hacia arriba
	 */
	public void subirOpcionSeleccionada() {
		int tmp = opcionSeleccionada - 1;
		if (tmp == -1)
			tmp = 2;
		opcionSeleccionada = tmp;
	}

	/**
	 * Cambia la accion seleccionada hacia abajo
	 */
	public void bajarOpcionSeleccionada() {
		opcionSeleccionada = (1 + opcionSeleccionada) % 3;
	}

	/**
	 * Dibuja los textos de las opciones justificados al centro y de la mitad hacia
	 * abajo de la pantalla. Tambien dibuja un icono animado junto a la accion
	 * seleccionada actualmente.
	 */
	@Override
	public void dibujar(Graphics2D g2d) {
		
		//Coordenada Y inicial
		g2d.setFont(fuente);
		FontMetrics metrics = g2d.getFontMetrics(fuente);
		String[] textos = { Strings.INICIA_PARTIDA, Strings.VER_INSTRUCCIONES, Strings.SALIR };
		
		//Dibujar los textos
		int i=0;
		int separacion=0;
		while ( i < textos.length) {
			//metricas del texto
			int anchoTexto = metrics.stringWidth(textos[i]);
			int altoTexto = metrics.getHeight();
			//reborde negro
			g2d.setColor(new Color(0, 0, 0, (int) opacidad));
			g2d.drawString(textos[i], (int) (x - (anchoTexto / 2) + 2), (int) (y+separacion - (altoTexto / 2) + 2));
			g2d.setColor(Colores.CARA_TEXTO);
			
			//cara de color con opacidad
			Color c = Colores.CARA_TEXTO;
			try {
				g2d.setPaint(new Color((int) c.getRed(), (int) c.getGreen(), (int) c.getBlue(), (int) opacidad));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			g2d.drawString(textos[i], (int) (x - (anchoTexto / 2)), (int) (y+separacion - (altoTexto / 2)));
			
			//dibujar el icono si es la opcion seleccionada
			if (i == opcionSeleccionada) {
				BufferedImage img = imgSeleccionado[fotogramaActual];
				
				//dibujar el icono a la izquierda del texto
				int anchoImagen= (int) (img.getWidth() * opacidad / 254);
				int altoImagen= (int) (img.getHeight() * opacidad / 254);
				int posX=(int) (x - (anchoTexto / 2) - anchoImagen - 10);
				int posY=(int) y + separacion - altoTexto / 2 - altoImagen + 5;
				g2d.drawImage(img, posX, posY,anchoImagen, altoImagen,null);
			}
			
			//calculo de la nueva separacion
			i++;
			separacion=altoTexto * 2*i;
		}
	}

	@Override
	public void nuevoFotograma(int frame, long delta, float deltaSegundo) {
		super.nuevoFotograma(frame, delta, deltaSegundo);
		fotogramaActual = (1 + fotogramaActual) % 6;
	}

}
