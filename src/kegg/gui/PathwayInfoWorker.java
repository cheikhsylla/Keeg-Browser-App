package kegg.gui;
/**
 * PathwayInfoWorker.java
 */
import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.SwingWorker;

import kegg.recuperation.Fichiers;

/**
 * Thread qui s'execute lors d'un clic sur une reaction dans le Pathway Browser. 
 */
public class PathwayInfoWorker extends SwingWorker<Void, Void> {
	
	private String species; // nom de l'espece entree dans le champs species
	private String map_id; // nom de l'identifiant entree dans le champs id
	private String reaction;
	private JLabel image;
	
	/**
	 * Constructeur du PathwayInfoWorker
	 * @param species Identifiant de l'espece.
	 * @param map_id Identifiant de la voie metabolique.
	 * @param reaction Identifiant de la reaction selectionnee.
	 */
	public PathwayInfoWorker(String species, String map_id, String reaction, JLabel image) {
		this.species = species;
		this.map_id = map_id;
		this.reaction = reaction;
		this.image = image;
	}
	
	@Override
	public Void doInBackground() {
		InfoPathway.setReaction(reaction);
		InfoPathway.setInfo(Fichiers.getReactionText(reaction));
		InfoPathway.setList(Fichiers.involvesGenesList(species, map_id));
		return null;
	}
	
	@Override 
	protected void done() {
		image.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
	}
	
}
