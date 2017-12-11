package DB_service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import sa_task.Counter;
import sa_task.Log;
import sa_task.Twitter;

public class DB_service {

	 static final String dburl = "jdbc:mysql://localhost:8066/dbtest";
	 static final String dbuser = "test";
	 static final String dbpwd = "test";
//	static final String dburl = "jdbc:mysql://localhost:3306/twitter";
//	static final String dbuser = "root";
//	static final String dbpwd = "root";
	private Connection connect;

	public DB_service() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.connect = DriverManager.getConnection(dburl, dbuser, dbpwd);
		} catch (Exception e) {
			return;
		}
	}

	public boolean NewTwitter(int tmpid, String string, Date date) {
		try {
			PreparedStatement Statement = connect
					.prepareStatement("INSERT INTO Twitter (TwitterID, Content, Date, Time) VALUES (?,?,?,?)");
			Statement.setInt(1, tmpid);
			Statement.setString(2, string);
			Statement.setDate(3, new java.sql.Date(date.getTime()));
			Statement.setTime(4, new java.sql.Time(date.getTime()));

			Statement.executeUpdate();
			PreparedStatement Statement1 = connect.prepareStatement("INSERT INTO ClickCount VALUES (?,?)");
			Statement1.setInt(1, tmpid);
			Statement1.setInt(2, 0);

			Statement1.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}

		return true;
	}

	public boolean PrintContent(int tmpid) {
		try {
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Twitter where TwitterID = '" + tmpid + "' ");

			if (rs.wasNull())
				return false;

			while (rs.next()) {
				System.out.println(rs.getString("Content"));
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean Del(int tmpid) {
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("delete from Twitter where TwitterID = '" + tmpid + "' ");
			// System.out.println("hahaha");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public boolean Modify(int tmpid, String newContent) {
		try {
			Statement stmt = connect.createStatement();
			// System.out.println("[update Twitter set Content='"+newContent+"' where
			// TwitterID = '"+tmpid+"' ]");
			stmt.executeUpdate("update Twitter set Content='" + newContent + "' where TwitterID = '" + tmpid + "' ");

			return true;

		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public void Counter(int id) {
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("update ClickCount set Click=Click+1 where TwitterID = '" + id + "' ");

		} catch (Exception e) {
			System.out.println(e);
			return;
		}
	}

	public void AddLog(int id, int op) {
		try {

			Statement stmt = connect.createStatement();
			java.util.Date date = new Date();
			String cmd = String.format("insert into Log(TwitterID, Operation, Date, Time) values(%d,%d,'%s','%s')", id,
					op, new java.sql.Date(date.getTime()), new java.sql.Time(date.getTime()));

			// System.out.println(cmd);
			stmt.execute(cmd);
		} catch (Exception e) {
			System.out.println(e);
			return;
		}

		return;
	}

	public List<Twitter> GetTwitters() {
		List<Twitter> ls = new ArrayList<Twitter>();
		try {
			Statement stmt = connect.createStatement();
			Statement stmt2 = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Twitter");

			if (rs.wasNull())
				return null;

			while (rs.next()) {
				Twitter tmp = new Twitter(rs.getInt("TwitterID"), rs.getString("Content"));
				ResultSet rs1 = stmt2
						.executeQuery("select * from ClickCount where TwitterID = '" + rs.getInt("TwitterID") + "' ");
				int tn = 0;
				while (rs1.next()) {
					tn = rs1.getInt("Click");
				}
				Counter tc = new Counter(tmp.tid, tn);
				Log tl = new Log(tmp.tid);
				tmp.attach(tc);
				tmp.attach(tl);
				ls.add(tmp);
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return ls;
	}

	public String Search(int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			return null;
		}
		try {
			Connection connect = DriverManager.getConnection(dburl, dbuser, dbpwd);
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Twitter where TwitterID = '" + id + "'");

			if (rs.wasNull())
				return null;

			while (rs.next()) {
				return rs.getString("Content");
			}
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
		return null;
	}

	public boolean Clear() {
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("delete from Twitter");
			stmt.executeUpdate("delete from Log");
			stmt.executeUpdate("delete from ClickCount");
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public void setCounter(int id, int cnt) {
		try {
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("update ClickCount set Click=" + cnt + " where TwitterID = '" + id + "' ");
		} catch (Exception e) {
			System.out.println(e);
			return;
		}
	}

	public void gendata(int maxuser, int maxnum) {
		this.Clear();
		Random rd = new Random();
		for (int user = 0; user < maxuser; user++) {
			for (int num = 0; num < maxnum; num++) {
				String msg = String.format("user[%d]--blog[%d]", user, num);
				Date date = new Date();
				int id = date.hashCode();

				String sql;
				sql = String.format("insert into Log(TwitterID, Operation, Date, Time) values(%d,%d,'%s','%s')", id, 0,
						new java.sql.Date(date.getTime()), new java.sql.Time(date.getTime()));

				Statement stmt;
				try {
					stmt = connect.createStatement();
					stmt.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				sql = String.format("insert into Twitter(TwitterID, Content, Date, Time) values(%d,'%s','%s','%s')", id,
						msg, new java.sql.Date(date.getTime()), new java.sql.Time(date.getTime()));
				try {
					stmt = connect.createStatement();
					stmt.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				sql = String.format("insert into ClickCount(TwitterID, Click) values(%d, %d)", id, rd.nextInt(10000));
				try {
					stmt = connect.createStatement();
					stmt.execute(sql);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}

			}
		}
	}

	public void gendata2(int maxuser, int maxnum) {
		this.Clear();
		Random rd = new Random();
		int[][] ids = new int[maxuser][maxnum];
		try {
			connect.setAutoCommit(false);
			String sql;
			PreparedStatement prest;
			sql = "INSERT INTO Log(TwitterID, Operation, Date, Time) VALUES(?,?,?,?)";
			prest = connect.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int user = 0; user < maxuser; user++) {
				for (int num = 0; num < maxnum; num++) {
					Date date = new Date();
					ids[user][num] = helper.getID();
					prest.setInt(1, ids[user][num]);
					prest.setInt(2, 0);
					prest.setDate(3, new java.sql.Date(date.getTime()));
					prest.setTime(4, new java.sql.Time(date.getTime()));
					prest.addBatch();
				}
			}
			prest.executeBatch();
			connect.commit();
			System.out.println("add log ok");
			sql = "INSERT INTO Twitter(TwitterID, Content, Date, Time) VALUES(?,?,?,?)";
			prest = connect.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int user = 0; user < maxuser; user++) {
				for (int num = 0; num < maxnum; num++) {
					String msg = String.format("user[%d]--blog[%d]", user, num);
					Date date = new Date();
					prest.setInt(1, ids[user][num]);
					prest.setString(2, msg);
					prest.setDate(3, new java.sql.Date(date.getTime()));
					prest.setTime(4, new java.sql.Time(date.getTime()));
					prest.addBatch();
				}
			}
			prest.executeBatch();
			connect.commit();
			System.out.println("add twitter ok");
			sql = "INSERT INTO ClickCount(TwitterID, Click) VALUES(?,?)";
			prest = connect.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			for (int user = 0; user < maxuser; user++) {
				for (int num = 0; num < maxnum; num++) {
					prest.setInt(1, ids[user][num]);
					prest.setInt(2, rd.nextInt(10000));
					prest.addBatch();
				}
			}
			prest.executeBatch();
			connect.commit();
			System.out.println("add Click ok");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1);
			e1.printStackTrace();
		}
	}
}