package socketpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class PaintingPrimitive implements Serializable {
	
	private Color color;
	
	public PaintingPrimitive() {
		
		this.color = Color.BLACK;
	}
	
	public PaintingPrimitive(Color c) {
		
		this.color = c;
	}
	
	public final void draw(Graphics g) {
	    g.setColor(this.color);
	    drawGeometry(g);
	}

	protected abstract void drawGeometry(Graphics g);
	
	protected abstract void printPoints();

}
