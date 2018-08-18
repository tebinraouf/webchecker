package org.eclipse.epsilon.webchecker;

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
}
