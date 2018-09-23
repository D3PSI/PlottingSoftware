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
	
	public static final double G_RESOLUTION		= 0.0001;
	public static final int X_SCALE 			= 100;
	public static final int Y_SCALE				= 100;
	
	public Window() {
		
		ImageIcon icon = new ImageIcon("res/icon/icon.png");
		
		frame = new JFrame(TITLE);
		frame.setSize(SCR_WIDTH, SCR_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());
		frame.setPreferredSize(frame.getSize());
		
		JPanel panel = new Graph(frame.getSize(), frame);
		frame.add(panel);
		frame.setBackground(Color.WHITE);
		frame.pack();
		frame.setVisible(true);
		
		frame.addComponentListener(new ComponentAdapter() {
			
			public void componentResized(ComponentEvent componentEvent) {
				
				Rectangle r = frame.getBounds();
				int width = r.width;
				int height = r.height;
				
				SCR_WIDTH = width;
				SCR_HEIGHT = height;
				panel.setBounds(0, 0, r.width, r.height);
				
			}
			
		});
		
	}
	
	/**
	 * 
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
		 * 
		 * 
		 */
		@Override
		public void paintComponent(Graphics g) {
			double p1X = 0;	
			double p1Y = 0;
			//double p2X = 0;
			//double p2Y = 0;
			
			g.drawLine(SCR_WIDTH / 2, 0, SCR_WIDTH / 2, SCR_HEIGHT);
			g.drawLine(0, SCR_HEIGHT / 2, SCR_WIDTH, SCR_HEIGHT / 2);
			
			/*
			 * X-AXIS
			 */
			for(int i = 0; i < SCR_WIDTH; i += X_SCALE) {
				
				g.drawLine(i + SCR_WIDTH / 2, SCR_HEIGHT / 2 + 5, i + SCR_WIDTH / 2, SCR_HEIGHT / 2 - 5);
				g.drawLine(-i + SCR_WIDTH / 2, SCR_HEIGHT / 2 + 5, -i + SCR_WIDTH / 2, SCR_HEIGHT / 2 - 5);
				
			}
			
			/*
			 * Y-AXIS
			 */
			for(int i = 0; i < SCR_HEIGHT; i += Y_SCALE) {
				
				g.drawLine(SCR_WIDTH / 2 + 5, i + SCR_HEIGHT / 2, SCR_WIDTH / 2 - 5, i + SCR_HEIGHT / 2);
				g.drawLine(SCR_WIDTH / 2 + 5, -i + SCR_HEIGHT / 2, SCR_WIDTH / 2 - 5, -i + SCR_HEIGHT / 2);
				
			}
			
			/*
			 * Plotting-action
			 */
			for(double x = -((SCR_WIDTH / 2) / X_SCALE); x < (SCR_WIDTH / 2) / X_SCALE; x += G_RESOLUTION) {
				if(x == -((SCR_WIDTH / 2) / X_SCALE)) {
					g.setColor(Color.WHITE);
				}
				
				double y = Math.atan(Math.pow(x, 2));
				p1X = x * X_SCALE + SCR_WIDTH / 2;
				p1Y = -(y * Y_SCALE - SCR_HEIGHT / 2);
				
				//g.drawLine((int) p1X, (int) p1Y, (int) p2X, (int) p2Y);
				g.drawOval((int) p1X, (int) p1Y, 1, 1);
				g.setColor(Color.RED);
				
				//p2X = p1X;
				//p2Y = p1Y;
			}
			
		}
		
	}
	
}
