/**
 * 
 */
package reto8juego.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
	public Ventana(Controlador control,Motor motor) {
		this.control=control;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(Config.ANCHO, Config.ALTO);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		//agregar lienzo
		Lienzo l = new Lienzo(motor);
		l.setBackground(Color.GREEN);
		this.add(l,BorderLayout.CENTER);
		
		//asignar lienzo al motor
		motor.setLienzo(l);
		this.setVisible(true);
		this.addKeyListener(control);
	}
}
