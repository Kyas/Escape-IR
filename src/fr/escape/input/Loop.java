package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

// TODO Comment
public final class Loop implements Gesture {
	private final static float RIGHT_FLAG = 1.0f;
	private final static float LEFT_FLAG = 2.0f;

	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		int faultTolerance = 90;
		int coeff = 25;
		Input maxX = start;
		Input minX = start;
		for(Input event : events) {
			if(event.getX() > maxX.getX()) maxX = event;
			if(event.getX() < minX.getX()) minX = event;
		}
		
		int minD = start.getX() - minX.getX();
		int maxD = maxX.getX() - start.getX();
		boolean isRight = maxD > minD;
		
		int diameter = (isRight)?maxD:minD;
		if(diameter < 75 || diameter > 125)
			return false;
		if(end.getX() > start.getX() + coeff || end.getX() < start.getX() - coeff || end.getY() > start.getY() + coeff || end.getY() < start.getY() - coeff)
			return false;
		
		int radius = diameter / 2;
		int cx = start.getX() + ((isRight)?radius:-radius);
		int cy = start.getY();
		
		int smallRad = radius - (radius*faultTolerance/100);
		int bigRad = radius + (radius*faultTolerance/100);
		for(Input event : events) {
			double dist = Math.sqrt(Math.pow(event.getX()-cx, 2)+Math.pow(event.getY()-cy, 2));
			if(dist < smallRad || dist > bigRad) return false;
		}
		
		velocity[0] = diameter;
		velocity[1] = 0.0f;
		velocity[2] = 0.0f;
		velocity[3] = ((isRight)?RIGHT_FLAG:LEFT_FLAG);
		
		return true;
	}
}
