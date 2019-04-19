package Options;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import FTPAccess.FTPDownload;
import FTPAccess.FTPRemove;
import Manager.ManagerController;
import Manager.ManagerFrame;

public class OptionsController {
	
	private OptionsFrame of;
	private ManagerController mc;
	private ManagerFrame mf;
	private boolean dir;
	
	public OptionsController(OptionsFrame of, boolean dir) {
		this.of = of;
		this.dir = dir;
		mc = of.getManagerController();
		mf = mc.getManagerFrame();
		
	}
	
	public void buttonHandler() {
		
		for(int i = 0; i < of.optButton.length; i++) {
			of.optButton[i].addMouseListener(new OptionsMouseListener(of.optButton[i]));
			
			if(dir) {
				dirOptionHandler(of.optButton[i]);
			} else {
				fileOptionHandler(of.optButton[i]);
			}
		}
		
	}
	
	//----DIRECTORY ACTIONS----
	private void open() {
		System.out.println("Open Directory.");
		of.close();
		mc.resetListPanel();
		mf.setList(mf.listPanel, mf.getCurrentPath() + "/" + of.label.getText());
		mc.panelHandler();
		
	}
	private void moveDir() {
		System.out.println("Move this Directory and contentes.");
	}
	private void showDirProp() {
		System.out.println("Show Directory Properties.");
	}
	
	//----FILE ACTIONS----
	private void download() {
		System.out.println("Download File.");
		of.close();
		
		String path = mf.getCurrentPath();
		FTPDownload load = new FTPDownload(mc.getConnection(), path);
		
		
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int status = fc.showSaveDialog(null);
		
		String filePath = null;
		if(status == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile().getPath();
			load.downloadFile(of.label.getText(), filePath + "/" + of.label.getText());
			
		}
		
	}
	private void remove() {
		System.out.println("Remove File.");
		of.close();
		
		String path = mf.getCurrentPath();
		FTPRemove remove = new FTPRemove(mc.getConnection(), path);
		remove.deleteFile(of.label.getText());
	}
	private void moveFile() {
		System.out.println("Move This file.");
	}
	private void showFileProp() {
		System.out.println("Show file Properties.");
	}
	
	//----OPTION BUTTONS----
	
	//Dir Options Handler.
	//Dir Buttons use the following text below.
	//"Open", "Move To", "Properties"
	private void dirOptionHandler(JButton btn) {
		String option = btn.getText();

		if(option.equals("Open")) {
			btn.addActionListener(a -> open());
		} 
		else if(option.equals("Move To")) {
			btn.addActionListener(a -> moveDir());
		}
		else if(option.equals("Properties")) {
			btn.addActionListener(a -> showDirProp());
		}
		else {
			System.out.println("This button does nothing.");
		}
	}
	//File Options Handler.
	//File Buttons user the following text below.
	//"Download", "Remove", "Move To", "Properties"
	private void fileOptionHandler(JButton btn) {
		String option = btn.getText();
		
		if(option.equals("Download")) {
			btn.addActionListener(a -> download());
		} 
		else if(option.equals("Remove")) {
			btn.addActionListener(a -> remove());
		} 
		else if(option.equals("Move To")) {
			btn.addActionListener(a -> moveFile());
		} 
		else if(option.equals("Properties")) {
			btn.addActionListener(a -> showFileProp());
		} 
		else {
			System.out.println("This button does nothing.");
		}
	}
	
}
