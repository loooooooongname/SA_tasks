package class_define;

import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	
	public static void PrintMenu() {
		System.out.println("菜单：");
		System.out.println("1.新建微博");
		System.out.println("2.查看微博");
		System.out.println("3.删除微博");
		System.out.println("4.修改微博");
		System.out.println("5.退出菜单");
		System.out.println("请选择：");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*
		Twitter twitter = new Twitter();
		Observer observer = new Log(-1);
		Observer observer2 = new Counter(1);
		twitter.attach(observer, observer2);
		twitter.AddNew("hello world");
		*/
		System.out.println();
		int choose;
		Scanner sc = new Scanner(System.in);
		while (true) {
			PrintMenu();
			System.out.println("!!!");
			choose = sc.nextInt();
			if (choose == 1)
				New();
			else if (choose == 2)
				Search();
			else if (choose == 3)
				Del();
			else if (choose == 4)
				Modify();
			else if (choose == 5)
				break;
			else 
				System.out.println("wrong select");
			//System.out.println("end");
		}
		
	}

	private static void Modify() {
		// TODO Auto-generated method stub
		System.out.println("输入修改微博的ID：");
		int tmpid = sc.nextInt();
		
		System.out.println("输入新的内容：");
		
		String tmp = sc.nextLine();
		//System.out.println("["+tmp+"]");
		String newContent = sc.nextLine();
		System.out.println("{"+newContent+"}");
		
		Twitter twitter = new Twitter();
		Observer observer2 = new Log(-1);
		Observer observer = new Counter(1);
		twitter.attach(observer, observer2);
		
		twitter.Modify(tmpid, newContent);
		
	}

	private static void Del() {
		// TODO Auto-generated method stub
		System.out.println("输入删除微博的ID：");
		int tmpid = sc.nextInt();
		
		Twitter twitter = new Twitter();
		Observer observer2 = new Log(-1);
		Observer observer = new Counter(1);
		twitter.attach(observer, observer2);
		
		twitter.Del(tmpid);
		
	}

	private static void Search() {
		// TODO Auto-generated method stub
		System.out.println("输入查找微博的ID：");
		int tmpid = sc.nextInt();
		
		Twitter twitter = new Twitter();
		Observer observer2 = new Log(-1);
		Observer observer = new Counter(1);
		twitter.attach(observer, observer2);
		
		twitter.Search(tmpid);
		
		
	}

	private static void New() {
		// TODO Auto-generated method stub
		System.out.println("输入新微博内容：");
		
		Twitter twitter = new Twitter();
		Observer observer2 = new Log(-1);
		Observer observer = new Counter(1);
		twitter.attach(observer, observer2);
		

		String ct = sc.nextLine();
		System.out.println(ct);
		twitter.AddNew(ct);

		

	}

}
