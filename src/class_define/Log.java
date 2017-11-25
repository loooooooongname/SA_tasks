package class_define;

import DB_service.DB_service;

public class Log implements Observer {
	public int lid;
	@Override
	public void update(int id, int op) {
		System.out.println("In log.update");
		// TODO Auto-generated method stub
		// 1 : new
		// 2 : del
		// 3 : upd
		// else : nop
		DB_service db = new DB_service();
		db.AddLog(id, op);
		//System.out.println(String.format("[%d], [%d]", id, op));
	}
	public Log(int lid) {
		this.lid = lid;
	}
}
