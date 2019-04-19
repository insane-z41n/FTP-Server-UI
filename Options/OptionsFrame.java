package Options;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Manager.ManagerController;
import Skins.SkinManager;

@SuppressWarnings("serial")
public class OptionsFrame extends JFrame{

	private ManagerController mc;
	public int xPos, yPos;
	public String [] options;
	public JLabel label;
	
	private SkinManager skin;

	public JButton [] optButton;
	private boolean dir;
	
	public OptionsFrame(ManagerController mc, JLabel label, int xPos, int yPos, String [] options, boolean dir) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.options = options;
		this.dir = dir;
		this.mc = mc;
		this.label = label;
		
		skin = new SkinManager();
		skin.setPrimary(Color.decode("#d1f0ff"));

		optButton = new JButton[options.length];
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(options.length,0));
		setLocation(xPos, yPos);
		setUndecorated(true);
		setResizable(false);
	}
	
	public void initFrame() {
		
		System.out.println("Initialize Options Frame.");
		
		for(int i = 0; i < options.length; i++) {
			
			optButton[i] = new JButton(options[i]);
			optButton[i].setBackground(skin.primary);
			optButton[i].setForeground(skin.secondary);
			optButton[i].setFont(skin.plainInfoFont);
			optButton[i].setHorizontalAlignment(SwingConstants.LEFT);
			optButton[i].setBorder(new EmptyBorder(5,3,5, getArrWidth(options)));
			optButton[i].setFocusPainted(false);
			add(optButton[i]);
		}
		
		OptionsController oc = new OptionsController(this, dir);
		oc.buttonHandler();
		
		pack();
		setVisible(true);
		
		System.out.println("The option frame width: " + this.getWidth());
		System.out.println("The option frame height: " + this.getHeight());
	}
	
	private int getArrWidth(String [] arr) {
		int width = 0;
		for(int i = 0; i < arr.length; i++) {
			int len = arr[i].length();
			if(len > width) {
				width = len;
			}
		}
		return width * skin.plainInfoFont.getSize()/2;
	}
	public void close() {
		setVisible(false);
		dispose();
	}
	
	public ManagerController getManagerController() {
		return mc;
	}

}
