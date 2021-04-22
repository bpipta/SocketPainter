package socketpainter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class Painter extends JFrame implements ActionListener, MouseListener, MouseMotionListener, WindowListener {
	
	private JPanel holder;
	private JPanel leftPanel;
	private PaintingPanel centerPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	private JPanel chatPanel;
	private JTextArea chatText;
	private JTextArea chatBox;
	private JButton sendButton;
	private JTextArea messageBox;
	
	private JButton redPaint;
	private JButton bluePaint;
	private JButton greenPaint;
	private JButton yellowPaint;
	private JButton blackPaint;
	private JButton orangePaint;
	private JButton magentaPaint;
	private JButton pinkPaint; 
	private JButton grayPaint;
	private JButton cyanPaint;
	private JButton darkGrayPaint;
	private JButton whitePaint;
	private JButton lineButton;
	private JButton circleButton;
	
	private Color color;
	private String shapeType;
	private PaintingPrimitive shape;
	private PaintingPrimitive shapeOutline;
	private Point point1;
	private Point point2;
	private String name;
	
	private Socket socket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public Painter() {
		
		addWindowListener(this);
		
		setSize(500, 500);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		holder = new JPanel();
		holder.setLayout(new BorderLayout());

		// Create the paints 

		leftPanel = new JPanel();
		leftPanel.setLayout(new GridLayout(6, 2)); // 3 by 1
		leftPanel.setPreferredSize(new Dimension(75, this.getHeight()));

		// add red paint button
		redPaint = new JButton();
		redPaint.setBackground(Color.RED);
		redPaint.setOpaque(true);
		redPaint.setBorderPainted(false);
		redPaint.addActionListener(this);
		redPaint.setActionCommand("red");
		leftPanel.add(redPaint);  // Added in next open cell in the grid
		
		//blue
		bluePaint = new JButton();
		bluePaint.setBackground(Color.BLUE);
		bluePaint.setOpaque(true);
		bluePaint.setBorderPainted(false);
		bluePaint.addActionListener(this);
		bluePaint.setActionCommand("blue");
		leftPanel.add(bluePaint);
		
		//green
		greenPaint = new JButton();
		greenPaint.setBackground(Color.GREEN);
		greenPaint.setOpaque(true);
		greenPaint.setBorderPainted(false);
		greenPaint.addActionListener(this);
		greenPaint.setActionCommand("green");
		leftPanel.add(greenPaint);
		
		//yellow
		yellowPaint = new JButton();
		yellowPaint.setBackground(Color.YELLOW);
		yellowPaint.setOpaque(true);
		yellowPaint.setBorderPainted(false);
		yellowPaint.addActionListener(this);
		yellowPaint.setActionCommand("yellow");
		leftPanel.add(yellowPaint);
		
		//orange
		orangePaint = new JButton();
		orangePaint.setBackground(Color.ORANGE);
		orangePaint.setOpaque(true);
		orangePaint.setBorderPainted(false);
		orangePaint.addActionListener(this);
		orangePaint.setActionCommand("orange");
		leftPanel.add(orangePaint);
		
		//magenta
		magentaPaint = new JButton();
		magentaPaint.setBackground(Color.MAGENTA);
		magentaPaint.setOpaque(true);
		magentaPaint.setBorderPainted(false);
		magentaPaint.addActionListener(this);
		magentaPaint.setActionCommand("magenta");
		leftPanel.add(magentaPaint);
		
		//pink
		pinkPaint = new JButton();
		pinkPaint.setBackground(Color.PINK);
		pinkPaint.setOpaque(true);
		pinkPaint.setBorderPainted(false);
		pinkPaint.addActionListener(this);
		pinkPaint.setActionCommand("pink");
		leftPanel.add(pinkPaint);
		
		//cyan
		cyanPaint = new JButton();
		cyanPaint.setBackground(Color.CYAN);
		cyanPaint.setOpaque(true);
		cyanPaint.setBorderPainted(false);
		cyanPaint.addActionListener(this);
		cyanPaint.setActionCommand("cyan");
		leftPanel.add(cyanPaint);
		
		//gray
		grayPaint = new JButton();
		grayPaint.setBackground(Color.GRAY);
		grayPaint.setOpaque(true);
		grayPaint.setBorderPainted(false);
		grayPaint.addActionListener(this);
		grayPaint.setActionCommand("gray");
		leftPanel.add(grayPaint);
		
		//dark gray
		darkGrayPaint = new JButton();
		darkGrayPaint.setBackground(Color.DARK_GRAY);
		darkGrayPaint.setOpaque(true);
		darkGrayPaint.setBorderPainted(false);
		darkGrayPaint.addActionListener(this);
		darkGrayPaint.setActionCommand("dark gray");
		leftPanel.add(darkGrayPaint);
		
		//white
		whitePaint = new JButton();
		whitePaint.setBackground(Color.WHITE);
		whitePaint.setOpaque(true);
		whitePaint.setBorderPainted(false);
		whitePaint.addActionListener(this);
		whitePaint.setActionCommand("white");
		leftPanel.add(whitePaint);
		
		//black
		blackPaint = new JButton();
		blackPaint.setBackground(Color.BLACK);
		blackPaint.setOpaque(true);
		blackPaint.setBorderPainted(false);
		blackPaint.addActionListener(this);
		blackPaint.setActionCommand("black");
		leftPanel.add(blackPaint);

		// add the panels to the overall panel, holder
		// note that holder's layout should be set to BorderLayout
		holder.add(leftPanel, BorderLayout.WEST);

		// use similar code to add topPanel buttons to the NORTH region
		topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 2));
		
		lineButton = new JButton("Line");
		lineButton.addActionListener(this);
		lineButton.setActionCommand("line");
		topPanel.add(lineButton);
		
		circleButton = new JButton("Circle");
		circleButton.addActionListener(this);
		circleButton.setActionCommand("circle");
		topPanel.add(circleButton);
		
		holder.add(topPanel, BorderLayout.NORTH);
		
		// omit the center panel for now
		// after finishing the PaintingPanel class (described later) add a
		// new object of this class as the CENTER panel
		centerPanel = new PaintingPanel();
		centerPanel.addMouseListener(this);
		centerPanel.addMouseMotionListener(this);
		holder.add(centerPanel, BorderLayout.CENTER);

		// And later you will add the chat panel to the SOUTH
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		
		chatPanel = new JPanel();
		chatPanel.setLayout(new BoxLayout(chatPanel, BoxLayout.X_AXIS));
		
		chatText = new JTextArea();
		chatText.setBackground(Color.LIGHT_GRAY);
		chatText.setText("Message: ");
		chatText.setEditable(false);
		chatPanel.add(chatText);
		
		chatBox = new JTextArea();
		chatBox.setBackground(Color.LIGHT_GRAY);
		chatBox.setLineWrap(true);
		chatPanel.add(chatBox);
		
		sendButton  = new JButton("Send Message");
		sendButton.addActionListener(this);
		sendButton.setActionCommand("send");
		chatPanel.add(sendButton);
		
		bottomPanel.add(chatPanel);
		
		messageBox = new JTextArea(5, 1);
		messageBox.setBackground(Color.GRAY);
		messageBox.setEditable(false);
		messageBox.setLineWrap(true);
		bottomPanel.add(new JScrollPane(messageBox));
		
		holder.add(bottomPanel, BorderLayout.SOUTH);
		

		// Lastly, connect the holder to the JFrame
		setContentPane(holder);
		
		//Set JFrame location to the middle of the screen
		setLocationRelativeTo(null);

		// And make it visible to layout all the components on the screen
		setVisible(true);
		
		//Set to draw a red line by default
		color = Color.BLACK;
		shapeType = "line";
		
		//Asking for painter's name
		String tempName;
		do {
		name = JOptionPane.showInputDialog("Enter your name");
		tempName = name.replaceAll("\\s", "");
		} while(tempName.equals(""));
		
		setTitle(name);
		
		//Sockets
		try {
			
			socket = new Socket("localhost", 7777);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			//Reading current board state and setting the initial board to it
			centerPanel.setPrimitives((ArrayList<PaintingPrimitive>)ois.readObject());
			repaint();
			
			//For writing all the previous messages to the new painter's message box 
			/*ArrayList<String> str = (ArrayList<String>) ois.readObject();
			for(int i = 0; i < str.size(); i++) {
				messageBox.append(str.get(i));
				
			}
			messageBox.setCaretPosition(messageBox.getDocument().getLength()); */ 
			
			oos.writeObject(name);
			
			//Starting listening thread
			ListeningThread ls = new ListeningThread(ois, centerPanel, messageBox);
			Thread th = new Thread(ls);
			th.start();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//Button action listener
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().equals("red")) {
			color = Color.RED;
		} 
		else if(arg0.getActionCommand().equals("blue")) {
			color = Color.BLUE;
		} 
		else if(arg0.getActionCommand().equals("green")) {
			color = Color.GREEN;
		}
		else if(arg0.getActionCommand().equals("yellow")) {
			color = Color.YELLOW;
		}
		else if(arg0.getActionCommand().equals("black")) {
			color = Color.BLACK;
		}
		else if(arg0.getActionCommand().equals("orange")) {
			color = Color.ORANGE;
		}
		else if(arg0.getActionCommand().equals("magenta")) {
			color = Color.MAGENTA;
		}
		else if(arg0.getActionCommand().equals("pink")) {
			color = Color.PINK;
		}
		else if(arg0.getActionCommand().equals("cyan")) {
			color = Color.CYAN;
		}
		else if(arg0.getActionCommand().equals("gray")) {
			color = Color.GRAY;
		}
		else if(arg0.getActionCommand().equals("dark gray")) {
			color = Color.DARK_GRAY;
		}
		else if(arg0.getActionCommand().equals("white")) {
			color = Color.WHITE;
		}
		else if(arg0.getActionCommand().equals("line")) {
			shapeType = "line";
		}
		else if(arg0.getActionCommand().equals("circle")) {
			shapeType = "circle";
		} else if(arg0.getActionCommand().equals("send")) {
			String message = chatBox.getText();
			if(!message.equals("")) {
				messageBox.append(name + ": " + message + "\n");
			}
			chatBox.setText("");
			
			//Sending message and painter name to hub
			try {
				oos.writeObject(name);
				oos.writeObject(message);
				messageBox.setCaretPosition(messageBox.getDocument().getLength());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent arg0) {
		point1 = new Point(arg0.getX(), arg0.getY());
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		point2 = new Point(arg0.getX(), arg0.getY());
		if(shapeType.equals("line")) {
			shape = new Line(point1, point2, color);
		} else if (shapeType.equals("circle")) {
			shape = new Circle(point1, point2, color);
		} else {
			System.out.println("Please select a shape.");
		}
		centerPanel.addPrimitive(shape);
		
		//Writing shape to hub
		try {
			oos.writeObject(shape);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
		
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) { //Method to close painter without generating errors
		try { 
			//Writing -1 to tell the hub the painter is disconnecting
			oos.writeObject(-1);
			//Finally writing the name of the disconnecting painter
			oos.writeObject(name);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Method for drawing the preview of the lines and circles when the mouse is dragged
	@Override
	public void mouseDragged(MouseEvent arg0) {
		point2 = new Point(arg0.getX(), arg0.getY());
		centerPanel.removePrimitive(shapeOutline);
		repaint();
		if(shapeType.equals("line")) {
			shapeOutline = new Line(point1, point2, color);
		} else if (shapeType.equals("circle")) {
			shapeOutline = new Circle(point1, point2, color);
		}
		centerPanel.addPrimitive(shapeOutline);
		repaint();
	}
	
	//Unused methods (main at bottom)
	//%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	@Override
	public void windowClosed(WindowEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	//=%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
	
	public static void main(String[] args) {
		
		new Painter();
		
	}

	

}
