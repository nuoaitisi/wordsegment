package dao.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	private static Connection con=null;
	private static PreparedStatement ps=null;
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=initCon();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static PreparedStatement getStatement(String sql){
		try {
			if(con==null){
				con=initCon();
			}
			ps=con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ps;
	}
	
	private static Connection initCon() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/yunpeng.li2","root","123456");
	}

	public static void Close(){
		if(con!=null)
			try {
				con.close();
				con=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(ps!=null)
			try {
				ps.close();
				ps=null;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	
	
	public static void main(String[] args) {
		PreparedStatement ps=DBUtil.getStatement("select * from diff");
		try {
			ResultSet set=ps.executeQuery();
			while(set.next()){
				System.out.println(set.getString(1));
				System.out.println(set.getString(2));
				System.out.println(set.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void commit() throws SQLException {
		// TODO Auto-generated method stub
		con.commit();
	}
}
