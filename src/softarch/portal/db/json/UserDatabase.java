package softarch.portal.db.json;

import java.io.File;
import java.io.IOException;

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
	
	public UserDatabase(){
		try{
			userdb = new File("userdb.json");

			//if file doesnt exists, then create it
			if(!userdb.exists()){
				userdb.createNewFile();
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) throws DatabaseException {
		insertJson(profile.asJSON());
	}

}
