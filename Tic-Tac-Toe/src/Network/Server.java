package Network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	// -- Attributes -- //
	private ServerSocket serverSocket;
	private Socket socket;
	private ClientHandler[] clients = new ClientHandler[2];
	private ObjectInputStream input;
	private ObjectOutputStream output;
	
	// -- Constructor -- //
	public Server() throws IOException {
		// Server is listening on port 10 //
		this.serverSocket = new ServerSocket(10);
		System.out.println("Waiting for connection...");
		
		// Run Loop //
		for (int i=0;i<2;i++) {
			this.socket = null;
			try {
				// Connect to Client //
				this.socket = this.serverSocket.accept();
				System.out.println("Connected to " + socket.getInetAddress().getHostName());
				
				// Get Input and Output Streams //
				System.out.println("Setting up streams...");
				this.input = new ObjectInputStream(this.socket.getInputStream());
				this.output = new ObjectOutputStream(this.socket.getOutputStream());
				this.output.flush();
				System.out.println("Set up complete.");
				
				// Create threads //
				this.clients[i] = new ClientHandler(this, this.socket, this.input, this.output, i);
				
				// Invoke start function //
				this.clients[i].start();
			} catch (Exception e) {
				this.socket.close();
			}
		}
	}
	
	// -- Methods -- //
	public void sendAll(int[] data) {
		for (ClientHandler client : this.clients) {
			try {
				client.send(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// -- Main -- //
	public static void main(String[] args) throws IOException {
		new Server();
	}
}

class ClientHandler extends Thread {
	// -- Attributes -- //
	private final Server server;
	private final ObjectInputStream input;
	private final ObjectOutputStream output;
	private int[] rec;
	private int[] send;
	private int player;
	
	// -- Constructor -- //
	public ClientHandler(Server server, Socket socket, ObjectInputStream input, ObjectOutputStream output, int player) {
		this.server = server;
		this.input = input;
		this.output = output;
		this.player = player;	// Identifies which player the thread belongs to
	}
	
	// -- Methods -- //
	public void send(int[] data) throws IOException {
		this.output.writeObject(data);
	}
	
	@Override
	public void run() {
		try {
			this.output.writeObject(this.player);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				this.rec = (int[]) this.input.readObject();
				this.send = this.rec;
				this.server.sendAll(this.send);
			} catch(IOException | ClassNotFoundException e) {
				;
			}
		}
	}
}