package Manager;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Skins.SkinManager;
import fz.frames.options_frame.OptionsFrame;

/**
 * @author zainm
 *
 */
public class ManagerMouseListener extends MouseAdapter implements MouseListener {
	
	private ManagerController mc;
	private ManagerFrame mf;
	private JPanel panel;
	private JLabel label;
	private SkinManager skin;
	private boolean dir;
	
	public ManagerMouseListener(ManagerController mc, JPanel panel, JLabel label, boolean dir) {
		this.mc = mc;
		this.panel = panel;
		this.label = label;
		this.dir = dir;
		skin = new SkinManager();
		mf = mc.getManagerFrame();
	}

	public void mouseClicked(MouseEvent me) {
		
		String [] dirOptions = {"Open", "Move To", "Properties"};
		String [] fileOptions = {"Download", "Remove", "Move To", "Properties"};

		Point p = MouseInfo.getPointerInfo().getLocation();
		int xPos = (int) p.getX();
		int yPos = (int) p.getY();
		
		OptionsFrame of;
		if(dir) {
			of = new OptionsFrame(mc, label, xPos, yPos, dirOptions, dir);
		} else {
			of = new OptionsFrame(mc, label, xPos, yPos, fileOptions, dir);
		}

		
		if(me.getButton() == MouseEvent.BUTTON1) {
			//System.out.println("Mouse Left Click.\n");
			of.close();
			if(dir) {
				mc.resetListPanel();
				mf.setList(mf.listPanel, mf.getCurrentPath() + "/" + label.getText());
				mc.panelHandler();
			} else {
				System.out.println("This is a file not a directory");
			}
		}
		if(me.getButton() == MouseEvent.BUTTON2) {
			//System.out.println("Mouse Middle Click.\n");
		}
		if(me.getButton() == MouseEvent.BUTTON3) {
			System.out.println("Mouse Right Click.\n");
			of.initFrame();
		}
	}

	public void mouseEntered(MouseEvent me) {
		//System.out.println("Mouse Entered.\n");
		panel.setBackground(skin.back);
	}

	public void mouseExited(MouseEvent me) {
		//System.out.println("Mouse Exited.\n");
		panel.setBackground(skin.primary);
	}

	
	//Return the longest width within opt arr
	private int getArrWidth(String [] arr) {
		int width = 0;
		String longest = "";
		for(int i = 0; i < arr.length; i++) {
			int length = arr[i].length();
			if(length > longest.length()) {
				longest = arr[i];
			}
		}
		
		return width;
	}
	
	private int getArrHeight(String [] arr) {
		return 0;
	}


}
