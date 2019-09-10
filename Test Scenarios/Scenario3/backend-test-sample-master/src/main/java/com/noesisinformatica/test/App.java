package com.noesisinformatica.test;

/**
 * Hello world!
 *
 */
public class App {

    public static void main( String[] args )
    {
        DatabaseConnectionInfo iDatabaseConnectionInfo = new DatabaseConnectionInfo();
        iDatabaseConnectionInfo.setDriverClass("org.hsqldb.jdbcDriver");
        iDatabaseConnectionInfo.setUrl("jdbc:hsqldb:termdb");
        iDatabaseConnectionInfo.setUsername("root");
        iDatabaseConnectionInfo.setPassword("admin");

        DataService dataService = new DataService(iDatabaseConnectionInfo);
        System.out.println( "Number of terms : " + dataService.getAllTerm().size());
        dataService.saveTerm("Term from app");
    }
}
