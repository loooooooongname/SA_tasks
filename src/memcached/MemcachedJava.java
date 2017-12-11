package memcached;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;
import sa_task.Counter;
import sa_task.Log;
import sa_task.Twitter;

public class MemcachedJava {

	static final String dburl = "jdbc:mysql://localhost:3306/twitter";
	static final String dbuser = "root";
	static final String dbpwd = "root";
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
				ResultSet rs2 = stmt2.executeQuery("select * from Twitter where TwitterID = '"+rs.getInt("TwitterID")+"'");
				String tmpcontent = "";
				while(rs2.next()) {
					tmpcontent = rs2.getString("Content");
					break;
				}
				Twitter tmp = new Twitter(rs.getInt("TwitterID"), tmpcontent);
				Counter tc = new Counter(tmp.tid, rs.getInt("Click"));
				Log tl = new Log(tmp.tid);
				tmp.attach(tc);
				tmp.attach(tl);
				
				try {
					mcc.set(rs.getInt("TwitterID")+"", 0, tmp);
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

	public Twitter search(int tmp) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			Twitter a = (Twitter)(mcc.get(tmp+""));
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
			mcc.add(t.tid+"", 0, t);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}

	public boolean modify(int tmp, String tmpstr) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			Twitter tt = new Twitter(tmp, tmpstr);
			mcc.set(tmp+"", 0, tt);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}

	public boolean del(int tmp) {
		// TODO Auto-generated method stub
		try {
			MemcachedClient mcc = new MemcachedClient(new InetSocketAddress("127.0.0.1", 11211));
			mcc.delete(tmp+"");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
}