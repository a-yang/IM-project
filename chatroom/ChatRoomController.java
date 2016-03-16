package chatroom;

import java.io.*;

public class ChatRoomController {
	private ChatRoom model;
	private ChatRoomView view;
	
	public ChatRoomController(ChatRoom model, ChatRoomView view) throws IOException {
		this.model = model;
		this.view = view;
		runChatRoom();
	}
	
	public void runChatRoom() throws IOException {
		this.view.startChatRoom();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("starting thread 1");
					while (true)
					{
						model.addClient();
						System.out.println("One client added in.");
					}
				} catch (IOException e) {
					System.out.println(e);
				}

			}
		}).start();
						
		new Thread(new Runnable() {
				public void run() {
					System.out.println("very beginning of thread 2");
						while (model.clientSockets.size() == 0)
						{
							try {
								Thread.sleep(100);
							} catch (InterruptedException e)
							{
								//nothing?
							}
						}
						System.out.println("starting thread 2");
						
						while (true)
						{
							try {
								for (int i = 0; i < model.clientSockets.size(); i++)
								{
									model.receiveMessage(model.clientSockets.get(i));
								}
							} catch (IOException e)
							{
								e.printStackTrace();
							}
						}
						

				}
			}).start();
	}
}
