package Hitch.persistance;

/**
 * Created by olafurma on 6.2.2016.
 */
import java.sql.*;

public class SQLHelper{
  Connection c;

  public SQLHelper(){
    c = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:data.db");
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Opened database successfully");

  }


  public static void main(String[] args) {
    System.out.println("arf");
    SQLHelper hehe = new SQLHelper();
  }
}