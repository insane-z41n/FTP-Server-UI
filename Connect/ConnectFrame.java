package Connect;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Skins.SkinManager;

@SuppressWarnings("serial")
public class ConnectFrame extends JFrame {
	
	public int width, height;
	public String title;
	
	private SkinManager skin;
	private int labelWidth = 0;
	private int btnWidth = 0;

	public JTextField tfHostname, tfUsername;
	public JPasswordField pfPassword;
	public JButton btnCancel, btnConnect;
	
	public ConnectFrame(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
		
		skin = new SkinManager();
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	}
	
	public void initFrame() {
		
		getContentPane().setBackground(skin.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(8,8,8,8));
		int componentWidth = width - 16;
		//int componentHeight = height - 16;
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(skin.primary);
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		add(titlePanel, BorderLayout.NORTH);
		
		JLabel titleLabel = new JLabel("FTP Connection");
		titleLabel.setFont(skin.titleFont);
		titleLabel.setForeground(skin.secondary);
		titlePanel.add(titleLabel);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBackground(skin.primary);
		inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.PAGE_AXIS));
		//inputPanel.setPreferredSize(new Dimension(componentWidth, componentHeight));
		add(inputPanel, BorderLayout.CENTER);
		
		
		//---INPUT PANELS---
		JPanel hostnamePanel = new JPanel();
		JPanel usernamePanel = new JPanel();
		JPanel passwordPanel = new JPanel();
		
		createInputPanel(new JPanel [] {hostnamePanel, usernamePanel, passwordPanel});
	
		inputPanel.add(Box.createVerticalGlue());
		inputPanel.add(hostnamePanel);
		inputPanel.add(usernamePanel);
		inputPanel.add(passwordPanel);
		inputPanel.add(Box.createVerticalGlue());
		
		//--=INPUT LABELS---
		JLabel hostLabel = new JLabel("Hostname: ");
		JLabel userLabel = new JLabel("Username: ");
		JLabel passLabel = new JLabel("Password: ");
		
		createInputLabel(new JLabel [] {hostLabel, userLabel, passLabel});
		
		hostnamePanel.add(hostLabel);
		usernamePanel.add(userLabel);
		passwordPanel.add(passLabel);
		
		//---INPUT TEXTFIELDS---
		tfHostname = new JTextField();
		tfUsername = new JTextField();
		pfPassword = new JPasswordField();
		
		
		createInputTextField(new JTextField [] {tfHostname, tfUsername, pfPassword}, componentWidth - (labelWidth + 100));
		
		hostnamePanel.add(tfHostname);
		usernamePanel.add(tfUsername);
		passwordPanel.add(pfPassword);
		
		//---BUTTONS---
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(skin.primary);
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setBorder(new EmptyBorder(20,0,20,0));
		add(bottomPanel, BorderLayout.SOUTH);
		
		btnCancel = new JButton("Cancel");
		btnConnect = new JButton("Connect");
		
		createConnectButtons(new JButton [] {btnCancel, btnConnect});
		
		bottomPanel.add(Box.createHorizontalGlue());
		//bottomPanel.add(btnCancel);
		//bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(btnConnect);
		bottomPanel.add(Box.createHorizontalGlue());
		
		pack();
		setVisible(true);
		
		ConnectController cc = new ConnectController(this);
		cc.handler();
		
	}
	
	//Create the input panels within the parent panel, inputPanel
	//contains labels and textfields.
	private void createInputPanel(JPanel [] panel) {
		for(int i = 0; i < panel.length; i++) {
			panel[i].setBackground(skin.primary);
			panel[i].setLayout(new FlowLayout(FlowLayout.CENTER));
			panel[i].setBorder(null);
		}
	}
	
	//Create the labels that describe their coressponding textfield.
	private void createInputLabel(JLabel [] label) {
		int tempWidth = 0;
		for(int i = 0; i < label.length; i++) {
			label[i].setForeground(skin.secondary);
			label[i].setFont(skin.infoFont);
			int longest = label[i].getText().length();
			if(longest > tempWidth) {
				tempWidth = longest;
				labelWidth = tempWidth * label[i].getFont().getSize() - 40;
			}
			label[i].setHorizontalAlignment(SwingConstants.TRAILING);
		}
		
		for(int j = 0; j < label.length; j++) {
			label[j].setPreferredSize(new Dimension(labelWidth, label[j].getFont().getSize() + 5));
		}
	}
	
	//Create the textfields within the input panel.
	private void createInputTextField(JTextField [] tf, int width) {
		for(int i = 0; i < tf.length; i++) {
			tf[i].setFont(skin.plainInfoFont);
			tf[i].setBackground(skin.tert);
			tf[i].setForeground(skin.secondary);
			tf[i].setCaretColor(skin.primary);
			tf[i].setBorder(new EmptyBorder(0,5,0,0));
			tf[i].setPreferredSize(new Dimension(width, skin.infoFont.getSize() + 10));
		}
	}
	
	//Create Buttons in bottom panel.
	private void createConnectButtons(JButton [] btn) {
		int tempWidth = 0;
		for(int i = 0; i < btn.length; i++) {
			btn[i].setFont(skin.buttonFont);
			btn[i].setBackground(skin.primary);
			btn[i].setForeground(skin.secondary);
			btn[i].setBorder(new LineBorder(skin.secondary, 2));
			btn[i].setFocusPainted(false);
			int currentWidth = btn[i].getText().length();
			if(currentWidth > tempWidth) {
				tempWidth = currentWidth;
				btnWidth = tempWidth * btn[i].getFont().getSize();
			}
		}
		
		for(int j = 0; j < btn.length; j++) {
			btn[j].setPreferredSize(new Dimension(btnWidth, btn[j].getFont().getSize() + 10));
		}
	}
	
	
}
