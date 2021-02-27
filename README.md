# SQL-Calendar-Manager

#### A project that manages a calendar table in MySQL RDBMS using Java and a JDBC driver. [Use case Diagram](https://imgur.com/ta07l4Z.jpg)

This project manages a mock calendar app in the form of a table on MySQL. It will create the database and table, and there are various methods that perform the basic tasks required for managing a table in MySQL (Refer to the Javadoc for further details). To use simply call the methods; no writing SQL queries needed.

##### Requirements

The following are needed to run the project:
1. A JVM;
2. A JDBC driver version 4.0 or later (I used MySQL Connector/J 8.0.22);
3. A connection to MySQL RDBMS.

##### Features
1. Javadoc provided for every method, class and constructor declaration.
2. SQL Connection checking: SQL operations will abort if a connection to MySQL have not been provided.
3. default value allowed: passing "default" to the methods will use the column's default/current value.
