package Client;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.ServerSocket;
	import java.net.Socket;


	public class ServerThread2 implements Runnable {
		private static controlCommunications control;
		
		// A reference to the server socket is passed in, all threads share it
		private ServerSocket serverSocket = null;
		int id; 
		
		//Recieving the same control object
		public ServerThread2(ServerSocket s, int id,controlCommunications control)
		{
			this.control=control;
			serverSocket = s;
			//As a slave thread it gets added to our list
			control.addCommunication(id);
			this.id = id;
		}
		
		@Override
		public void run() {
			
			// This thread accepts its own client socket from the shared server socket
			try (Socket slaveSocket = serverSocket.accept();
					PrintWriter responseWriter = new PrintWriter(slaveSocket.getOutputStream(), true);
					BufferedReader requestReader = new BufferedReader(new InputStreamReader(slaveSocket.getInputStream()));
					) {
				String requestString;
				//Communicating with the Master about which slave is workings
			while ((requestString = requestReader.readLine()) != null) {
					responseWriter.println("Connected Slave "+id);
					while(!control.isDone(id)) {
						responseWriter.println("Slave:"+id+" Is working");
					}
					responseWriter.println(id + " Slave: Is Finished");
					responseWriter.println("Done");
					
				}
			} catch (IOException e) {
				System.out.println(
						"Exception caught when trying to listen on port " + serverSocket.getLocalPort() + " or listening for a connection");
				System.out.println(e.getMessage());
			}
		}
	}


