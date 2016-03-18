package server;

import client.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
	public ServerSocket serverSocket;
	public ArrayList<Socket> clientSockets;
	public ArrayList<String> messageHistoryQueue;
	Socket newClient;
	
	public Server(Integer portNumber) throws IOException
	{
		serverSocket = new ServerSocket(portNumber);
		clientSockets = new ArrayList<Socket>();
		messageHistoryQueue = new ArrayList<String>();
		
		runChatRoom();
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
}
