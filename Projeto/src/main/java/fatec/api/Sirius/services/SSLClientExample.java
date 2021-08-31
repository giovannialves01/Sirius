package fatec.api.Sirius.services;

import java.io.OutputStream;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLClientExample {
	final static String pathToStores = "/tmp/ssl/client";
	final static String trustStoreFile = "truststore";
	final static String passwrd = "1234";

	final static String theServerName = "localhost";
	final static int theServerPort = 1234;

	static boolean debug = false;

	void doClientSide() throws Exception {
		SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
		SSLSocket sslSocket = (SSLSocket) sslsf.createSocket(theServerName, 12345);
		OutputStream sslOS=sslSocket.getOutputStream();
		sslOS.write("Hello World".getBytes());
		sslOS.flush();
		sslSocket.close();
	}//
}
