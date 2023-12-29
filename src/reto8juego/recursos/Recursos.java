/**
 * 
 */
package reto8juego.recursos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Recursos {
	private HashMap<String, BufferedImage> recursos=new HashMap();
	private String[]listaImg= {
			"fondo0",
			"fondo1",
			"fondo2",
			"fondo3",
	};
	private static Recursos instancia;
	
	private Recursos() {}
	
	public static Recursos getInstancia(){
		if (instancia==null) {
			instancia =new Recursos();
		}
		return instancia;
	}
	
	public boolean cargarRecursos() {
		return cargarImg();
	}
	
	private boolean cargarImg() {
		for (String nombre : listaImg) {
			try {
				BufferedImage img =ImageIO.read(getClass().getResource(nombre+".png"));
				recursos.put(nombre, img);
			} catch (IOException e) {
				return false;
			}			
		}
		return true;
	}
	
	public BufferedImage getImg(String nombre) {
		return recursos.get(nombre);
	}
}
