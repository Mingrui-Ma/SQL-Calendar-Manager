package com.connector.calendar_db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * The <code>CalendarConnector</code> class establishes a connection to a calendar database and 
 * defines various methods that interact with the database. 
 * <p>
 * A JDBC driver as well as connection to a SQL database are required for this class to function properly.
 * <p>
 * <code>CalendarConnector</code> is encapsulated around a <code>Connection</code> object and methods use it to communicate with the database. 
 * The user will have to close the <code>Connection</code> manually. 
 * <p> 
 * <br>
 * <b> Initiating the database</b>
 * <p>
 * The constructor with a <code>Connection</code> argument and <code>setConnection()</code> 
 * creates the <code>'Calendar'</code> database, and the <code>'Calendar_Records'</code> table, if they don't already exist.
 * <p>
 * Until the database is initiated through either means, the methods that communicate with the database will not work and will exit early. 
 * @author Mingrui Ma
 *
 */
public class CalendarConnector {
	private Connection con;
	/*
	 * Ready is only true if the <code>CalendarConnector</code> instance has a valid <code>Connection</code>. 
	 */
	private boolean ready;
	
	/**
	 * The default constructor leaves the <code>Connection</code> Object uninitiated.
	 * <code>setConnection()</code> must be called before the instance can work properly.
	 */
	public CalendarConnector()	{
		ready = false;
	}
	
	/**
	 * Sets the argument object as the <code>Connection</code> object that the caller is holding. 
	 * <p>
	 * The database will then be initiated.
	 * <p>
	 * Creates the <code>'Calendar'</code> database, and the <code>'Calendar_Records'</code> table, if they don't already exist.
	 * <br>
	 * The table contains the following columns: <ul>
	 * 	 <li>'ID' - data type <code>INT</code>, auto increments;</li>
	 *   <li>'Date' - data type <code>DATE</code>;</li>
	 *   <li>'Time' - data type <code>TIME</code>, default '0:00';</li>
	 *   <li>'Event' - data type <code>VARCHAR(1023)</code>, default 'No record';</li>
	 *   <li>'Note' - data type <code>VARCHAR(1023)</code>, default 'No record'. </li>
	 *   </ul>
	 * @param con Connection object to the SQL database.
	 */
	public CalendarConnector(Connection connection)	{
		this.con = connection;
		try	{			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			stmt.addBatch("CREATE DATABASE IF NOT EXISTS Calendar");
			
			stmt.addBatch("USE Calendar");
			
			stmt.addBatch("CREATE TABLE IF NOT EXISTS `Calendar_Records` (\r\n"
					+ "	`ID` INT PRIMARY KEY AUTO_INCREMENT,\r\n"
					+ "	`Date` DATE,\r\n"
					+ " `Time` TIME DEFAULT '0:00',\r\n"
					+ " `Event` VARCHAR(1023) DEFAULT 'No record',\r\n"
					+ " `Note` VARCHAR(1023) DEFAULT 'No record'\r\n"
					+ " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
			
			stmt.executeBatch();
			con.commit();	
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		ready = true;
	}
	
	/**
	 * @return The <code>Connection</code> object that the calling <code>CalendarConnector</code> is holding.
	 */
	public Connection getConnection()	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
		}
			return this.con;
	}
	
