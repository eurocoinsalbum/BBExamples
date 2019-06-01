import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Siedler2D extends JFrame {
	private SiedlerCanvas siedlerCanvas;
	public static int IMG_SIZE = 48;
	
	public Siedler2D() {
		super("BBSiedler");
		siedlerCanvas = new SiedlerCanvas();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, Main.SIZE_X * IMG_SIZE, Main.SIZE_Y * IMG_SIZE);
		getContentPane().add(siedlerCanvas);
		setVisible(true);
	}
	
	public void zeichne() {
		siedlerCanvas.repaint();
	}
	
	private class SiedlerCanvas extends JComponent {
		private Image[] images = new Image[Main.ANZAHL_FELDINHALT_TYPEN];
		private Image imageGold;

		public SiedlerCanvas() {
			images[Main.BAUM] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/baum.png"));
			images[Main.STAMM] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/stamm.png"));
			images[Main.WIESE] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/wiese.png"));
			images[Main.SETZLING] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/topfpflanze.png"));
			images[Main.BRETTER] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/holz.png"));
			images[Main.FOERSTER] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/bauer.png"));
			images[Main.HOLZFAELLER] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/axt.png"));
			images[Main.SAEGEWERK] = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/saege.png"));
			imageGold = Toolkit.getDefaultToolkit().getImage(getClass().getResource("img/gold.png"));
		}

		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			for (int y = 0; y < Main.SIZE_Y; y++) {
				for (int x = 0; x < Main.SIZE_X; x++) {
					Position position = Main.createPosition(y, x);
					Image image = images[Main.getFeldInhalt(position)];
					g2.drawImage(image, x * IMG_SIZE, y * IMG_SIZE, this);
				}
			}
			g2.drawImage(imageGold, 0, 0, this);
			g2.drawString(String.valueOf(Main.spieler.gold), 25, 20);

			g2.drawString("All Icons by www.icons8.de", 0, Main.SIZE_Y * IMG_SIZE - 50);
			g2.finalize();
		}
	}
}
