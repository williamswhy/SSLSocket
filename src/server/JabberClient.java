/*
 * JabberClient.java
 * Author: Williams Wang
 * Last Edit: 1/7/17 by why
 * 
 * This class is the client which can send ssl socket to MultiJabberServer. 
 * With both main() function and sendSocket() function, it can send a socket either from console
 * or inside programs. The return or output from the functions are the reponse from the socket.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class JabberClient {
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "sslservertrust");
		System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = null;
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(args[0], Integer.parseInt(args[1]));
			System.out.println("sslsocket=" + sslsocket);
			br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));
			pw.println(args[2]);
			pw.flush();
			for (int i = 0; i < 10; i++) {
				pw.println(i);
				pw.flush();
				str = br.readLine();
				System.out.println(str);
			}
			pw.println("END");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				sslsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String sendSocket(String server, int port, String message) {
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		String str = "";
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(server, port);
			System.out.println("sslsocket=" + sslsocket);
			br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));
			pw.println(message);
			pw.flush();
			str += (br.readLine()+ "\n");
			for (int i = 0; i < 10; i++) {
				pw.println("howdy " + i);
				pw.flush();
				str += (br.readLine()+ "\n");
			}
			System.out.println(str);
			pw.println("END");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				sslsocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
}