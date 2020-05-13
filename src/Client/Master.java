package Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class Master {	
	private static controlCommunications control =getCommunication.getCommunication();
	public static void main(String[] args) throws IOException {
		// Hardcode port number if necessary
		args = new String[] {"30121"};
		String [ ]args2 = new String[ ] {"30122"}; //The slave port
		
		if (args.length != 1)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		if (args2.length != 1)
		{
			System.err.println("Usage: java EchoServer <port number>");
			System.exit(1);
		}
		
		int portNumber = Integer.parseInt(args[0]);
		int portNumber2= Integer.parseInt(args2[0]);
		final int THREADS = 3;		
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber);
			ServerSocket slaveSocket = new ServerSocket(portNumber2); ) {
			ArrayList<Thread> threads = new ArrayList<Thread>();
			ArrayList<Thread> threads2 = new ArrayList<Thread>();
		//Connecting to the different threaded servers
			if(serverSocket.isBound() && slaveSocket.isBound()) {
			for (int i = 0; i < THREADS; i++) {
				threads.add(new Thread(new ServerThread(serverSocket, i,control)));
			    threads2.add(new Thread(new ServerThread2(slaveSocket, i,control
			    		)));}
			for (Thread t : threads) {
				t.start();
			
			}
			for (Thread t : threads2) {
				t.start();
			
			}
			for (Thread t: threads)
			{
				try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (Thread t: threads2)
			{
				try {
					t.join();
				} 
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		}
		
			
		catch (IOException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + "30122"  + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}

	}
