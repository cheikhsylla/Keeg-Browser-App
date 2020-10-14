package kegg.gui;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * Etiquette avec un fond colore.
 * @author Antoine Cossa & Cheick Sylla
 */
public class Etiquette extends JLabel {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Couleur du fond de l'etiquette.
	 */
	private final Color COLOR = new Color(130, 200, 255) ;
	private final int OFFSET = 5;
	
	/**
	 * Constructeur d'etiquette a fond colore.
	 * @param nom_etiquette Nom de l'etiquette passee en argument.
	 */
	public Etiquette(String nom_etiquette) {
		setText(nom_etiquette);
		setOpaque(true);
		setBackground(COLOR);
		setAlignmentX(LEFT_ALIGNMENT);
		setBorder(BorderFactory.createEmptyBorder(OFFSET,OFFSET,OFFSET,OFFSET));
		
	}
}
