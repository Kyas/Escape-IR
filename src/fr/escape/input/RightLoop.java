package fr.escape.input;

import java.util.List;
import fr.umlv.zen2.MotionEvent;

public class RightLoop implements Gesture {
	//TODO remove with main
	//final static int[] array = new int[2];

	@Override
	public boolean accept(MotionEvent start, List<MotionEvent> events) {
		boolean valid = true;
		int faultTolerance = 50;
		MotionEvent maxX = start;
		for(MotionEvent event : events) {
			if(event.getX() > maxX.getX()) maxX = event;
		}
		int diameter = maxX.getX() - start.getX();
		int radius = diameter / 2;
		int cx = start.getX() + radius;
		int cy = start.getY();
		
		/*int tmp = start.getX() + diameter;
		System.out.println(start.getX()+","+start.getY() + " " + cx+","+cy + " " + tmp+","+start.getY());*/
		
		//TODO remove with main
		/*array[0] = diameter;
		array[1] = faultTolerance;*/
		
		int smallRad = radius - faultTolerance;
		int bigRad = radius + faultTolerance;
		for(MotionEvent event : events) {
			double dist = Math.sqrt(Math.pow(event.getX()-cx, 2)+Math.pow(event.getY()-cy, 2));
			if(dist < smallRad || dist > bigRad) valid = false;
		}
		return valid;
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub
		
	}
	
	//TODO main de test a virer quand pu necessaire
	/*public static void main(String[] args) {
		final int WIDTH = 800;
	    final int HEIGHT = 600;
	    final Gesture g = new RightLoop();
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
		                    	if(g.accept(start,events)) graphics.setPaint(new Color(0,255,0));
		                    	else graphics.setPaint(new Color(255,0,0));
		                    	graphics.drawOval(start.getX(),start.getY(),array[0]-array[1],array[0]-array[1]);
		                    	graphics.drawOval(start.getX(),start.getY(),array[0]+array[1],array[0]+array[1]);
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
	}*/

}
