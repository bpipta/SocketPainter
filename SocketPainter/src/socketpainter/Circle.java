package socketpainter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class Circle extends PaintingPrimitive {
	
	private Point center;
	private Point radiusPoint;
	
	public Circle() {
		this.center = new Point();
		this.radiusPoint = new Point();
	}
	
	public Circle(Point center, Point radius, Color c) {
		super(c);
		this.center = center;
		this.radiusPoint = radius;
	}
	
	
	@Override
	protected void drawGeometry(Graphics g) {
		int radius = (int) Math.abs(center.distance(radiusPoint));
        g.drawOval(center.x - radius, center.y - radius, radius*2, radius*2);  
	}
	
	protected void printPoints() {
		System.out.println(center.toString() + " " + radiusPoint.toString() );
		
	}
	
	

}
