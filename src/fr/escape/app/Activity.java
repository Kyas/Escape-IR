/*****************************************************************************
 * 
 * Copyright 2012 See AUTHORS file.
 * 
 * This file is part of Escape-IR.
 * 
 * Escape-IR is free software: you can redistribute it and/or modify
 * it under the terms of the zlib license. See the COPYING file.
 * 
 *****************************************************************************/

package fr.escape.app;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

import fr.escape.input.EventListener;
import fr.escape.resources.Resources;
import fr.umlv.zen2.Application;
import fr.umlv.zen2.ApplicationCode;
import fr.umlv.zen2.ApplicationContext;
import fr.umlv.zen2.MotionEvent;
import fr.umlv.zen2.MotionEvent.Kind;

// TODO Comment
public final class Activity {

	public static final int LOG_NONE = 0;
	public static final int LOG_DEBUG = 3;
	public static final int LOG_INFO = 2;
	public static final int LOG_ERROR = 1;
	
	private final EventListener listener;
	private final Graphics graphics;
	private final Queue<Runnable> runnables = new LinkedList<Runnable>();
	private final String title;
	private int logLevel;
	
	public Activity(Game game) {
		this(game, new Configuration());
	}
	
	public Activity(Game game, Configuration configuration) {
		
		graphics = new Graphics(game, configuration);
		logLevel = LOG_INFO;
		title = configuration.title;
		listener = game;
		
		Foundation.activity = this;
		Foundation.graphics = graphics;
		Foundation.resources = new Resources();

		//TODO Don't do this here
		Foundation.resources.load();
		
		game.create();
		initialize();
	}
	
	/**
	 * Initialize and Run the Game with Graphics Engine and Activity Core
	 */
	private void initialize () {
		
		// TODO May need to remove comment for Run Exec
		
		Application.run(this.title, graphics.getWidth(), graphics.getHeight(), new ApplicationCode() {
			
			@Override
			public void run(ApplicationContext context) {
				
				try {
					
					log("Activity", "Application started");
					Input lastEvent = null;
					for(;;) {
						
						
						int executionTime = 0;
						
						MotionEvent mEvent = context.pollMotion();
						if(mEvent != null) {
							Input event = new Input(mEvent);
							event(event, lastEvent);
							lastEvent = event;
						}
						
						/**
						 * May need to implements QoS in Runnable execution.
						 */
						synchronized(getRunnables()) {
							
							if(!getRunnables().isEmpty()) {
								
								long start = System.currentTimeMillis();
								Runnable next;
								
								while((next = getRunnables().poll()) != null) {
									try {
										next.run();
									} catch(Throwable t) {
										error("Activity - Runnable", "Error while executing a Runnable", t);
									}
								}
								
								executionTime = (int) (System.currentTimeMillis() - start);
								debug("Activity - Runnable", "Runnable(s) executed in "+executionTime+" ms");
							}
						}
						
						try {
							
							int sleep = getGraphics().getNextWakeUp() - executionTime;
							if(sleep > 0) {
								Thread.sleep(sleep);
							}
							
						} catch(InterruptedException e) {
							Thread.currentThread().interrupt();
						}
						
						getGraphics().render(context);
						
					}
					
				} finally {
					debug("Activity", "Application closed");			
				}
				
			}
			
		});
		
	}

	/** 
	 * Logs a message to the console.
	 */
	public void log(String tag, String message) {
		if(logLevel >= LOG_INFO) {
			System.out.println(tag + ": " + message);
		}
	}

	/**
	 * Logs a message to the console.
	 */
	public void log(String tag, String message, Exception exception) {
		if(logLevel >= LOG_INFO) {
			log(tag, message);
			exception.printStackTrace(System.out);
		}
	}

	/** 
	 * Logs an error message to the console.
	 */
	public void error(String tag, String message) {
		if (logLevel >= LOG_ERROR) {
			System.err.println(tag + ": " + message);
		}
	}

	/** 
	 * Logs an error message to the console.
	 */
	public void error(String tag, String message, Throwable exception) {
		if(logLevel >= LOG_ERROR) {
			error(tag, message);
			exception.printStackTrace(System.err);
		}
	}

	/** 
	 * Logs a debug message to the console.
	 */
	public void debug(String tag, String message) {
		if(logLevel >= LOG_DEBUG) {
			System.out.println(tag + ": " + message);
		}
	}

	/** 
	 * Logs a debug message to the console.
	 */
	public void debug(String tag, String message, Throwable exception) {
		if(logLevel >= LOG_DEBUG) {
			debug(tag, message);
			exception.printStackTrace(System.out);
		}
	}

	/** 
	 * Sets the log level. 
	 * 
	 * {@link #LOG_NONE} will mute all log output. 
	 * {@link #LOG_ERROR} will only let error messages through.
	 * {@link #LOG_INFO} will let all non-debug messages through, and {@link #LOG_DEBUG} will let all messages through.
	 * 
	 * @param logLevel {@link #LOG_NONE}, {@link #LOG_ERROR}, {@link #LOG_INFO}, {@link #LOG_DEBUG}. 
	 */
	public void setLogLevel(int logLevel) {
		switch(logLevel) {
			case LOG_NONE: case LOG_ERROR: case LOG_INFO: case LOG_DEBUG: {
				this.logLevel = logLevel;
				break;
			}
			default: {
				throw new IllegalArgumentException("Unknown Log Level");
			}
		}
		
	}
	
	/**
	 * <p>
	 * Return the Graphics Engine.
	 * 
	 * <p>
	 * Wrapper for Anonymous Class in {@link Activity#initialize()}.
	 * 
	 * @return Graphics Engine 
	 */
	Graphics getGraphics() {
		return graphics;
	}
	
	/**
	 * <p>
	 * Return Runnable Queue.
	 * 
	 * <p>
	 * Wrapper for Anonymous Class in {@link Activity#initialize()}.
	 * 
	 * @return Runnable Queue
	 */
	Queue<Runnable> getRunnables() {
		return runnables;
	}
	
	/**
	 * <p>
	 * Return Runnable Queue.
	 * 
	 * <p>
	 * Wrapper for Anonymous Class in {@link Activity#initialize()}.
	 * 
	 * @return Event Listener
	 */
	EventListener getEventListener() {
		return listener;
	}
	
	/**
	 * Push a Runnable into Execution Queue for next loop.
	 * 
	 * @param runnable Runnable to execute.
	 */
	public void post(Runnable runnable) {
		synchronized (getRunnables()) {
			getRunnables().add(runnable);
		}
	}
	
	/**
	 * Push an Event to EventListener
	 */
	public void event(final Input event, final Input lastEvent) {

		Objects.requireNonNull(event);
		
		switch(event.getKind()) {
			case ACTION_DOWN: {
				break;
			}
			case ACTION_MOVE: {
				
				post(new Runnable() {
					
					@Override
					public void run() {
						getEventListener().move(lastEvent);
					}
					
				});
				
				break;
			}
			case ACTION_UP: {
				if(lastEvent.getKind() == Kind.ACTION_DOWN) {
					
					post(new Runnable() {
						
						@Override
						public void run() {
							getEventListener().touch(lastEvent);
						}
						
					});
					
				} else {
					
					post(new Runnable() {
						
						@Override
						public void run() {
							getEventListener().move(event);
						}
						
					});
					
				}
				
				break;
			}
			default: {
				throw new AssertionError("Unknown Event");
			}
		}
	}
	
}
