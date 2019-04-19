package Manager;

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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.apache.commons.net.ftp.FTPFile;

import FTPAccess.FTPConnection;
import FTPAccess.FTPConverter;
import Skins.SkinManager;

@SuppressWarnings("serial")
public class ManagerFrame extends JFrame{

	//Constructor Variables.
	public FTPConnection conn;
	public int width, height;
	public String title;
	
	private int mfWidth, mfHeight;				//Frame width, height.
	private SkinManager skin;					//Skin manager.

	//Components
	public JPanel listPanel;					//listPanel contains list of directories and files.
	public JButton btnBack, btnHome, btnBrowse, btnUpload;		
	public JTextField tfUpload;
	public JPanel [] dirPanel, filePanel;		//directory items will be placed in here.
	public JLabel [] dirLabel, fileLabel, fileSize;
	
	//Buttons width.
	private int btnWidth = 0;
	
	private final String homePath;
	private String currentPath;
	
	public ManagerFrame(FTPConnection conn, int width, int height, String title) {
		this.conn = conn;
		this.width = width;
		this.height = height;
		this.title = title;
		
		mfWidth = (int) (width * 1.5);
		mfHeight = (int) (height * 1.5);
		
		//Contains the skin for this frame.
		skin = new SkinManager();
		
		homePath = "";
		
		if(currentPath == null) {
			currentPath = homePath;
		}
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(mfWidth, mfHeight);
		setLayout(new BorderLayout());
		
	}
	
