package socketpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.beans.Transient;
import java.io.Serializable;

public class Line extends PaintingPrimitive {
	
	private Point startPoint;
	private Point endPoint;
	
	public Line() {
		this.startPoint = new Point();
		this.endPoint = new Point();
	}
	
	public Line(Point start, Point end, Color c) {
		super(c);
		this.startPoint = start;
		this.endPoint = end;
		
	}

	@Override
	protected void drawGeometry(Graphics g) {
		g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		
	}
	
	protected void printPoints() {
		System.out.println(startPoint.toString() + " " + endPoint.toString() );
		
	}

}
