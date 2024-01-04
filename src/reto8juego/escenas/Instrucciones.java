/**
 * 
 */
package reto8juego.escenas;

import java.awt.event.KeyEvent;

import reto8juego.Controlador;
import reto8juego.actores.IconoConTexto;
import reto8juego.config.Config;
import reto8juego.motor.Animacion;
import reto8juego.motor.Escena;
import reto8juego.motor.Funcion;
import reto8juego.recursos.Strings;

/**
 * <p>
 * Escena de instrucciones. Muestra un listado de textos con icono informando
 * sobre el funcionamiento del juego.
 * </p>
 * <p>
 * Las filas de texto entran con una animacion de entrada y se produce una
 * animacion de salida para terminar
 * </p>
 * <p>
 * Escucha la pulsacion de cualquier tecla para finalizar y volver al menu
 * inicial.
 * </p>
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Instrucciones extends Escena {

	/**
	 * Lista de textos
	 */
	private final String[] textos = { Strings.INST_TXT0, Strings.INST_TXT1, Strings.INST_TXT2, Strings.INST_TXT3,
			Strings.INST_TXT4, Strings.INST_TXT5, Strings.INST_TXT6, Strings.INST_TXT7 };

	/**
	 * Lista de iconos para cada texto
	 */
	private final String[] iconos = { Strings.INST_ICO0, Strings.INST_ICO1, Strings.INST_ICO2, Strings.INST_ICO3,
			Strings.INST_ICO4, Strings.INST_ICO5, Strings.INST_ICO6, Strings.INST_ICO7, };

	/**
	 * Lista de elementos graficos que componen las instrucciones
	 */
	private IconoConTexto[] lineas = new IconoConTexto[9];

	/**
	 * Define si la escena responde al teclado
	 */
	private boolean controlActivo = true;

	/**
	 * Constructor. Lanza automaticamente el inicio de la escena
	 * 
	 * @param control           Referencia al control
	 * 
	 * @param callbackTerminado Callback a ejecutar al terminar la escena
	 */
	public Instrucciones(Controlador control, Funcion callbackTerminado) {
		super(control, callbackTerminado);
		iniciar();
	}

	/**
	 * Inicia la escena creando los elementos de textos de las instrucciones y
	 * animandolos en su entrada
	 */
	@Override
	public void iniciar() {

		// posicion superior izquierda del texto fuera de la pantalla ya que la
		// animacion de los elementos los pondra en su sitio
		int xinicio = Config.ANCHO + 200;
		// x final de los textos tras la animacion
		int x = 130;

		// y del primer texto
		int y = 90;

		// Limpiar capas del motor
		motor.vaciarCapas();

		// Crear lineas explicativas agregandoles una animacion de entrada

		for (int i = 0; i < textos.length; i++) {
			// crear elemento
			IconoConTexto texto = new IconoConTexto(textos[i], iconos[i], xinicio, y, false);
			lineas[i] = texto;
			// espera para generar efecto de ola
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// agregar animacion
			Animacion animacion = new Animacion(1000, xinicio, x, null);
			texto.setAnimacionX(animacion);

			// agregar elemento al motor
			motor.agregarCapaGui(texto);

			// incrementa y para la siguiente linea
			y += 100;
		}

		// texto inferior de volver al menu principal
		IconoConTexto textoVolver = new IconoConTexto(Strings.INST_VOLVER, null, Config.CENTRO_ANCHO, y, true);
		Animacion animacion = new Animacion(1000, Config.ALTO + 100, y, () -> controlActivo = true);
		textoVolver.setAnimacionY(animacion);
		motor.agregarCapaGui(textoVolver);
		lineas[8] = textoVolver;
	}

	/**
	 * Al terminar se les agrega una animacion de salida a los elementos y tras
	 * terminar la animacion se ejecuta el calback de salida establecido en la
	 * escena
	 */
	@Override
	public void terminar() {

		// animar elementos
		controlActivo = false;
		int x = 130;
		int xfin = Config.ANCHO + 200;
		Animacion animacionLineas = new Animacion(1000, x, xfin, null);
		for (int i = 0; i < textos.length; i++) {
			lineas[i].setAnimacionX(animacionLineas);

		}

		// animar texto inferior y establecer la accion final de la animacion como la
		// ejecucion del callback
		// de la escena
		Animacion animacion = new Animacion(1000, (float) lineas[8].getY(), Config.ALTO + 100,
				() -> callbackTerminado.apply());
		lineas[8].setAnimacionY(animacion);

	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	/**
	 * Termina la escena de instrucciones volviendo al menu principal tras recibir
	 * este evento de cualquier tecla
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		if (controlActivo)
			terminar();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
