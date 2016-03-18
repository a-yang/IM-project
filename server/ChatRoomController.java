package server;

import java.io.*;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

import javax.swing.JButton;

import javax.swing.JButton;

public class ChatRoomController {
		public int port;
		public String host;
		public String username;
		public ChatRoomView view;
		public String fileName;
		Client client;
		Boolean isHost = false;
		Server server;
		
		public ChatRoomController(){
			view = new ChatRoomView(this);
		}
		
		public ChatRoomController(ChatRoomView view){
		    view = view;
	
		} 
		public void ButtonListenerHost(){
			view.HostView(this);
		}
	    public void ButtonListenerStart(){

    		try {
    			isHost = true;
        		server = new server.Server(port);
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
	    		

	        client = new Client ("localhost", port, username);
	        startSystem();
			
		}    
	    
		public void ButtonListenerJoin(){
	        view.JoinView(this);		
	    }
		
		public void ButtonListenerJoinC(){
			client = new Client (host, port, username);

			startSystem();
		}
		
		public void startSystem() {
			view.chatRoom(this);
			redirectOutput();
		}
		
		public void getSendButtonListener() {
			view.sendMessage();
		}
	
		
		public void readFileListener(final String fileName){			
			new Thread(new Runnable() {
				public void run() {
					client.sendFile(fileName);
				}
			}).start();
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
		
		public void setInfo(String hostName, int portNumber, String name) {
			host = hostName;
			port = portNumber;
			username = name;
		}
		
		public void closeServer() {
			if (isHost) {
				System.out.println("is host");
				server.close();
			}
		}
	
	}	