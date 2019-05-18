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
public class status extends JPanel
{   
    JButton jb=new JButton();
    JFrame jf=new JFrame();
 
    JTable table=new JTable();
    
   
    
    
    String username;
//    public status(String s){
//        username = s;
//        new status();
//    }
    public status(String s)
    {
        
        username = s;
        
        
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        //  Connect to an MySQL Database, run query, get result set
        // String url = "jdbc:mysql://localhost:3306/faculty?zeroDateTimeBehavior=convertToNull";
        // String userid = "root";
        // String password = "root";
        String sql = "SELECT Application_id, Event_Name,  FA, AD, CSO FROM Application where Club_ID='"+username+"'";
        System.out.print(sql);


        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try {
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
        }catch (SQLException e)
        {
            System.out.println( e.getMessage() );
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
        
         table = new JTable(dataVector, columnNamesVector){
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
     DefaultTableModel tableModel = new DefaultTableModel() {

    @Override
    public boolean isCellEditable(int row, int column) {

       return false;
    }
    
};

//         table.setModel(tableModel);
         
        //add the table to the frame
        JScrollPane jsp = new JScrollPane(table);

        
        this.add(jsp);
        jsp.setSize(1200, 600);
         
         jf.add(this);
        jf.setTitle("Pending Requests");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        jf.pack();
       // this.setSize(400,400);
       this.setLocation(0,0);
        this.setSize(300,300); 
       jf.setSize(400,400);
      JButton jb=new JButton();
        jb.setText("BACK");
       // jf.add(jb);
     //  this.setLocation(100, 100);
       //jb.setLocation(600,05);
       // jb.setSize(30,40);
          jf.setVisible(true);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(true);
//        table.setSize(1200, 1200);
         this.add(jb);
         
         
//         JButton jb1=new JButton("SUBMIT");
//          this.add(jb1);
//        this.setVisible(true);
//        jb1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jb1ActionPerformed(evt);
//            }
//        });
        
        jb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbActionPerformed(evt);
            }
        });

  
        
        
    }
    

           
     private void jbActionPerformed(java.awt.event.ActionEvent evt) {                                         
       jf.dispose();
       new profile().setVisible(true);
        // TODO add your handling code here:
    }            
     
//     
 

//     @Override
//    public boolean isCellEditable(int row, int col) {
//     switch (col) {
//         case 0:
//         case 1:
//             return true;
//         default:
//             return false;
//      }
//}

     
    public static void main(String[] args)
    {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new status();
//            }
//        });
    }   
}
