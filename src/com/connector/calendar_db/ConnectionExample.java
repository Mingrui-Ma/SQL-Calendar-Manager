package com.connector.calendar_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class ConnectionExample {
	public static void main(String[] args) {
		try	{
			//input your SQL connection, username and password here
			final Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306","username","password");		
			con.setAutoCommit(false);	
			CalendarConnector calConnector= new CalendarConnector(con);
			
			//add multiple records to the table
			String[][] manyRecords = {
					{"2020-08-15", "default", "birthday", "yay"},
					{"2020-09-07", "default", "labour day", "default"},
					{"2020-10-12", "default", "Thanksgiving day", "turkey day"},
					{"2020-10-30", "default", "Halloween", "Trick or Treat"},
					{"2020-11-01", "default", "default", "Daylight saving time ends"},
					{"2020-12-24", "default", "Christmas eve", "default"},
					{"2020-12-31", "23:59", "New year\\'s eve", "count them"},
					{"2021-02-14", "11:11", "Valentine\\'s day", "I wish"},
					{"2021-02-20", "23:00", "can\\'t sleep", "did I leave the stove on?"}
			};
			int	result,
				success = 0,
				failure = 0;
			for(String[] record:manyRecords)	{
				result = calConnector.addRecord(record[0], record[1], record[2], record[3]);
				if(result == 1)
					success++;
				if(result == 0)
					failure++;
			}
			System.out.println("Insert operation complete. "
					+ "successful inserts: " + success
					+ "; unsuccessful inserts: " + failure);
			System.out.println();
			
			//update some records
			calConnector.updateRecordByDate("2020-10-30", "2020-10-31", "default", "default", "Halloween is on the 31st.");
			calConnector.updateRecordByDate("2021-02-20", "2021-02-20", "23:20", "default", "it\\'s probably fine.");
			System.out.println("Update operation complete.");
			System.out.println();
			
			//delete some records
			calConnector.removeRecordByDate("2020-09-07");
			System.out.println("Removal operation complete.");
			System.out.println();
			
			//display some records
			String jan1 = "2021-01-01";
			ArrayList<String[]> recordsOnJan1 = calConnector.readRecordByDate(jan1);
			System.out.println("Record count on " + jan1 + ": " + recordsOnJan1.size());
			System.out.println();
			CalendarConnector.displayRecords(calConnector.readAllRecords());
			
			con.close();
		}	catch(Exception e)	{
			e.printStackTrace();
		}
	}
}