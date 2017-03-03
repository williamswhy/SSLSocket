/*
 * ServeOneJabber.java
 * Author: Williams Wang
 * Last Edit:1/16/17 by why
 * 
 * This class is reponsible for verifying the integrity of the request and parse it into a map for further processing.
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
				str = br.readLine();
				if (str.equals("END")) {
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();
					break;
				}
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