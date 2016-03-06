package Hitch.persistance;

import Hitch.persistance.dao.RidesDAO;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Should be extending the Rides class, that hasnt been written
 *  If a ride is deleted/inserted you must both call fetchRides() and createRideArray()
 *
 * The class has the attribute int[][] rideArray that always has all the rides in it since
 * its last update. It does not get updated automatically
 * I could do it
 * But I didnt
 * Its the simple matter of calling fethRides() and createRideArray() in the back of deleteRide() and
 * insertRide()
 * I feel like I have explained this enough
 *
 * A unique ID is created for each ride
 * The database is designed in such a way that a phone number can occur more than once, i.e a phonenumber
 * isnt unique in the database, this should probably be fixed
 *
 * This is the database schema
 *
 * create table rides(
 *	id INTEGER primary key autoincrement,
 *	pickup int,
 *	destination int,
 *	phone int
 *	);
 *
 * Just add 'unique' right behind 'phone int' so that it reads 'phone int unique'
 *
 * Goodbye
 */
public class SQLHelper {
  private Logger logger = Logger.getLogger(SQLHelper.class.getName());
  private Connection connection;

  private String url = "jdbc:postgresql://localhost/master";
  private String user = "david";
  private String password = "";

  private List<RidesDAO> requestedRidesCache = new ArrayList<RidesDAO>();
  private final String[] columnNames         = new String[]{"id", "pickup", "dropOff", "sessionId"};
  private final String   FETCH_RIDES         = "select id, pickup, dropOff from rides;";
  private final String   insertion           = "insert into rides(pickup, dropOff, sessionId) values(?,?,?);";
  private final String   remove              = "delete from rides where sessionId is (?);";
  private final String   FETCH_MESSAGES      = "select message from messages where sessionId = ?;";
  private final String   MESSAGE_INSERTION   = "insert into messages(message, sessionId) values(?, (SELECT sessionId FROM rides where id is (?)));";


  /**
   * [createConnection description]
   * Use: createConnection()
   * After: static variable connection is now connected to database data.db
   * Used by insertRide() and fetchRides()
   */
  public void createConnection()
  {
    connection = null;
    try {
      Class.forName("org.sqlite.JDBC");
      connection = DriverManager.getConnection(url,user,password);
    } catch ( Exception e ) {
      logger.log(Level.SEVERE, e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
    logger.info("Opened database successfully");
  }

  public void deleteRide(String sessionId)
  {
    createConnection();
    try{
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(remove);
      statement.setString(1, sessionId);

      statement.executeUpdate();
      connection.commit();
      connection.close();

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, "SQL exception " + ex.getMessage(), ex);
    } catch (Exception e){
      logger.log(Level.SEVERE, "Delete Ride Error", e);
    }
    updateCache();
  }

  public void insertRide(int pickup, int dropOff, String sessionId)
  {
    createConnection();
    try{
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(insertion);

      statement.setInt(1,pickup);
      statement.setInt(2,dropOff);
      statement.setString(3, sessionId);

      statement.executeUpdate();
      statement.close();
      connection.commit();
      connection.close();

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, "SQL exception: " + ex.getMessage(), ex);
    } catch (Exception e){
      logger.log(Level.SEVERE, "Insert Ride Error", e);
    }
    updateCache();
  }

  public void updateCache(){
    requestedRidesCache = new ArrayList<RidesDAO>();
    createConnection();
    try{
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(FETCH_RIDES);
      ResultSet resultSet = statement.executeQuery();

      while(resultSet.next()) {
        int pickup = resultSet.getInt(columnNames[1]);
        int dropOff = resultSet.getInt(columnNames[2]);
        int id = resultSet.getInt(columnNames[0]);
        RidesDAO dao = new RidesDAO(pickup, dropOff, id);
        requestedRidesCache.add(dao);
      }

      resultSet.close();
      statement.close();
      connection.close();

    } catch (SQLException ex) {
      logger.log(Level.SEVERE, "SQL exception: " + ex.getMessage(), ex);
    } catch (Exception e) {
      logger.log(Level.SEVERE, "Update Cache Error", e);
    }
  }

  public void insertMessage(String message, int passengerId)
  {
    createConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(MESSAGE_INSERTION);

      statement.setString(1, message);
      statement.setInt(2, passengerId);

      statement.executeUpdate();
      statement.close();
      connection.commit();
      connection.close();

    } catch (SQLException ex){
      logger.log(Level.SEVERE, "SQL exception: " + ex.getMessage(), ex);
    } catch (Exception e){
      logger.log(Level.SEVERE, "Insert Message Error", e);
    }
  }

  public List<String> fetchMessages(String sessionId)
  {
    List<String> messages = new ArrayList<String>();
    createConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(FETCH_MESSAGES);

      statement.setString(1, sessionId);
      ResultSet resultSet = statement.executeQuery();

      while(resultSet.next()) {
        String message = resultSet.getString("message");
        messages.add(message);
      }

      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException ex){
      logger.log(Level.SEVERE, "SQL exception: " + ex.getMessage(), ex);
    } catch (Exception e){
      logger.log(Level.SEVERE, "Insert Message Error", e);
    }
    return messages;
  }

  public List<RidesDAO> fetchRides()
  {
    if(this.requestedRidesCache.isEmpty()){
      updateCache();
    }
    return this.requestedRidesCache;
  }


}