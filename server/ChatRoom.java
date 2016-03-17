package server;

import java.io.*;
import java.util.Scanner;

public class ChatRoom {

	public static void main(String[] args) throws IOException {
		
		ChatRoomView view = new ChatRoomView();
		ChatRoomController controller = new ChatRoomController(view);
	}
}
