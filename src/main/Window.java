/**
 * Main Entry Point for the Java application.
 */

package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
		
		Border redline = BorderFactory.createLineBorder(Color.BLACK);
		Border compound = null;
		compound = BorderFactory.createCompoundBorder(redline, compound);;
		compound = BorderFactory.createTitledBorder(compound, "GRAPH", TitledBorder.CENTER, TitledBorder.TOP);
		
		frame = new JFrame(TITLE);
		frame.setSize(SCR_WIDTH, SCR_HEIGHT);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setIconImage(icon.getImage());
		frame.setPreferredSize(frame.getSize());
		
		JPanel panel = new Graph(frame.getSize());
		JPanel disc = new Disc(frame.getSize());
		panel.setBorder(compound);
		compound = BorderFactory.createTitledBorder(compound, "DISKUSSION", TitledBorder.CENTER, TitledBorder.TOP);
		disc.setBorder(compound);
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		disc.setSize(SCR_WIDTH - PANEL_WIDTH, SCR_HEIGHT);
		Dimension dim = new Dimension(SCR_WIDTH - PANEL_WIDTH, SCR_HEIGHT);
		disc.setPreferredSize(dim);
		container.add(panel);
		container.add(disc);
		frame.add(container);
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
				disc.setBounds(PANEL_WIDTH, 0, r.width * 1 / 4, r.height);
				
			}
			
		});
		
	}
	
	/**
	 * MAIN ENTRY POINT
	 * @author D3PSI
	 *
	 */
	public static void main(String args[]) {
		
		Window win = new Window();
		
	}
	
	public class Disc extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -6290552416021916302L;
		
		double nullstelle;
		List<Double> nullstellen = new ArrayList<Double>();
		
		/*
		 * 
		 * @param dimension
		 */
		public Disc(Dimension dim) {
			
			setSize(dim);
			setPreferredSize(dim);
			addLabels(this);
			
		}
		
		/*
		 * 
		 */
		private void addLabels(JPanel panel) {

			List<Double> values = new ArrayList<Double>();	
			OptionalDouble average;
			
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			
			for(double x = -((PANEL_WIDTH / 2) / X_SCALE) - 1; x < (PANEL_WIDTH / 2) / X_SCALE + 1; x += G_RESOLUTION / 10) {
				
				double y = Graph.f(x);
				
				if(Double.isNaN(y)) {
					
					continue;
					
				} else if(y < 0.01 && y > -0.01) {
				
					double val = x;
					values.add(val);
					
				}else if(y == 0) {
					
					nullstelle = x;
					nullstellen.add(nullstelle);
					
				}
				
			}
			
			List<Double> actualNullstellen = new ArrayList<Double>();
			
			for(int i = 0; i < values.size() - 1; i++) {
				
				if(values.get(i + 1) - 0.01 < values.get(i)) {
					
					actualNullstellen.add(values.get(i));
					
				}
			
			}
			
			average = actualNullstellen.stream().mapToDouble(a -> a).average();
			if(average.isPresent())
				nullstellen.add(average.getAsDouble());
			
			JLabel lbl1 = new JLabel("Achsenabschnitt:	y = " + df.format(Graph.f(0)));
			panel.add(lbl1);
			for(int i = 0; i < nullstellen.size(); i++) {
				JLabel lbl2 = new JLabel("Nullstellen:	x = " + df.format(nullstellen.get(i)));
				panel.add(lbl2);
			}
			
		}
		
		/*
		 * 
		 */
		@Override
		public void paintComponent(Graphics g) {
			
			
			
		}
		
	}
	
	public static class Graph extends JPanel {
	
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
			for(double x = -((PANEL_WIDTH / 2) / X_SCALE) - 1; x < (PANEL_WIDTH / 2) / X_SCALE + 1; x += G_RESOLUTION) {
				
				if(x == -((PANEL_WIDTH / 2) / X_SCALE)) {
					
					g.setColor(Color.WHITE);
					
				}
				
				double y = f(x);
				
				if(Double.isNaN(y)) {
					
					continue;
					
				}
				
				X = x * X_SCALE + PANEL_WIDTH / 2;
				Y = -(y * Y_SCALE - PANEL_HEIGHT / 2);
				
				g.drawOval((int) X, (int) Y, 1, 1);
				g.setColor(Color.RED);
				
			}
			
		}
		
		public static double f(double x) {
			
			double y = 2*x*x - 2;
			return y;
			
		}
		
	}
	
}
