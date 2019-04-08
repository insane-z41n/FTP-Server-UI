package Connect;

import org.apache.commons.net.ftp.FTPClient;

import FTPAccess.FTPConnection;

public class ConnectActions {
	
	ConnectFrame conFrame;
	
	public ConnectActions(ConnectFrame conFrame) {
		this.conFrame = conFrame;
	}
	
	public void actions() {
		conFrame.btnCancel.addActionListener(a -> cancel());
		conFrame.btnConnect.addActionListener(a -> connect());
	}
	
	//btnCancel exits frame.
	private void cancel() {
		System.exit(0);
	}
	
	//btnConnect attempts to connect ftp given information.
	private void connect() {
		String hostname = conFrame.tfHostname.getText();
		String username = conFrame.tfUsername.getText();
		char [] pass = conFrame.pfPassword.getPassword();
		String password = pass.toString();
		
		FTPConnection conn = new FTPConnection(hostname, username, password);
		FTPClient client = conn.getConnection();
		
		
		if(client != null) {
			//Succesful Connection.
			System.out.println("You are connected");
		} else {
			//Error when connecting.
			System.out.println("Could not connect");
			conFrame.tfHostname.setText("");
			conFrame.tfUsername.setText("");
			conFrame.pfPassword.setText("");
		}
	}

}
