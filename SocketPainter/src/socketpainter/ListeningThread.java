package socketpainter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import javax.swing.JTextArea;

public class ListeningThread implements Runnable {

	private ObjectInputStream ois;
	private PaintingPanel drawingBoard;
	private JTextArea messageBox;
	
	public ListeningThread(ObjectInputStream ois, PaintingPanel db, JTextArea mb) {
		this.ois = ois;
		this.drawingBoard = db;
		this.messageBox = mb;
	}
	
	@Override
	public void run() {
		
		try {
			
			while(true) {
				
				//Listening for messages from the hub
				Object obj = ois.readObject();
				
				//Checking to see the type of object received
				if(obj instanceof PaintingPrimitive) {
					
					drawingBoard.addPrimitive((PaintingPrimitive)obj);
					drawingBoard.repaint();
				
				 } else {
					
					String message = (String)obj;
					messageBox.append(message);
					messageBox.setCaretPosition(messageBox.getDocument().getLength());
				} 
			}
			
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

}
