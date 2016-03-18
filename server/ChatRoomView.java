package server;

import java.io.*;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import java.awt.event.*;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class ChatRoomView extends JFrame {

    JLabel statusbar;
    JPanel panel;
    ChatRoomController controller;
    JTextArea port;
    JTextArea username;
    JTextArea host;
    JTextArea chatMessages;
    JTextField typeMessageBox;
    JTextArea fileName;
    JTextField fileMessageBox;
 
    
    public ChatRoomView(ChatRoomController cc) {
    	Color blue = new Color(100,149,237);
    	controller = cc;
        initUI();
        panel.setBackground(blue);
    }

    public final void initUI() {

        panel = new JPanel();
        statusbar = new JLabel("Welcome!");


        panel.setLayout(null);

        // Initialize buttons
        JButton host = new JButton("Host a Chat");
        JButton join = new JButton("Join a Chat");

        
        // Set button borders
        host.setBounds(125, 90, 150, 25);
        join.setBounds(125, 140, 150, 25);
       

  
        // Add action listeners to the buttons
        host.addActionListener(new ActionListener(){
   	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.ButtonListenerHost();			
			}
        });
        join.addActionListener(new ActionListener(){
        	
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.ButtonListenerJoin();			
			}
        });

        // Add buttons to panel
        panel.add(host);
        panel.add(join);
       
        // Add panel to JFrame
        add(panel);
        add(statusbar, BorderLayout.SOUTH);

        setTitle("Advanced Programming Chat");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
    }
    
    public void HostView(final ChatRoomController b){
    	
    	panel.removeAll();
    	panel.updateUI();
    

    	port = new JTextArea("Port Number");
    	port.setBounds(125, 60, 150, 25);
    
    	
    	username = new JTextArea("User Name");    	
    	username.setBounds(125, 90, 150, 25);	
  
    	 Boolean portclicked = false;
    	 Boolean userclicked = false;
    	
    	if(portclicked.booleanValue() == false){
    		port.addMouseListener(new MouseAdapter(){
    			@Override
    			public void mouseClicked(MouseEvent e){
    				port.setText("");
    			}
    		});
    		portclicked = true;
    	}
    	
    	if (userclicked.booleanValue() == false){
    		username.addMouseListener(new MouseAdapter(){
    			@Override
    			public void mouseClicked(MouseEvent e){
    				username.setText(""); 
    			}            	  	
    		});
    		userclicked = true;
    	}
 
    	JButton start = new JButton("Start Chat");
    	start.setBounds(125, 140, 150, 25);
        start.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String realPort = port.getText();
				b.setInfo("localhost", Integer.parseInt(realPort), username.getText());
				b.ButtonListenerStart();	
				statusbar.setText("Started Chat as " + username.getText());
			}
        });
    	
        // Add buttons to panel
        panel.add(start);
        panel.add(port);
        panel.add(username);       

        // Add panel to JFrame
        add(panel);
        add(statusbar, BorderLayout.SOUTH);

        setTitle("Advanced Programming Chat");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
    	
    }
    
    public void JoinView(final ChatRoomController b){
    	
    	panel.removeAll();
    	panel.updateUI();
    	
    	port = new JTextArea("Port Number");
    	port.setBounds(125, 50, 150, 25);
    
    	host = new JTextArea("Host Name");
    	host.setBounds(125, 80, 150, 25);
    	
    	username = new JTextArea("User Name");    	
    	username.setBounds(125, 110, 150, 25);
    	Boolean portclicked = false;
    	Boolean userclicked = false;
    	Boolean hostclicked = false;
    	
    	if(portclicked.booleanValue() == false){
    		port.addMouseListener(new MouseAdapter(){
    			@Override
    			public void mouseClicked(MouseEvent e){
    				port.setText("");
    			}
    		});
    		portclicked = true;
    	}
    	
    	if (userclicked.booleanValue() == false){
    		username.addMouseListener(new MouseAdapter(){
    			@Override
    			public void mouseClicked(MouseEvent e){
    				username.setText("");
    			}
    		});
    		userclicked = true;
    	} 
    	
    	if (hostclicked.booleanValue() == false){
    		host.addMouseListener(new MouseAdapter(){
    			@Override
    			public void mouseClicked(MouseEvent e){
    				host.setText("");
    			}
    		});
    		hostclicked = true;
    	}
    	
    	JButton join = new JButton("Join Chat");
    	join.setBounds(125, 160, 150, 25);   
        join.addActionListener(new ActionListener(){
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		String realPort = port.getText();
				
				b.setInfo(host.getText(), Integer.parseInt(realPort), username.getText());
				b.ButtonListenerJoinC();	
				statusbar.setText("Joined Chat as " + username.getText());
			}
        });

        // Add buttons to panel
        panel.add(join);
        panel.add(port);
        panel.add(username);  
        panel.add(host);

        // Add panel to JFrame
        add(panel);
        add(statusbar, BorderLayout.SOUTH);

        setTitle("Advanced Programming Chat");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setVisible(true);
    }
    
    public void chatRoom(final ChatRoomController b) {
    	
    	panel.removeAll();
    	panel.updateUI();
    	
    	//panel = new JPanel();
    	panel.setLayout(new GridBagLayout());
   
    	chatMessages = new JTextArea();
    	chatMessages.setForeground(Color.BLACK);
    	
    	DefaultCaret caret = (DefaultCaret)chatMessages.getCaret();
    	caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    	 
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
        
        Action action = new AbstractAction(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		b.getSendButtonListener();
        		
        	}
        };
        Action action1 = new AbstractAction(){
        	@Override
        	public void actionPerformed(ActionEvent e){
        		String fileName = fileMessageBox.getText();
        		b.readFileListener(fileName);
        		fileMessageBox.setText("");
        		
        	}
        };
         
        JButton sendMessage = new JButton("Send");
        sendMessage.addActionListener(action);
        typeMessageBox.addActionListener(action);
         
        c.gridx = GridBagConstraints.RELATIVE;
        c.weightx = 0;
        panel.add(sendMessage, c);
        
        fileMessageBox = new JTextField("File Path");
        c.gridx = 0;
        c.gridy =2;
        c.weightx = .8;
        c.weighty = 0;
        c.gridwidth = 675;
        panel.add(fileMessageBox, c);
        
        JButton uploadFile = new JButton("Upload");
        c.gridy = 2;
        c.gridx = GridBagConstraints.RELATIVE;
        c.weightx = 0;
        c.insets = new Insets(20,5,20,20);
        
        fileMessageBox.addMouseListener(new MouseAdapter(){
        	@Override
        	public void mouseClicked(MouseEvent e){
        		fileMessageBox.setText("");
        	}
        });
         
        fileMessageBox.addActionListener(action1);
        uploadFile.addActionListener(action1);
         
        panel.add(uploadFile, c);
               
        add(panel);
    	
        setTitle("Advanced Programming Chat");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    	setVisible(true);
        this.addComponentListener(new ComponentAdapter() {
        	@Override
            public void componentHidden(ComponentEvent e) {
            	System.out.println("called");
                controller.closeServer();
                dispose();
                System.exit(0);
            }
        });
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