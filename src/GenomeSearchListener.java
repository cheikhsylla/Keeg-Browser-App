import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class GenomeSearchListener implements ActionListener {
	
	private GenomeMenu menu_gene;
	
	
	GenomeSearchListener(GenomeMenu menu_gene) {
		this.menu_gene = menu_gene;
	}
	
	@Override
	public void actionPerformed(ActionEvent clic) {
    	if (text_species.getText() != "" && text_ID.getText() != "") {
    		species = text_species.getText();
    		id = text_ID.getText().trim();
    		System.out.println("Species: " + species + " ; ID: " + id); // TEST
    	}
	}
	
}
