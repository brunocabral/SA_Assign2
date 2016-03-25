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
	
	protected final String FREE_SUBSCRIPTION_FILE = "freeuser.json"; 
	protected final String CHEAP_SUBSCRIPTION_FILE = "cheapuser.json";
	protected final String EXP_SUBSCRIPTION_FILE = "expensiveuser.json";
	
	public UserDatabase(){
		try{
			cheapUserDb = new File(CHEAP_SUBSCRIPTION_FILE);  // ########## 	FIX LOCATION
			freeUserDb = new File(FREE_SUBSCRIPTION_FILE);     // ########## 	FIX LOCATION
			expensiveUserDb = new File(EXP_SUBSCRIPTION_FILE);   // ########## 	FIX LOCATION
		
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
	 * Inserts a new user profile into the user json file
	 * (one file for each type of subscription).
	 */
	public void insert(UserProfile profile) throws DatabaseException {
		
		String fileName = getSubscriptionFileName(profile); //get the name of json file 
		
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

	/*
	 * Return a array of UserProfile from a specific user subscription
	 * @param fileName	name of the json file
	 * @param subscription class of user subscription - it will be user for give the correct
	 * parameter for the fromJson() method, that will parse from text json to an array of objects
	 */
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
	
	
	public void update(UserProfile profile){
		
		String fileName = getSubscriptionFileName(profile);
		
		
	}
	
	public UserProfile findUser(String username){

		UserProfile[] usersArray;
		usersArray = getUsersFromFile(FREE_SUBSCRIPTION_FILE,FreeSubscription.class);
		if (usersArray != null){ //null = empty file
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return usersArray[i];
				}
			}
		}

		usersArray = getUsersFromFile(CHEAP_SUBSCRIPTION_FILE,CheapSubscription.class);
		if (usersArray != null){
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return usersArray[i];
				}
			}
		}

		usersArray = getUsersFromFile(EXP_SUBSCRIPTION_FILE,ExpensiveSubscription.class);
		if (usersArray != null){
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return usersArray[i];
				}
			}
		}
		
		return null; //if there is no user with this username
	}
	
	public boolean userExists(String username){

		UserProfile[] usersArray;
		usersArray = getUsersFromFile(FREE_SUBSCRIPTION_FILE,FreeSubscription.class);
		if (usersArray != null){
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return true;
				}
			}
		}

		usersArray = getUsersFromFile(CHEAP_SUBSCRIPTION_FILE,CheapSubscription.class);
		if (usersArray != null){
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return true;
				}
			}
		}

		usersArray = getUsersFromFile(EXP_SUBSCRIPTION_FILE,ExpensiveSubscription.class);
		if (usersArray != null){
			for (int i = 0; i <= usersArray.length; i++){
				if ( usersArray[i].getUsername().equals(username)){
					return true;
				}
			}
		}
		
		return false; 
	}
	
	/**
	 * Check what is the respective json file for each type of subscription
	 * @param profile 
	 * @return String - Name of the file
	 */
	private String getSubscriptionFileName(UserProfile profile){
		String fileName = "";
		
		if(profile instanceof FreeSubscription){
			fileName = "freeuser.json";

		}else if(profile instanceof CheapSubscription){
			fileName = "cheapuser.json";

		}else if(profile instanceof ExpensiveSubscription){
			fileName = "expensiveuser.json";
		}
		
		return fileName;
	}


}
