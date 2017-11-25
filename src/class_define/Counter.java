package class_define;

import DB_service.DB_service;

public class Counter implements Observer {
	public int cid;
	@Override
	public void update(int id, int op) {
		System.out.println("in counter.update");
		// TODO Auto-generated method stub
	    DB_service db = new DB_service();
	    db.Counter(id);
		//System.out.println(String.format("[%d], [%d]", id, op));
	}
	public Counter(int cid) {
		this.cid = cid;
	}
}
