package softarch.portal.db.json;

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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * This class encapsulates the user database.
 * @author Bruno M. Cabral/ Luiz N Junior.
 */
public class UserDatabase extends Database {
	/**
	 * Creates a new user database.
	 */
	public UserDatabase(String dbUser, String dbPassword, String dbUrl) {
		super(dbUser, dbPassword, dbUrl);
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) throws DatabaseException {
		insertJson(profile.asJSON());
	}

}
