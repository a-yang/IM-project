package server;

import java.io.*;
import java.net.*;

public class Client {
	public Socket clientSocket;
	String userName;
	
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
    				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                    		new InputStreamReader(clientSocket.getInputStream()));

                ) {
    				out.println(userName + " joined the chat.");
                    String userInput;
                    while (true) {

                    	
                    	if (in.ready()) {
                    		
                    		String inputLine = in.readLine();
    						/*Boolean firstLine = true;
    						Boolean isImage = false;
    						while (in.ready()) {
    							if (!firstLine && !isImage) {
    								inputLine += "\n";
    							}
    							inputLine += in.readLine();
    							if (firstLine) {
    								byte bytes[] = inputLine.getBytes();
    								if (bytes[0] == 0xEF && bytes[1] == 0xBF) {
    									isImage = true;
    								}
    							}
    							firstLine = false;

    						}
    						byte bytes[] = inputLine.getBytes();
    						System.out.println("first 4 bytes");
    						for (int i = 0;i < 4; i++) {
    							System.out.println(bytes[i] & 0xff);
    						}*/
                            System.out.println(inputLine);
                    	}
                    	if (System.in.available() != 0){
                        	ByteArrayOutputStream o = new ByteArrayOutputStream();
                        	while (System.in.available() != 0) {
                        		o.write(System.in.read());
                        	}
                        	byte b[] = o.toByteArray();
                    		String byteArray = new String(b);
                        	/*if (out.checkError()){
                        		System.out.println("Host has left.");
                        		break;
                        	}*/
                    		out.println(userName + ": " + byteArray);

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
		            BufferedOutputStream outStream = new BufferedOutputStream(clientSocket.getOutputStream());
		            byte buffer[] = new byte[(int) f.length()];
		            int read;
		            String intro = userName + " sent " + fileName + ": \n";
		            outStream.write(intro.getBytes());
		            outStream.flush();
		            while((read = d.read(buffer))!=-1)
		            {
		                outStream.write(buffer, 0, read);
		                outStream.flush();
		            }
			}
	        
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
