package kegg.gui;
/**
 * InfoGenome.java
 */
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kegg.recuperation.Fichiers;

/**
 * Partie Gene Information du programme.
 * @author Antoine Cossa & Cheick Sylla
 *
 */
public class InfoGenome extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 5;
	
	// Initialiser les composants de la partie droite de l'application
		// les deux conteneurs principaux de Information
	private JPanel info_panel;
	private Box box;
	
	private Etiquette info_label;
	private static JButton button;
	private static JEditorPane info_content;
	
	private JLabel involved_label;
	private static JList<String> involved_reaction_list;
	private static DefaultListModel<String> liste_model;
	
	private Legend legend;
	
	private String selectedReaction;
	private String selectedItem;
	
	public InfoGenome(String info_name, String involved_name) {
		setBorder(BorderFactory.createEmptyBorder(0,OFFSET,0,0)); // top, left, bottom, right
		setMinimumSize(new Dimension(300, 200));
		
		info_label = new Etiquette(info_name);
		involved_label = new JLabel(involved_name);
		involved_label.setAlignmentX(LEFT_ALIGNMENT);
		
		button = new JButton("Legend");
		button.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
		button.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event) {
	    		legend = new Legend();
				legend.setVisible(true);
	    	}	
        }); 
		
		info_content = new JEditorPane();
		info_content.setEditable(false);
		info_content.setSize(new Dimension(280, 100));
		info_content.setMinimumSize(new Dimension(280, 100));
		info_content.setPreferredSize(new Dimension(280, 100));
		
		liste_model = new DefaultListModel<String>();
		
		involved_reaction_list = new JList<String>(liste_model);
		involved_reaction_list.setSize(280, 50);
		involved_reaction_list.setMinimumSize(new Dimension(280, 50));
		involved_reaction_list.setPreferredSize(new Dimension(280, 50));
		involved_reaction_list.setSelectedIndex(0);
		involved_reaction_list.setVisibleRowCount(3);
		
		involved_reaction_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (!involved_reaction_list.isSelectionEmpty()) {
					selectedItem = involved_reaction_list.getSelectedValue();
					System.out.println("selectedItem:"+selectedItem);
					Pattern pathway = Pattern.compile("(R\\d{5})\\s@\\s([a-z]{3,4})([0-9]{5})");
		    		Matcher match = pathway.matcher(selectedItem);
		    		if (match.find()) {
		    			selectedReaction = match.group(1);
		    			String species = match.group(2);
		    			String map_id = match.group(3);
//		    			System.out.println("selectedReaction :"+selectedReaction);	// TEST 
//		    			System.out.println("species :"+species);					// TEST
//		    			System.out.println("map_id :"+map_id);						// TEST
		    			BrowserPathway.setText(species, map_id);
		    			BrowserPathway.clickSearch();
		    			
		    			Fichiers.setRectangle(map_id, selectedReaction);
	    	    		InfoPathway.setReaction(selectedReaction);
	    	    		InfoPathway.setInfo(Fichiers.getReactionText(selectedReaction));
//	    	    		System.out.println("TEST");
	    	    		DefaultListModel<String> igl = Fichiers.involvesGenesList(species, map_id);
						InfoPathway.setList(igl);
//	    	    		System.out.println("TEST2");
		    		}
				}
			}
		});
		
		// Ajout des composants
		info_panel = new JPanel();
		info_panel.setLayout(new BorderLayout());
		info_panel.setBorder(BorderFactory.createEmptyBorder(0,0,OFFSET,0)); // top, left, bottom, right
		info_panel.add(info_label, BorderLayout.WEST);
		info_panel.add(button, BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		add(info_panel, BorderLayout.NORTH);
		
		box = Box.createVerticalBox();
		box.add(new JScrollPane(info_content));
		box.add(involved_label);
		box.add(new JScrollPane(involved_reaction_list));
		
		add(box, BorderLayout.CENTER);
	}
	
	/**
	 * Defini le fichier 
	 * @param info Fichier contenant les informations du gene actuellement 
	 * affiche dans le GenomeBrowser.
	 */
	public static void setInfo(File info) {
		try {
			info_content.setPage(info.toURI().toURL());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void setList(DefaultListModel<String> list) {
		liste_model = list;
		involved_reaction_list.setModel(liste_model);
	}
}
