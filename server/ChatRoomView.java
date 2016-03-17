package server;

import java.io.*;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class ChatRoomView extends JFrame {

    JLabel statusbar;
    JPanel panel;
    JTextArea chatMessages;
    JTextField typeMessageBox;

    public ChatRoomView() {
    	//chatRoom();
        
    }
    
    public void chatRoom(ActionListener sendButtonListener) {
    	panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
    	
    	
    	
    	chatMessages = new JTextArea();
    	chatMessages.setForeground(Color.BLACK);
    	JScrollPane scrollPane = new JScrollPane(chatMessages); 
    	
    	chatMessages.setEditable(false);
    	 GridBagConstraints c = new GridBagConstraints();
    	 c.insets = new Insets(20,20,20,20);
         c.gridwidth = GridBagConstraints.REMAINDER;
  
         c.fill = GridBagConstraints.BOTH;
         c.weightx = 1;
         c.weighty = 1;
         panel.add(scrollPane, c);
         
         typeMessageBox = new JTextField();
         c.gridx = 0;
         c.weightx = .8;
         c.weighty = 0;
         c.gridwidth = 700;
         panel.add(typeMessageBox, c);
         
         JButton sendMessage = new JButton("Send");
         sendMessage.addActionListener(sendButtonListener);
         c.gridx = GridBagConstraints.RELATIVE;
         c.weightx = 0;
         panel.add(sendMessage, c);
         
    	add(panel);
    	
    	
        setTitle("Advanced Programming IM Chatroom");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    	setVisible(true);
    }
    
    void newMessage(String message) {
    	chatMessages.append(message);
    }
    
    void sendMessage() {
    	String message = typeMessageBox.getText();
    	InputStream toSocket = new ByteArrayInputStream(message.getBytes());
    	System.setIn(toSocket);
    	typeMessageBox.setText("");

    }
}