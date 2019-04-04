import org.apache.commons.net.ftp.FTPFile;

/*-----FILE CONVERTER CLASS------
 * 
 * @param con
 * @param path
 * 
 * Uses connection class to copy FTPFile information
 * and return it as String [].
 */

public class FTPConverter {
	
	private FTPConnection conn = null;
	private String path = null;
	
  //Converter using path.
	public FTPConverter(FTPConnection conn, String path) {
		this.conn = conn;
		this.path = path;
	}
	
  //Converter not using path.
	public FTPConverter(FTPConnection conn) {
		this.conn = conn;
	}
	
  //Returns file names as String [].
	public String [] getFileNames() {
		
		FTPFile [] ftpFiles = null;
		
		if(path == null) {
			ftpFiles = conn.getFileNames();
		} else {
			ftpFiles = conn.getFileNames(path);
		}
		
		String [] fileNames = new String[ftpFiles.length];
		if(ftpFiles.length < 1) {
			return null;
		}
		else {
			for(int i = 0; i < ftpFiles.length; i++) {
				fileNames[i] = ftpFiles[i].getName();
				
			}
			return fileNames;
		}
	}
	
   //Returns directory names as String [].
	public String [] getDirNames() {
		FTPFile [] ftpDirs = null;
		
		if(path == null) {
			ftpDirs = conn.getDirNames();
		} else {
			ftpDirs = conn.getDirNames(path);
		}
		
		String [] fileDirs = new String[ftpDirs.length];
		if(ftpDirs.length < 1) {
			return null;
		}
		else {
			for(int i = 0; i < ftpDirs.length; i++) {
				fileDirs[i] = ftpDirs[i].getName();
				
			}
			return fileDirs;
		}
	}

}
