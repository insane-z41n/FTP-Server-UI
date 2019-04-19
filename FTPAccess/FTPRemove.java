package FTPAccess;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

public class FTPRemove {

	private FTPConnection conn;
	private String serverPath;
	
	public FTPRemove(FTPConnection conn, String serverPath) {
		this.conn = conn;
		this.serverPath = serverPath;
	}
	
	public boolean deleteFile(String fileName) {
		
		boolean done = false;
		
		FTPClient client = conn.getConnection();
		try {
			client.changeWorkingDirectory(serverPath);
			done = client.deleteFile(fileName);
			//Check if done.
			if(done) {
				System.out.println("\nFile delete was successful! >> " + client.getReplyString());
			}
			else {
				System.out.println("File delete was unsuccessful! >> " + client.getReplyString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			conn.closeConnection(client);
		}
		
		
		
		return done;
	}

}
