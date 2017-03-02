/*
 * MultiJabberServer.java
 * Author: Williams Wang
 * Last Edit: 1/24/17 by why
 * 
 * This class is a listener on port 9999. Every ssl socket sent to this port
 * will be assigned to a new thread called ServeOneJabber.
 */
package client;

import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

public class MultiJabberServer {

	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.keyStore", "sslclientkeys");
		System.setProperty("javax.net.ssl.keyStorePassword", "123456");
		System.setProperty("javax.net.ssl.trustStore", "sslclienttrust");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		SSLServerSocket sslserversocket = null;
		SSLSocket sslsocket = null;
		try {
			SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			sslserversocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(9998);
			while (true) {
				sslsocket = (SSLSocket) sslserversocket.accept();
				System.out.println("sslsocket:" + sslsocket);
				new ServeOneJabber(sslsocket);

			}
		} catch (Exception e) {
			try {
				sslsocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}