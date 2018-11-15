package com.dhb.common.exception;

public class SerializeException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SerializeException(String errorMsg,Throwable e){
		super(errorMsg,e);
	}
	
	public SerializeException(String errorMsg){
		super(errorMsg);
	}
	
	public SerializeException(Throwable e){
		super(e);
	}
}
