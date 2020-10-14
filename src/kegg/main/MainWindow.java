package kegg.main;
/**
 * Fenetre.java
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import kegg.recuperation.Fichiers;
import kegg.gui.Panneau;
/** 
 * Classe qui permet l'affichage graphique du programme.
 * @author Antoine Cossa & Cheick Sylla
 */
public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;
	
    private static Panneau main_container;
    
	public MainWindow() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLocationRelativeTo(null); // affiche la fenetre au milieu de l'ecran
        setTitle("KEGG Browser");    // nom de la fenetre
        setSize(900, 480);           // taille de la fenetre
        setMinimumSize(new Dimension(750, 480));     // taille minimun de la fenetre
        setResizable(true);          // redimension de la fenetre
        setVisible(true);
        try {
        	Image icon = ImageIO.read(new File("./data/kegg.gif"));
        	setIconImage(icon);
        } catch (IOException ioe) {
        	System.out.println("Could not set program icon:" + ioe.getMessage());
        }
        
        Container conteneur = getContentPane();
        main_container = new Panneau();
        conteneur.add(main_container);
        
    }
    
	/**
	 * @param args
	 */
    public static void main(String[] arg) throws IOException {
    	Fichiers.setWorkingDirectory();
    	SwingUtilities.invokeLater(new Runnable() {
	    	public void run() {
	    		try {
					new MainWindow();
				} catch (IOException e) {
					System.err.println("Error:" + e.getMessage());
					e.printStackTrace();
				}
	    	}
    	});
    }

    
}
