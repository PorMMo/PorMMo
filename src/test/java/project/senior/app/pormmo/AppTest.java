/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package project.senior.app.pormmo;

import junit.framework.TestCase;

/**
 *
 * @author techmage
 */
public class AppTest extends TestCase {
    
    public AppTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of main method, of class App.
     */
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        App.main(args);
        // TODO review the generated test code and remove the default call to fail.
        if(1==2) assert(true);
        else assert(false);
    }
}
