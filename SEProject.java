/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.project;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author divyanshu
 */
public class SEProject {

    /**
     * @param args the command line arguments
     */
     public static String myUrl= "jdbc:mysql://localhost:3306/Faculty?zeroDateTimeBehavior=convertToNull"; 
    public static void main(String[] args) {
        // TODO code application logic here
        try{
        String myDriver = "com.mysql.jdbc.Driver";                                                                                                                                                                                                                                                                                                                                                          
       
                
        Class.forName(myDriver);
        //mysql driver configuration with url and password of mysql server
//        Connection conn = DriverManager.getConnection(myUrl,"root","root");
        }catch(Exception e){
            System.out.println("error: "+e);
        }
    }
    
}
