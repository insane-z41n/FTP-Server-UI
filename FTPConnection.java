import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPConnection {
	
	private final String HOSTNAME;
	private final String UNAME;
	private final String PASSWORD;

	private FTPClient connClient;
	
	public FTPConnection(String hostname, String username, String password) {
		this.HOSTNAME = hostname;
		this.UNAME = username;
		this.PASSWORD = password;
		
		
	}
	
	//Creates a connection with and return FTPClient object.
	public FTPClient getConnection() {
		FTPClient client = new FTPClient();
		try {
			client.connect(HOSTNAME);
			client.enterLocalPassiveMode();
			client.login(UNAME, PASSWORD);
			
			return client;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	//Returns an array of FTPFile files list within path.
	public FTPFile [] getFileListNames(String path) {
		FTPClient connClient = getConnection();
		FTPFile [] files = null;
		try {
			files = connClient.listFiles(path);
			closeConnection(connClient);
			return files;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Returns an array of FTPFile directories list within path.
	public FTPFile [] getFileDirectoryNames(String path) {
		FTPClient connClient = getConnection();
		FTPFile [] files = null;
		try {
			files = connClient.listDirectories(path);
			closeConnection(connClient);
			return files;
		} catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Close the connection.
	public void closeConnection(FTPClient client) {
		try {
			client.logout();
			client.disconnect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
