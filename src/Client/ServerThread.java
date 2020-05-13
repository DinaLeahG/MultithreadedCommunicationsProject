package Client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThread implements Runnable {
	// A reference to the server socket is passed in, all threads share it
	private ServerSocket serverSocket = null;
	private static controlCommunications control;
	int id; 
    ServerSocket slaveSocket;
	public ServerThread(ServerSocket s, int id,controlCommunications control)
	{
		this.control=control;
		serverSocket = s;
		this.id = id;
	}
	
	@Override
	public void run() {
		
		
		// This thread accepts its own client socket from the shared server socket
		try (Socket clientSocket = serverSocket.accept();
				PrintWriter responseWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader requestReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));) {
			String requestString;
		while ((requestString = requestReader.readLine()) != null) {
				System.out.println(requestString + " received by client " + id);
				int slaveNum=control.addWork(requestString);
				
				if(slaveNum==-1) {
					responseWriter.println("All slaves are busy");}
				else {
				System.out.println("Sending "+requestString+" To Slave "+slaveNum);
				responseWriter.println(requestString + " Is being handled by Slave: "+slaveNum);
				
				while(!control.isDone(slaveNum)) {
					responseWriter.println(requestString+" Slave: "+slaveNum+" Is still in progress");
					control.workInProgress();
				}
				System.out.println("Sending client "+id+" Job Complete");
				responseWriter.println(requestString+" Slave: "+slaveNum+" Is Finished");
				}
				responseWriter.println("Done");
				
	

			
				
			}
		} catch (IOException | InterruptedException e) {
			System.out.println(
					"Exception caught when trying to listen on port " + serverSocket.getLocalPort() + " or listening for a connection");
			System.out.println(e.getMessage());
		}
	}
}
