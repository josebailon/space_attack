/**
 * 
 */
package reto8juego.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;

import reto8juego.Controlador;
import reto8juego.config.Config;

/**
 * JFrame que contiene la visualizacion del juego. 
 * Contiene un componente Lienzo que sera donde se dibuje.
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Ventana extends JFrame {
	private static final long serialVersionUID = 1L;
	

	
	/**
	 * Lienzo de dibujado
	 */
	private Lienzo lienzo;
	
	/**
	 * Constructor
	 * 
	 * @param control Referencia al controlador
	 */
	public Ventana(Controlador control) {
		
		//configurar ventana
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Config.ANCHO, Config.ALTO);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//agregar lienzo
		lienzo = new Lienzo();
		this.add(lienzo,BorderLayout.CENTER);
		
		//poner el controlador como keylistener
		this.addKeyListener(control);
		
		//mostrar el jframe
		this.setVisible(true);
	}
	/**
	 * Devuelve una referencia al lienzo
	 * 
	 * @return El lienzo
	 */
	public Lienzo getLienzo() {
		return lienzo;
	}
 
}