	public void initFrame() {
		getContentPane().setBackground(skin.primary);
		((JComponent) getContentPane()).setBorder(new EmptyBorder(8,8,8,8));
	
		//Top Title Panel, containing label.
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(skin.primary);
		titlePanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		add(titlePanel, BorderLayout.NORTH);
		
		JLabel titleLabel = new JLabel("File Manager");			//Title.
		titleLabel.setFont(skin.titleFont);
		titleLabel.setForeground(skin.secondary);
		titlePanel.add(titleLabel);
		
		//Contains the Scroll Pane.
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(skin.primary);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
		add(centerPanel, BorderLayout.CENTER);
		
		btnBack = new JButton("<");
		btnHome = new JButton("|^|");
		
		createNavButtons(new JButton [] {btnBack, btnHome});
		
		int navPanelHeight = btnBack.getFont().getSize() + 30;
		JPanel navPanel = new JPanel();
		navPanel.setBackground(skin.primary);
		navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.LINE_AXIS));
		navPanel.setPreferredSize(new Dimension(mfWidth, navPanelHeight));
		centerPanel.add(navPanel);
		
		JPanel navButtonPanel = new JPanel();
		navButtonPanel.setBackground(skin.primary);
		navButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		navButtonPanel.setAlignmentY(CENTER_ALIGNMENT);
		navButtonPanel.setBorder(new EmptyBorder(5,0,5,0));
		navPanel.add(navButtonPanel);
		
		navButtonPanel.add(btnBack);
		navButtonPanel.add(btnHome);
		
		//Header panel includes information of files list.
		JPanel headerPanel = new JPanel();
		headerPanel.setBackground(skin.primary);
		headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.LINE_AXIS));
		headerPanel.setBorder(new EmptyBorder(0,5,0,5));
		
		JLabel fileNameLabel = new JLabel("File Name");
		JLabel fileSizeLabel = new JLabel("Size");
		
		createHeaderLabels(new JLabel [] {fileNameLabel, fileSizeLabel});
		
		headerPanel.add(fileNameLabel);
		headerPanel.add(Box.createHorizontalGlue());
		headerPanel.add(fileSizeLabel);
		
		listPanel = new JPanel();
		listPanel.setBackground(skin.primary);
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		
		setList(listPanel, currentPath);
		
		centerPanel.add(Box.createHorizontalStrut(20));
		//Scroll Pane contains the file and directory.
		//**CHANGE THE HEIGHT BASED ON BOTTOM PANEL HEIGHT.
		JScrollPane fileScroller = new JScrollPane(listPanel);
		fileScroller.setColumnHeaderView(headerPanel);
		fileScroller.setBackground(skin.primary);
		fileScroller.setBorder(new LineBorder(skin.secondary, 2));
		fileScroller.setPreferredSize(new Dimension(mfWidth, mfHeight-navPanelHeight));
		fileScroller.getVerticalScrollBar().setBackground(skin.primary);
		fileScroller.getVerticalScrollBar().setForeground(skin.secondary);
		centerPanel.add(fileScroller);
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(skin.primary);
		//bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.LINE_AXIS));
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.setBorder(new EmptyBorder(8,0,8,8));
		bottomPanel.setPreferredSize(new Dimension(mfWidth, mfHeight/10));
		add(bottomPanel, BorderLayout.SOUTH);
		
		btnBrowse = new JButton ("Browse");
		btnUpload = new JButton("Upload File");
		tfUpload = new JTextField();
		
		createManagerButtons(new JButton [] {btnUpload, btnBrowse});
	
		//TextField Upload.
		tfUpload.setBackground(skin.tert);
		tfUpload.setBorder(new LineBorder(skin.secondary, 2));
		tfUpload.setFont(skin.plainInfoFont);
		tfUpload.setEditable(false);
		tfUpload.setPreferredSize(new Dimension(mfWidth - btnWidth*3, tfUpload.getFont().getSize() + 10));
		
		bottomPanel.add(btnBrowse);
		bottomPanel.add(tfUpload);
		bottomPanel.add(Box.createHorizontalStrut(btnWidth/2));
		bottomPanel.add(btnUpload);
		
		ManagerController mc = new ManagerController(this, conn);
		mc.buttonHandler();
		mc.panelHandler();
	
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
		
		
	}
	
	public void setList(JPanel parent, String currentPath) {
		
		setCurrentPath(currentPath);
		
		FTPConverter convert = new FTPConverter(conn, currentPath);
		
		FTPFile [] fileSize = conn.getFileNames(currentPath);
		
		String [] dirName = convert.getDirNames();
		String [] fileName = convert.getFileNames();
		
		if(dirName != null && dirName.length > 0) {
			System.out.println("Set Directorys.");
			setDirs(dirName, parent);
		}
		if(fileName != null && fileName.length > 0) {
			System.out.println("Set Files.");
			setFiles(fileName, fileSize, parent);
		}
	}
	
	private void setDirs(String [] dirs, JPanel parent) {
		
		dirPanel = new JPanel [dirs.length];
		dirLabel = new JLabel [dirs.length];
		
		for(int i = 0; i < dirs.length; i++) {
			dirPanel[i] = new JPanel();
			dirPanel[i].setBackground(skin.primary);
			dirPanel[i].setLayout(new BoxLayout(dirPanel[i], BoxLayout.LINE_AXIS));
			dirPanel[i].setBorder(new EmptyBorder(0,5,0,5));
			parent.add(dirPanel[i]);
			
			dirLabel[i] = new JLabel(dirs[i]);
			dirLabel[i].setFont(skin.infoFont);
			dirLabel[i].setForeground(skin.secondary);
			dirPanel[i].add(dirLabel[i]);
			
			dirPanel[i].add(Box.createHorizontalGlue());
			
			parent.add(Box.createHorizontalGlue());
		}
	}
	
	private void setFiles(String [] files, FTPFile [] size, JPanel parent) {
		filePanel = new JPanel[files.length];
		fileLabel = new JLabel[files.length];
		fileSize = new JLabel[files.length];
		
		for(int i = 0; i < files.length; i++) {
			filePanel[i] = new JPanel();
			filePanel[i].setBackground(skin.primary);
			filePanel[i].setLayout(new BoxLayout(filePanel[i], BoxLayout.LINE_AXIS));
			filePanel[i].setBorder(new EmptyBorder(0,5,0,5));
			parent.add(filePanel[i]);
			
			
			fileLabel[i] = new JLabel(files[i]);
			fileLabel[i].setFont(skin.plainInfoFont);
			fileLabel[i].setForeground(skin.secondary);
			filePanel[i].add(fileLabel[i]);
			
			filePanel[i].add(Box.createHorizontalGlue());
			//parent.add(Box.createHorizontalGlue());
			
			if(files[i].equals(size[i].getName())) {
				String bytes = String.valueOf(size[i].getSize());
				fileSize[i] = new JLabel(bytes);
				fileSize[i].setFont(skin.plainInfoFont);
				fileSize[i].setForeground(skin.tert);
				filePanel[i].add(fileSize[i]);
				
				System.out.println("file size: " + bytes);
			}
			
			
		}
		
	}
	
	private void createNavButtons(JButton [] btn) {
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
				btnWidth = tempWidth * (btn[i].getFont().getSize()/2);
			}
		}
			
		for(int j = 0; j < btn.length; j++) {
			btn[j].setPreferredSize(new Dimension(btnWidth, btn[j].getFont().getSize() + 10));
		}
	}
	
	//Create Buttons in bottom panel.
	private void createManagerButtons(JButton [] btn) {
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
				btnWidth = tempWidth * (btn[i].getFont().getSize()/2);
			}
		}
			
		for(int j = 0; j < btn.length; j++) {
			btn[j].setPreferredSize(new Dimension(btnWidth, btn[j].getFont().getSize() + 10));
		}
	}
	
	//Header Labels.
	private void createHeaderLabels(JLabel [] label) {
		//int last = label.length -1;
		for(int i = 0; i < label.length; i++) {
			label[i].setFont(skin.infoFont);
			label[i].setForeground(skin.secondary);
//			if(i != last) {
//				label[i].setBorder(new MatteBorder(0,0,0,1, skin.secondary));
//			}
		}
	}
	public String getHomePath() {
		return homePath;
	}
	
	public void setCurrentPath(String currentPath) {
		this.currentPath = currentPath;
	}
	public String getCurrentPath() {
		return currentPath;
	}
}
