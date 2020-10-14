package kegg.gui;
/**
 * EnterKeyListenerG.java
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyListener qui permet de lancer la recherche a l'aide de la touche Entree
 * pour le BrowserGenome.
 * @author Antoine Cossa & Cheick Sylla
 */
public class EnterKeyListenerG implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			BrowserGenome.clickSearch();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
    

}
