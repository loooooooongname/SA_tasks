package sa_task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;



import DB_service.DB_service;
import sa_task.Twitter;

public class Main_test {
	public static void show(List<Twitter> ts) {
		for (Twitter t : ts) {
			Counter C = (Counter)t.obs.get(0);
			System.out.println(String.format("---------\nid:%d\ncontent:%s\nviewed:%d\n---------\n", t.tid, t.content, C.count));
		}
	}
	
	public static void PrintMenu() {
		System.out.println("²Ëµ¥£º");
		System.out.println("1.²é¿´Î¢²©");
		System.out.println("2.ÐÂ½¨Î¢²©");
		System.out.println("3.ÐÞ¸ÄÎ¢²©");
		System.out.println("4.É¾³ýÎ¢²©");
		System.out.println("5.ÍË³ö²Ëµ¥");
		System.out.println("ÇëÑ¡Ôñ£º");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin = new Scanner(System.in);
		int cmd;
		
		List<Twitter> twitters = new ArrayList<Twitter>();
		DB_service db = new DB_service();
		twitters = db.GetTwitters();
		
		int maxn = 10000;
		
		
		// create weibo
		for (int i = 0; i < maxn; i++) {
			String msg = String.format("this is content%d", i);
			Twitter t = new Twitter(msg);
			Counter tc = new Counter(t.tid);
			Log tl = new Log(t.tid);
			t.attach(tc);
			t.attach(tl);
			twitters.add(t);
			twitters.get(twitters.size()-1).notify("2");
			Date date = new Date();
			db.NewTwitter(date.hashCode(), msg, date);
		}
		
		// view
		for (int i = 0; i < maxn; i++) {
			twitters.get(i).notify("1");
		}
		
		// modify
		for (int i = 0; i < maxn; i++) {
			String msg = String.format("this is modified%d", i);
			twitters.get(i).content = msg;
			twitters.get(i).notify("3");
			db.Modify(i, twitters.get(i).content);
		}
		
		// del
		for (int i = 0; i < maxn; i++) {
			twitters.get(i).notify("4");
			twitters.remove(i);
			db.Del(i);
		}
	}

}
