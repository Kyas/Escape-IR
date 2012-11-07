package fr.escape.graphics;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public final class Shapes {

	public static Shape createCircle(int centerX, int centerY, double radius) {
		Shape circle = new Ellipse2D.Double(centerX - radius, centerY - radius, 2.0 * radius, 2.0 * radius);
		return circle;
	}
	
	public static Shape createRectangle(int x, int y) {
		return createRectangle(0, 0, x, y);
	}
	
	public static Shape createRectangle(int x, int y, int width, int height) {
		Shape rectangle = new Rectangle(x, y);
		return rectangle;
	}
	
	public static Shape createLine(int x1, int y1, int x2, int y2) {
		Shape line = new Line2D.Double(x1, y1, x2, y2);
		return line;
	}
}
