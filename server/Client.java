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
			runChat();
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
                    String userInput;
                    while (true) {
                    	if (in.ready()) {
                            System.out.println(in.readLine());
                    	}
                    	if (System.in.available() != 0){
                        	ByteArrayOutputStream o = new ByteArrayOutputStream();
                        	while (System.in.available() != 0) {
                        		o.write(System.in.read());
                        	}
                        	byte b[] = o.toByteArray();
                    		String byteArray = new String(b);
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
	
}
