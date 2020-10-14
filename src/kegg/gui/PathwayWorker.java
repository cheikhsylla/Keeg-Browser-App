package kegg.gui;
/**
 * PathwayWorker.java
 */
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingWorker;


public class PathwayWorker extends SwingWorker<Void, Void> {
	
	private String species; // nom de l'espece entree dans le champs species
	private String map_id; // nom de l'identifiant entree dans le champs id
	private JButton search;
	
	public PathwayWorker(String species, String map_id, JButton search) {
		this.species = species;
		this.map_id = map_id;
		this.search = search;
	}
	
	@Override
	public Void doInBackground() {
		BrowserPathway.afficherContenu(species, map_id);
		return null;
	}
	
	@Override 
	protected void done() {
		search.setEnabled(true);
		search.setIcon((Icon)(new ImageIcon("./data/search.png")));
	}
	
}
