package server;

import java.io.*;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ChatRoomController {
		public int port;
		public String host = "localhost";
		public String username;
		public ChatRoomView view;
		
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
	            		Client chatHost = new Client (host, port, username);
	            	}
	            }).start();
	            startSystem();
			
		}    
	    
		public void ButtonListenerJoin(){
	        view.JoinView(this);		
	    }
		
		public void ButtonListenerJoinC(){
			   new Thread(new Runnable() {
	            	public void run() {
	            		Client client = new Client (host, port, username);
	            	}
	            }).start();
	            startSystem();
			
		}
		
		public void startSystem() {
			view.chatRoom(this);
			redirectOutput();
		}
		
		public void getSendButtonListener() {
			
					view.sendMessage();
			
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
	
	}