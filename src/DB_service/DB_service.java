package DB_service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import sa_task.Counter;
import sa_task.Log;
import sa_task.Twitter;

public class DB_service {
	
	static final String dburl = "jdbc:mysql://localhost:3306/twitter";
	static final String dbuser = "root";
	static final String dbpwd = "123456";
	
	public boolean NewTwitter(int tmpid, String string, Date date) {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			return false;
		}

		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			PreparedStatement Statement=connect.prepareStatement("INSERT INTO Twitter VALUES (?,?,?,?)");
			Statement.setInt(1, tmpid);
			Statement.setString(2, string);
			Statement.setDate(3, new java.sql.Date(date.getTime()));
			Statement.setTime(4, new java.sql.Time(date.getTime()));

			Statement.executeUpdate();
			
			PreparedStatement Statement1=connect.prepareStatement("INSERT INTO ClickCount VALUES (?,?)");
			Statement1.setInt(1, tmpid);
			Statement1.setInt(2, 0);

			Statement1.executeUpdate();
			
		}catch(Exception e){
			return false;
		}

		return true;
	}

	public boolean PrintContent(int tmpid) {
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
			ResultSet rs = stmt.executeQuery("select * from Twitter where TwitterID = '"+tmpid+"' ");

			if (rs.wasNull())
				return false;

			while (rs.next()){
				System.out.println(rs.getString("Content"));
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public boolean Del(int tmpid) {
		// TODO Auto-generated method stub

		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}
		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("delete from Twitter where TwitterID = '"+tmpid+"' ");

			return true;
		}catch (Exception e){
			System.out.println(e);
			return false;
		}
	}

	public boolean Modify(int tmpid, String newContent) {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			System.out.println(e);
			return false;
		}
		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("update Twitter set Content='"+newContent+"' where TwitterID = '"+tmpid+"' ");

			return true;

		}catch (Exception e){
			System.out.println(e);
			return false;
		}
	}

	public void Counter(int id) {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			System.out.println(e);
			return ;
		}
		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			Statement stmt = connect.createStatement();
			stmt.executeUpdate("update ClickCount set Click=Click+1 where TwitterID = '"+id+"' ");


		}catch (Exception e){
			System.out.println(e);
			return ;
		}
	}

	public void AddLog(int id, int op) {
		System.out.println(id+" "+op);
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			return ;
		}

		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			PreparedStatement Statement=connect.prepareStatement("INSERT INTO Log VALUES (?,?,?,?)");
			Statement.setInt(1, id);
			Statement.setInt(2, op);
			java.util.Date date = new Date();
			Statement.setDate(3, new java.sql.Date(date.getTime()));
			Statement.setTime(4, new java.sql.Time(date.getTime()));

			Statement.executeUpdate();
			
			
		}catch(Exception e){
			return ;
		}

		return ;
	}

	public List<Twitter> GetTwitters() {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch (Exception e){
			return null;
		}
		List<Twitter> ls = new ArrayList<Twitter>();
		try{
			Connection connect = DriverManager.getConnection(dburl,dbuser,dbpwd);
			Statement stmt = connect.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Twitter");

			if (rs.wasNull())
				return null;
			
			
			while (rs.next()){
				Twitter tmp = new Twitter(rs.getInt("TwitterID"), rs.getString("Content"));
				Counter tc = new Counter(tmp.tid);
				Log tl = new Log(tmp.tid);
				tmp.attach(tc);
				tmp.attach(tl);
				ls.add(tmp);
				System.out.println(rs.getString("Content"));
			}
		}catch(Exception e){
			return null;
		}
		return ls;
	}
}