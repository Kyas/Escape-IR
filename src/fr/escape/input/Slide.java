package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

public class Slide implements Gesture {
	
	private static int DEFAULT_FAULT_TOLERANCE = 25;
	private final static int COEFFICIENT = 3;
	private static double COEFFDIR = 0.2;
	private static float VELOCITY = 2.0f;

	@Override
	public boolean accept(Input start, List<Input> events, Input end, float[] velocity) {
		int faultTolerance = DEFAULT_FAULT_TOLERANCE;
		boolean isRight = start.getX() < end.getX();

		double div = end.getX()-start.getX();
  		double cd = (end.getY()-start.getY())/((div!=0)?div:1);
		
  		if(cd < COEFFDIR && cd > -COEFFDIR) {
  			double pUp = (end.getY() + faultTolerance) - (cd * (end.getX() + faultTolerance));
      		double pDown = (end.getY() - faultTolerance) - (cd * (end.getX() - faultTolerance));
      		for(Input event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			if(event.getY() > yUp || event.getY() < yDown) 
      				return false;
      		}
      		
      		velocity[0] = Math.abs((end.getX() - start.getX())) / COEFFICIENT;
      		velocity[1] = (isRight)?VELOCITY:-VELOCITY;
      		velocity[2] = 0.0f;
      		
      		return true;
  		}
  		
		return false;
	}

}
