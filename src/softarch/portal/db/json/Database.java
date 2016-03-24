package softarch.portal.db.json;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;


/**
 * This abstract class implements the behaviour that is to be shared
 * by all databases.
 * @author 
 */
public class Database {
	
	
	/**
	 * Append the given json data.
	 * Note that no result will be returned.
	 */
	public void insertJson(String data){
		
		//true = append file
		FileWriter fileWritter;
		try {
			fileWritter = new FileWriter("userdb.json",true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write(data);
	        bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
		
	}
}
