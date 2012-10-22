package fr.escape.input;

import java.util.List;

import fr.umlv.zen2.MotionEvent;

public class LeftLoop implements Gesture {

	@Override
	public boolean accept(MotionEvent start, List<MotionEvent> events,MotionEvent end) {
		boolean valid = true;
		int faultTolerance = 90;
		int coeff = 25;
		MotionEvent minX = start;
		for(MotionEvent event : events) {
			if(event.getX() < minX.getX()) minX = event;
		}
		
		int diameter = start.getX() - minX.getX();
		if(diameter < 75 || diameter > 125) return false;
		if(end.getX() > start.getX() + coeff || end.getX() < start.getX() - coeff || end.getY() > start.getY() + coeff || end.getY() < start.getY() - coeff) return false;
		
		int radius = diameter / 2;
		int cx = start.getX() - radius;
		int cy = start.getY();
		
		int smallRad = radius - (radius*faultTolerance/100);
		int bigRad = radius + (radius*faultTolerance/100);
		for(MotionEvent event : events) {
			double dist = Math.sqrt(Math.pow(event.getX()-cx, 2)+Math.pow(event.getY()-cy, 2));
			if(dist < smallRad || dist > bigRad) return false;
		}
		return valid;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
	}

}
