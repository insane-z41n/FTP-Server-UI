import java.io.Console;
import java.util.Scanner;

public class FTPClient {
	
	public static void main (String [] args) {
		
		String hostname;
		String username;
		String password;
		
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
		else {
			System.out.prinltn("\nCould not Connect");
		}
		
	}

}
