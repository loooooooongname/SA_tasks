package sa_task;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import DB_service.helper;
public class Twitter implements Subject, Serializable {
	
	public List<Observer> obs;
	public int tid;
	public String content;
	public Date date;
	public Time time;
	
	@Override
	public void attach(Observer observer) {
		// TODO Auto-generated method stub
		obs.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		// TODO Auto-generated method stub
		obs.remove(observer);
	}

	@Override
	public void notify(String message) {
		// TODO Auto-generated method stub
		for (Observer ob : obs) {
			ob.update(message);
		}
	}
	
	public Twitter (String msg) {
		java.util.Date tdate = new java.util.Date();
		this.content = msg;
		this.obs = new ArrayList<Observer>();
		this.tid = helper.getID();
		this.date = new java.sql.Date(tdate.getTime());
		this.time = new java.sql.Time(tdate.getTime());

	}
	
	public Twitter(int tid, String msg) {
		java.util.Date tdate = new java.util.Date();
		this.tid = tid;
		this.content = msg;
		this.obs = new ArrayList<Observer>();
		this.date = new java.sql.Date(tdate.getTime());
		this.time = new java.sql.Time(tdate.getTime());
	}
	public Twitter(int tid, String msg, Date date, Time time) {
		this.tid = tid;
		this.content = msg;
		this.date = date;
		this.time = time;
		this.obs = new ArrayList<Observer>();
	}
	public void show() {
		Counter C = (Counter)obs.get(0);
		System.out.println(String.format("---------\nid:%d\ncontent:%s\nviewed:%d\n---------\n", tid, content, C.count));
	}
}
