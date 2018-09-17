/**
 * 
 * Main Entry Point for the Java application.
 */

package main;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Window {
	
	public static JFrame frame;
	public static final String TITLE	= "PlottingSoftware by D3PSI";
	public static final int SCR_WIDTH	= 1024;
	public static final int SCR_HEIGHT	= 768;
	
	public static final int X_SCALE = 1;
	public static final int Y_SCALE = 100;
	
	public Window() {
		
		frame = new JFrame(TITLE);
		frame.setSize(SCR_WIDTH, SCR_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setPreferredSize(frame.getSize());
		frame.add(new Graph(frame.getSize()));
		frame.pack();
		frame.setVisible(true);
		
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
		public Graph(Dimension dimension) {
			
			setSize(dimension);
			setPreferredSize(dimension);
			
		}
		
		/**
		 * 
		 * 
		 */
		@Override
		public void paintComponent(Graphics g) {
			double p1X = SCR_WIDTH / 2;	
			double p1Y = 0;
			double p2X = SCR_WIDTH / 2;
			double p2Y = 0;

			g.drawLine(SCR_WIDTH / 2, 0, SCR_WIDTH / 2, SCR_HEIGHT);
			g.drawLine(0, SCR_HEIGHT / 2, SCR_WIDTH, SCR_HEIGHT / 2);
			
			for(double i = -1000; i < 1000; i += 0.01) {
				double value = Math.pow(i, 2) + 1;
				p1X = i * X_SCALE + SCR_WIDTH / 2;
				p1Y = -(value / Y_SCALE - SCR_HEIGHT / 2);
				
				g.drawLine((int) p2X, (int) p2Y, (int) p1X, (int) p1Y);
				
				p2X = p1X;
				p2Y = p1Y;
			}
			
			
		}
		
	}
	
}
