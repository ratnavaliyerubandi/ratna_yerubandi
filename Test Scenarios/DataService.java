package com.noesisinformatica.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class DataService {

    public DataService(){
        // save some initial data
        saveTerm("First term");
        saveTerm("Second term");
        saveTerm("Another term");
        saveTerm("Miscellaneous term");
        saveTerm("");
    }
    
    /**
     * I am using HyperSQL DB. Also assuming HSQLDB driver is available in the path.
     * 
     */
    private Connection getConnection() throws Exception {
	    Class.forName("org.hsqldb.jdbcDriver" );
		Connection conn = DriverManager.getConnection("jdbc:hsqldb:dsdb", "user", "pwd");
		
		return conn;
	}
	
	String getTermForId(Long id){
	    Connection conn = null;
    	PreparedStatement pstmtSelectSQL = null;
    	ResultSet rs = null;
    	String selectSQL = null;
    	String tempTerm = null;
    	
        try {        
            conn = this.getConnection();
            selectSQL = "select term from terms_map where id = ?";
            
            pstmtSelectSQL = conn.prepareStatement(selectSQL);
            pstmtSelectSQL.setLong(id);
            rs =  pstmtSelectSQL.executeQuery();

            while(rs.next()) {
            	tempTerm = rs.getString("term")
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
            		rs.close();
            	}
            	if (pstmtSelectSQL != null) {
	            	pstmtSelectSQL.close();
           		}
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return tempTerm;
    }

    /**
     * Assuming a table by name 'terms_map' exists in db 'dsdb'.
     *
     */ 
    void saveTerm(String term) {
    	Connection conn = null;
    	PreparedStatement pstmtSelectSQL = null;
    	PreparedStatement pstmtInsertSQL = null;
    	ResultSet rs = null;
    	String selectSQL = null;
    	String insertSQL = null;
    	String tempTerm = null;
    	
        try {        
            conn = this.getConnection();
            selectSQL = "select term from terms_map where term = ?";
            insertSQL = "insert into terms_map(id, term) values(?, ?)";
            
            pstmtSelectSQL = conn.prepareStatement(selectSQL);
            pstmtSelectSQL.setString(term);
            rs =  pstmtSelectSQL.executeQuery();
            /** 
             * ResultSet will have a row only when the term is already present in 
             * terms_map table. Control will enter below IF only when the term is not 
             * present in terms_map table.
             */
            if (! rs.next()) {
            	pstmtInsertSQL = conn.prepareStatement(insertSQL);
            	pstmtInsertSQL.setLong(1, (getLastUsedId(conn) + 1));
            	pstmtInsertSQL.setString(2, term);
            	pstmtInsertSQL.execute();
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
            		rs.close();
            	}
            	if (pstmtSelectSQL != null) {
	            	pstmtSelectSQL.close();
           		}
            	if (pstmtInsertSQL != null) {
                	pstmtInsertSQL.close();
            	}
                if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
    
    Collection<String> getAllTerm() {
        Connection conn = null;
    	Statement stmtSelectSQL = null;
    	ResultSet rs = null;
    	List<String> terms = null; 
    	
        try {     
        	terms = new ArrayList<String>();
        	
        	conn = this.getConnection();
			stmtSelectSQL = conn.createStatement(); 
            rs =  stmtSelectSQL.executeQuery("select term from terms_map");

            while(rs.next()) {
            	terms.add(rs.getString("term"));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
            		rs.close();
            	}
            	if (stmtSelectSQL != null) {
	            	stmtSelectSQL.close();
           		}
           		if (conn != null) {
                    conn.close();
                }
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return terms;
    }

    public long getLastUsedId(Connection conn) {
    	Statement stmtSelectSQL = null;
    	ResultSet rs = null;
    	long lastID = -1;
    	
        try {     
        	stmtSelectSQL = conn.createStatement(); 
            rs =  stmtSelectSQL.executeQuery("select max(id) as max_id from terms_map");

            while(rs.next()) {
            	lastID = rs.getLong("max_id")
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
            		rs.close();
            	}
            	if (stmtSelectSQL != null) {
	            	stmtSelectSQL.close();
           		}
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        return lastID;
    }
    
}
