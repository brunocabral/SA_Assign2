package softarch.portal.db.json;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.*;

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
			cheapUserDb = new File("cheapuser.json");  // ########## 	FIX LOCATION
			freeUserDb = new File("freeuser.json");     // ########## 	FIX LOCATION
			expensiveUserDb = new File("expensiveuser.json");   // ########## 	FIX LOCATION
		
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
		String fileName = "";
		
		if(profile instanceof FreeSubscription){
			fileName = "freeuser.json";

		}else if(profile instanceof CheapSubscription){
			fileName = "cheapuser.json";

		}else if(profile instanceof ExpensiveSubscription){
			fileName = "expensiveuser.json";
		}
		
		UserProfile[] users;
		users = getUsersFromFile(fileName, profile.getClass());
		
//		System.out.println(users == null ? "yesshh1": "no1");
//		System.out.println("Users length: " + users.length);
		ArrayList<UserProfile> usersList;
//		System.out.println("PASSANDO");
		//If file is empty, creates an empty ArrayList
		if (users == null){
			usersList = new ArrayList<UserProfile>();
//			System.out.println(users == null ? "yesshh2": "no2");
		//Otherwise, converts the result from array to ArrayList	
		}else{
			usersList = new ArrayList<UserProfile>(Arrays.asList(users));	
//			System.out.println("ENTROU ELSE");
		}
		
			
		//-------- PRINT TEST ----------------------------
		
//		for (UserProfile temp : usersList) {
//			System.out.println("username: "+ temp.getUsername());
//		}
//		UserProfile fs = new FreeSubscription("novo", "caraio", "funciona", "poha", "t@t", new Date());
		//----------------------------------------------
		
		usersList.add(profile);
		Gson gson = new Gson();  
		// ------ PRINT TEST ---------
//		System.out.println("arraylist length: " + usersList.size());
		// ------ ----------- ---------
		// convert java object to JSON format,  
		// and returned as JSON formatted string  
		String json = gson.toJson(usersList);  

		FileWriter fileWritter;		
		try {
			fileWritter = new FileWriter(fileName); //file
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	        bufferWritter.write( json );
	        bufferWritter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public UserProfile[] getUsersFromFile(String fileName, Class subscription){
		UserProfile[] users = null;
		BufferedReader reader;
		try {
			Gson gson = new GsonBuilder().create();
			reader = new BufferedReader(new FileReader(fileName));
			
			if(subscription.equals(FreeSubscription.class) ){
//				System.out.println("É FREE");
				users = gson.fromJson(reader, FreeSubscription[].class);
				
			}else if(subscription.equals(CheapSubscription.class) ){
//				System.out.println("É CHEAP");
				users = gson.fromJson(reader, CheapSubscription[].class);
				
			}else if(subscription.equals(ExpensiveSubscription.class) ){
//				System.out.println("É EXPENSIVE");
				users = gson.fromJson(reader, ExpensiveSubscription[].class);
			}
			
//			System.out.println(users == null? "yes":"no");
//			System.out.println("Object mode: " + users[0]);

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		
		return users;
	
	}

}
