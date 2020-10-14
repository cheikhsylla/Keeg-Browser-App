package kegg.gui;

/**
 * Legend.java
 */
import java.awt.Container;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

//import org.fit.cssbox.swingbox.BrowserPane;

public class Legend extends JFrame {
	private static final long serialVersionUID = 1L;
//	private BrowserPane bp;
	JLabel legend_viewer;
	
	public Legend() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setTitle("ORF Color");
		setSize(580,650);
		setResizable(false);
		
//		bp = new BrowserPane();
//		try {
//			bp.setPage(new URL("http://www.kegg.jp/kegg/docs/ko_color.html"));
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		ImageIcon image = new ImageIcon("./data/legend.png");
		
		int img_width = image.getIconWidth();
		int img_heigth = image.getIconHeight();
		
		setSize(img_width + 15, img_heigth + 30);
		
		
		legend_viewer = new JLabel();
		legend_viewer.setIcon((Icon) image);
		
		
		
		Container container = getContentPane();
//		container.add(bp);
		container.add(legend_viewer);
			
	}
	
}
