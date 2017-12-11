package DB_service;

import java.util.UUID;

public class helper {
	static int tot = 0;
	public static int getID() {
		return UUID.randomUUID().hashCode();
	}
}
