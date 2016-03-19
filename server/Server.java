package server;

import client.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
	public ServerSocket serverSocket;
	public ArrayList<Socket> clientSockets;
	public final ArrayList<byte []> messageHistoryQueue = new ArrayList<byte []>();
	Socket newClient;
	
	public Server(final Integer portNumber) throws IOException
	{
		new Thread(new Runnable() {
			public void run() {
				try {
				serverSocket = new ServerSocket(portNumber);
				clientSockets = new ArrayList<Socket>();
				
				runChatRoom();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();

	}
	
	public void runChatRoom() throws IOException {		
		try {
			while (true)
			{
				Socket socket = serverSocket.accept();
				clientSockets.add(socket);
				new SocketThread(socket, this).start();
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public void close() {
		try {
		for (int i = 0; i < clientSockets.size(); i++) {
			BufferedReader in = new BufferedReader(
	                new InputStreamReader(clientSockets.get(i).getInputStream()));
			new PrintWriter(clientSockets.get(i).getOutputStream(), true).println("Host has ended this chatroom.");
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
