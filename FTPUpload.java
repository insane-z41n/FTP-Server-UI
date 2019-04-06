import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/*-----FILE UPLOAD CLASS------
 * 
 * @param con
 * @param path
 * 
 * Uses connection class to copy FTPFile information
 * and upload the file to desired folder.
 */
public class FTPUpload {
	
	private FTPConnection conn = null;
	private String uploadPath = null;
	
	public FTPUpload(FTPConnection conn, String uploadPath) {
		this.uploadPath = uploadPath;
		this.conn = conn;
	}
	
	public FTPUpload(FTPConnection conn) {
		this.conn = conn;
	}
	
	/* uploadFile Method, uploads clients file to ftp host.
	 * 
	 * @param localFilePath contains the location of files on clients computer.
	 * @param uploadFileName is the new file name that will appear on host ftp.
	 */
	public void uploadFile(File file) {
		FTPClient client = conn.getConnection();

		String fileType = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		System.out.println("The File Type is: " + fileType);
		
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file.getPath()))) {
			client.setFileType(FTP.BINARY_FILE_TYPE);
			System.out.println("File Path: " + file.getPath());
			boolean done = false;
			//upload file to ftp server with new file name.
			
			if(uploadPath != null) {
				//Upload to a specific location.
				client.sendSiteCommand("chmod" + "755" + uploadPath);
				client.changeWorkingDirectory(uploadPath);
			} else {
				client.sendSiteCommand("chmod" + "755" + client.printWorkingDirectory());
			}
			System.out.println("File Name: " + file.getName());
			done = client.storeFile(file.getName(), in);
			in.close();

			if(done) {
				System.out.println("\nFile upload was successful! >> " + client.getReplyString());
			}
			else {
				System.out.println("File upload was unsuccessful!");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			//Close Connection.
			conn.closeConnection(client);
		}
		
		
	}

	
	
}
