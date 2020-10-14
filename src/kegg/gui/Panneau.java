package kegg.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Panneau extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 5;
	
	// Initialisation des composants
	private JPanel gene_part; // top half
	private JPanel gene_part_left;
	private JPanel gene_part_right;
	private BrowserGenome genome_browser;
//	public GenomeMenu menu_genome; // menu
	private InfoGenome gene_info;
	
	private JPanel path_part; // bottom half
	private JPanel path_part_left;
	private JPanel path_part_right;
	private BrowserPathway pathway_browser;
//	protected Menu menu_pathway; // menu
	private InfoPathway react_info;
    
    public Panneau() {
		setLayout(new GridLayout(2,1));
	    
	    // =======  Definition de la partie Genome browser (moitie haute) ==========
	    gene_part = new JPanel();
	    gene_part.setLayout(new BorderLayout());
	    
	    gene_part_left = new JPanel();
	    gene_part_right = new JPanel();
	    gene_part_right.setPreferredSize(new Dimension(300, 240));
	    gene_part_left.setLayout(new BorderLayout());
	    gene_part_right.setLayout(new BorderLayout());
	    
	    add(gene_part);
	    gene_part.add(gene_part_left, BorderLayout.CENTER);
	    gene_part.add(gene_part_right, BorderLayout.EAST);
	    gene_part.setBorder(BorderFactory.createEmptyBorder(OFFSET,OFFSET,OFFSET,OFFSET)); // top, left, bottom, right
	    
//	    menu_genome = new GenomeMenu("Genome browser", "Gene ID");
//	    gene_part_left.add(menu_genome, BorderLayout.NORTH);
	    
	    genome_browser = new BrowserGenome("Genome browser", "Gene ID");
	    gene_part_left.add(genome_browser, BorderLayout.CENTER);
	    
	    gene_info = new InfoGenome("Gene Information", "Involved in reaction(s)");
	    gene_part_right.add(gene_info, BorderLayout.CENTER);
	    
	    
	    //==================== Pathway browser part (bottom half) ==================
	    path_part = new JPanel();
	    path_part.setLayout(new BorderLayout());
	    
	    path_part_left = new JPanel();
	    path_part_right = new JPanel();
	    path_part_right.setPreferredSize(new Dimension(300, 240));
	    path_part_left.setLayout(new BorderLayout());
	    path_part_right.setLayout(new BorderLayout());
	    
	    add(path_part);
	    path_part.add(path_part_left, BorderLayout.CENTER);
	    path_part.add(path_part_right, BorderLayout.EAST);
	    path_part.setBorder(BorderFactory.createEmptyBorder(0,OFFSET,OFFSET,OFFSET)); // top, left, bottom, right
	    
	    pathway_browser = new BrowserPathway("Pathway browser", "Map ID");
	    path_part_left.add(pathway_browser, BorderLayout.CENTER);
	    
//	    menu_pathway = new PathwayBrowser("Pathway browser", "Map ID");
//	    path_part_left.add(menu_pathway, BorderLayout.NORTH);
	    
	    react_info = new InfoPathway("Reaction Information", "Involves gene(s)");
	    path_part_right.add(react_info, BorderLayout.CENTER);
    }


    

}
