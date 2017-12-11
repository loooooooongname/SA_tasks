package DB_service;

import java.util.UUID;

public class helper {
	public static int getID() {
		int id = UUID.randomUUID().hashCode(); 
		while (id >= 0 && id < 1500000) {
			id = UUID.randomUUID().hashCode();
		}
		return id;
	}
}
