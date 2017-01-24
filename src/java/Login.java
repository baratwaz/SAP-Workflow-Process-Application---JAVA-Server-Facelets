/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;


/**
 *
 * @author sannith
 */
@ManagedBean
@SessionScoped
public class Login implements Serializable{
      //attributes
    final String USERNAME = "allams0388";
    final String PASSWORD = "1507364";
    final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu:3306/allams0388";
    private String password;
    private String role;
    private String username;
    private int errorCount;
    private String securityCode;
    private String blocked;
    private String newpassword;
    private String oldseccode;
    private String oldusername;
    private String tablepassword;
    private String loginerrormsg;
    private String forgotpassword;
    private String wrongsecuritycode;
    public String getLoginerrormsg() {
        return loginerrormsg;
    }

    public String getForgotpassword() {
        return forgotpassword;
    }

    public void setForgotpassword(String forgotpassword) {
        this.forgotpassword = forgotpassword;
    }

    public void setLoginerrormsg(String loginerrormsg) {
        this.loginerrormsg = loginerrormsg;
    }
    
    

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBlocked() {
        return blocked;
    }

    public void setBlocked(String blocked) {
        this.blocked = blocked;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
  public String log()
    {
        //load the Driver
         Connection connection = null;  //a connection to the database
        Statement statement = null;    //execution of a statement
        ResultSet resultSet = null;  
        int count=0;
        try
        {   
            //set of results
            //connect to the database with user name and password
                        Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DATABASE_URL, 
                    USERNAME, PASSWORD);   
            statement  = connection.createStatement();
            Statement stmnt=connection.createStatement();
            System.out.println(" this");
            //resultSet = statement.executeQuery("Select * from user_details where userName ="+ "'"+ username+"'" + "and password='"+password+"'" );
            String sql1 = "select * from user ud, companyrole cr where ud.username = cr.username and ud.username ="+ "'"+ username+"'" + "and password='"+password+"'";
           System.out.println(sql1);
            resultSet=stmnt.executeQuery(sql1);
            if(resultSet.next() && resultSet.getInt("Block_counter")<5 )
            {
                this.tablepassword= resultSet.getNString("password");
                this.oldusername = resultSet.getNString("username");
                statement.executeUpdate("update user set Block_counter = 0 where username='"+username+"'");
           
                if(resultSet.getString("role").equals("Buyer")){return "BuyerPage";}
                //else if(resultSet.getString(3).equals("BuyerUser")){return "BuyerAdmin";}
                else if(resultSet.getString("role").equals("Supplier")){return "index";}
                //else if(resultSet.getString(3).equals("SupplierUser")){return "SupplierAdmin";}
                }
                
            else
            {
            statement.executeUpdate("update user set Block_counter = Block_counter + 1 where username='"+username+"'");
            count++;
            if(count>4)
              {
              return "forgotPwd";
              }
            }
            
       // }
//        catch (SQLException e)
//        {
//            //go to internalError.xhtml
//            e.printStackTrace();
//            return (e.getMessage());
//        }
//        finally
//        {
//            try
//            {
//                resultSet.close();
//                statement.close();
//                connection.close();
                 
            }
            catch (ClassNotFoundException e)
            {
           
                  System.out.println(e.getMessage());
                    //resultSet.close();
                  // statement.close();
                //connection.close();
            } 
        catch (SQLException ex)
        {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
             
            
        return null;
        }
        
  public void isvalidsecuritycode()
  {
       MYJDBC data=new MYJDBC();
      String result="";
      ResultSet rs1=null;
        String query1="select * from user where securitycode='"+securityCode+"'";
        Connection c;
        try {
            c = data.getConnection();
            Statement st = c.createStatement();
               rs1=st.executeQuery(query1);
               this.wrongsecuritycode="";
        if(!rs1.next()){
      this.wrongsecuritycode= "Wrong SecurityCode";
        }
      }
      
       
              catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
  public void isvalidusername()
  {
      MYJDBC data=new MYJDBC();
      String result="";
      ResultSet rs=null;
        String query="select * from user where username='"+username+"'";
        Connection c;
        try {
            c = data.getConnection();
            Statement s = c.createStatement();
               rs=s.executeQuery(query);
                this.forgotpassword="";
        if(!rs.next())
       {
     
         this.forgotpassword= "Username doesnt exist";
      
      
       }
             } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
      
  }
  
  public String loginNotok()
  {
      if( (this.oldusername!=this.username) || (this.tablepassword!=this.password))
      {
          //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Credentials mismatch"));

       
         //context.addMessage(null, new FacesMessage("Passwords are not equal."));
          return "Credentials Mismatch";
      }
      else
          return "";      
  }
  
  public String forgetpwd(){
      
      try
        {
            Class.forName("com.mysql.jdbc.Driver");
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }
        Connection connection1 = null;  //a connection to the database
        Statement statement1 = null;    //execution of a statement
        ResultSet resultSet1 = null;   //set of results
        
        
        try
        {      
            //connect to the database with user name and password
            this.isvalidusername();
            this.isvalidsecuritycode();
            connection1 = DriverManager.getConnection(DATABASE_URL, 
                    USERNAME, PASSWORD);   
            statement1 = connection1.createStatement();
            Statement stmt = connection1.createStatement();
            
            resultSet1 = stmt.executeQuery("Select * from user where username ='"+username+"'" );
            
            if(resultSet1.next())
            {
                this.oldseccode = resultSet1.getString("securitycode");
                this.oldusername = resultSet1.getString("username");
                
                //resultSet1.next();
                if(resultSet1.getString("securitycode").equals(securityCode))
                {
                statement1.executeUpdate("update user set password='"+newpassword+"', Block_counter=0 where username='"+username+"'");
               return "Login";
                }
                else
                {
                return null;
                
                
                }
                }
            else
            {
            return null;
                 
            }
            
        }
        catch (SQLException e)
        {
            //go to internalError.xhtml
            e.printStackTrace();
            return ("internalError123");
        }
        finally
        {
            try
            {
                resultSet1.close();
                statement1.close();
                connection1.close();
                 
            }
            catch (Exception e)
            {
                e.printStackTrace();    
            }
        }
  
  }
 
        
}