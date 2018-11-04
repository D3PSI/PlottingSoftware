/**
 * Main Entry Point for the Java application.
 */

package main;

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Window extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -498460634305555226L;
	public static JFrame frame;
	public static final String TITLE			= "PlottingSoftware by D3PSI";
	public static int SCR_WIDTH					= 1600;
	public static int SCR_HEIGHT				= 780;
	
	public static int PANEL_WIDTH 				= 3 * SCR_WIDTH / 4;
	public static int PANEL_HEIGHT 				= SCR_HEIGHT;
	
	public static final double G_RESOLUTION		= 0.00001;
	public static final int X_SCALE 			= 50;
	public static final int Y_SCALE				= 50;
	
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
		disc.setSize((SCR_WIDTH - PANEL_WIDTH) - 130, SCR_HEIGHT);
		Dimension dim = new Dimension((SCR_WIDTH - PANEL_WIDTH) - 130, SCR_HEIGHT);
		disc.setPreferredSize(dim);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		Button graphspeichern = new Button("Graph speichern");
		Button allesspeichern = new Button("Alles speichern");
		graphspeichern.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					screenshot();
					
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
					
				}
		
			}
		});
		allesspeichern.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent e) {
			
				try {
					
					screenshotAll();
					
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
					
				}
		
			}
		});
		buttons.add(graphspeichern);
		buttons.add(allesspeichern);
		container.add(panel);
		container.add(disc);
		container.add(buttons);
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
				disc.setBounds(PANEL_WIDTH, 0, (r.width * 1 / 4) - 130, r.height);
				
			}
			
		});
		
	}
	
	public void screenshot() throws InterruptedException {
		
		try {
			
	         Robot robot = new Robot();
	         String fileName = "graph.jpg";
	 
	         Rectangle screenRect = new Rectangle(frame.getLocationOnScreen().x + 8, frame.getLocationOnScreen().y + 31, PANEL_WIDTH, PANEL_HEIGHT - 50);
	         BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	         
	         ImageIO.write(screenFullImage, "jpg", new File(fileName));
	         
	      } catch (AWTException | IOException ex) {
	    	  
	               System.err.println(ex);
	      
	      }
		
	}
	
	public void screenshotAll() throws InterruptedException {
		
		try {
			
	         Robot robot = new Robot();
	         String fileName = "diskussion.jpg";
	 
	         Rectangle screenRect = new Rectangle(frame.getLocationOnScreen().x + 8, frame.getLocationOnScreen().y + 31, SCR_WIDTH - 130, SCR_HEIGHT - 50);
	         BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
	         
	         ImageIO.write(screenFullImage, "jpg", new File(fileName));
	         
	      } catch (AWTException | IOException ex) {
	    	  
	               System.err.println(ex);
	      
	      }
		
	}
	
	/**
	 * MAIN ENTRY POINT
	 * @author D3PSI
	 *
	 */
	public static void main(String args[]) {
		
		new Window();
		
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
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			addLabels(this);
			
		}
		
		/*
		 * 
		 */
		private void addLabels(JPanel panel) {
			
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			
			for(double x = -(1000 / X_SCALE); x < 1000 / X_SCALE + 1; x += 0.000001) {
				
				double y = Graph.f(x);
				
				if(Double.isNaN(y)) {
					
					continue;
					
				} else if(y < 0.00001 && y > -0.00001) {
				
					nullstelle = x;
					nullstellen.add(Double.parseDouble(df.format(nullstelle)));
					
				}else if(y == 0) {
					
					nullstelle = x;
					nullstellen.add(Double.parseDouble(df.format(nullstelle)));
					
				}
				
			}
			
			Set<Double> hs = new LinkedHashSet<>();
			hs.addAll(nullstellen);
			nullstellen.clear();
			nullstellen.addAll(hs);
			
			JLabel lbl1 = new JLabel("Achsenabschnitt:	y = " + df.format(Graph.f(0)));
			panel.add(lbl1);
			for(int i = 0; i < nullstellen.size(); i++) {
				JLabel lbl2 = new JLabel("Nullstelle:	x = " + df.format(nullstellen.get(i)));
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
			
			double y = Math.cos(x) / Math.sin(x);
			return y;
			
		}
		
	}
	
}
