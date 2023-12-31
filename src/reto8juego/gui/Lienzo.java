/**
 * 
 */
package reto8juego.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import javax.swing.JPanel;

import reto8juego.motor.Dibujo;
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
	ArrayList<LinkedBlockingDeque<Dibujo>> capas = new ArrayList();

	float i=0;
	public Lienzo() {

	}



	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D) g;
		for (LinkedBlockingDeque<Dibujo> capa : capas) {
			Iterator<Dibujo> it =capa.iterator();
			while (it.hasNext()) {
				it.next().dibujar(g2);
			}
		}

	}



	@Override
	public void actionPerformed(ActionEvent e) {
//		y+=0.5;
//		repaint();
	}



	/**
	 * @param capaFondo
	 */
	public void addCapa(LinkedBlockingDeque<Dibujo> capa) {
		this.capas.add(capa);
	}

	
}
