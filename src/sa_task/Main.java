package sa_task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DB_service.DB_service;
import sa_task.Twitter;

public class Main {
	public static void show(List<Twitter> ts) {
		for (Twitter t : ts) {
			Counter C = (Counter)t.obs.get(0);
			System.out.println(String.format("---------\nid:%d\ncontent:%s\nviewed:%d\n---------\n", t.tid, t.content, C.count));
		}
	}
	
	public static void PrintMenu() {
		System.out.println("�˵���");
		System.out.println("1.�鿴΢��");
		System.out.println("2.�½�΢��");
		System.out.println("3.�޸�΢��");
		System.out.println("4.ɾ��΢��");
		System.out.println("5.�˳��˵�");
		System.out.println("��ѡ��");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin = new Scanner(System.in);
		int cmd;
		
		List<Twitter> twitters = new ArrayList<Twitter>();
		DB_service db = new DB_service();
		twitters = db.GetTwitters();
		
		System.out.println(twitters.size());
		
		while(true) {
			show(twitters);
			PrintMenu();
			System.out.print("option:");
			cmd = cin.nextInt();
			// view
			if (cmd == 1) {
				for (int i = 0; i < twitters.size(); i++) {
					System.out.println(String.format("%d:%d", i, twitters.get(i).tid));
				}
				int tmp = cin.nextInt();
				twitters.get(tmp).notify("1");
			}
			// new
			else if (cmd == 2) {
				String msg = cin.next();
				Twitter t = new Twitter(msg);
				Counter tc = new Counter(t.tid);
				Log tl = new Log(t.tid);
				System.out.println(t.tid);
				t.attach(tc);
				t.attach(tl);
				twitters.add(t);
				twitters.get(twitters.size()-1).notify("2");
				Date date = new Date();
				db.NewTwitter(t.tid, msg, date);
			}
			// modify
			else if (cmd == 3) {
				for (int i = 0; i < twitters.size(); i++) {
					System.out.println(String.format("%d:%d", i, twitters.get(i).tid));
				}
				int tmp = cin.nextInt();
				twitters.get(tmp).content = cin.next();
				twitters.get(tmp).notify("3");
				db.Modify(twitters.get(tmp).tid, twitters.get(tmp).content);
			}
			// del
			else if (cmd == 4) {
				for (int i = 0; i < twitters.size(); i++) {
					System.out.println(String.format("%d:%d", i, twitters.get(i).tid));
				}
				int tmp = cin.nextInt();
				twitters.get(tmp).notify("4");
				db.Del(twitters.get(tmp).tid);
				twitters.remove(tmp);
			}
			// exit
			else if (cmd == 5) {
				break;
			}
		}
		
	}

}
