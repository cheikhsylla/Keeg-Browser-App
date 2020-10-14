package kegg.gui;
/**
 * EnterKeyListenerP.java
 */
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * KeyListener qui permet de lancer la recherche a l'aide de la touche Entree 
 * pour le BrowserPathway.
 * @author Antoine Cossa & Cheick Sylla
 */
public class EnterKeyListenerP implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			BrowserPathway.clickSearch();
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void keyTyped(KeyEvent e) {}
    

}
