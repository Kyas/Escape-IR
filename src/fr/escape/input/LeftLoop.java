package fr.escape.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

public class LeftLoop implements Gesture {
	//TODO remove with main
	//final static int[] array = new int[2];

	@Override
	public boolean accept(MotionEvent start, List<MotionEvent> events,MotionEvent end) {
		boolean valid = true;
		int faultTolerance = 90;
		if((start.getX()+(start.getX()*faultTolerance/100)) < end.getX() || (start.getX()-(start.getX()*faultTolerance/100)) > end.getX()) return false;
		MotionEvent minX = start;
		for(MotionEvent event : events) {
			if(event.getX() < minX.getX()) minX = event;
		}
		int diameter = start.getX() - minX.getX();
		int radius = diameter / 2;
		int cx = start.getX() - radius;
		int cy = start.getY();
		
		int smallRad = radius - (radius*faultTolerance/100);
		int bigRad = radius + (radius*faultTolerance/100);
		for(MotionEvent event : events) {
			double dist = Math.sqrt(Math.pow(event.getX()-cx, 2)+Math.pow(event.getY()-cy, 2));
			if(dist < smallRad || dist > bigRad) valid = false;
		}
		System.out.println(valid);
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
	    final Gesture g = new LeftLoop();
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
		                    	MotionEvent lastEvent = start;
		                    	while(it.hasNext()) {
		                    		MotionEvent currentEvent = it.next();
		                    		graphics.setPaint(new Color(0,0,255));
		                    		graphics.drawLine(lastEvent.getX(),lastEvent.getY(),currentEvent.getX(),currentEvent.getY());
		                    		lastEvent = currentEvent;
		                    	}
		                    	if(g.accept(start,events,event)) graphics.setPaint(new Color(0,255,0));
		                    	else graphics.setPaint(new Color(255,0,0));
		                    	//graphics.drawOval(start.getX(),start.getY(),array[0]-array[1],array[0]-array[1]);
		                    	//graphics.drawOval(start.getX(),start.getY(),array[0]+array[1],array[0]+array[1]);
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