	/**
	 * Sets the argument object as the <code>Connection</code> object that the caller is holding. 
	 * <p>
	 * The database will then be initiated.
	 * <p>
	 * Creates the <code>'Calendar'</code> database, and the <code>'Calendar_Records'</code> table, if they don't already exist.
	 * <br>
	 * The table contains the following columns: <ul>
	 * 	 <li>'ID' - data type <code>INT</code>, auto increments;</li>
	 *   <li>'Date' - data type <code>DATE</code>;</li>
	 *   <li>'Time' - data type <code>TIME</code>, default '0:00';</li>
	 *   <li>'Event' - data type <code>VARCHAR(1023)</code>, default 'No record';</li>
	 *   <li>'Note' - data type <code>VARCHAR(1023)</code>, default 'No record'. </li>
	 *   </ul>
	 * @param connection The <code>Connection</code> object to use for the calling <code>CalendarConnector</code>.
	 */
	public void setConnection(Connection connection)	{
		this.con = connection;
		try	{			
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

			stmt.addBatch("CREATE DATABASE IF NOT EXISTS Calendar");
			
			stmt.addBatch("USE Calendar");
			
			stmt.addBatch("CREATE TABLE IF NOT EXISTS `Calendar_Records` (\r\n"
					+ "	`ID` INT PRIMARY KEY AUTO_INCREMENT,\r\n"
					+ "	`Date` DATE,\r\n"
					+ " `Time` TIME DEFAULT '0:00',\r\n"
					+ " `Event` VARCHAR(1023) DEFAULT 'No record',\r\n"
					+ " `Note` VARCHAR(1023) DEFAULT 'No record'\r\n"
					+ " ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;");
			
			stmt.executeBatch();
			con.commit();	
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		ready = true;
	}

	
	/**
	 * @param cal The <code>GregorianCalendar</code> object to get a string for.
	 * @return the date of the given <code>GregorianCalendar</code> as a string, in the format "YYYY-MM-DD".
	 */
	static String getDate(GregorianCalendar cal)	{
		Integer day = cal.get(Calendar.DAY_OF_MONTH), 
				month = cal.get(Calendar.MONTH) + 1,  
				yr = cal.get(Calendar.YEAR);
		String date = yr + "-"
					+ month + "-"
					+ day;	
		return date;
	}
	
	/**
	 * Given an <code>ArrayList<String[]></code>, print all of its contents.
	 * <br>
	 * This method is intended to work with other methods in <code>CalendarConnector</code>. 
	 * @param queryResult
	 */
	public static void displayRecords(ArrayList<String[]> queryResult)	{
		System.out.println("Displaying query results. ");		
		System.out.println("Columns: ID;  Date;  Time;  Event;  Note ");
		
		String[] record;
		Iterator<String[]> itr = queryResult.iterator();
		while(itr.hasNext())	{
			record = itr.next();
			System.out.println("         " + record[0] + ";  " + record[1] + ";  " + record[2] + ";  " + record[3] + ";  " + record[4]);
		}
	}
	
	/**
	 * Adds a record to the <code>Calendar_Records</code> table, using the given information.
	 * <p>
	 * Returns 1 if the operation is successful, and 0 if it is not.
	 * <p>
	 * Passing the string "default" to any of the string arguments will cause the default value of the respective column to be used.
	 * <p>
	 * @param cal <code>GregorianCalendar</code> object corresponding to the date to add the record for.
	 * @param Time Time of day to create the record for. Examples of accepted formats: 
	 * "12:00", "12:00:00", "120000" (all meaning noon), "12" (meaning 12 seconds past midnight).
	 * @param event The event string to add.
	 * @param note The note string to add.
	 */
	public int addRecord(GregorianCalendar cal, String time, String event, String note)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return 0;
		}
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.addBatch("USE calendar");
			
			String date = getDate(cal),
					sql = "INSERT INTO calendar_records (date, ";
			if(!time.toLowerCase().equals("default"))	{
				sql += "time, ";
			}
			if(!event.toLowerCase().equals("default"))	{
				sql += "event, ";
			}
			if(!note.toLowerCase().equals("default"))	{
				sql += "note, ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += ") VALUES ('" + date + "', ";
			if(!time.toLowerCase().equals("default"))	{
				sql += "'" + time + "', ";
			}
			if(!event.toLowerCase().equals("default"))	{
				sql += "'" + event + "', ";
			}
			if(!note.toLowerCase().equals("default"))	{
				sql += "'" + note + "', ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += ");";
			//System.out.println(sql);
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
			return 1;
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Adds a record to the <code>Calendar_Records</code> table, using the given information.
	 * <p>
	 * Returns 1 if the operation is successful, and 0 if it is not.
	 * <p>
	 * Passing the string "default" to any of the string arguments (except <code>date</code>) 
	 * will cause the default value of the respective column to be used.
	 * <p>
	 * @param date The date to add the record on.
	 * @param Time Time of day to create the record for. Examples of accepted formats: 
	 * "12:00", "12:00:00", "120000" (all meaning noon), "12" (meaning 12 seconds past midnight).
	 * @param event The event string to add.
	 * @param note The note string to add.
	 */
	public int addRecord(String date, String time, String event, String note)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return 0;
		}
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.addBatch("USE calendar");
			
