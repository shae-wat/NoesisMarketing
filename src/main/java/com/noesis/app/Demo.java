package com.noesis.app;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.http.HttpException;

public class Demo {

	public static void main (String args[])
	{
		try {
			Utils.httpGetFromUrl("test");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
