package sa_task;

import java.util.Date;
import java.util.Random;

import DB_service.DB_service;

public class Main_generator {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DB_service db = new DB_service();
		
		int maxuser = 3;
		int maxnum = 50;
		
		db.Clear();
		
		Random rd = new Random();
		
		for (int user = 0; user < maxuser; user ++) {
			for (int num = 0; num < maxnum; num++) {
				String msg = String.format("user[%d]--blog[%d]", user, num);
				Date date = new Date();
				int id = date.hashCode();
				db.NewTwitter(id, msg, date);
				db.AddLog(id, 0);
				db.setCounter(id, rd.nextInt(10000));
			}
		}
	}

}
