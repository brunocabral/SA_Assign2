package softarch.portal.db.json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;


/**
 * This abstract class implements the behaviour that is to be shared
 * by all databases.
 * @author Niels Joncheere
 */
public class Database {
	
	/**
	 * Executes the given query.
	 * Note that no result will be returned.
	 */
	public void insertJson(String query){
		//Escrever no arquivo JSON aqui
		
	}
}
