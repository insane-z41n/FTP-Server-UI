import java.io.File;

import javax.swing.JFileChooser;

import FTPAccess.FTPConnection;
import FTPAccess.FTPConverter;
import FTPAccess.FTPUpload;

public class ManagerController {
	
	private ManagerFrame mf;
	private FTPConnection conn;
	
	private String filePath;
	
	public ManagerController(ManagerFrame mf, FTPConnection conn) {
		this.mf = mf;
		this.conn = conn;
		filePath = null;
	}
	
	public void buttonHandler() {
		mf.btnBrowse.addActionListener(a -> browse());
		mf.btnUpload.addActionListener(a -> upload());
		mf.btnBack.addActionListener(a -> goToParentDir());
		mf.btnHome.addActionListener(a -> goToRootDir());
	}
	
	public void panelHandler() {
		//File Panel mouse listener.
		if(mf.filePanel != null) {
			for(int f = 0; f < mf.filePanel.length; f++) {
				mf.filePanel[f].addMouseListener(new ManagerMouseListener(this, mf.filePanel[f], mf.fileLabel[f], false));
			}
		}
		
		//Directory Panel mouse listener.
		if(mf.dirPanel != null) {
			for(int d = 0; d < mf.dirPanel.length; d++) {
				mf.dirPanel[d].addMouseListener(new ManagerMouseListener(this, mf.dirPanel[d], mf.dirLabel[d], true));
			}
		}
	}
	
	//btnBrowse.
	//Opens browser to allow user to chose a file or directory they would like to upload.
	private void browse() {
		System.out.println("Browse button pressed.\n");
		
		//Open jfilechooser and show user files.
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int status = fc.showSaveDialog(null);
		
		//Set file path to tfUpload.
		if(status == JFileChooser.APPROVE_OPTION) {
			filePath = fc.getSelectedFile().getPath();
			mf.tfUpload.setText(filePath);
		}
	}
	
	//btnUpload
	//Uploads file at current directory.
	private void upload() {
		System.out.println("Upload button pressed.\n");
		
		//Checks to see if tfUpload checkbox is empty
		if(mf.tfUpload.getText().trim().equals("")) {
			System.out.println("Nothing inside tfUpload");
		} else {
			boolean overwrite = false;
			//getsCurrent serverPath.
			String path = mf.getCurrentPath();
			FTPUpload upload = new FTPUpload(conn, path);
			FTPConverter convert = new FTPConverter(conn, path);
			
			String [] fileName = convert.getFileNames();
			String [] dirName = convert.getDirNames();
			File userFile = new File(filePath);
			
			for(int i = 0; i < fileName.length; i++) {
				if(userFile.getName().equals(fileName[i])) {
					overwrite = true;
				}
			}

			//Check if file exists.
			if(userFile.exists()) {
				
				if(overwrite) {
					System.out.println("You will be overwriting a file are you sure you want to do this.");
					//if yes then - mf.setList(mf.filesPanel, mf.getServerPath());
					//else no then - change file name when saving.
				} else if(upload.uploadFile(userFile)) { //upload file.
					System.out.println("Your file has been uploaded.");
					mf.tfUpload.setText("");
					
				} else if(dirName == null) {
					//Show error message in upload.
					System.out.println("You cannot upload here");
				} else {
					System.out.println("Try to upload in one of the next folders.");
					//Search tree for finding a place to upload folder.
				}
				
			} else {
				
				System.out.println("Could not find file");
			}
		}
		
		//If user has selected a directory go through directory and save all files and directory.
		//else just upload file at current directory.
	}
	
	//Return to parent directory.
	private void goToParentDir() {
		String thePath = mf.getCurrentPath();
		
		System.out.println("Current Path: " + thePath);
		String [] allPaths = thePath.split("/");
		String newPath = "";
		for(int i = 0; i < allPaths.length - 1; i++) {
			newPath +=  allPaths[i] + "/";
		}
		if(newPath.equals("") || newPath.equals("/")) {
			newPath = "";
		}
		
		System.out.println("Go to : " + newPath);
		mf.setCurrentPath(newPath);
		resetListPanel();
		mf.setList(mf.listPanel, mf.getCurrentPath());
		panelHandler();
	}
	
	//Go to home directory.
	private void goToRootDir() {
		mf.setCurrentPath(mf.getHomePath());
		resetListPanel();
		mf.setList(mf.listPanel, mf.getCurrentPath());
		panelHandler();
	}
	
	//Reset list panel.
	public void resetListPanel() {
		mf.listPanel.removeAll();
		mf.listPanel.revalidate();
		mf.listPanel.repaint();
	}
	
	
	public ManagerFrame getManagerFrame() {
		return mf;
	}
	
	public FTPConnection getConnection() {
		return conn;
	}


}
