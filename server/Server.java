package server;

import java.io.*;
import chatroom.*;
import java.util.Scanner;
import client.*;

public class Server {
	//server class
	//x2
	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Start a chatroom or join one (s/j)");
		String choice = scanner.next();
		
		if (choice.equals("s")) {
            System.out.println("Enter port number: ");
            Integer port = scanner.nextInt(); 
            scanner.close();
            
    		chatroom.ChatRoom chat = new ChatRoom(port);
    		chatroom.ChatRoomView chatView = new ChatRoomView();
    		chatroom.ChatRoomController controller =
    				new ChatRoomController(chat, chatView);
    		
    		//Client host = new Client ("localhost", port);
		}
		else if (choice.equals("j"))
		{
			System.out.println("Enter host name: ");
			String hostName = scanner.next();
			System.out.println("Enter port number: ");
			Integer port = scanner.nextInt();
			System.out.println("Pick a username: ");
			String userName = scanner.next();
			Client client = new Client(hostName, port, userName);
		}
		else
		{
			System.out.println("Invalid choice");
		}
	}
}
