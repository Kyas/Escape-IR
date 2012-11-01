package fr.escape.input;

import java.util.List;

import fr.escape.app.Input;

public class BackOff implements Gesture {

	@Override
	public double accept(Input start, List<Input> events, Input end) {
		if(start.getY() >= end.getY()) return 0;
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
      			if(start.getX() <= end.getX() && (event.getY() < yUp || event.getY() > yDown)) return 0;
      			else if(start.getX() > end.getX() && (event.getY() > yUp || event.getY() < yDown)) return 0;
      		}
  		} else {
  			return 0;
  		}
		return cd;
	}
}
