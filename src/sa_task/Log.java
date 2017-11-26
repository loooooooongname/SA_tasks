package sa_task;

import java.util.Date;

import DB_service.DB_service;

public class Log implements Observer {
	public int tid;
	
	
	@Override
	public void update(String message) {
		// TODO Auto-generated method stub
		Date date = new Date();
		int tmpid = date.hashCode();
		
		if (message == "2") {
			// new
			System.out.println(String.format("new twitter [%d] at [%s]", tid, new Date().toString()));
			DB_service db = new DB_service();
			db.AddLog(tid, 0);
		}
		// modify
		else if (message == "3") {
			System.out.println(String.format("modify twitter [%d] at [%s]", tid, new Date().toString()));
			DB_service db = new DB_service();
			db.AddLog(tid, 1);
		}
		// del
		else if (message == "4") {
			System.out.println(String.format("delete twitter [%d] at [%s]", tid, new Date().toString()));
			DB_service db = new DB_service();
			db.AddLog(tid, 2);
		}
		// nop
		else {
		}
	}
	
	public Log(int tid) {
		this.tid = tid;
	}
}
