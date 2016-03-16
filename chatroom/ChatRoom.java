package chatroom;

import client.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ChatRoom {
	public ServerSocket serverSocket;
	public ArrayList<Client> clientSockets;
	Client newClient;
	
	public ChatRoom(int portNumber) throws IOException
	{
		serverSocket = new ServerSocket(portNumber);
		clientSockets = new ArrayList<Client>();
	}
	
	public void addClient() throws IOException {
		
		newClient = new Client(serverSocket.accept());
		clientSockets.add(newClient);
	}
	
	public void receiveMessage(Client c) throws IOException {
		//System.out.println("server.receiveMessage");
		BufferedReader in = new BufferedReader(
	                new InputStreamReader(c.clientSocket.getInputStream()));
		if (in.ready()) {
			sendMessage(in.readLine());
		}
	}
	
	public void sendMessage(String inputLine) throws IOException {
                 
		//System.out.println("server.sendMessage");

		for (int i = 0; i < clientSockets.size(); i++)
		{
			new PrintWriter(clientSockets.get(i).clientSocket.getOutputStream(), true).println(inputLine);
		}
		System.out.println("Client: " + inputLine);

	}
	
}
