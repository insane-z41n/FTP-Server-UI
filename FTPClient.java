import java.util.Scanner;

public class FTPClient {
	
	public static void main (String [] args) {
		
		String hostname;
		String username;
		String password;
		String path = "";	//enter ftp folder path here.
		
		System.out.println("------Welcome to FTP Connection!------\n\n");
		Scanner input = new Scanner(System.in);
		System.out.print("FTP Hostname: ");
		hostname = input.nextLine();
		System.out.print("FTP Username: ");
		username = input.nextLine();
		System.out.print("FTP Password: ");
		password = input.nextLine();
	
		input.close();
		//Close user input.
		
		//Create create connection with ftp using FTPConnection class.
		FTPConnection conn = new FTPConnection(hostname, username, password);
		conn.getConnection();
		if(conn != null) {
			System.out.println("\nConnected");
		}
		
		//Get file and director names as String [] with FTPConverter class.
		FTPConverter convert = new FTPConverter(conn, path);
		System.out.println("\n<<DIRECTORY NAMES>>");
		String [] dirNames = convert.getDirNames();	//Return directory names.
		for(int i = 0; i < dirNames.length; i++) {
			System.out.println(dirNames[i]);
		}
		
		System.out.println("\n<<FILE NAMES>>");
		String [] fileNames = convert.getFileNames();	//Return file names.
		for(int i = 0; i < fileNames.length; i++) {
			System.out.println(fileNames[i]);
		}
	}

}
