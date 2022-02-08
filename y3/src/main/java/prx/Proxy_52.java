package prx;

import java.net.*;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import java.io.*;

public class Proxy_52 {
	public static void main(String args[]) throws Exception {
		System.out.println("--- server started ---");
		ServerSocket serso = new ServerSocket(8080);
		Socket socket1 = null;
		while (true) {
			String s = "qq", ss = "";
			socket1 = serso.accept();
			DataInputStream get_in = new DataInputStream(socket1.getInputStream());
			BufferedReader br = new BufferedReader(new InputStreamReader(get_in));
			while (!s.equals("")) {
				s = br.readLine();
				ss = ss + s + "\r\n";
				if (s.indexOf("Content-Length: ") == 0) {
					String si = s.substring(16);
					System.err.println("-->   " + si);
					int i = Integer.parseInt(si);
					System.err.println(i);

					while (i-- > -14) {
						System.out.print((char) br.read());
					}
				}
			}
			//
			ss = ss.replace("Accept-Encoding: gzip,deflate\r\n", "");
			//
			System.out.println(ss);
			int i = ss.indexOf("Host: ");
			System.out.println("i =" + i);
			String host = ss.substring(i);
			host = host.substring(6, host.indexOf("\r\n"));
			int j = host.indexOf(":");
			String port = host.substring(j + 1);
			host = host.substring(0, j);
			System.out.println("Host = " + host);
			int iport = Integer.parseInt(port);
			System.out.println("port = " + iport);
			System.out.println("---");
			System.out.println("******************\r\n" + ss + "\r\n******************");

			try {
				SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
				SSLSocket socket =
						// (SSLSocket)factory.createSocket("www.verisign.com", 443);
						(SSLSocket) factory.createSocket(host, 443);

				/*
				 * send http request
				 *
				 * Before any application data is sent or received, the SSL socket will do SSL
				 * handshaking first to set up the security attributes.
				 *
				 * SSL handshaking can be initiated by either flushing data down the pipe, or by
				 * starting the handshaking by hand.
				 *
				 * Handshaking is started manually in this example because PrintWriter catches
				 * all IOExceptions (including SSLExceptions), sets an internal error flag, and
				 * then returns without rethrowing the exception.
				 *
				 * Unfortunately, this means any error messages are lost, which caused lots of
				 * confusion for others using this code. The only way to tell there was an error
				 * is to call PrintWriter.checkError().
				 */
				socket.startHandshake();

				PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));

				out.println("GET / HTTP/1.0");
				out.println();
				out.flush();

				/*
				 * Make sure there were no surprises
				 */
				if (out.checkError())
					System.out.println("SSLSocketClient:  java.io.PrintWriter error");

				/* read response */
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
String sss="";
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					sss=sss + inputLine;
				
					System.out.println(sss);

				in.close();
				out.close();
				socket.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("\r\n============ FINISH ===============");
		}
	}
}