package com.hp.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.hp.model.Account;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@Service("userService")
public class UserService {

	static String db_name = "mydb", db_collection = "mydata";
	static int port_no = 27017;
	static String hostname = "localhost";	
	private static Logger log = Logger.getLogger(UserService.class);
	DBCollection coll  = new MongoClient( hostname, port_no).getDB(db_name).getCollection(db_collection); 
	
	public Account getLoggedInUser(Account acc) {
		List<Account> usersList = getAll();
		for (Account s : usersList) {
			if(acc.getUserName().equals(s.getUserName()) && acc.getPassword().equals(s.getPassword())) {
				return s;
			}
			else {
				continue;
			}
		}
		return null;
	}
	// Fetch all users from the mongo database.
	public List getAll() {
		List user_list = new ArrayList();
		// Fetching cursor object for iterating on the database records.
		DBCursor cursor = coll.find();	
		while(cursor.hasNext()) {			
			DBObject dbObject = cursor.next();
			Account user = new Account();
			user.setUserName(dbObject.get("username").toString());
			user.setPassword(dbObject.get("password").toString());
			user.setEmail(dbObject.get("email").toString());
			user.setContactNumber(dbObject.get("contactNumber").toString());

			// Adding the user details to the list.
			user_list.add(user);
		}
		log.debug("Total records fetched from the mongo database are= " + user_list.size());
		return user_list;
	}

	// Add a new user to the mongo database.
	public boolean add(Account user) {
		boolean acc1 = false;
		Random ran = new Random();
		try {			
			// Create a new object and add the new user details to this object.
			BasicDBObject doc = new BasicDBObject();
			doc.put("id", String.valueOf(ran.nextInt(100))); 
			doc.put("username", user.getUserName());
			doc.put("password", user.getPassword());
			doc.put("email", user.getEmail());
			doc.put("contactNumber", user.getContactNumber());

			// Save a new user to the mongo collection.
			coll.insert(doc);
			
			
			acc1 = true;
			
		} catch (Exception e) {
			acc1 = false;
			log.error("An error occurred while saving a new user to the mongo database", e);			
		}
		return acc1;
	}

	// Update the selected user in the mongo database.
	public Boolean edit(Account user) {
		return null;
		/*boolean output = false;
		log.debug("Updating the existing user in the mongo database; Entered user_id is= " + user.getId());
		try {
			// Fetching the user details.
			BasicDBObject existing = (BasicDBObject) getDBObject(user.getId());

			DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

			// Create a new object and assign the updated details.
			BasicDBObject edited = new BasicDBObject();
			edited.put("id", user.getId()); 
			edited.put("name", user.getName());

			// Update the existing user to the mongo database.
			coll.update(existing, edited);
			output = true;
		} catch (Exception e) {
			output = false;
			log.error("An error has occurred while updating an existing user to the mongo database", e);			
		}
		return output;*/
	}

	// Delete a user from the mongo database.
	public Boolean delete(String id) {
		return null;
		/*boolean output = false;
		log.debug("Deleting an existing user from the mongo database; Entered user_id is= " + id);
		try {
			// Fetching the required user from the mongo database.
			BasicDBObject item = (BasicDBObject) getDBObject(id);

			DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

			// Deleting the selected user from the mongo database.
			coll.remove(item);
			output = true;			
		} catch (Exception e) {
			output = false;
			log.error("An error occurred while deleting an existing user from the mongo database", e);			
		}	
		return output;*/
	}

	// Fetching a particular record from the mongo database.
	private DBObject getDBObject(String id) {
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Fetching the record object from the mongo database.
		DBObject where_query = new BasicDBObject();

		// Put the selected user_id to search.
		where_query.put("id", id);
		return coll.findOne(where_query);
	}

	// Fetching a single user details from the mongo database.
	public Account findUserId(String id) {
		return null;
		/*Account u = new Account();
		DBCollection coll = MongoFactory.getCollection(db_name, db_collection);

		// Fetching the record object from the mongo database.
		DBObject where_query = new BasicDBObject();
		where_query.put("id", id);

		DBObject dbo = coll.findOne(where_query);		
		u.setId(dbo.get("id").toString());
		u.setName(dbo.get("name").toString());

		// Return user object.
		return u;*/
	}
}