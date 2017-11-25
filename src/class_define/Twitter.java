package class_define;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DB_service.DB_service;


public class Twitter implements Subject {
	
	private String content;
	private Date date;
	private long tid;
	private List<Observer> counters;
	private List<Observer> logs;
	
	@Override
	public void attach(Observer counter, Observer log) {
		// TODO Auto-generated method stub
		this.counters.add(counter);
		this.logs.add(log);
	}

	@Override
	public void detach(Observer counter, Observer log) {
		// TODO Auto-generated method stub
		this.counters.remove(counter);
		this.logs.remove(log);
	}
	
	
//	@Override
//	public void notify(String message) {
//		// TODO Auto-generated method stub
//		for (Observer observer : this.counters) {
//			observer.update();
//		}
//		for (Observer observer : this.logs) {
//			observer.update();
//		}
//	}
	
	
	public Twitter(String str) {
		this.counters = new ArrayList<Observer>();
		this.logs = new ArrayList<Observer>();
		this.content = str;
		this.date = new Date();
		this.tid = this.date.hashCode();
	}
	
	public Twitter() {
		this.counters = new ArrayList<Observer>();
		this.logs = new ArrayList<Observer>();
		
	}

	public void AddNew(String string) {
		// TODO Auto-generated method stub
		DB_service db = new DB_service();
		Date date = new Date();
		int tmpid = date.hashCode();
		if (db.NewTwitter(tmpid, string, date))
			System.out.println("Add successfully");
		else {
			System.out.println("Failed");
			return;
		}
		System.out.println("In twitter.addnew");
		for (Observer observer : this.logs) {
			System.out.println("------");
			observer.update(tmpid, 0);
			System.out.println("hhh");
		}
	}

	public void Search(int tmpid) {
		// TODO Auto-generated method stub
		DB_service db = new DB_service();
		if (!db.PrintContent(tmpid))
			System.out.println("Can't Find");
		for (Observer observer : this.counters) {
			observer.update(tmpid,-1);
		}
			
	}

	public void Del(int tmpid) {
		// TODO Auto-generated method stub
		DB_service db = new DB_service();
		if (!db.Del(tmpid))
			System.out.println("Can't Find");
		for (Observer observer : this.logs) {
			observer.update(tmpid,2);
		}
	}

	public void Modify(int tmpid, String newContent) {
		// TODO Auto-generated method stub
		DB_service db = new DB_service();
		if (!db.Modify(tmpid, newContent))
			System.out.println("Can't Find");
		for (Observer observer : this.logs) {
			observer.update(tmpid,1);
		}
	}
	
	
}
