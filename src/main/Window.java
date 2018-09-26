/**
 * Main Entry Point for the Java application.
 */

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Window {
	
	public static JFrame frame;
	public static final String TITLE			= "PlottingSoftware by D3PSI";
	public static int SCR_WIDTH					= 1280;
	public static int SCR_HEIGHT				= 780;
	
	public static int PANEL_WIDTH 				= 3 * SCR_WIDTH / 4;
	public static int PANEL_HEIGHT 				= SCR_HEIGHT;
	
	public static final double G_RESOLUTION		= 0.0001;
	public static final int X_SCALE 			= 100;
	public static final int Y_SCALE				= 100;
	
	/*
	 * Constructor
	 */
	public Window() {
		
		ImageIcon icon = new ImageIcon("res/icon/icon.png");
		
		frame = new JFrame(TITLE);
		frame.setSize(SCR_WIDTH, SCR_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());
		frame.setPreferredSize(frame.getSize());
		
		JPanel panel = new Graph(frame.getSize(), frame);
		panel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		frame.add(panel);
		frame.setBackground(Color.WHITE);
		frame.pack();
		frame.setVisible(true);
		
		frame.addComponentListener(new ComponentAdapter() {
			
			public void componentResized(ComponentEvent componentEvent) {
				
				Rectangle r = frame.getBounds();
				int width = r.width;
				int height = r.height;
				
				PANEL_WIDTH = 3 * width / 4;
				SCR_WIDTH = width;
				PANEL_HEIGHT = height;
				SCR_HEIGHT = height;
				panel.setBounds(0, 0, r.width * 75 / 100, r.height);
				
			}
			
		});
		
	}
	
	/**
	 * MAIN ENTRY POINT
	 * @author D3PSI
	 *
	 */
	public static void main(String args[]) {
		
		new Window();
		
	}
	
	public class Graph extends JPanel {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -9119398871761358624L;
		
		/**
		 * 
		 * @param dimension
		 */
		public Graph(Dimension dimension, Frame frame) {
			
			setSize(dimension);
			setPreferredSize(dimension);
			
		}
		
		/**
		 * Plotting algorithm
		 * 
		 */
		@Override
		public void paintComponent(Graphics g) {
			
			double X = 0;	
			double Y = 0;
			
			g.drawLine(PANEL_WIDTH / 2, 0, PANEL_WIDTH / 2, PANEL_HEIGHT);
			g.drawLine(0, PANEL_HEIGHT / 2, PANEL_WIDTH, PANEL_HEIGHT / 2);
			
			/*
			 * X-AXIS
			 */
			for(int i = 0; i < PANEL_WIDTH; i += X_SCALE) {
				
				g.drawLine(i + PANEL_WIDTH / 2, PANEL_HEIGHT / 2 + 5, i + PANEL_WIDTH / 2, PANEL_HEIGHT / 2 - 5);
				g.drawLine(-i + PANEL_WIDTH / 2, PANEL_HEIGHT / 2 + 5, -i + PANEL_WIDTH / 2, PANEL_HEIGHT / 2 - 5);
				
			}
			
			/*
			 * Y-AXIS
			 */
			for(int i = 0; i < PANEL_HEIGHT; i += Y_SCALE) {
				
				g.drawLine(PANEL_WIDTH / 2 + 5, i + PANEL_HEIGHT / 2, PANEL_WIDTH / 2 - 5, i + PANEL_HEIGHT / 2);
				g.drawLine(PANEL_WIDTH / 2 + 5, -i + PANEL_HEIGHT / 2, PANEL_WIDTH / 2 - 5, -i + PANEL_HEIGHT / 2);
				
			}
			
			/*
			 * Plotting-action
			 */
			for(double x = -((PANEL_WIDTH / 2) / X_SCALE); x < (PANEL_WIDTH / 2) / X_SCALE; x += G_RESOLUTION) {
				
				if(x == -((PANEL_WIDTH / 2) / X_SCALE)) {
					
					g.setColor(Color.WHITE);
					
				}
				
				double y = f(x);
				X = x * X_SCALE + PANEL_WIDTH / 2;
				Y = -(y * Y_SCALE - PANEL_HEIGHT / 2);
				
				g.drawOval((int) X, (int) Y, 1, 1);
				g.setColor(Color.RED);
				
			}
			
		}
		
		public double f(double x) {
			
			double y = Math.atan(x);
			NS(y);
			return y;
			
		}
		
		public double NS(double x) {
			
			double X = ;
			return X;
			
			
		}
		
	}
	
}
