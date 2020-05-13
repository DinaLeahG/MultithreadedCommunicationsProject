package Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class controlCommunications {
	public static List<communication>slaves;
	public static List<communication>ableToWork;
	public static int num;
	public controlCommunications( ) {
		slaves= new ArrayList<communication>();
	}
	
	public void addCommunication(int id) {
		slaves.add(new communication(id));
	}
	
	//Choosing a slave based on a random int
	public static int  addWork(String request)  {
		ableToWork=new ArrayList<>();
		for(communication c:slaves) {
			if(c.getisAvail()) {
				ableToWork.add(c);
			}
		}
		
		//If no slaves are available then we wait for one to finish & keep repeating this method.
		if(ableToWork.isEmpty()&&slaves.isEmpty()) {
			addWork(request);
		}
		if(!ableToWork.isEmpty()) {
		Random rand = new Random();
		 num = rand.nextInt(ableToWork.size());
		ableToWork.get(num).setJobName(request);
		ableToWork.get(num).setIsAvail(false);
		return ableToWork.get(num).getId();}
		//A -1 means that all slaves are busy, and the client gets notified.
		return -1;
		
	}
	
	//This is assigning our available slave the work
	public void workInProgress() throws InterruptedException {
		ableToWork.get(num).inProgress();
		ableToWork.get(num).setIsAvail(true);
		
	}
	public boolean isDone(int id) {
		if(slaves.get(id).getisAvail()) {
			return true;
		}
		return false;
	}
}
