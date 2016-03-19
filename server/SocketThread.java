package server;

import java.net.Socket;
import java.util.Arrays;
import java.io.*;
import java.lang.reflect.Array;

import javax.imageio.*;
import java.awt.image.BufferedImage;


public class SocketThread extends Thread{
	Socket socket;
	Server server;
	int lastMessageSent = 0;
	int lastPhotoSent = 0;

	public SocketThread( Socket socket, Server server ) {
		this.socket = socket;
		this.server = server;
	}

	public void run() {
		try {
			while (true) {
				InputStream in = socket.getInputStream();
				byte file [];
				if (in.available() != 0) {
					byte[] data = new byte[1000000];
					int count = in.read(data);
					file = Arrays.copyOfRange(data, 0, count);    					
					server.messageHistoryQueue.add(file);
				}

				if (server.messageHistoryQueue.size() > lastMessageSent)
				{
					synchronized (server.messageHistoryQueue) {
						while (lastMessageSent < server.messageHistoryQueue.size())
						{
							byte message[] = server.messageHistoryQueue.get(lastMessageSent);
							lastMessageSent++;
							
							OutputStream out = (OutputStream) socket.getOutputStream();
							System.out.println("writing a message");
							out.write(message);
							out.flush();
						} //end while
					} //end synchronized
				} //end if
			} //end eternal while
		  }
		  catch (IOException e)
		  {
			  e.printStackTrace();
		  } 
	  }	  
}
