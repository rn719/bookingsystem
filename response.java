/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.project;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;

        import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author MAHE
 */
public class response extends JPanel
{   JButton jb=new JButton();
JFrame jf=new JFrame();
 
JTable table=new JTable();
    String username;

    public response(String s)
    {
        username = s;
        
        System.out.print("hello");
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        //  Connect to an MySQL Database, run query, get result set
        // String url = "jdbc:mysql://localhost:3306/faculty?zeroDateTimeBehavior=convertToNull";
        // String userid = "root";
        // String password = "root";
        String sql="";
        if(username.equals("CSOfficer")){
        sql = "SELECT Application_ID, Club_Name, Event_Name, CSO FROM Application naturanl join Clubs";
        }
        else if(username.equals("AsDir")){
             sql = "SELECT Application_ID, Club_Name, Event_Name, AD FROM Application natural join Clubs";
        }
        else{
            sql="Select Application_ID, Club_Name, Event_Name, FA from Application natural join Clubs where Clubs.Faculty_Id='"+username+"'";
        }


        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try {
            System.out.print(sql);
            Connection connection = DriverManager.getConnection(SEProject.myUrl,"root","root");
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery( sql );
        
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
      
            //  Get column names
            for (int i = 1; i <= columns; i++)
            {
                columnNames.add( md.getColumnName(i) );
            }

            //  Get row data
            while (rs.next())
            {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++)
                {
//                    System.out.print(rs.getObject(i));
                    row.add( rs.getObject(i) );
                }

                data.add( row );
            }
        }catch (Exception e)
        {
            System.out.println(e);
        }

        // Create Vectors and copy over elements from ArrayLists to them
        // Vector is deprecated but I am using them in this example to keep 
        // things simple - the best practice would be to create a custom defined
        // class which inherits from the AbstractTableModel class
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++)
        {
            ArrayList subArray = (ArrayList)data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++)
            {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++ )
            columnNamesVector.add(columnNames.get(i));

        //headers for the table
//        String[] columns = new String[] {
//            "Id", "Name", "Hourly Rate", "Part Time"
//        };
//         
//        //actual data for the table in a 2d array
//        Object[][] data = new Object[][] {
//            {1, "John", 40.0, false },
//            {2, "Rambo", 70.0, false },
//            {3, "Zorro", 60.0, true },
//        };
 
        //create table with data
         table = new JTable(dataVector, columnNamesVector)        {
            public Class getColumnClass(int column)
            {
                for (int row = 0; row < getRowCount(); row++)
                {
                    Object o = getValueAt(row, column);

                    if (o != null)
                    {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };
         
        //add the table to the frame
        this.add(new JScrollPane(table));
         
         jf.add(this);
        jf.setTitle("Pending Requests");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        jf.pack();
       // this.setSize(400,400);
       this.setLocation(0,0);
        this.setSize(500,500); 
       jf.setSize(800,800);
      JButton jb=new JButton();
        jb.setText("BACK");
       // jf.add(jb);
     //  this.setLocation(100, 100);
       //jb.setLocation(600,05);
       // jb.setSize(30,40);
          jf.setVisible(true);
         this.add(jb);
         JButton jb1=new JButton("SUBMIT");
          this.add(jb1);
        this.setVisible(true);
        jb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jb1ActionPerformed(evt);
            }
        });
        
        jb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActionPerformed(evt);
            }
        });

  
        
        
    }
     private void jb1ActionPerformed(java.awt.event.ActionEvent evt) {     
        try{
           
         Connection conn = DriverManager.getConnection(SEProject.myUrl,"root","root");
//         PreparedStatement pst ;
         int rows=table.getRowCount();

         conn.setAutoCommit(false);
          System.out.print("hello");

for(int row = 0; row<rows; row++)
{
   
    int app_ID = (int)table.getValueAt(row, 0);
    
//    String Club_Name = (String)table.getValueAt(row, 1);
//    String time1 = (String)table.getValueAt(row, 2);
//    boolean fa = (Boolean)table.getValueAt(row, 2);
//    boolean ad = (Boolean)table.getValueAt(row, 3);
    boolean perm = (Boolean)table.getValueAt(row, 3);

    try
    {
        String queryco="";
        if(username.equals("CSOfficer")){
        queryco = "update Application set cso="+perm+" where Application_ID='"+app_ID+"'";
        }
        else if(username.equals("AsDir")){
            queryco = "update Application set ad="+perm+" where Application_ID='"+app_ID+"'";
        }
        else{
            queryco = "update Application set fa="+perm+" where Application_ID='"+app_ID+"'";
        }
        System.out.print(queryco);
        PreparedStatement pst = conn.prepareStatement(queryco);

//        pst.setString(1,club_name);
//        pst.setString(2, room1);
//        pst.setString(3, time1);
//        pst.setBoolean(4, fa);
//        pst.setBoolean(5,ad);
//         pst.setBoolean(6, cso);
        pst.addBatch();
        pst.executeBatch();
        conn.commit();
    }
    catch(Exception e)
    {
        JOptionPane.showMessageDialog(this,e.getMessage());
    }


}

}
catch(Exception e){
    JOptionPane.showMessageDialog(this,e.getMessage());
}
   /*   
 try{
           
         Connection con = DriverManager.getConnection( "jdbc:mysql://localhost:3306/faculty?zeroDateTimeBehavior=convertToNull", "root", "root" );
         
         PreparedStatement pst = null;
       DefaultTableModel dtm01 = (DefaultTableModel) table.getModel();
             con.setAutoCommit(false);
        String sd0 = null;
        
        for (int i = 1; i < dtm01.getColumnCount(); i++) {
             
             System.out.println(dtm01.getColumnName(1));
            for (int j = 0; j < dtm01.getRowCount(); j++) {
                try {
                    sd0 = dtm01.getValueAt(j, i).toString();

                    String sql = "update faculty1 set "+dtm01.getColumnName(i)+"='"+sd0+"' where club_name='"+dtm01.getValueAt(j, 0).toString()+"'";

                    pst=con.prepareStatement(sql);
                    pst.execute();
                    System.out.println(sql);
                } catch (Exception ex) {
                    // Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.print(ex);
                }

            }

        }
       } catch (SQLException ex) {
                    // Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
                }
        // TODO add your handling code here:
        
         jf.dispose();*/
    }            
     private void jbActionPerformed(java.awt.event.ActionEvent evt) {                                         
       jf.dispose();
       new FA().setVisible(true);
        // TODO add your handling code here:
    }            
     
     
     public void thequery(String a)
     {
         
         
     }
    public static void main(String[] args)
    {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new response();
//            }
//        });
    }   
}
