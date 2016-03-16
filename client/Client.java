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
			this.userName = userName;
			clientSocket = new Socket(hostName, portNumber);
			runChat();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void receiveMessage()
	{
		try {
			//System.out.println("client.receiveMessage");
			BufferedReader in = 
					new BufferedReader(
							new InputStreamReader(clientSocket.getInputStream()));
			if (in.ready())
			{
				System.out.println(in.readLine());
			}
		} catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection");
            System.exit(1);
		}

	}
	/*
	public void runChat() {
		new Thread(new Runnable() {
			public void run() {
				while (true)
				{
					try {
						if (new BufferedReader(
								new InputStreamReader(System.in)).ready())
						{
							sendMessage();
						}
						
						if (new BufferedReader(
								new InputStreamReader(clientSocket.getInputStream())).ready()) {
							receiveMessage();
						}
					} catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
			}
		}).start();
	}*/
	
	public void sendMessage()
	{
		new Thread(new Runnable() {
			public void run() {
				try {
					//System.out.println("client.sendMessage");
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader stdIn =
                            new BufferedReader(
                                new InputStreamReader(System.in));
                    String userInput;
                    if (stdIn.ready())
                    {
                    	userInput = stdIn.readLine();
                        out.println(userInput);
                    }
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
	}
	
	public void runChat()
	{
        new Thread(new Runnable() {
        	public void run() {
        		try (
        				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        BufferedReader in = new BufferedReader(
                        		new InputStreamReader(clientSocket.getInputStream()));
                        BufferedReader stdIn = new BufferedReader(
                                new InputStreamReader(System.in))
                    ) {
    					
        			
                        String userInput;
                        while (true) {
                        	if (in.ready()) {
                                System.out.println(in.readLine());
                        	}
                        	if (stdIn.ready()){
                            	userInput = stdIn.readLine();
                                out.println(userName + ": " + userInput);
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
