package FTPAccess;

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
	
	//Returns file names as String [].
	//Returns null if no files found.
	public String [] getFileNames() {
		
		FTPFile [] ftpFiles = null;
		
		
		ftpFiles = conn.getFileNames(path);
		
		String [] fileNames = new String[ftpFiles.length];
		String [] dirNames = getDirNames();
		if(ftpFiles.length < 1) {
			return null;
		}
		else {
			int index = -1;
			for(int i = 0; i < ftpFiles.length; i++)
				fileNames[i] = ftpFiles[i].getName();
			if(dirNames != null) {
				for(int j = 0; j < fileNames.length; j++) {
					for(int k = 0; k < dirNames.length; k++) {
						if(dirNames[k].equals(fileNames[j])) {
							index = j;
							fileNames = removeElementArray(fileNames, index);
						}
					}
				}
			}
			
			return fileNames;
		}
		
	}
	
	//Returns directory names as String [].
	//Returns null if no directories found.
	public String [] getDirNames() {
		FTPFile [] ftpDirs = null;

		ftpDirs = conn.getDirNames(path);
		
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
	
	//Remove Array element at index and return new array.
	private String [] removeElementArray(String [] arr, int index) {
		if(arr == null || index < 0 || index >= arr.length) {
			return arr;
		}

		String [] newArr = new String[arr.length - 1];
		
		for(int i = 0, k = 0; i < arr.length; i++) {
			if(i == index) {
				continue;
			}
			
			newArr[k++] = arr[i];
		}
		
		return newArr;
		
	}

}
