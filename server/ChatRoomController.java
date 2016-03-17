package server;

import java.io.*;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

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
		view.chatRoom(getSendButtonListener());
		redirectOutput();
	}
	
	ActionListener getSendButtonListener() {
		return new ActionListener() {
			@Override public void actionPerformed (ActionEvent e) {
				view.sendMessage();
			}
		};
	}
	
	private void redirectOutput() {
		  OutputStream outPut = new OutputStream() {
		    @Override
		    public void write(final int byteString) throws IOException {
		      view.newMessage(String.valueOf((char) byteString));
		    }
		 
		    @Override
		    public void write(byte[] byteString, int off, int len) throws IOException {
		      view.newMessage(new String(byteString, off, len));
		    }
		 
		    @Override
		    public void write(byte[] byteString) throws IOException {
		      write(byteString, 0, byteString.length);
		    }
		  };
		  System.setOut(new PrintStream(outPut, true));
		  System.setErr(new PrintStream(outPut, true));
	}
}
