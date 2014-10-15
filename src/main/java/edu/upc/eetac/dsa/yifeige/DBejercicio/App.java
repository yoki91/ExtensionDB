package edu.upc.eetac.dsa.yifeige.DBejercicio;
import edu.upc.eetac.dsa.yifeige.DBejercicio.MySQLAccess;

public class App {
	  public static void main(String[] args) throws Exception {
	    MySQLAccess dao = new MySQLAccess();
	    dao.readDataBase();
	  }

	} 