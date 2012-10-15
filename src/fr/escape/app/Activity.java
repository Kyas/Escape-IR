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

public final class Activity {

	public static final int LOG_NONE = 0;
	public static final int LOG_DEBUG = 3;
	public static final int LOG_INFO = 2;
	public static final int LOG_ERROR = 1;
	
	private int logLevel;
	
	public Activity() {
		this.logLevel = LOG_INFO;
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
	
}
