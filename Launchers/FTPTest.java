package Launchers;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import FTPAccess.FTPConnection;
import FTPAccess.FTPConverter;
import FTPAccess.FTPCreateDir;
import FTPAccess.FTPDownload;
import FTPAccess.FTPRemove;
import FTPAccess.FTPUpload;

public class FTPTest {

	public static void main (String [] args) {
		
		String hostname;
		String username;
		String password;
		String path = "";
		
		System.out.println("------Welcome to FTP Connection!------\n\n");
		Scanner input = new Scanner(System.in);
		System.out.print("FTP Hostname: ");
		hostname = input.nextLine();
		System.out.print("FTP Username: ");
		username = input.nextLine();
		System.out.print("FTP Password: ");
		password = input.nextLine();
		
		input.close();
		FTPConnection conn = new FTPConnection(hostname, username, password);
		conn.getConnection();
		if(conn != null) {
			System.out.println("\nConnected");
		}
		
		//DISPLAYS ON CONSOLE THE DIRECTORY NAMES AND FILE NAMES WITHIN GIVEN PATH.
		FTPConverter convert = new FTPConverter(conn, path);
		showDirNames(convert);
		showFileNames(convert);
		
		//UPLOAD FILE.
		FTPUpload upload = new FTPUpload(conn, path);
		File file = new File("");
		if(file.exists()) {
			System.out.println("\nFile Exists");
		}
		upload.uploadFile(file);
		
		//UPLOAD DIRECTORY.
		String dirName = "New Folder";
		FTPCreateDir ftpCreate = new FTPCreateDir(conn, path);
		try {
			ftpCreate.createDir(dirName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//DOWNLOAD FILE.
		FTPDownload load = new FTPDownload(conn, path);
		load.downloadFile("Hello World.txt", "C:/Users/Desktop");
		
		//DELETE FILE.
		FTPRemove remove = new FTPRemove(conn, path);
		remove.deleteFile("Hello World.txt");
		
		System.exit(0);
	}
	
	
	
	//Display Directory Names. 
	private static void showDirNames(FTPConverter convert) {
		System.out.println("\n<<DIRECTORY NAMES>>");
		
		String [] dirNames = convert.getDirNames();
		if(dirNames == null) {
			System.out.println("No Directories Found!");
		}
		else {
			for(int i = 0; i < dirNames.length; i++) {
				System.out.println(dirNames[i]);
			}
		}
	}
	
	//Display File Names.
	private static void showFileNames(FTPConverter convert) {
		System.out.println("\n<<FILE NAMES>>");
		String [] fileNames = convert.getFileNames();
		if(fileNames == null) {
			System.out.println("No Files Found!");
		}
		else {
			for(int i = 0; i < fileNames.length; i++) {
				System.out.println(fileNames[i]);
			}
		}
		
	}

}
