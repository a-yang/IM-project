package server;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Client {
	public Socket clientSocket;
	String userName;
	int numPhotos = 1;
	
	public Client(String hostName, Integer portNumber, String userName)
	{
		try {
			this.userName = userName;
			clientSocket = new Socket(hostName, portNumber);
			
			new Thread(new Runnable() {
				public void run() {
					runChat();
				}
			}).start();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void runChat()
	{
		try (
				OutputStream out = (OutputStream) clientSocket.getOutputStream();
                InputStream in = (InputStream) clientSocket.getInputStream();
				) {
			String intro = userName + " joined the chat.";
			out.write(intro.getBytes());
			String userInput;
			while (true) {
				if (in.available() != 0) {
					byte[] bytes = new byte[10000000];
					int count = in.read(bytes);
					final byte data [] = Arrays.copyOfRange(bytes, 0, count);
				
				    //use magic numbers for jpg file to check if file is image or not
					if ((data[0] & 0xff) == 255 && (data[1] & 0xff) == 216) {
						new Thread(new Runnable() {
							public void run() {
								try {
									FileOutputStream fileOut= new FileOutputStream("image" + numPhotos + ".jpg");
	    	    					fileOut.write(data);
	    	    					System.out.println("saved image " + "image" + numPhotos + ".jpg");
	    	    					numPhotos++;
								} catch (FileNotFoundException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}).start();
		
					}
					else {
						String message = new String(data);
						System.out.println(message);
					}
    			}
                    
				if (System.in.available() != 0){
					ByteArrayOutputStream o = new ByteArrayOutputStream();
					while (System.in.available() != 0) {
						o.write(System.in.read());
					}
                	byte b[] = o.toByteArray();
                	String message = userName + ": ";
            		String byteArray = new String(b);
            		message += byteArray;
            		out.write(message.getBytes());
				}
			}
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
        } 
	}
	
	public void sendFile(String fileName) {
		try {
	        File f=new File(fileName); 
			if(f.exists()) { 
				BufferedInputStream d=new BufferedInputStream(new FileInputStream(fileName));
				OutputStream out = (OutputStream) clientSocket.getOutputStream();

				int length = (int) f.length();
			    byte[] file = new byte[length];
			    int offset = 0;
			    while (offset < length) {
			        int count = d.read(file, offset, (length - offset));
			        offset += length;
			    }
			    
			    //use magic numbers for jpg file to check if file is image or not
			    if ((file[0] & 0xff) != 255 && (file[1] & 0xff) != 216){
				    String message = userName + " sent " + fileName + "\n";
				    out.write(message.getBytes());
				    out.flush();
			    }
				out.write(file);
				out.flush();
	
			}
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
