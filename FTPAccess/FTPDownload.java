package FTPAccess;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

/*-----FILE DOWNLOAD CLASS------
 * 
 * @param conn
 * @param path
 * 
 * Uses connection class to save files from server
 * to desired path.
 */
public class FTPDownload {
	
	private FTPConnection conn = null;
	private String serverPath = null;
	public FTPDownload(FTPConnection conn, String serverPath) {
		this.conn = conn;
		this.serverPath = serverPath;
	}
	
	public boolean downloadFile(String filePath, String localPath) {
		boolean done = false;
		
		FTPClient client = conn.getConnection();
		
		
		client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
		
		try (FileOutputStream fos = new FileOutputStream(localPath)) {
			client.changeWorkingDirectory(serverPath);
			client.setFileType(FTP.BINARY_FILE_TYPE);
			done = client.retrieveFile(filePath, fos);
			fos.close();
			
			if(done) {
				System.out.println("File was downloaded succesfully! >> " + client.getReplyString());
			}
			else {
				System.out.println("File download was unsuccesful. >> " + client.getReplyString());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.closeConnection(client);
		}
		
		
		
		return done;
	}

}
