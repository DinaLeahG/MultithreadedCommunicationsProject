package Client;

public class communication {
	public  boolean isAvailable;
	public int id;
	public static String requestString;
	
	public communication(int id) {
		this.isAvailable=true;
		this.id=id;
		
	}
	
	public void setJobName(String request) {
		this.requestString=request;
	}
	public boolean inProgress() throws InterruptedException {
	this.isAvailable=false;
	//Working for 30 seconds on a job
		Thread.sleep(10000);
		this.isAvailable=true;
		return true;
	
		
	}

	public  int getId() {
		return this.id;
	}
	public boolean getisAvail() {
		return isAvailable;
	}
	public void setIsAvail(boolean val) {
		this.isAvailable=val;
	}
	

}
