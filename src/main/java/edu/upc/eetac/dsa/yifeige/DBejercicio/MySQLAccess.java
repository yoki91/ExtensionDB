package edu.upc.eetac.dsa.yifeige.DBejercicio;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class MySQLAccess 
{
private Connection connect = null;
private Statement statement = null;
private PreparedStatement preparedStatement = null;
private ResultSet resultSet = null;


  public void readDataBase() throws Exception 
  {
    try {
      // this will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      // setup the connection with the DB.
      connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?"+ "user=root&password=19911215");

      // statements allow to issue SQL queries to the database
      statement = connect.createStatement();
      // resultSet gets the result of the SQL query
      System.out.print("Es la primera consulta\n");
      resultSet = statement.executeQuery("select * from feedback.COMMENTS");
      writeResultSet(resultSet);
      
      
      System.out.print("Es la segunda consulta\n");
      // preparedStatements can use variables and are more efficient
      preparedStatement = connect.prepareStatement("insert into  feedback.COMMENTS values (default, ?, ?, ?, ? , ?, ?)");
      // "myuser, webpage, datum, summary, COMMENTS from feedback.COMMENTS");
      // parameters start with 1
      preparedStatement.setString(1, "yifei");
      preparedStatement.setString(2, "yifei@upc.edu");
      preparedStatement.setString(3, "yifei_homepage");
      preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
      preparedStatement.setString(5, "It's a DSA DB excises");
      preparedStatement.setString(6, "Good student");
      preparedStatement.executeUpdate();

      
      System.out.print("Es la tercera consulta\n");
      preparedStatement = connect.prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.COMMENTS");
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet); // WriteResultSet es una funcion para mostrar todo los datos por Eclipse

      // remove again the insert comment
      /*preparedStatement = connect.prepareStatement("delete from feedback.COMMENTS where myuser= ? ; ");
      preparedStatement.setString(1, "Test");
      preparedStatement.executeUpdate();*/
      
      resultSet = statement.executeQuery("select * from feedback.COMMENTS");
      writeMetaData(resultSet);
      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }

  private void writeMetaData(ResultSet resultSet) throws SQLException 
  {
    // now get some metadata from the database
    System.out.println("The columns in the table are: ");
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++)
    {
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  private void writeResultSet(ResultSet resultSet) throws SQLException 
  {
    // resultSet is initialised before the first data set
    while (resultSet.next()) 
    {
      // it is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g., resultSet.getSTring(2);
      String user = resultSet.getString("myuser");
      String website = resultSet.getString("webpage");
      String summary = resultSet.getString("summary");
      Date date = resultSet.getDate("datum");
      String comment = resultSet.getString("comments");
      
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summary: " + summary);
      System.out.println("Date: " + date);
      System.out.println("Comment: " + comment);
    }
  }

  // you need to close all three to make sure
  private void close() {
    close(resultSet);
    close(statement);
    close(connect);
  }
  private void close(AutoCloseable c) 
  {
    try 
    {
      if (c != null) 
      {
        c.close();
      }
    } 
    catch (Exception e) 
    {
    // don't throw now as it might leave following closables in undefined state
    }
  }
} 

