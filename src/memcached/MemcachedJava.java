package memcached;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import DB_service.DB_service;
import net.spy.memcached.MemcachedClient;
import sa_task.Counter;
import sa_task.Log;
import sa_task.Twitter;

public class MemcachedJava {

	static final String dburl = "jdbc:mysql://localhost:3306/twitter";
	static final String dbuser = "root";
	static final String dbpwd = "123456";
//	 static final String dburl = "jdbc:mysql://localhost:8066/dbtest";
//	 static final String dbuser = "test";
//	 static final String dbpwd = "test";
	public boolean add100() {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			return false;
		}
		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			Statement stmt = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ClickCount order by Click desc limit 100");

			if (rs.wasNull())
				return false;
			
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			
			while (rs.next()){
				DB_service db = new DB_service();
				Twitter t = db.getTwitterByID(rs.getInt("TwitterID"));
				try {
					mcc.set(rs.getInt("TwitterID")+"", 0, t);
//					System.out.println(rs.getInt("TwitterID"));
				}catch(Exception e) {
					System.out.println(e.getMessage());
					return false;
				}
			}
			mcc.shutdown();
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
		return true;
	}

	public Twitter search(Twitter t) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			Twitter a = (Twitter)(mcc.get(t.tid+""));
			a.notify("1");
			mcc.set(t.tid+"", 0, a);
			System.out.println(a.tid+" "+a.content);
			return a ;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public boolean newTwitter(Twitter t) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			t.notify("2");
			mcc.add(t.tid+"", 0, t);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

	public boolean modify(Twitter t) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			t.notify("3");
			mcc.set(t.tid+"", 0, t);
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public boolean del(Twitter t) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			t.notify("4");
			mcc.delete(t.tid+"");
			return true;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	public Twitter InCache(Twitter t) {
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			Twitter a = (Twitter)(mcc.get(t.tid+""));
			if (a == null)
				return null;
			else
				return a;
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	public static void maina() {
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			Twitter a = (Twitter)(mcc.get(1000+""));
			if (a == null) {
				System.out.println("null");
			}else {
				System.out.println(a);
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}