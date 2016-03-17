package server;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChatRoomView extends JFrame {

    JLabel statusbar;
    JPanel panel;
    JTextArea chatMessages;

    public ChatRoomView() {
    	//chatRoom();
        
    }

    public final void initUI() {

        panel = new JPanel();


        panel.setLayout(null);

        // Initialize buttons
        JButton close = new JButton("Close");
        JButton open = new JButton("Open");
        JButton find = new JButton("Find");
        JButton save = new JButton("Save");
        
        // Set button borders
        close.setBounds(40, 30, 80, 25);
        open.setBounds(40, 80, 80, 25);
        find.setBounds(40, 130, 80, 25);
        save.setBounds(40, 180, 80, 25);
        
        // Add action listeners to the buttons
        close.addActionListener(new ButtonListener());
        open.addActionListener(new ButtonListener());
        find.addActionListener(new ButtonListener());
        save.addActionListener(new ButtonListener());

        // Add buttons to panel
        panel.add(close);
        panel.add(open);
        panel.add(find);
        panel.add(save);

        // Add panel to JFrame
        add(panel);

        setTitle("Multiple Sources");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
    }

    // Create a new custom listener that is an inner class
    class ButtonListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JButton myButton = (JButton) e.getSource();
            String label = myButton.getText();
            statusbar.setText(" " + label + " button clicked");
        }
    }
    
    public void chatRoom() {
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
         
         JTextField typeMessageBox = new JTextField();
         c.gridx = 0;
         c.weightx = .8;
         c.weighty = 0;
         c.gridwidth = 700;
         panel.add(typeMessageBox, c);
         
         JButton sendMessage = new JButton("Send");
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
    
    void updateTextPane(String message) {
    	chatMessages.append(message);
    }
}