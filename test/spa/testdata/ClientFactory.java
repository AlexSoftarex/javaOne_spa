package spa.testdata;

import model.nojsonb.Client;

public class ClientFactory {
	
	public static Client getClient() {
		
		Client client = new Client();
		client.setAddress("client address");
		client.setAge((byte) 12);
		client.setEmail("client@email.com");
		client.setName("Client name");
		client.setSecondName("Client second name");
		return client;	
	}
}
