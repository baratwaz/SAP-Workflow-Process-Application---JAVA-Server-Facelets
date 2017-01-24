/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sonal_Sagar
 */
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

@ManagedBean (name="register")
@RequestScoped

public class Register {
    
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/allams0388";
        final String USERNAME="allams0388";
        final String PASSWORD="1507364";
    
    private String FName;
    private String LName;
    private String UserName;
    private String Password;
    private String Security;
    private String StreetAddress;
    private String Phone;
    private String userRole;
    private String Country;
    private String State;
    private String City;
    private String Zip;
    private String CompanyID;
    private String CompanyName;
    private String message;
    private String userExistMessage;

    public String getUserExistMessage() {
        return userExistMessage;
    }

    public void setUserExistMessage(String userExistMessage) {
        this.userExistMessage = userExistMessage;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getZip() {
        return Zip;
    }

    public void setZip(String Zip) {
        this.Zip = Zip;
    }
    
    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String CompanyID) {
        this.CompanyID = CompanyID;
    }
    
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String CompanyName) {
        this.CompanyName = CompanyName;
    }
    
    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getSecurity() {
        return Security;
    }

    public void setSecurity(String Security) {
        this.Security = Security;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String StreetAddress) {
        this.StreetAddress = StreetAddress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
    
    public String add(){
        
        Statement stmt = null;
        Statement stmtInsert = null;
        Connection con = null;
        ResultSet rs = null;
        
        
        try{
            try {
                
                Class.forName("com.mysql.jdbc.Driver");
            } 
            catch (ClassNotFoundException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
            con = DriverManager.getConnection(DATABASE_URL,USERNAME,PASSWORD);
            

            
           stmt=con.createStatement();
            this.setUserExistMessage("");
           rs=stmt.executeQuery("select * from user where username='"+UserName+"'");
           
           if(rs.next())
           {
                this.setMessage("");
                this.setUserExistMessage("UserName Already Exists, Please Enter different UserName");
               //User Already Exists
               //return "UserExists.xhtml";
           }
           else
           {
                this.setUserExistMessage("");
             con.setAutoCommit(false);
             stmtInsert = con.createStatement();
             String sql = "INSERT INTO user(firstname, lastname, username, password, securitycode, street2address, country, state, city, zipcode) VALUES"
                     + "                   ('"+FName+"','"+LName+"','"+UserName+"','"+Password+"','"+Security+"','"+StreetAddress+"','"+Country+"','"+State+"','"+City+"','"+Zip+"')";
             String sql1 ="INSERT INTO company(username, companyid, companyName) VALUES('"+UserName+"','"+CompanyID+"','"+CompanyName+"')";
             String sql2 ="INSERT INTO companyrole(username, companyid, role) VALUES('"+UserName+"','"+CompanyID+"','"+userRole+"')";
             stmtInsert.executeUpdate(sql);
             stmtInsert.executeUpdate(sql1);
             stmtInsert.executeUpdate(sql2);
             con.commit();
               this.setMessage("Registration Success");
             return "Login.xhtml";
           }
        }
        catch(SQLException ex){
            this.setMessage("");
            System.err.println("Driver Exception");
            
        }
        
        return null;
    }
}
