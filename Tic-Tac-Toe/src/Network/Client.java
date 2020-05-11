package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	// -- Attributes -- //
	private String ipv4;
	private int port;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	// -- Constructor -- //
	public Client(String ipv4) throws UnknownHostException, IOException {
		this.ipv4 = ipv4;
		this.port = 10;
		this.socket = new Socket(InetAddress.getByName(this.ipv4), this.port);
		this.output = new ObjectOutputStream(this.socket.getOutputStream());
		this.input = new ObjectInputStream(this.socket.getInputStream());
	}
	
	// -- Methods -- //
	public int[] getFromServer() throws ClassNotFoundException, IOException {
		return (int[]) this.input.readObject();
	}
	
	public void sendToServer(int[] pos) throws IOException {
		this.output.writeObject(pos);
	}
}
