/*
 * SocketClient.java
 * Author: Williams Wang
 * Last Edit: 3/3/17 by why
 * 
 * This class is the client which can send ssl socket to SocketListener. 
 * With both main() function and sendSocket() function, it can send a socket either from console
 * or inside programs. The return or output from the functions are the response from the socket.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SocketClient {
	
	/*
	 * SendSocket - send a socket to a target
	 * 
	 * @param server - target server
	 * @param port - target port
	 * @param message - message
	 * 
	 * @return - received responses
	 */
	public static String sendSocket(String server, int port, String message) {
		SSLSocket sslsocket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		StringBuilder str = new StringBuilder();
		try {
			SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			sslsocket = (SSLSocket) sslsocketfactory.createSocket(server, port);
			System.out.println("sslsocket=" + sslsocket);
			br = new BufferedReader(new InputStreamReader(sslsocket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(sslsocket.getOutputStream())));
			pw.println(message);
			pw.flush();
			str.append(br.readLine());
			str.append('\n');
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
		return str.toString();
	}

	public static void printUsage() {
		System.out.println("Usage:\n\tjava client.SocketClient [address] [port] [message]");
	}
	
	/*
	 * main - send a socket from system command
	 * 
	 * @param args[0] target address
	 * @param args[1] target port
	 * @param args[2] message
	 * 
	 * @print received responses
	 */
	public static void main(String[] args) {
		if(args.length != 3) {
			printUsage();
			return;
		}
		if(System.getProperty("javax.net.ssl.trustStore") == null || System.getProperty("javax.net.ssl.trustStorePassword") == null) {
			System.setProperty("javax.net.ssl.trustStore", "sslclienttrust");
			System.setProperty("javax.net.ssl.trustStorePassword", "123456");
		}
		System.out.println(sendSocket(args[0], Integer.parseInt(args[1]), args[2]));
	}
}