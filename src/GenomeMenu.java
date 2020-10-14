import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//package kegg.gui;

public class GenomeMenu extends JPanel /*, Zoomable*/ {
	private static final long serialVersionUID = 1L;
	
	private String species; // nom de l'espece entree dans le champs species
	private String id;      // nom de l'identifiant entree dans le champs id
	
	private final int OFFSET = 5;
	
	// Initialiser les composants du menu
	private Box menu_box;
	private Etiquette label_brow;
	private JLabel label_species;
	private JTextField text_species;
	private JLabel label_ID;
	private JTextField text_ID;
	protected JButton menu_bouton_search;
	
		 // Composants zoom
//	private JButton zoom;
//	private JButton unzoom;
	
	
	public GenomeMenu(String browser_name, String label_ID_text, GenomeBrowser browser) {
		
        menu_box = Box.createHorizontalBox(); // left menu
        
        label_brow = new Etiquette(browser_name);
        label_species = new JLabel("    Species  ");
        text_species = new JTextField(4);
        
        label_ID = new JLabel("     " + label_ID_text + "  "); 
        text_ID = new JTextField(5);
        
        menu_box.add(label_brow);
        menu_box.add(label_species);
        menu_box.add(text_species);
        menu_box.add(label_ID);
        menu_box.add(text_ID);
//        menu_box.add(unzoom);
//        menu_box.add(zoom);
        
        menu_bouton_search = new JButton("Search");

        menu_bouton_search.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent clic) {
	        	if (text_species.getText() != "" && text_ID.getText() != "") {
	        		species = text_species.getText();
	        		id = text_ID.getText();//.trim();
	        		System.out.println("Species: " + species + " ; ID: " + id); // TEST
	        		try {
						browser.afficherContenu(species, id);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	}
	    	}	
        }); 
        
        setLayout(new BorderLayout());
        add(menu_box, BorderLayout.WEST);
        add(menu_bouton_search, BorderLayout.EAST);
        setBorder(BorderFactory.createEmptyBorder(0,0,OFFSET,0)); // Marge du menu : top, left, bottom, right
        
    }
    
    public String getSpecies() {
    	return species;
    }
    
    public String getID() {
    	return id;
    }
    
    public void setSpecies(String species) {
    	 this.species = species;
    }
    
    public void setID(String id) {
    	 this.id = id;
    }

}
