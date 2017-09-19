package service;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dao.util.DBUtil;
import Util.COLNUM;
import Util.Util;

public class FileLoad2DB implements COLNUM{
	
	public static final int COL_TWO = 2;
	
	private static Set<File> files=new HashSet<File>();
	
	public static int FileUp(String filepath, String tablename){
		Util.getFile(new File(filepath),files);
		PreparedStatement ps=DBUtil.getStatement("insert into " + tablename + " values (?,?)");

		Iterator<File> it=(Iterator<File>) files.iterator();
		String content="";
		try {
			//ps.setString(1, tablename);
			while(it.hasNext()){
				content=Util.getContent(it.next());
				ps.setString(1, content.split("\n")[0]);
				ps.setString(2, content);
				ps.addBatch();
			}
			int[] uCounts = ps.executeBatch();
			print(uCounts);
			//DBUtil.commit();
			DBUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			DBUtil.Close();
			e.printStackTrace();
		}
		return 0;
	}

	private static void print(int []uCounts) {
		int error=0;
		for(int num:uCounts){
			if(num==0)
				error++;
		}
		System.out.println("成功："+(uCounts.length-error)+"条；"+"失败："+error+"条");
	}

	public static Map<String,String> getFile(String tablename) {
		PreparedStatement ps=DBUtil.getStatement("select * from " + tablename);
		Map<String,String> resultSet=new HashMap<String, String>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				resultSet.put(rs.getString(1), rs.getString(2));
			}
			DBUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//DBUtil.Close();
			e.printStackTrace();
			return resultSet;
		}
		return resultSet;
	}
	
	public static void close() {
		// TODO Auto-generated method stub
		DBUtil.Close();
	}

	public static void save(Map<String, ArrayList<String>> diff, String tablename) {
		PreparedStatement ps=DBUtil.getStatement("insert into " + tablename + " values(?,?,?)");
		try {
			for(Entry<String,ArrayList<String>>en :diff.entrySet()){
				//System.out.println(en.getKey());
				//System.out.println(en.getValue());
				ps.setString(1, en.getKey().split(",")[0]);
				ps.setString(2, en.getKey().split(",")[1]);
				ps.setString(3, en.getValue().toString().substring(1, en.getValue().toString().length()-1));
				ps.addBatch();
			}
			int[] uCounts = ps.executeBatch();
			print(uCounts);
			//DBUtil.commit();
			DBUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void save(Map<String, String> comparedMap, String tablename, boolean isSaveOriginal) {
		PreparedStatement ps=DBUtil.getStatement("insert into " + tablename + " values(?,?)");
		try {
			for(Entry<String,String>en :comparedMap.entrySet()){
				ps.setString(1, en.getKey());
				String s=en.getValue().split("\n")[0];
				//System.out.println(s+en.getValue().substring(en.getValue().indexOf("\n")).replaceAll(" +", ""));
				//s=.replaceAll(" +", "");
				if(isSaveOriginal)
					ps.setString(2, s+en.getValue().substring(en.getValue().indexOf("\n")).replaceAll(" +", ""));
				else
					ps.setString(2, en.getValue());
				ps.addBatch();
			}
			int[] uCounts = ps.executeBatch();
			print(uCounts);
			//DBUtil.commit();
			DBUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<String> getFile(String tablename, int colTwo) {
		PreparedStatement ps=DBUtil.getStatement("select * from " + tablename);
		ArrayList<String> resultSet=new ArrayList<String>();
		try {
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				resultSet.add(rs.getString(2));
			}
			DBUtil.Close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//DBUtil.Close();
			e.printStackTrace();
			return resultSet;
		}
		return resultSet;
	}

	public static void save(String textid, String article, String tablename,
			boolean b) {
		PreparedStatement ps=DBUtil.getStatement("insert into " + tablename + " values(?,?)");
		try {
			ps.setString(1, textid);
			ps.setString(2, article);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.Close();
		}
	}
	
	
	public static void update(String id, String content, String tablename, String fieldname){
		if(tablename==null || tablename.length()<=0)
			System.err.println("tablename is null");
		PreparedStatement ps=DBUtil.getStatement("update " + tablename + " set " + fieldname +" = ? where id = ?");
		try {
			ps.setString(1, content);
			ps.setString(2, id);
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBUtil.Close();
		}
		
	}
	
}
