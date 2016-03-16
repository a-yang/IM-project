package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SocketThread extends Thread{
	Socket socket;
	Server server;
	int lastMessageSent = 0;

	  public SocketThread( Socket socket, Server server ) {
		    this.socket = socket;
		    this.server = server;
	  }
	  
	  public void run() {
		  try {
			  System.out.println("Starting socketthread");
				while (true)
				{
					BufferedReader in = new BufferedReader(
			                new InputStreamReader(socket.getInputStream()));
					if (in.ready()) {
						//sendMessage(in.readLine());
						server.messageHistoryQueue.add(in.readLine());
					}
					
					if (server.messageHistoryQueue.size() > lastMessageSent)
					{
						synchronized (server.messageHistoryQueue) {
							while (lastMessageSent < server.messageHistoryQueue.size())
							{
								String inputLine = server.messageHistoryQueue.get(lastMessageSent);
								new PrintWriter(socket.getOutputStream(), true).println(inputLine);
								lastMessageSent++;
							}
						}
					}
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}

	}
}
