
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author SwathiReddy
 */
public class MYJDBC {

    private final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu:3306/allams0388";
    private final String user = "allams0388";
    private final String pwd = "1507364";
    ArrayList<PurchaseOrder> purchaseOrderList;

    public void loadMySQLDriver() 
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver is Laoded");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver Load Failed");
            System.out.println(e.getMessage());
        }
    }
    
    
    public Connection getConnection()
     {
         Connection c=null;
         try {
             loadMySQLDriver();
             c = DriverManager.getConnection(DB_URL, user, pwd);
         } 
         catch (SQLException ex) {
              System.out.println(ex.getMessage());
         }
         return c;
     }

    public ArrayList<PurchaseOrder> searchPO(String searchterm, String colName) {
        ArrayList<PurchaseOrder> plist = new ArrayList<>();

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();
            System.out.println("searchterm: " + searchterm);
            if (colName.equalsIgnoreCase("ponumber")) {
                query = "SELECT * FROM purchase_order WHERE PO_id like " + searchterm;
            } else if (colName.equalsIgnoreCase("buyer")) {
                query = "SELECT * FROM purchase_order WHERE UPPER(PO_Buyer) LIKE '" + searchterm.toUpperCase() + "'";
            } else if (colName.equalsIgnoreCase("supplier")) {
                query = "SELECT * FROM purchase_order WHERE UPPER(PO_Supplier) LIKE '" + searchterm.toUpperCase() + "'";
            } else if (colName.equalsIgnoreCase("status")) {
                query = "SELECT * FROM purchase_order WHERE UPPER(PO_status) LIKE '" + searchterm.toUpperCase() + "'";
            } else if (colName.equalsIgnoreCase("podate")) {
                query = "SELECT * FROM purchase_order WHERE PO_gen_date LIKE '" + searchterm + "'";
            }

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            ResultSet r = s.executeQuery(query);

            while (r.next() && !r.wasNull()) {
                plist.add(new PurchaseOrder(String.valueOf(r.getInt("PO_id")), r.getNString("PO_Supplier"), r.getNString("PO_Buyer"),
                        r.getNString("PO_status"), r.getDate("PO_gen_date").toString()));
                System.out.println(r.getInt("PO_id"));
                System.out.println(plist.get(0).getPOID());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.purchaseOrderList = plist;
        return purchaseOrderList;

    }

    public ArrayList<PurchaseOrder> getAllApprovedPO() {

        ArrayList<PurchaseOrder> allApprovedPO = new ArrayList<>();

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();

            query = "SELECT * FROM purchase_order WHERE UPPER(PO_status) = 'APPROVED'";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            ResultSet rall = s.executeQuery(query);

            while (rall.next() && !rall.wasNull()) {
                allApprovedPO.add(new PurchaseOrder(String.valueOf(rall.getInt("PO_id")), rall.getNString("PO_Supplier"), rall.getNString("PO_Buyer"),
                        rall.getNString("PO_status"), rall.getDate("PO_app_date").toString()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allApprovedPO;

    }
    
    public ArrayList<Invoice> getAllInvoice() 
    {
        ArrayList<Invoice> allApprovedinv = new ArrayList<>();

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();

            query = "SELECT * FROM invoice";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            ResultSet rall = s.executeQuery(query);

            while (rall.next() && !rall.wasNull()) {
                allApprovedinv.add(new Invoice( String.valueOf(rall.getInt("Inv_Number")),String.valueOf(rall.getInt("Inv_PO_ID")), rall.getNString("Inv_Sender"), rall.getNString("Inv_Receiver"),
                        rall.getDate("Inv_Gen_Date").toString()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allApprovedinv;

    }

    public ArrayList<PurchaseOrder> getAllPending() {

        ArrayList<PurchaseOrder> allPendingPO = new ArrayList<>();

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();

            query = "SELECT * FROM purchase_order WHERE UPPER(PO_status) = 'PENDING'";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            ResultSet rall = s.executeQuery(query);

            while (rall.next() && !rall.wasNull()) {
                allPendingPO.add(new PurchaseOrder(String.valueOf(rall.getInt("PO_id")), rall.getNString("PO_Supplier"), rall.getNString("PO_Buyer"),
                        rall.getNString("PO_status"), rall.getDate("PO_gen_date").toString()));
                System.out.println(allPendingPO.get(0).getPOID());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return allPendingPO;

    }


    
      public void updateSinglePurchaseOrder(String ponumber) {

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();

            query = "UPDATE purchase_order SET PO_status='Approved' WHERE PO_id='" + ponumber + "'";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            s.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //return invoice;
    }

     public void updateAllPurchaseOrder()
     {

        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();
             
            
            query = "UPDATE purchase_order SET PO_status='Approved' WHERE  UPPER(PO_status)='PENDING'";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            s.executeUpdate(query);
        }
        
           catch (SQLException e)
            {
            System.out.println(e.getMessage());
            }

        
    }

    public  ArrayList<Invoice> insertAllInvoice() 
    {
       ArrayList<Invoice> invList = new ArrayList<Invoice>();
        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();

            query = "SELECT * FROM purchase_order WHERE UPPER(PO_status) = 'APPROVED'";

            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
            System.out.println("query :" + query);
            ResultSet r = s.executeQuery(query);

            
            while (r.next() && !r.wasNull())
            {
                
            String queryinv = "SELECT * FROM invoice WHERE INV_PO_ID= " + r.getInt("PO_id");
            if(!s.executeQuery(queryinv).wasNull())
            {
                String query1 = "INSERT INTO invoice( INV_PO_ID,INV_Sender,INV_Receiver,INV_Gen_Date,Inv_status) VALUES("
                    + r.getInt("PO_id") + ",'" + r.getNString("PO_Supplier") + "','" + r.getNString("PO_Buyer") + "','"
                    + r.getDate("PO_gen_date") + "','" + r.getNString("PO_status");

            s.executeUpdate(query1);
            }
            
            }
    
        } 
        
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return invList;
    }



    public  Invoice insertToInvoice(String ponumber) 
    {
        Invoice invObj = new Invoice();
       
        try {

            String errorMsg = "";
            String str = "";
            String query = "";

            this.loadMySQLDriver();
            int ponum = Integer.parseInt(ponumber);

            query = "SELECT * FROM purchase_order WHERE PO_id= " + ponum;
            
            Connection c = DriverManager.getConnection(DB_URL, user, pwd);
            Statement s = c.createStatement();
  
            System.out.println("query :" + query);
            ResultSet r = s.executeQuery(query);
          
               
                while (r.next() && !r.wasNull())
                  {
                    
                        String query1 = "INSERT INTO invoice(INV_PO_ID,INV_Sender,INV_Receiver,INV_Gen_Date,Inv_status) VALUES("
                        + r.getInt("PO_id") + ",'" + r.getNString("PO_Supplier") + "','" + r.getNString("PO_Buyer") + "','"
                        + r.getDate("PO_gen_date") + "','" + r.getNString("PO_status") + "')"; 
                        Statement s2 = c.createStatement();
                        s2.executeUpdate(query1);
                      
                  }
        } 

        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

        return invObj;
    }
}
