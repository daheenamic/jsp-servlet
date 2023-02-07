package com.dahee.main;

public interface ServiceInterface {
	public void setDao(Object dao);
	public Object service(Object obj) throws Exception;
}