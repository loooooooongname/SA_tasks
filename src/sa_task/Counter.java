package sa_task;

import DB_service.DB_service;

public class Counter implements Observer {
	public int count;
	public int tid;
	
	@Override
	public void update(String message) {
		// times of view
		if (message == "1") {
			DB_service db = new DB_service();
			db.Counter(tid);
			count++;
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public Counter(int tid) {
		this.tid = tid;
		count = 0;
	}
	
}
