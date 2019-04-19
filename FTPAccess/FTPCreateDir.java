package FTPAccess;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPCreateDir {
	
	private FTPConnection con;
	private String uploadPath = null;
	
	public FTPCreateDir(FTPConnection con, String uploadPath) {
		this.con = con;
		this.uploadPath = uploadPath;
	}
	public FTPCreateDir(FTPConnection con) {
		this.con = con;
	}
	
	//Creates Dir at located working path.
	public void createDir(String dirName) throws IOException {
		
		//Makes sure not to make a duplicate directory.
		//import dirNames from ftpConverter.
		FTPClient client = con.getConnection();
		boolean dirExists = true;
		dirExists = client.makeDirectory(dirName);
		if(uploadPath != null) {
			client.sendSiteCommand("chmod" + "755" + uploadPath);
			client.changeWorkingDirectory(uploadPath);
		} else {
			client.sendSiteCommand("chmod" + "755" + client.printWorkingDirectory());
		}
		if(!dirExists) {
			boolean created =  client.makeDirectory(dirName);
			if(created) {
				System.out.println("Created Directory: " + dirName);
				client.changeWorkingDirectory(dirName);
			} else {
				System.out.println("Error, could not create directory: " + dirName + " error = " + client.getReplyString());
			}
		}
	}

}
