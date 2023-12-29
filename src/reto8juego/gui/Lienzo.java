/**
 * 
 */
package reto8juego.gui;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import reto8juego.actores.Dibujo;
import reto8juego.motor.Motor;
import reto8juego.recursos.Recursos;

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
		
		

	}



	@Override
	public void actionPerformed(ActionEvent e) {
//		y+=0.5;
//		repaint();
	}

	
}
