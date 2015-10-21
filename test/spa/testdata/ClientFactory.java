package spa.testdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.nojsonb.Client;

public class ClientFactory {

	private List<String> emails = new ArrayList<>();
	
	private String[] words = new String[]{"ss", "aa", "b", "ll", "yy", "ee", "ww", "tt", "g", "n", "ki", "v", "q", "i", "p", "es", "km"};

	public ClientFactory() {
		
		
		for(int emailsAmount = 0; emailsAmount < words.length; emailsAmount++) {
			
			StringBuilder newEmail = new StringBuilder();
			for(int emailLength = 0; emailLength < words.length; emailLength++) {
				
				int index = new Random().nextInt(words.length);
				newEmail.append(words[index]);
			}
			emails.add(newEmail.toString());
		}
		
	}

	public Client getClient() {

		Client client = new Client();
		client.setAddress("client address");
		client.setAge((byte) 12);
		client.setEmail(emails.get(new Random().nextInt(emails.size())) + "client@email.com");
		client.setName("Client name");
		client.setSecondName("Client second name");
		return client;
	}
}
