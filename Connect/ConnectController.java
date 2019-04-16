package Connect;


import org.apache.commons.net.ftp.FTPClient;

import FTPAccess.FTPConnection;
import Manager.ManagerFrame;

public class ConnectController {
	
	private ConnectFrame conFrame;
	private FTPConnection conn;
	
	public ConnectController(ConnectFrame conFrame) {
		this.conFrame = conFrame;
	}
	
	public void handler() {
		conFrame.btnCancel.addActionListener(a -> cancel());
		conFrame.btnConnect.addActionListener(a -> connect());
	}
	
	//btnCancel exits frame.
	private void cancel() {
		System.exit(0);
	}
	
	//btnConnect attempts to connect to ftp, given information.
	private void connect() {
		String hostname = conFrame.tfHostname.getText();
		String username = conFrame.tfUsername.getText();
		String password = String.valueOf(conFrame.pfPassword.getPassword());
		
		conn = new FTPConnection(hostname, username, password);
		FTPClient client = conn.getConnection();
		
		if(client != null) {
			//Succesful Connection.
			System.out.println("You are connected");
			
			ManagerFrame mfFrame = new ManagerFrame(conn, conFrame.width, conFrame.height, conFrame.title);
			mfFrame.initFrame();
			
			conFrame.setVisible(false);
			conFrame.dispose();
		} else {
			//Error when connecting.
			System.out.println("Could not connect");
			conFrame.tfHostname.setText("");
			conFrame.tfUsername.setText("");
			conFrame.pfPassword.setText("");
		}
	}

}
