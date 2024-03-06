package java2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MyJPanel extends JPanel {
	MyJPanel() {
		Dimension tdim = Toolkit.getDefaultToolkit().getScreenSize();	
		int tw = tdim.width;
		int th = tdim.height;
		// Anteil in Prozent wie viel ein MyJPanel ausgefüllt wird
		int pro = 50;
		int fw = tw * pro / 100;
		int fh = th * pro / 100;
		Dimension fdim = new Dimension(fw, fh);
		this.setPreferredSize(fdim);
		this.setMinimumSize(fdim);
		this.setBackground(Color.GRAY);		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int width = 400;
		int length = 280;
		
		Rectangle grundflaeche = new Rectangle(0, 0, width,length);
		BufferedImage bi = new BufferedImage(60, 60, BufferedImage.TYPE_INT_RGB);
		Graphics2D big = bi.createGraphics();
		
		g2.scale(1.4, 1.4);
	//	g2.translate(width, length);
	
	//Die Grundfläche, als quadratische 30 x 30 cm Kacheln in zwei verschiedenen Farben
		big.setColor(Color.black);
		big.fillRect(30, 0, 30, 30);
		big.fillRect(0, 30, 30, 30);
		big.setColor(Color.yellow);
		big.fillRect(0, 0, 30, 30);
		big.fillRect(30, 30, 30, 30);

		Rectangle r = new Rectangle(0, 0, 60, 60);		
		
		g2.setPaint(new TexturePaint(bi, r));
		g2.draw(grundflaeche);
		g2.fill(grundflaeche);
		
	//Ein ca. 350 x 60 cm großer Teppich mit einem linearen Farbverlauf
		GradientPaint gradientPaint = new GradientPaint(0,0,Color.green, 350, 0, Color.green);
		g2.setPaint(gradientPaint);
		g2.fillRect(0, 0, 350, 60);
		
		
	//Eine abgehängte Decke in der linken Hälfte des Zimmers. Mit transparent dargestellt werden (alpha = 0.5).
		Color deckeFarbe = new Color(0,0,255,128);
		g2.setColor(deckeFarbe);
		g2.fillRect(0, 0, 200, 280);
					
		
	//Ein Schreibtisch im repräsentativen Format 200 mal 100.
	//Der Schreibtisch soll gedreht sein, sodaß er in das Zimmer passt und nicht mit der Liege kollidiert.	
		double liegeWinkel = Math.toRadians(14.0);
		AffineTransform previousTransform = g2.getTransform();
		AffineTransform transform = new AffineTransform();
		transform.rotate(liegeWinkel);
		g2.transform(transform);	
		
		g2.setColor(Color.WHITE);
		g2.fillRect(210, 0, 200, 100);
		g2.setTransform(previousTransform);	
		
	//Eine Liege im Format 220 mal 120 Einheiten
		g2.setColor(Color.CYAN);
		g2.fillRect(0, 160, 220, 120);
		
	//Darauf eine Decke mit einem darüber gelegten Fanmotiv. Dieses soll als Bild importiert werden.
		int x = 200;
		int y = 100;
		BufferedImage img = null;
				try {
					img = ImageIO.read(new File("bayernMuenchen.png"));
				} catch (IOException e) {	
					System.out.println("Bild konnte nicht geladen werden");
				}
				
		g2.drawImage(img, 0, 160, 220, 120, null);

	//Couchtisch
		g2.setColor(Color.ORANGE);
		g2.fillOval(50, 90, 80, 50);
		
	//Eine Lichtleiste an der Decke, welche in der rechte Hälfte des Zimmers mit Wandabstand 20 verläuft 
	//und bis unter die abgehängte Decke reicht. Sie soll gestrichelt dargestellt werden.	
		float[] dashPattern = {21.0f, 9.0f, 3.0f, 9.0f};
		BasicStroke dashedStroke = new BasicStroke(5.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1.0f, dashPattern, 0.0f);
		g2.setStroke(dashedStroke);
		g2.setColor(Color.MAGENTA);
		g2.drawLine(200, 20,380, 20);
		g2.drawLine(380, 20,380, 260);
		g2.drawLine(380, 260,200, 260);	
		
		
	//Schriftzug: „Das Zimmer von“ links oben.	
		g2.setFont(new Font("Papyrus",Font.ITALIC,20)); 
		g2.setColor(Color.DARK_GRAY);
		g2.drawString("Das Zimmer von: Leilani Riehmann",10,45);
		
	//Kollisionsprüfung
		Rectangle stisch = new Rectangle(200, 10, 200, 100);
		Rectangle liege = new Rectangle(0, 160, 220, 120);
		
		if (stisch.intersects(liege)) {
			System.out.println("Die Zeichnungen berühren sich.");
		} else {
			System.out.println("Die Zeichnungen berühren sich nicht.");
		}
		
	}

}
