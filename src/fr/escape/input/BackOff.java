package fr.escape.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.escape.app.Foundation;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

public class BackOff implements Gesture {

	@Override
	public boolean accept(MotionEvent start, List<MotionEvent> events, MotionEvent end) {
		if(start.getY() > end.getY()) return false;
		int height = 600;//Foundation.graphics.getHeight();
		int coeff = 100;
		int faultTolerence = 20;
		boolean valid = false;
		
  		double downCoeffDir = ((start.getY() + height) - start.getY())/((start.getX() + coeff) - start.getX());
  		double upCoeffDir = ((start.getY() + height) - start.getY())/((start.getX() - coeff) - start.getX());
		double cd = (double)(end.getY()-start.getY())/(end.getX()-start.getX());
  		
  		if(cd < upCoeffDir || cd > downCoeffDir) {
  			valid = true;
  			double pUp = (end.getY() + faultTolerence) - (cd * (end.getX() + faultTolerence));
      		double pDown = (end.getY() - faultTolerence) - (cd * (end.getX() - faultTolerence));
      		for(MotionEvent event : events) {
      			double yUp = cd * event.getX() + pUp;
      			double yDown = cd * event.getX() + pDown;
      			if(yUp < event.getY() || yDown > event.getY()) {
      				valid = false;
      			}
      		}
  		}
		return valid;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	//TODO main de test a virer quand pu necessaire
	public static void main(String[] args) {
		final int WIDTH = 800;
	    final int HEIGHT = 600;
	    final Gesture g = new BackOff();
	    final LinkedList<MotionEvent> events = new LinkedList<MotionEvent>();
	    Application.run("Detect motions", WIDTH, HEIGHT, new ApplicationCode() {
	        @Override
	        public void run(final ApplicationContext context) {
	        	final Font font = new Font("arial", Font.BOLD, 30);
	        	for(;;) {
	        		final MotionEvent event = context.waitMotion();
	                if (event == null) {
	                  return;
	                }
	                context.render(new ApplicationRenderCode() {
	                  @Override
	                  public void render(Graphics2D graphics) {
	                    graphics.setFont(font);
	                    switch(event.getKind().name()) {
		                    case "ACTION_DOWN" : 
		                    	events.add(event);
		                    	break;
		                    case "ACTION_UP" : 
		                    	Iterator<MotionEvent> it = events.iterator();
		                    	if(it.hasNext()) {
			                    	MotionEvent start = it.next();
			                    	it.remove();
			                    	if(g.accept(start,events,event)) graphics.setPaint(new Color(0,255,0));
			                    	else graphics.setPaint(new Color(255,0,0));
			                    	graphics.drawLine(start.getX(),start.getY(),event.getX(),event.getY());
			                    	events.clear();
		                    	}
		                    	break;
		                    case "ACTION_MOVE" :
		                    	events.add(event);
		                    	break;
		                    default :
		                  		break;
	                    }
	                  }
	                });
	              }
	            }
	    });
	}

}
