/**
 * 
 */
package reto8juego.gui;

import java.awt.BorderLayout;
import java.awt.Color;


import javax.swing.JFrame;

import reto8juego.Controlador;
import reto8juego.config.Config;
import reto8juego.motor.Motor;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Ventana extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private Controlador control;
	private Lienzo lienzo;
	public Ventana(Controlador control) {
		this.control=control;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Config.ANCHO, Config.ALTO);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//agregar lienzo
		lienzo = new Lienzo();
		this.add(lienzo,BorderLayout.CENTER);
		
		//asignar lienzo al motor
		this.setVisible(true);
		this.addKeyListener(control);
	}
	/**
	 * @return
	 */
	public Lienzo getLienzo() {
		return lienzo;
	}
 
}
