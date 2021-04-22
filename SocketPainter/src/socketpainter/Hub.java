package socketpainter;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Hub {

	public static void main(String[] args) {
		
		ArrayList<PaintingPrimitive> board = new ArrayList<PaintingPrimitive>();
		ArrayList<ObjectOutputStream> oosArr = new ArrayList<ObjectOutputStream>();
		ArrayList<String> messages = new ArrayList<String>();
		
		try {
			ServerSocket ss = new ServerSocket(7777);
			System.out.println("Server online, waiting for painters to connect...");
			
			while(true) {
				Socket s = ss.accept(); //Blocking
				
				System.out.println("Painter connected on: " + s);
				
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				oosArr.add(oos);
				
				System.out.println("Current connections: " + oosArr.size());
				
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				
				//Writing current board state to new painters
				oos.writeObject(board);
				//oos.writeObject(messages); (For sending messages for new painter)
				
				//Make a new thread and add it to the thread array
				ConnectPainterThread cpt = new ConnectPainterThread(board, ois, oos, oosArr, messages); //socketArr.get(painterNum)
				Thread th = new Thread(cpt);
				th.start();
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
