package server;

import java.net.Socket;

import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;


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
				while (true)
				{/*
					InputStream input = (InputStream) socket.getInputStream();
						if (input.available() != 0) {
							System.out.println("reading photo");
						ByteArrayOutputStream in = new ByteArrayOutputStream();
						
						byte[] buffer = new byte[1024];
						int read = 0;
						while ((read = input.read(buffer, 0, buffer.length)) != -1) {
							in.write(buffer, 0, read);
						}
						byte[] imageBytes = in.toByteArray();
						System.out.println("read " + imageBytes.length + " bytes");
						
						InputStream in2 = new ByteArrayInputStream(imageBytes);
						BufferedImage bImageFromConvert = ImageIO.read(in2);
						
						ImageIO.write(bImageFromConvert, "jpg", new File(
								"/Users/angelayang316/Downloads/downloadedimg.jpg"));
						}*/
					BufferedReader in = new BufferedReader(
			                new InputStreamReader(socket.getInputStream()));
					if (in.ready()) {
						String input;
						/*Boolean firstLine = true;
						Boolean isImage = false;
						while (in.ready()) {
							if (!firstLine && !isImage) {
								input += "\n";
							}
							input += in.readLine();
							if (firstLine) {
								byte bytes[] = input.getBytes();
								System.out.println("first byte: " + (bytes[0] & 0xff));
								System.out.println("second byte: " + (bytes[1] & 0xff));
								if (bytes[0] == 239 && bytes[1] == 191) {
									System.out.println("caught a photo");
									isImage = true;
								}
							}
							firstLine = false;

						}*/
						while (in.ready()) {
							input = in.readLine();
						server.messageHistoryQueue.add(input);
						}
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
