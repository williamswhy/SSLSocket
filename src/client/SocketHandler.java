/*
 * SocketHandler.java
 * Author: Williams Wang
 * Last Edit:3/3/17 by why
 * 
 * A Thread to deal with socket messages.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.net.ssl.SSLSocket;

public class SocketHandler extends Thread {
	private SSLSocket socket = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;

	/*
	 * Constructor - initialize variables
	 * 
	 * @param s - an ssl socket created by SocketListener
	 */
	public SocketHandler(SSLSocket s) {
		socket = s;
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			String str;
			try {
				//read a line
				str = br.readLine();
				//if it is "END", disconnect
				if (str.equals("END")) {
					pw.println("END");
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
					break;
				}
				//else, send "Message Received"
				pw.println("Message Received");
				pw.flush();
				System.out.println("Client Socket Message:" + str);
			} catch (Exception e) {
				try {
					br.close();
					pw.close();
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}