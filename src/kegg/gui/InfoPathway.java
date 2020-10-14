package kegg.gui;
/**
 * InfoPathway.java
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import kegg.recuperation.Fichiers;

/**
 * 
 * @author Antoine Cossa & Cheick Sylla
 *
 */
public class InfoPathway extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private final int OFFSET = 5;
	
//	 Initialiser les composants de la partie droite de l'application
	private JPanel info_panel;
	private Box box;
	
	private Etiquette info_label;
	private JButton button_image;
	private static JEditorPane info_content;
	
	private JLabel involved_label;
	private static JList<String> involved_list;
	private static DefaultListModel<String> liste_model;
	
	/**
	 * Identifiant de la reaction selectionnee via clic.
	 */
	private static String reaction;
	/**
	 * Identifiant du gene selectionne dans la JList.
	 */
	private static String selectedGene;
	
	public InfoPathway(String info_name, String involved_name) {
		setBorder(BorderFactory.createEmptyBorder(0,OFFSET,0,0)); // top, left, bottom, right
		setMinimumSize(new Dimension(300, 200));
		
		info_label = new Etiquette(info_name);
		involved_label = new JLabel(involved_name);
		involved_label.setAlignmentX(LEFT_ALIGNMENT);
		
		button_image = new JButton("Image");
		button_image.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
		button_image.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent event) {
	    		if (info_content.getPage() != null) {
		    		JFrame imagePopUp = new JFrame();
		    		imagePopUp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    		imagePopUp.setSize(800, 400);
		    		imagePopUp.setVisible(true);
		    		Container container = imagePopUp.getContentPane();
		    		JLabel image = new JLabel();
		    		
		    		File image_path;
		    		
		    		image_path = Fichiers.getReactionImage(reaction);
		    		imagePopUp.setTitle("Reaction " + reaction + " Image");
		    		ImageIcon image_icon = new ImageIcon(image_path.getPath());
					image.setIcon((Icon) image_icon);
					// Definition de la taille de la fenetre en fonction de la 
					// taille de l'image
					int img_width = image_icon.getIconWidth();
					int img_heigth = image_icon.getIconHeight();
					if (img_width <= 800 && img_heigth <= 400) {
						imagePopUp.setSize(img_width+15, img_heigth+40);
						container.add(image);
					} else if (img_width <= 800) {
						imagePopUp.setSize(img_width+50, 400);
						container.add(new JScrollPane(image));
					} else if (img_heigth <= 400) {
						imagePopUp.setSize(800, img_heigth+60);
						container.add(new JScrollPane(image));
					} else {
						container.add(new JScrollPane(image));
					}
	    		}
	    	}
		});
		
		info_content = new JEditorPane();
		info_content.setEditable(false);
		info_content.setMinimumSize(new Dimension(280, 100));
		info_content.setPreferredSize(new Dimension(280, 100));
		
		liste_model = new DefaultListModel<String>();
		
		involved_list = new JList<String>(liste_model);
		involved_list.setSize(280, 50);
		involved_list.setMinimumSize(new Dimension(280, 50));
		involved_list.setPreferredSize(new Dimension(280, 50));
		involved_list.setVisibleRowCount(3);
		
		involved_list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedGene = involved_list.getSelectedValue();
				if (selectedGene != BrowserGenome.getID()) {
					BrowserGenome.setSpecies(BrowserPathway.getSpecies());
					BrowserGenome.setID(selectedGene);
					BrowserGenome.clickSearch();
				}
				
			}
		});
		
		// Ajout des composants
		info_panel = new JPanel();
		info_panel.setLayout(new BorderLayout());
		info_panel.setBorder(BorderFactory.createEmptyBorder(0,0,OFFSET,0)); // top, left, bottom, right
		info_panel.add(info_label, BorderLayout.WEST);
		info_panel.add(button_image, BorderLayout.EAST);
		
		setLayout(new BorderLayout());
		add(info_panel, BorderLayout.NORTH);
		
		box = Box.createVerticalBox();
		box.add(new JScrollPane(info_content));
		box.add(involved_label);
		box.add(new JScrollPane(involved_list));
		
		add(box, BorderLayout.CENTER);
	}
	
	/**
	 * Definie le fichier txt a afficher dans info_content.
	 * @param info File : chemin du fichier .txt
	 */
	public static void setInfo(File info) {
		try {
			info_content.setPage(info.toURI().toURL());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Definie la liste des genes a afficher dans la JList involved_list.
	 * @param list DefaultListModel<String>
	 */
	public static void setList(DefaultListModel<String> list) {
		liste_model = list;
		involved_list.setModel(liste_model);
		// verifie que la liste n'est pas vide
		if (involved_list.getFirstVisibleIndex() != -1) {
			involved_list.setSelectedIndex(0);
		}
	}
	
	public static void setReaction(String react) {
		reaction = react;
	}
}
