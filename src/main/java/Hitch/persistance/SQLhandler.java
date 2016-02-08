package Hitch.persistance;

import java.sql.*;
public class SQLhandler{

	public static void insert(String insertion, String[] values){
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(insertion);
			for (int i = 1; i <= values.length; i++ ) {
				statement.setString(i,values[i-1]);
			}
			statement.executeUpdate();
			statement.close();
			connection.commit();
			connection.close();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e){
			System.out.println("insertions Error");
		}	
	}	

	public static String[][] preparedSelection(String selection, String[] values){
		String[][] data;
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
			PreparedStatement statement = connection.prepareStatement(selection);
			PreparedStatement statementSize = connection.prepareStatement("SELECT count(*) from (" + selection + ")");

			for (int i = 1; i <= values.length; i++ ) {
				statement.setString(i,values[i-1]);
				statementSize.setString(i,values[i-1]);
			}
			System.out.println(statement.toString());
			ResultSet resultSet = statement.executeQuery();
			ResultSet resultSetSize = statementSize.executeQuery();
			data = objectConstructor(resultSet,resultSetSize);
			resultSet.close();
			resultSetSize.close();
			statement.close();
			statementSize.close();
			connection.close();
			return data;


		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e){
			System.out.println("insertions Error");
		}	

		return null;
	}

	public static String[][] selections(String[] selectionGroup){
		String selection = "";
		for (int i = 0; i < selectionGroup.length; i++) {
			selection = selection + selectionGroup[i]; 
		}
		String[][] data;
		try{
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:data.db");
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement(selection);
			PreparedStatement statementSize = connection.prepareStatement("SELECT count(*) from (" + selection + ")");
			

			ResultSet resultSet = statement.executeQuery();
			ResultSet resultSetSize = statementSize.executeQuery();
			data = objectConstructor(resultSet,resultSetSize);
			resultSet.close();
			resultSetSize.close();
			statement.close();
			statementSize.close();
			connection.close();
			return data;

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} catch (Exception e){System.out.println("fail");}

		return null;
	}

	public static String[][] objectConstructor(ResultSet resultSet,ResultSet resultSetSize){
		String[][] data;
		try{
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			int columnCount = resultSetMetaData.getColumnCount();
			String[] types = new String[columnCount];
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount ; i++) {
				types[i] = resultSetMetaData.getColumnTypeName(i+1);
				columnNames[i] = resultSetMetaData.getColumnLabel(i+1);
			}

			int rowCount = resultSetSize.getInt("count(*)");


			data = new String[columnCount][rowCount];
			
			for (int k = 0; k < rowCount; k++) {
				resultSet.next();			
				for (int j = 0; j < columnCount; j++) {
					if(types[j].equals("VARCHAR")) data[j][k] = resultSet.getString(columnNames[j]);
					if(types[j].equals("DATETIME")) data[j][k] = resultSet.getString(columnNames[j]);
					if(types[j].equals("INTEGER") || types[j].equals("INT")) data[j][k] = String.valueOf(resultSet.getInt(columnNames[j]));
				}	
			}
			return data;
		} catch (Exception e){System.out.println("error");}
		return null;
	}


	public static void main(String[] args) {
		System.out.print("");
		String a = "SELECT Task FROM Tasks JOIN Groups on Tasks.groupId = Groups.groupId WHERE Groupname is ? and ProjectId is (Select ProjectId from Groups where Groupname is ? and predecessor is null)";
		System.out.println(preparedSelection(a,(new String[]{"Einstaklingsverkefni 1","304G"}))[0][1]);
	}
}