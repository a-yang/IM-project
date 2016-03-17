package server;

import java.io.*;
import java.util.Scanner;

public class ChatRoomController {
	ChatRoomView view;
	Client model;
	
	public ChatRoomController(ChatRoomView view)
	{
		this.view = view;
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Start a chatroom or join one (s/j)");
		String choice = scanner.next();
		
		if (choice.equals("s")) {
            System.out.print("Enter port number: ");
            final Integer port = scanner.nextInt(); 
            
            System.out.print("Pick a username: ");
            final String userName = scanner.next();
            //scanner.close();
            
            new Thread(new Runnable() {
            	public void run() {
            		try {
                		server.Server chat = new server.Server(port);
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}
            }).start();
    		
            new Thread(new Runnable() {
            	public void run() {
                	System.out.println("Starting host client");
                	model = new Client ("localhost", port, userName);
            	}
            }).start();


		}
		else if (choice.equals("j"))
		{
			System.out.print("Enter host name: ");
			final String hostName = scanner.next();
			System.out.print("Enter port number: ");
			final Integer port = scanner.nextInt();
			System.out.print("Pick a username: ");
			final String userName = scanner.next();
			
			new Thread(new Runnable() {
				public void run() {
					model = new Client(hostName, port, userName);
				}
			}).start();
		}
		else
		{
			System.out.println("Invalid choice");
		}
		
		
		startSystem();
	}
	
	public void startSystem() {
		view.chatRoom();
		redirectOutput();
	}
	
	private void redirectOutput() {
		  OutputStream out = new OutputStream() {
		    @Override
		    public void write(final int b) throws IOException {
		      view.updateTextPane(String.valueOf((char) b));
		    }
		 
		    @Override
		    public void write(byte[] b, int off, int len) throws IOException {
		      view.updateTextPane(new String(b, off, len));
		    }
		 
		    @Override
		    public void write(byte[] b) throws IOException {
		      write(b, 0, b.length);
		    }
		  };
		  System.setOut(new PrintStream(out, true));
		  System.setErr(new PrintStream(out, true));
	}
}
