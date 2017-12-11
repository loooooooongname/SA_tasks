package sa_task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DB_service.DB_service;
import memcached.MemcachedJava;
import sa_task.Twitter;

public class Main {
	public static void show(List<Twitter> ts) {
		for (Twitter t : ts) {
			Counter C = (Counter)t.obs.get(0);
			System.out.println(String.format("---------\nid:%d\ncontent:%s\nviewed:%d\n---------\n", t.tid, t.content, C.count));
		}
	}
	
	public static void PrintMenu() {
		System.out.println("菜单：");
		System.out.println("1.查看微博");
		System.out.println("2.新建微博");
		System.out.println("3.修改微博");
		System.out.println("4.删除微博");
		System.out.println("5.退出菜单");
		System.out.println("请选择：");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cin = new Scanner(System.in);
		int cmd;
		
		MemcachedJava mj = new MemcachedJava();
		if (!mj.add100()) {
			System.out.println("Cache import failed");
			return;
		}
		DB_service db = new DB_service();
		
		while(true) {
			//show(twitters);
			PrintMenu();
			System.out.print("option:");
			cmd = cin.nextInt();
			// view
			if (cmd == 1) {
				System.out.println("输入要查看的ID：");
				int tmp = cin.nextInt();
				
				long startTime = System.currentTimeMillis();
				
				Twitter t_twitter = mj.search(tmp);
				
				long endTime   = System.currentTimeMillis(); 
				long TotalTime = endTime - startTime;
				
				if (t_twitter != null) {
					System.out.println("Read from cache time is "+TotalTime+"ms");
					System.out.println("id:"+tmp);
					System.out.println("content:"+t_twitter.content);
					db.Counter(tmp);
				}else {
					System.out.println("id:"+tmp);
					startTime = System.currentTimeMillis();
					System.out.println("content:"+db.Search(tmp));
					endTime   = System.currentTimeMillis(); 
					TotalTime = endTime - startTime;
					
					System.out.println("Read from database time is "+TotalTime+"ms");
					db.Counter(tmp);
				}
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
				if (!mj.newTwitter(t)) {
					System.out.println("Add in Cache failed");
				}
				Date date = new Date();
				db.NewTwitter(t.tid, msg, date);
			}
			// modify
			else if (cmd == 3) {
				System.out.println("输入微博ID：");
				int tmp = cin.nextInt();
				String tmpstr = cin.next();
				if (!mj.modify(tmp,tmpstr)) {
					System.out.println("Modify in Cache failed");
				}
				db.Modify(tmp,tmpstr);
			}
			// del
			else if (cmd == 4) {
				System.out.println("输入微博ID：");
				int tmp = cin.nextInt();
				if (!mj.del(tmp)) {
					System.out.println("Delete in Cache failed");
				}
				db.Del(tmp);
				//twitters.remove(tmp);
			}
			// exit
			else if (cmd == 5) {
				break;
			}
		}
		
	}

}
