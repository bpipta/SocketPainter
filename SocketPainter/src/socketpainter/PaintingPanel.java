package socketpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel{
	
	private ArrayList<PaintingPrimitive> primitives = new ArrayList<PaintingPrimitive>();
	
	public PaintingPanel() {
		setBackground(Color.WHITE);
	}
	
	public void addPrimitive(PaintingPrimitive obj) {
        this.primitives.add(obj);

	}
	
	public void removePrimitive(PaintingPrimitive obj) {
		primitives.remove(obj);
		
	}
	
	public int primitivesSize() {
		return this.primitives.size();
	}
	
	public void setPrimitives(ArrayList<PaintingPrimitive> p) {
		this.primitives = p;
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(PaintingPrimitive obj : primitives) { 
                    obj.draw(g);
        }
	}

}
