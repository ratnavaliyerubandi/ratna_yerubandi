package com.noesisinformatica.test;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

    DataService dataService = null;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // instantiate data service
        dataService = new DataService();
    }

    public void testApp(){

        // some terms must have been created during initialisation
        int termInDataService = dataService.getAllTerm().size();
        System.out.println( "Number of terms : " + termInDataService);
        assert termInDataService > 0;
    }

    public void testSaveTerm() throws Exception {

        int termInDataService = dataService.getAllTerm().size();
        // adding term should increment number
        dataService.saveTerm("Term from app");
        assert dataService.getAllTerm().size() > termInDataService;
    }

    public void testGetTerm() throws Exception {

        String testTerm = "Second test term";
        // adding term and getting last used id should give us the id
        dataService.saveTerm(testTerm);
        long id = dataService.getLastUsedId();
        // retrieving using id should give us the term
        assertEquals(testTerm, dataService.getTermForId(id));
    }
}
