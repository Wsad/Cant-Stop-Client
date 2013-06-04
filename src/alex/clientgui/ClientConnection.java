package alex.clientgui;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientConnection {
	private final int port;
	private final String host;
	private final Socket socket;
	private final BufferedReader serverIn;
    private final BufferedReader userIn;
    private final PrintWriter serverOut;
	
	public ClientConnection(String hostIn, int portIn){
		int tempPort = portIn;
		String tempHost = hostIn;
		Socket tempSocket = null;
		boolean connected = false;
		Scanner scan = new Scanner(System.in);
		
		while (!connected){
			try {
		         tempSocket = new Socket(tempHost, tempPort);
		         connected = true;
		         
		         
		    }
		    catch (UnknownHostException e) {
		         System.err.println("Unknown host: " + tempHost);
		         System.err.print("Enter new port: ");
		         tempPort = scan.nextInt();
		         scan.next();
		         
		    }
		    catch (IOException e) {
		         System.err.println("Unable to get I/O connection to: "
		                           + tempHost + " on port: " + tempPort);
		         System.err.print("Enter new host: ");
		         tempHost = scan.nextLine();
		         System.err.print("Enter new port: ");
		         tempPort = scan.nextInt();
		         scan.next();
		    }
		}
		host = tempHost;
		port = tempPort;
		socket = tempSocket;
		
		BufferedReader tempServerIn = null;
	    BufferedReader tempUserIn = null;
	    PrintWriter tempServerOut = null;
		
	    try {
	    	tempServerIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    	tempUserIn = new BufferedReader(new InputStreamReader(System.in));
	    	tempServerOut = new PrintWriter(socket.getOutputStream(), true /* autoFlush */);
	    }
	    catch (IOException e){
	    	System.err.println("Unable to create Reader or Writer: " + e.getMessage());
	    }
	    serverIn = tempServerIn;
	    userIn = tempUserIn;
	    serverOut = tempServerOut;
	}
	
	public String read(){
		try {
			return serverIn.readLine();
		}
		catch (IOException e){
			System.err.println("Unable to read from server: " + e.getMessage());
			return "";
		}
	}
	
	public void print(String out){
		serverOut.println(out);
	}
	public void close(){
		try{	
			serverOut.close();
			serverIn.close();
			userIn.close();
			socket.close();
		}
		catch (IOException e){
			System.err.println("Unable to close connection: " + e.getMessage());
		}
	}
	
}
