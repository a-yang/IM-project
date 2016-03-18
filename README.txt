alw22 asy24

TO HOST:
open ChatRoom.java
Click Host Chat
Type in port number and user name
Then type your messages in the text box and press enter or send
upload file name in file text box and press enter or send

TO JOIN:
open ChatRoom.java
Click Join Chat
type in port number, user name and host name (local host or IP address)
Then type your messages in the text box and press enter or send
upload file name in file text box and press enter or send

IMPLEMENTED:

We implemented this group chat messaging with 5 different classes. Our ChatRoom.java is the file to start the chat. This opens the ChatRoomController.java which does the majority of the work in the program. 

ChatRoomView holds all of the views for the program and calls ChatRoomController on an event like a joining or hosting the chat or sending a message/file (either by enter or button). 

The ChatRoomController opens the view with launches the application. It holds the action listeners for the host and join button and then the action listeners for those screens respective buttons. The host starts a chat on the local host and a specified port number. The client can connect to the chat by specifying the IP address and the same port and will join it. When you click join or host, the ChatRoomController creates a new client and opens the runChat() function in the Client.java file.

The Client.java file has a thread that says while true: if system.in (which was previously set to the messageBox so the messages output to the box) has something in it, read that in to a byte array and then output that to the message box. 
To deal with sending a file, in ChatRoomController we call client.sendFile(filename) which sends the file name to a different input stream reader in Client. It creates a new File object and then defines a InputStream that reads in the file object. The file is read in to a byte array and then the BufferedInputStream sends that byte array to the server, which sends it back to all the clients. If the file is an image, it is automatically saved as “image<number>.jpg” (where number is an incremented counter starting from 1 in case multiple images are sent) in the same directory as the program. If it’s not an image, then the contents of the file are outputted to the screen as plain text.

In Server.java, a new thread is called when a Client is created, which continually listens for new messages, which are added to the queue of messages.. 

SocketThread.java defines the InputStream, which reads from the client’s socket and then adds that message to the queue that holds the message history. It also checks to see if the message history queue has a message that hasn’t been outputted, and then sends that to the client to be outputted. This process is synchronized so that if multiple messages come in at once the server can deal with it and output all of them in order.

FEATURES
Supports text file sending - outputs to the screen
Supports image (jpg) file sending - saves to a file
Automatic scrolling in the chat message screen
Automatic message sending/file uploading using the enter key
Automatic notification if host ends chatroom