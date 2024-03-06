package java2D;

import javax.swing.JFrame;

public class MyJFrame extends JFrame{
	
		public MyJFrame() {
		this.setTitle("2d Basis");
		// Platzierung des Frames bei Aufruf
		this.setLocationByPlatform(true);
	
		
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// Einzige Komponente in Content Pane
		setContentPane(new MyJPanel());
		pack();
		setVisible(true);
		}
		public static void main(String args[]) {
		new MyJFrame();
		}

}
