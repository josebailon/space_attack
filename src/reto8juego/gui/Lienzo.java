/**
 * 
 */
package reto8juego.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JPanel;

import reto8juego.actores.Dibujo;
import reto8juego.motor.Motor;

/**
 * 
 * @author Jose Javier Bailon Ortiz
 */
public class Lienzo extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Motor motor;
	

	public Lienzo(Motor motor) {
		this.motor=motor;
	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		
		
		//fondo
		Iterator<Dibujo> capaFondo = motor.getCapaFondo();
		while (capaFondo.hasNext()) {
			capaFondo.next().dibujar(g2);
		}
		//nave
		Iterator<Dibujo> capaNave= motor.getCapaNave();
		while (capaNave.hasNext()) {
			capaNave.next().dibujar(g2);
		}

		
		//nave
		Iterator<Dibujo> capaGui= motor.getCapaGui();
		while (capaGui.hasNext()) {
			capaGui.next().dibujar(g2);
		}
		
		

	}



	@Override
	public void actionPerformed(ActionEvent e) {
//		y+=0.5;
//		repaint();
	}

	
}
