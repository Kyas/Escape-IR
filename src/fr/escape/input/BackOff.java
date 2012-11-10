package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

// TODO Comment
public final class BackOff implements Gesture {

	@Override
	public boolean accept(Input start, List<Input> events, Input end,float[] velocity) {
		if(start.getY() >= end.getY()) return false;
		int height = 600;//Foundation.graphics.getHeight();
		int coeff = 150;
		int faultTolerence = 20;
		
  		double downCoeffDir = ((start.getY() + height)-start.getY())/((start.getX() + coeff)-start.getX());
  		double upCoeffDir = ((start.getY() + height)-start.getY())/((start.getX() - coeff)-start.getX());
  		
  		int div = end.getX()-start.getX();
  		double cd = (end.getY()-start.getY())/((div!=0)?div:1);
  		
  		if(cd < upCoeffDir || cd > downCoeffDir) {
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(Input event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			if(start.getX() <= end.getX() && (event.getY() < yUp || event.getY() > yDown)) return false;
      			else if(start.getX() > end.getX() && (event.getY() > yUp || event.getY() < yDown)) return false;
      		}
      		
      		velocity[0] = (end.getY() - start.getY()) / 10;
      		velocity[1] = 0.0f;
      		velocity[2] = 1.5f;
      		
      		return true;
  		}
  		
		return false;
	}
}
