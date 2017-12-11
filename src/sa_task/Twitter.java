package sa_task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Twitter implements Subject, Serializable {
	
	public List<Observer> obs;
	public int tid;
	public String content;
	
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
		this.content = msg;
		this.obs = new ArrayList<Observer>();
		this.tid = new Date().hashCode();
	}
	
	public Twitter(int tid, String msg) {
		this.tid = tid;
		this.content = msg;
		this.obs = new ArrayList<Observer>();
	}
	
}
