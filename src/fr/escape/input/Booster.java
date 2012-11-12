package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

// TODO Comment
public final class Booster implements Gesture {
	private static int DEFAULT_FAULT_TOLERANCE = 25;
	private final static int COEFFICIENT = 3;
	private static double COEFFDIR = 4.0;
	private static float VELOCITY = 2.0f;

	@Override
	public boolean accept(Input start, List<Input> events, Input end,float[] velocity) {
		boolean isFront = start.getY() > end.getY();
		int faultTolerence = DEFAULT_FAULT_TOLERANCE;
  		
  		double div = end.getX()-start.getX();
  		double cd = (end.getY()-start.getY())/((div!=0)?div:1);
  		
  		if(cd > COEFFDIR || cd < -COEFFDIR) {
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(Input event : events) {
      			double yUp = cd * event.getX() + Math.max(pUp, pDown);
      			double yDown = cd * event.getX() + Math.min(pUp, pDown);
      			
      			if(event.getY() > yUp || event.getY() < yDown)
      				return false;
      		}
      		
      		velocity[0] = Math.abs((end.getY() - start.getY()) / COEFFICIENT);
      		velocity[1] = 0.0f;
      		velocity[2] = (isFront)?-VELOCITY:VELOCITY;
      		
      		return true;
  		}
  		
		return false;
	}
}
