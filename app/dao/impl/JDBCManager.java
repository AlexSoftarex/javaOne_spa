package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PGobject;

import play.db.DB;

public class JDBCManager {

	private Connection con = DB.getConnection(); 


	public  void delete(String query) {

	}

	public  void executeUpdate(String query) {

	}

	public  List<String> executeRead(String query) throws SQLException {
		
		Statement stmt = con.createStatement();
	
		ResultSet rs = stmt.executeQuery(query);
		List<String> jsonList = new ArrayList<>();
		
		ResultSetMetaData rsmd = rs.getMetaData();
		 
		
		while (rs.next()) {
			
			jsonList.add(rs.getString(rsmd.getColumnName(1)));
		}

		return jsonList;
	}

	public void executeCreate(String query, String json)
			throws SQLException {

		PGobject dataObject = new PGobject();
		dataObject.setType("jsonb");
		dataObject.setValue(json);

		PreparedStatement stmt = con.prepareStatement(query);
		stmt.setObject(1, dataObject);

		stmt.executeUpdate();
	}

}
