package Client;

public class getCommunication {
	public static controlCommunications controler = new controlCommunications();
// Controls the communication Object
	public static controlCommunications getCommunication() {
		return controler;
	}

	public static void addCommunication(int id) {
		controler.addCommunication(id);
	}

}
