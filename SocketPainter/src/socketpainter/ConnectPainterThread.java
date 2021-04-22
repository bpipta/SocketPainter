package socketpainter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ConnectPainterThread implements Runnable {

	private ArrayList<PaintingPrimitive> board;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ArrayList<ObjectOutputStream> oosArr;
	private ArrayList<String> messages;
	
	public ConnectPainterThread(ArrayList<PaintingPrimitive> b, ObjectInputStream ois,  
			ObjectOutputStream oos, ArrayList<ObjectOutputStream> oosA, ArrayList<String> m) {
		this.board = b;
		this.ois = ois;
		this.oos = oos;
		this.oosArr = oosA;
		this.messages = m;
	}
	
	@Override
	public synchronized void run() {
		
		try {
			
			String joinMessage = (String)ois.readObject();
			joinMessage += " joined\n";
			//messages.add(joinMessage); (For saving messages for new painter)
			
			writeToPainters(joinMessage);
			
			while(true) {
				
				//Listening for objects from the painter
				Object obj = ois.readObject(); //Blocking
				
				//If statements checking if they are strings or shapes or other
				if(obj instanceof PaintingPrimitive) {
					
					PaintingPrimitive shape = (PaintingPrimitive)obj; 
					
					//Adding shape to community board
					board.add(shape);
					
					//Write shape to all other connected painters
					writeToPainters(shape);
					
				 } else if (obj instanceof String){
					
					String message = (String)obj + ": ";
					message += (ois.readObject()) + "\n";
					//messages.add(message); (For saving messages for new painter)
					
					writeToPainters(message);
					
					//Else statement for when painter is closed
				} else {
					//Reading name from painter that's leaving
					String leaveMessage = (String)ois.readObject() + " left\n";
					//messages.add(leaveMessage); (For saving messages for new painter)
					
					//Removing stream from array
					for(int i = 0; i < oosArr.size(); i++) {
						if(oos == oosArr.get(i)) {
							oosArr.remove(i);
						}
					} 
					
					for(int i = 0; i < oosArr.size(); i++) {
						//Writing object to all other painters
						oosArr.get(i).writeObject(leaveMessage);
					} 
					
					ois.close();
					oos.close();
					return;
				}
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//method to write objects to all connected painters
	public void writeToPainters(Object obj) {
		for(int i = 0; i < oosArr.size(); i++) {
			//Checking for double drawing
			if(oos != oosArr.get(i)) {
				//Writing object to all other painters
				try {
					oosArr.get(i).writeObject(obj);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
	}

	

}
