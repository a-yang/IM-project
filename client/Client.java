package client;

import java.io.*;
import java.net.*;

public class Client {
	public Socket clientSocket;
	String userName;
	
	public Client(Socket connection)
	{
		userName = "<null>";
		clientSocket = connection;
	}
	
	public Client(String hostName, Integer portNumber, String userName)
	{
		try {
			System.out.println("Setting up client");
			this.userName = userName;
			clientSocket = new Socket(hostName, portNumber);
			System.out.println("running chat now");
			runChat();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void runChat()
	{
		System.out.println("runChat start");
        new Thread(new Runnable() {
        	public void run() {
        		try (
        				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(
                        		new InputStreamReader(clientSocket.getInputStream()));
                        BufferedReader stdIn = new BufferedReader(
                                new InputStreamReader(System.in))
                    ) {
        			System.out.println("in runChat");
        			Boolean first = true;
                    String userInput;
                    while (true) {
                    	try {
                    	while (!stdIn.ready() || !in.ready()) {
                    		Thread.sleep(100);
                    	}
                    	} catch (InterruptedException e) {
                    		//nothing?
                    	}
                    	System.out.println("stdIn or in is ready");
                    	if (in.ready()) {
                            System.out.println(in.readLine());
                    	}
                    	if (stdIn.ready()){
                        	userInput = stdIn.readLine();
                            out.println(userName + ": " + userInput);
                    	}
                    	if (first)
                    	{
                    		System.out.println("in while");
                    		first = false;
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
        }).start();

	}
	
}
