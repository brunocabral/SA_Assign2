package softarch.portal.db.json;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import softarch.portal.data.CheapSubscription;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.ExpertAdministrator;
import softarch.portal.data.ExternalAdministrator;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.Operator;
import softarch.portal.data.RegularAdministrator;
import softarch.portal.data.UserProfile;
import softarch.portal.db.json.Database;
import softarch.portal.db.DatabaseException;


/**
 * This class encapsulates the user database.
 * @author Bruno M. Cabral/ Luiz N Junior.
 */
public class UserDatabase extends Database {
	
	protected File userdb;
	protected File cheapUserDb;
	protected File freeUserDb;
	protected File expensiveUserDb;
	
	public UserDatabase(){
		try{
			cheapUserDb = new File("cheapuserdb.json");  // ########## 	FIX LOCATION
			freeUserDb = new File("freeuserdb.json");     // ########## 	FIX LOCATION
			expensiveUserDb = new File("expensiveuserdb.json");   // ########## 	FIX LOCATION
//			userdb = new File("userdb.json");
			
			//if file doesnt exists, then create it
//			if(!userdb.exists()){
//				userdb.createNewFile();
//			}
			
			if(!cheapUserDb.exists()){
				cheapUserDb.createNewFile();
			}
			
			if(!freeUserDb.exists()){
				freeUserDb.createNewFile();
			}
			
			if(!expensiveUserDb.exists()){
				expensiveUserDb.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) throws DatabaseException {
		
		
//		JSONObject userJson = profile.asJSONObject();
//		
//		String type = (String) userJson.get("type");
//
//		String dbName = "";
//		if (type.equals("free")){
//			dbName = "freeuserdb.json";
//			
//		}else if(type.equals("cheap")){
//			dbName = "cheapuserdb.json";
//		
//		}else if(type.equals("expensive")){
//			dbName = "expensiveuserdb.json";
//		}
//		userJson.remove("type"); //remove flag 
//	
//		JSONArray users = new JSONArray();
//		
//		if (getUsers(dbName) != null){
//			users = getUsers(dbName);
//		}	
//		
//		users.add(userJson.toString());
//		
//		FileWriter fileWritter;		
//		try {
//			fileWritter = new FileWriter(dbName); //file
//			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
//	        bufferWritter.write( users.toJSONString() );
//	        bufferWritter.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
		
	public JSONArray getUsers(String fileName){

		JSONParser parser = new JSONParser();
		
		JSONArray users = null;
		try {
			System.out.println("print1");
			Object obj = parser.parse(new FileReader(fileName)); // ########## 	FIX LOCATION	
			System.out.println("print2");
			users = (JSONArray) obj;
			System.out.println("print3");
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Parse Exception");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		return users;		
	}

}
