package Data;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;

public class App {

	private static DB dbInstance = null;

	public App(String hostName, int port, String databaseName) {
		if (dbInstance == null) {
			dbInstance = connect(hostName, port, databaseName);
		}
	}

	public DB getDB() {
		return dbInstance;
	}

	public DBCollection getCollection(String collectionName) {
		return getDB().getCollection(collectionName);
	}

	public DB connect(String hostName, int port, String databaseName) {
		MongoClient mc = new MongoClient(hostName, port);
		DB db = mc.getDB(databaseName);
		return db;
	}

	public void dropDB() {
		getDB().dropDatabase();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		App app = new App("localhost", 27017, "sample");
		DBCollection collection = app.getCollection("user");
		// CRUD operations
		/*
		 * @insert
		 * 
		 * app.insert(collection);
		 */

		/*
		 * @list all
		 * 
		 * app.getAll(collection);
		 */
		/*
		 * @list with filter System.out.println("filter: ");
		 * 
		 * final DBParameter param = new DBParameter("name", "albert");
		 * app.getList(collection, param);
		 */
		/*
		 * @update
		 * 
		 * app.update(collection);
		 */
		/*
		 * @collection drop
		 * 
		 * app.drop(collection);
		 */
		/*
		 * @database drop
		 * 
		 * app.dropDB();
		 */
	}

	public void getAll(DBCollection collection) {
		DBCursor curs = collection.find();
		while (curs.hasNext()) {
			System.out.println(curs.next());
		}
	}

	public void getList(DBCollection collection, DBParameter param) {
		DBCursor curs = collection.find(new BasicDBObject(param.getName(), param.getValue()));
		for (DBObject cur : curs) {
			System.out.println(cur);
		}
	}

	public void drop(DBCollection collection) {

		collection.drop();
		System.out.println("silme sonrasý");
		getAll(collection);
	}

	public WriteResult update(DBCollection collection) {
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.append("name", "albert").append("date", "1876").append("country", "Check");
		WriteResult result = collection.update(new BasicDBObject("date", "1879"), new BasicDBObject("$set", updateObj));
		getAll(collection);
		return result;

	}

	public void delete(DBCollection collection) {
		BasicDBObject updateObj = new BasicDBObject();
		updateObj.append("name", "albert").append("date", "1876").append("country", "Check");
		collection.dropIndex(new BasicDBObject("date", "1879"));
		getAll(collection);

	}

	public WriteResult insert(DBCollection collection) {
		// Insert

		BasicDBObject obj = new BasicDBObject();
		obj.append("name", "albert").append("date", "1879").append("country", "Germany");
		WriteResult result = collection.insert(obj);
		System.out.println(result);

		obj = new BasicDBObject();
		obj.append("name", "newton").append("date", "1643").append("country", "England");
		result = collection.insert(obj);
		System.out.println(result);
		return result;
	}

	static class DBParameter {
		String name, value;

		public DBParameter(String n, String v) {
			name = n;
			value = v;
		}

		public String getName() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

}
