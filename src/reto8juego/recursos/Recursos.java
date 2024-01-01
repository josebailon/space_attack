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
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Recursos {
	private HashMap<String, BufferedImage> imagenes=new HashMap();
	private HashMap<String, Font> fuentes=new HashMap();
	private String[]listaImg= {
			"fondo0",
			"fondo1",
			"fondo2",
			"fondo3",
			"nave0",
			"nave1",
			"nave2",
			"nave3",
			"nave4",
			"nave5",
			"nave6",
			"nave7",
			"nave8",
			"nave9",
			"nave10",
			"nave11",
			"nave12",
			"nave13",
			"nave14",
			"escudo",
			"disparoAmigo",
			"meteorito0",
			"meteorito1",
			"meteorito2",
			"expmeteo0",
			"expmeteo1",
			"expmeteo2",
			"expmeteo3",
			"expmeteo4",
			"expmeteo5",
			"expmeteo6",
			"expmeteo7",
			"expmeteo8",
			"expnave0",
			"expnave1",
			"expnave2",
			"expnave3",
			"expnave4",
			"expnave5",
			"expnave6",
			"expnave7",
			"expnave8",
			"moneda0",
			"moneda1",
			"moneda2",
			"moneda3",
			"moneda4",
			"moneda5",
			"escudo0",
			"escudo1",
			"escudo2",
			"escudo3",
			"escudo4",
			"escudo5",
			"vida0",
			"vida1",
			"vida2",
			"vida3",
			"vida4",
			"vida5",
			"salud0",
			"salud1",
			"salud2",
			"salud3",
			"salud4",
			"salud5",
			
			};
	private String[]listaFuentes= {"CHECKBK0","COMPUTERRobot","plasmati"};
	
	
	private static Recursos instancia;
	
	private Recursos() {}
	
	public static Recursos getInstancia(){
		if (instancia==null) {
			instancia =new Recursos();
		}
		return instancia;
	}
	
	public boolean cargarRecursos() {
		return cargarImg() && cargarFuentes();
	}
	
	/**
	 * @return
	 */
	private boolean cargarFuentes() {
		for (String nombre : listaFuentes) {
			try {
				Font fuente =Font.createFont(Font.TRUETYPE_FONT,Recursos.class.getResourceAsStream("fuentes/"+nombre+".ttf"));

//				Font fuente =Font.createFont(Font.TRUETYPE_FONT,getClass().getResource(nombre+".ttf").openStream());
				fuentes.put(nombre, fuente);
			} catch (IOException e) {
				return false;
			} catch (FontFormatException e) {
				return false;
			}			
		}
		return true;
	}

	private boolean cargarImg() {
		for (String nombre : listaImg) {
			try {
				BufferedImage img =ImageIO.read(Recursos.class.getResourceAsStream("img/"+nombre+".png"));

				//BufferedImage img =ImageIO.read(getClass().getResource(nombre+".png"));
				imagenes.put(nombre, img);
			} catch (IOException e) {
				return false;
			}			
		}
		return true;
	}
	
	public BufferedImage getImg(String nombre) {
		return imagenes.get(nombre);
	}
	
	public Font getFuente(String nombre) {
		return fuentes.get(nombre);
	}
}
