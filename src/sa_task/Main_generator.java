package sa_task;

import java.util.Date;
import java.util.Scanner;

import DB_service.DB_service;

public class Main_generator {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DB_service db = new DB_service();
		System.out.println(new Date());
		db.gendata2(3, 500);
		System.out.println(new Date());
		
//		Scanner cin = new Scanner(System.in);
		
		
//		while(true) {
//			int id = cin.nextInt();
//			Twitter twitter = db.getTwitterByID(id);
//			twitter.show();
//			twitter.content = cin.next();
//			db.saveTwitterToDB(twitter);
//		}
		
	}
}
