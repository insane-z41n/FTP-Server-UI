package Options;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

import Skins.SkinManager;

public class OptionsMouseListener extends MouseAdapter implements MouseListener {

	private JButton btn;
	private SkinManager skin;
	
	public OptionsMouseListener(JButton btn) {
		this.btn = btn;
		skin = new SkinManager();
	}
	
	public void mouseEntered(MouseEvent me) {
		btn.setBackground(skin.back);
	}
	
	public void mouseExited(MouseEvent me) {
		btn.setBackground(skin.optPrimary);
	}
}
