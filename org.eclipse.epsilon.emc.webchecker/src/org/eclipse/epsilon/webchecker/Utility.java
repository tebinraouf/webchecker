package org.eclipse.epsilon.webchecker;

import java.net.URI;
import java.net.URL;


public class Utility {
	public static boolean isValidURL(String uri) {
		try
	    {
	        URL url = new URL(uri);
	        url.toURI();
	        return true;
	    } catch (Exception exception)
	    {
	        return false;
	    }
	}
	public static boolean isValidURI(String uri) {
		try {
			URI.create(uri);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
}
