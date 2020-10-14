import java.awt.BorderLayout;
import java.awt.Graphics;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

//package kegg.gui;
/**
 * Browser  
 */
public class Browser extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Icon image;
	private JEditorPane html_viewer;
	
	
//	private static final double SCALE = 0.7;
	
    public Browser(JEditorPane html_viewer) {
    	this.html_viewer = html_viewer;
        this.html_viewer = new JEditorPane(); // Contenu html
        this.html_viewer.setEditable(false);  // Contenu non modifiable par l'utilisateur
//        html_viewer.addHyperlinkListener(new Hyperactive());
        
        // Ajout au browser
	    setLayout(new BorderLayout());
	    add(new JScrollPane(html_viewer), BorderLayout.CENTER);
	    
	    try {
//	    	URL url = new URL("http://www.kegg.jp/kegg-bin/show_genomemap?ORG=" + menu.getSpecies() + "&ACCESSION=" + menu.getID());// "&CHR=c&START_POS=660001");
	    	URL url = new URL("http://www.kegg.jp/kegg-bin/show_genomemap?ORG=eco&CHR=c&START_POS=660001");
			afficherHtml(html_viewer, url);
		} 
	    catch (MalformedURLException e) {
			e.printStackTrace();
		} 
	    catch (IOException ioe) {
			ioe.printStackTrace();
		}
    }
    
    public Browser(JLabel image_viewer) {
    	image_viewer = new JLabel();
  	
    	// Ajout au browser
    	setLayout(new BorderLayout());
	    add(new JScrollPane(image_viewer), BorderLayout.CENTER);
	    try {
			afficherImage(image_viewer, new URL("http://www.google.com"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
    }
    
    
    public void afficherImage(JLabel image_viewer, URL url) throws IOException {
    	
    	image = (Icon)(new ImageIcon("file://./data/hsa04911.png"));
//    	image = (Icon)(new ImageIcon("http://rest.kegg.jp/get/" + ??? + "/image"));
		image_viewer.setIcon(image); 
    }
    
    public void afficherHtml(JEditorPane html_viewer, URL url) throws IOException {
        html_viewer.setPage(url);
    }
    
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		Graphics2D g2d = (Graphics2D) g;
//		g2d.scale(SCALE, SCALE);
	}
}
