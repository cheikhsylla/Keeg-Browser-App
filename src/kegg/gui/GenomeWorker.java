package kegg.gui;
/**
 * GenomeWorker.java
 */
import java.awt.Cursor;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingWorker;

import kegg.recuperation.Fichiers;

/**
 * Thread en tache de fond pour le Genome Browser.
 * @author Antoine Cossa & Cheick Sylla
 *
 */
public class GenomeWorker extends SwingWorker<Void, Void> {
	
	private String species; // nom de l'espece entree dans le champs species
	private String gene_id; // nom de l'identifiant entree dans le champs id
	private JButton search;
	
	public GenomeWorker(String species, String gene_id, JButton search) {
		this.species = species;
		this.gene_id = gene_id;
		this.search = search;
	}
	
	@Override
	public Void doInBackground() {
        BrowserGenome.setPage();
		try {
			InfoGenome.setInfo(Fichiers.getGenomeFiles(species, gene_id));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Recupere la liste des voies metaboliques impliquees
		Fichiers.getPathwayList(species, gene_id);
		InfoGenome.setList(Fichiers.involvedInReactionsList(species, gene_id));
		return null;
	}
	
	@Override 
	protected void done() {
		search.setEnabled(true);
		search.setIcon((Icon)(new ImageIcon("./data/search.png")));
		search.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
	}
	
}