			String sql = "INSERT INTO calendar_records (date, ";
			if(!time.toLowerCase().equals("default"))	{
				sql += "time, ";
			}
			if(!event.toLowerCase().equals("default"))	{
				sql += "event, ";
			}
			if(!note.toLowerCase().equals("default"))	{
				sql += "note, ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += ") VALUES ('" + date + "', ";
			if(!time.toLowerCase().equals("default"))	{
				sql += "'" + time + "', ";
			}
			if(!event.toLowerCase().equals("default"))	{
				sql += "'" + event + "', ";
			}
			if(!note.toLowerCase().equals("default"))	{
				sql += "'" + note + "', ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += ");";
			//System.out.println(sql);
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
			return 1;
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Read all records in the table. The records are sorted by ID.
	 * @param cal
	 * @return An ArrayList of arrays of strings. Each String[] represents a record in the table.
	 */
	public ArrayList<String[]> readAllRecords()	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return new ArrayList<String[]>();
		}
		
		ArrayList<String[]> recordList = new ArrayList<>();	
		String[] record;
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.execute("USE Calendar");
			
			String sql = "SELECT * "
					+ "FROM Calendar_Records;";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())	{
				record = new String[5];
				record[0] = Integer.toString(rs.getInt(1));
				record[1] = rs.getDate(2).toString();
				record[2] = rs.getTime(3).toString();
				record[3] = rs.getString(4);
				record[4] = rs.getString(5);
				recordList.add(record);
				//System.out.println(record[0] + ", " + record[1] + ", " + record[2] + ", " + record[3] + ", " + record[4]);
			}
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		if(recordList.size()==0)	{		//query got no record
			record = new String[5];
			record[0] = "No id";
			record[1] = "No date";
			record[2] = "0:00";
			record[3] = "No record";
			record[4] = "No record";
			recordList.add(record);
		}
		return recordList;
	}
	
	/**
	 * Read all records for the given date. The records are sorted by ID.
	 * @param date A string of the date to search for. Must in in format "YYYY-MM-DD".
	 * @return An ArrayList of arrays of strings. Each String[] represents a record in the table.
	 */
	public ArrayList<String[]> readRecordByDate(String date)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return new ArrayList<String[]>();
		}
		/*
		 * Bug: ID = 8 returns twice for some reason?
		 */
		ArrayList<String[]> recordList = new ArrayList<>();
		String[] record; 
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.execute("USE Calendar");
			
			String sql = "SELECT * "
					+ "FROM Calendar_Records "
					+ "WHERE date = '" + date + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())	{	
				record = new String[5];
				record[0] = Integer.toString(rs.getInt(1));
				record[1] = rs.getDate(2).toString();
				record[2] = rs.getTime(3).toString();
				record[3] = rs.getString(4);
				record[4] = rs.getString(5);
				recordList.add(record);
			}
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		if(recordList.size()==0)	{		//query got no record
			record = new String[5];
			record[0] = "No id";
			record[1] = "No date";
			record[2] = "0:00";
			record[3] = "No record";
			record[4] = "No record";
			recordList.add(record);
		}
		return recordList;
	}
	
	/**
	 * Read all records for the given date. The records are sorted by ID.
	 * @param cal The <code>GregorianCalendar</code> for the date to search for.
	 * @return An ArrayList of arrays of strings. Each String[] represents a record in the table.
	 */
	public ArrayList<String[]> readRecordByDate(GregorianCalendar cal)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return new ArrayList<String[]>();
		}
		/*
		 * Bug: ID = 8 returns twice for some reason?
		 */
		ArrayList<String[]> recordList = new ArrayList<>();
		String[] record;
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.execute("USE Calendar");
			
			String date = getDate(cal);
			String sql = "SELECT * "
					+ "FROM Calendar_Records "
					+ "WHERE date = '" + date + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())	{	
				record = new String[5];
				record[0] = Integer.toString(rs.getInt(1));
				record[1] = rs.getDate(2).toString();
				record[2] = rs.getTime(3).toString();
				record[3] = rs.getString(4);
				record[4] = rs.getString(5);
				recordList.add(record);
			}
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		if(recordList.size()==0)	{		//query got no record
			record = new String[5];
			record[0] = "No id";
			record[1] = "No date";
			record[2] = "0:00";
			record[3] = "No record";
			record[4] = "No record";
			recordList.add(record);
		}
		return recordList;
	}
	
	/**
	 * Read all records for the given ID.
	 * @param cal
	 * @return An ArrayList of arrays of strings. Each String[] represents a record in the table.
	 */
	public ArrayList<String[]> readRecordByID(int id)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return new ArrayList<String[]>();
		}
		ArrayList<String[]> recordList = new ArrayList<>();
		String[] record;
		try	{
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			
			stmt.execute("USE Calendar");
			
			String sql = "SELECT * "
					+ "FROM Calendar_Records "
					+ "WHERE id = '" + id + "';";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next())	{
				record = new String[5];
				record[0] = Integer.toString(rs.getInt(1));
				record[1] = rs.getDate(2).toString();
				record[2] = rs.getTime(3).toString();
				record[3] = rs.getString(4);
				record[4] = rs.getString(5);
				recordList.add(record);
			}
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
		if(recordList.size()==0)	{		//query got no record
			record = new String[5];
			record[0] = "No id";
			record[1] = "No date";
			record[2] = "0:00";
			record[3] = "No record";
			record[4] = "No record";
			recordList.add(record);
		}
		return recordList;
	}
	
	/**
	 * Update the record with the given id.
	 * <p>
	 * Does nothing if no record has the given id.
	 * <p>
	 * Passing "default" as an argument will leave the respective column unchanged. 
	 * At least one column must be changed.
	 * <p>
	 * @param id the ID of the record to update.
	 * @param newDate the new date to update the record to. Follows the format "YYYY-MM-DD".
	 * @param newTime the new time to update the record to.
	 * @param newEvent the new event to update the record to.
	 * @param newNote the new note to update the record to.
	 */
	public void updateRecordByID(int id, String newDate, String newTime, String newEvent, String newNote)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return;
		}
		try	{	
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
			
			stmt.addBatch("USE calendar");
			
			String sql = "UPDATE calendar_records "
					+ "SET "; 
			if(!newDate.toLowerCase().equals("default"))	{
				sql += "date = '" + newDate+ "', ";
			}
			if(!newTime.toLowerCase().equals("default"))	{
				sql += "time = '" + newTime + "', ";
			}
			if(!newEvent.toLowerCase().equals("default"))	{
				sql += "event = '" + newEvent + "', ";
			}
			if(!newNote.toLowerCase().equals("default"))	{
				sql += "note = '" + newNote + "', ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += " WHERE id = " + id + ";";
			//System.out.println(sql);
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
	}
	
	/**
	 * Update all records with the given original date.
	 * <p>
	 * Does nothing if no record has the given date.
	 * <p>
	 * Passing "default" as an argument will leave the respective column unchanged. 
	 * At least one column must be changed.
	 * <p>
	 * @param oldDate the original date of the record to update. Follows the format "YYYY-MM-DD".
	 * @param newDate the new date to update the record to. Follows the format "YYYY-MM-DD".
	 * @param newTime the new time to update the record to.
	 * @param newEvent the new event to update the record to.
	 * @param newNote the new note to update the record to.
	 */
	public void updateRecordByDate(String oldDate, String newDate, String newTime, String newEvent, String newNote)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return;
		}
		try	{	
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
			
			stmt.addBatch("USE calendar");
			
			String sql = "UPDATE calendar_records "
					+ "SET "; 
			if(!newDate.toLowerCase().equals("default"))	{
				sql += "date = '" + newDate+ "', ";
			}
			if(!newTime.toLowerCase().equals("default"))	{
				sql += "time = '" + newTime + "', ";
			}
			if(!newEvent.toLowerCase().equals("default"))	{
				sql += "event = '" + newEvent + "', ";
			}
			if(!newNote.toLowerCase().equals("default"))	{
				sql += "note = '" + newNote + "', ";
			}
			sql = sql.substring(0, sql.length()-2);		//remove trailing comma
			sql += " WHERE date = '" + oldDate + "';";
			System.out.println(sql);
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove the record of the given id.
	 * <p>
	 * Does nothing if no record has the given id.
	 * @param id the ID of the record to remove.
	 */
	public void removeRecordByID(int id)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return;
		}
		try	{	
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
			
			stmt.addBatch("USE calendar");
			
			String sql = "DELETE FROM calendar_records "
					+ "WHERE id = " + id;
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove records on the given date.
	 * <p>
	 * Does nothing if no record has the given date.
	 * @param id the ID of the record to remove.
	 */
	public void removeRecordByDate(String date)	{
		if(!ready)	{
			System.out.println("Error: uninitiated connection.");
			return;
		}
		try	{	
			Statement stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);		
			
			stmt.addBatch("USE calendar");
			
			String sql = "DELETE FROM calendar_records "
					+ "WHERE date = '" + date + "';";
			
			stmt.addBatch(sql);
			stmt.executeBatch();
			con.commit();
		}	catch(Exception e)	{ 
			e.printStackTrace();
		}
	}
}
