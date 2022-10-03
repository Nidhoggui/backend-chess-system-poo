package com.backendchesssystempoo.facade.exceptions;

public class RunningGameException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RunningGameException(String msg)
	{
		super(msg);
	}
}
