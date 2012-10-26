package fr.escape.input;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;

import fr.escape.app.Input;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.ApplicationRenderCode;
import fr.umlv.zen2.MotionEvent;

public class GestureMain {

	public static void main(String[] args) {
		final int WIDTH = 800;
		final int HEIGHT = 600;
		final LinkedList<Gesture> lGesture = new LinkedList<Gesture>();
		lGesture.add(new Drift());
		lGesture.add(new LeftLoop());
		lGesture.add(new RightLoop());
		lGesture.add(new BackOff());
		final LinkedList<Input> events = new LinkedList<Input>();
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
							System.out.println(event.getKind().name());
							graphics.setFont(font);
							Input e = new Input(event);
							switch(e.getKind().name()) {
								case "ACTION_DOWN" : 
									events.add(e);
									break;
								case "ACTION_UP" : 
									Iterator<Input> it = events.iterator();
									if(it.hasNext()) {
										Input start = it.next(); it.remove();
										for(Gesture g : lGesture) {
											if(g.accept(start,events,e)) {
												graphics.setPaint(new Color(0,255,0));
												System.out.println(g.getClass().toString());
												break;
											}
											else {
												graphics.setPaint(new Color(255,0,0));
											}
										}
										
										Input lastEvent = start;
										for(Input event : events) {
											graphics.drawLine(lastEvent.getX(),lastEvent.getY(),event.getX(),event.getY());
											lastEvent = event;
										}
										events.clear();
									}
									break;
								case "ACTION_MOVE" :
									events.add(e);
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
