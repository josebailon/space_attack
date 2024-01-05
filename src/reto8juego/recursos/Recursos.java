/**
 * 
 */
package reto8juego.recursos;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * Sigue el patron singleton. Se encarga de cargar de disco los archivos de
 * recuros (imagenes y fuentes) y servirlos a quien lo solicite. Guarda los
 * elementos cargados en un hashmap con key string
 * 
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Recursos {

	/**
	 * Imagenes cargadas
	 */
	private HashMap<String, BufferedImage> imagenes = new HashMap<String, BufferedImage>();

	/**
	 * Fuentes cargadas
	 */
	private HashMap<String, Font> fuentes = new HashMap<String, Font>();

	/**
	 * Lista de imagenes a cargar
	 */
	private String[] listaImg = { "menu0", "menu1", "menu2", "menu3", "menu4", "menu5", "fondo0", "fondo1", "fondo2",
			"fondo3", "nave0", "nave1", "nave2", "nave3", "nave4", "nave5", "nave6", "nave7", "nave8", "nave9",
			"nave10", "nave11", "nave12", "nave13", "nave14", "escudo", "disparoAmigo0", "disparoAmigo1",
			"disparoAmigo2", "disparoAmigo3", "disparoAmigo4", "disparoAmigo5", "meteorito0", "meteorito1",
			"meteorito2", "expmeteo0", "expmeteo1", "expmeteo2", "expmeteo3", "expmeteo4", "expmeteo5", "expmeteo6",
			"expmeteo7", "expmeteo8", "expnave0", "expnave1", "expnave2", "expnave3", "expnave4", "expnave5",
			"expnave6", "expnave7", "expnave8", "moneda0", "moneda1", "moneda2", "moneda3", "moneda4", "moneda5",
			"escudo0", "escudo1", "escudo2", "escudo3", "escudo4", "escudo5", "vida0", "vida1", "vida2", "vida3",
			"vida4", "vida5", "salud0", "salud1", "salud2", "salud3", "salud4", "salud5", "arma0", "arma1", "arma2",
			"arma3", "arma4", "arma5", "enemigo0", "enemigo1", "enemigo2", "disparoEnemigo", "cursores", "ins_peligro",
			"ins_asteorides" };

	/**
	 * Lista de fuentes a cargar
	 */
	private String[] listaFuentes = { "COMPUTERRobot", "plasmati" };

	/**
	 * Instancia singleton
	 */
	private static Recursos instancia;

	/**
	 * Constructor privado para singleton
	 */
	private Recursos() {
	}

	/**
	 * Devuelve una instancia singleton creandola si no existe
	 * 
	 * @return La instancia singleton
	 */
	public static Recursos getInstancia() {
		if (instancia == null) {
			instancia = new Recursos();
		}
		return instancia;
	}

	/**
	 * Carga los recursos
	 * 
	 * @return True si se han cargado todos, False si no se han cargado
	 */
	public boolean cargarRecursos() {
		return cargarImg() && cargarFuentes();
	}

	/**
	 * Carga las fuentes
	 * 
	 * @return True si se han cargado todas False si no se han cargado todas
	 */
	private boolean cargarFuentes() {

		// recorrer lista de fuentes
		for (String nombre : listaFuentes) {
			try {
				// cargar la fuente
				Font fuente = Font.createFont(Font.TRUETYPE_FONT,
						Recursos.class.getResourceAsStream("fuentes/" + nombre + ".ttf"));
				fuentes.put(nombre, fuente);
			} catch (IOException e) {
				System.out.println("Problema cargando la fuente" + nombre + ".ttf");
				return false;
			} catch (FontFormatException e) {
				System.out.println("Problema cargando la fuente" + nombre + ".ttf");
				return false;
			}
		}
		return true;
	}

	/**
	 * Carga las imagenes
	 * 
	 * @return True si se han cargado todas, False si no se han cargado todas
	 */
	private boolean cargarImg() {

		// recorrer lista de imagenes
		for (String nombre : listaImg) {
			try {
				// cargar imagen
				BufferedImage img = ImageIO.read(Recursos.class.getResourceAsStream("img/" + nombre + ".png"));
				imagenes.put(nombre, img);
			} catch (IOException | IllegalArgumentException e) {
				System.out.println("Problema cargando la imagen " + nombre + ".png");
				return false;
			}
		}
		return true;
	}

	/**
	 * Devuelve una imagen cargada
	 * 
	 * @param nombre Key usada en el hasmap para identificar la imagen
	 * 
	 * @return La imagen como BufferedImage
	 */
	public BufferedImage getImg(String nombre) {
		return imagenes.get(nombre);
	}

	/**
	 * Devuelve una fuente cargada
	 * 
	 * @param nombre Key usada en el hasmap para identificar la fuente
	 * @return la fuente como Font
	 */
	public Font getFuente(String nombre) {
		return fuentes.get(nombre);
	}
}
