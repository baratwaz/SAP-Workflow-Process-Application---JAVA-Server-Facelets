/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Bharadwaj
 */
@Named(value = "order")
@SessionScoped
@ManagedBean
public class Order {

    private String Product_Searched;
    private String supplier;
   // private List<String> supplierList=new ArrayList<>();
    private String product_type;
    private ArrayList<Products> result_list= new ArrayList<Products>();
    private ArrayList<Products> addpo= new ArrayList<Products>();
     private ArrayList<Products> selectalllist= new ArrayList<Products>();
    private String sortby;
    private boolean checked;
    private int Quanity;
    private boolean selectall;

    public ArrayList<Products> getSelectalllist() {
        return selectalllist;
    }

    public void setSelectalllist(ArrayList<Products> selectalllist) {
        this.selectalllist = selectalllist;
    }
    
    public boolean isSelectall() {
        return selectall;
    }

    public void setSelectall(boolean selectall) {
        this.selectall = selectall;
    }
    
    public int getQuanity() {
        return Quanity;
    }

    public void setQuanity(int Quanity) {
        this.Quanity = Quanity;
    }

    public ArrayList<Products> getAddpo() {
        return addpo;
    }

    public void setAddpo(ArrayList<Products> addpo) {
        this.addpo = addpo;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    public String getSortby() {
        return sortby;
    }

    public void setSortby(String sortby) {
        this.sortby = sortby;
    }
    

    public ArrayList<Products> getResult_list() {
        return result_list;
    }

    public void setResult_list(ArrayList<Products> result_list) {
        this.result_list = result_list;
    }
    

    public String getProduct_Searched() {
        return Product_Searched;
    }

    public void setProduct_Searched(String Product_Searched) {
        this.Product_Searched = Product_Searched;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

//    public List<String> getSupplierList() {
//        return supplierList;
//    }
//
//    public void setSupplierList(List<String> supplierList) {
//        this.supplierList = supplierList;
//    }
    public List<String> get_SupplierList()
    {
        //ArrayList<String> list=new ArrayList<>();
        List<String> supplierList=new ArrayList<>();
        // data=new JDBCLayer();
        //data.loadMySQLDriver();
        MYJDBC data=new MYJDBC();
        String query="select supplier_name from supplier";
        Connection c;
        try {
            c = data.getConnection();
            Statement s = c.createStatement();
                 ResultSet rs=s.executeQuery(query);
                  while(rs.next())
                  {
                      supplierList.add(rs.getString("supplier_name"));
                  }
             } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        return supplierList;
    }
    
    public String logout()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login";
    }
    public void Search()
    {
        String sname=this.supplier;
        String ptype=this.product_type;
       // System.out.println("here u r idiot: "+this.getHellWith() +"and withic: "+this.hellWith);
        
        MYJDBC data=new MYJDBC();
        String query="";
        if(Product_Searched.isEmpty())
        {
        if(!sname.toLowerCase().equals("SelectSupplier".toLowerCase())&& !ptype.toLowerCase().equals("SelectItem".toLowerCase()) )
            query="select * from products where supplier_name=\""+this.supplier+"\"and item_type=\""+this.product_type+"\"";
        else if(sname.toLowerCase().equals("SelectSupplier".toLowerCase())&&ptype.toLowerCase().equals("SelectItem".toLowerCase()) )
            query="select * from products";
        else if(sname.toLowerCase().equals("SelectSupplier".toLowerCase()) && this.product_type!=null)
            query="select * from products where item_type=\""+this.product_type+"\"";
        else if(sname!=null && ptype.toLowerCase().equals("SelectItem".toLowerCase()))
            query="select * from products where supplier_name=\""+this.supplier+"\"";
        }
        else
        {
         if(!sname.toLowerCase().equals("SelectSupplier".toLowerCase())&& !ptype.toLowerCase().equals("SelectItem".toLowerCase()) )
            query="select * from products where supplier_name=\""+this.supplier+"\"and item_type=\""+this.product_type+"\" and item_name like '%"+Product_Searched+"%'";
        else if(sname.toLowerCase().equals("SelectSupplier".toLowerCase())&&ptype.toLowerCase().equals("SelectItem".toLowerCase()) )
            query="select * from products where item_name like '%"+Product_Searched+"%'";
        else if(sname.toLowerCase().equals("SelectSupplier".toLowerCase()) && this.product_type!=null)
            query="select * from products where item_type=\""+this.product_type+"\"and item_name like '%"+Product_Searched+"%'";
        else if(sname!=null && ptype.toLowerCase().equals("SelectItem".toLowerCase()))
            query="select * from products where supplier_name=\""+this.supplier+"\" and item_name like '%"+Product_Searched+"%'";
        }
        
      // query="select * from products";
        Connection c;
         try {
            c = data.getConnection();
            Statement s = c.createStatement();
                 ResultSet rs=s.executeQuery(query);
                 result_list = new ArrayList<Products>();
                  while(rs.next())
                  {
                     Products prod = new Products();
                //prod.setItem_id(rs.getInt("item_id"));
                prod.setItem_id(rs.getInt("item_id"));
                prod.setItem_name(rs.getString("item_name"));
                prod.setItem_image(rs.getString("item_image"));
                prod.setItem_price(rs.getInt("item_price"));
                prod.setItem_description(rs.getString("item_description"));
                prod.setSupplier_name(rs.getString("supplier_name"));
                prod.setItem_unitOfMeasurement(1);
                //products.add(prod);
                result_list.add(prod);
                  }
             } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
         //return list;
    }
    public void sorting()
    {
        if(sortby.equals("sortname"))
        {
            for(int j=0;j<result_list.size();j++)
            {
                for(int i=0;i<result_list.size()-1;i++)
                {
                    if(result_list.get(i).getItem_name().compareTo(result_list.get(i+1).getItem_name())>0)
                    {
                        Products temp=result_list.get(i);
                        result_list.set(i,result_list.get(i+1));
                        result_list.set((i+1),temp);
                    }
                }
            }
        }
        else if(sortby.equals("sortprice"))
        {
            for(int j=0;j<result_list.size();j++)
            {
                for(int i=0;i<result_list.size()-1;i++)
                {
                    if(result_list.get(i).getItem_price()>result_list.get(i+1).getItem_price())
                    {
                        Products temp=result_list.get(i);
                        result_list.set(i,result_list.get(i+1));
                        result_list.set((i+1),temp);
                    }
                }
            }
        }

    }
    public Order() {
    }
    
   
    public String ChecklistAdd(){
        addpo.clear();
        for (int i = 0; i < result_list.size(); i++) {
            if(result_list.get(i).isChecked()){
                addpo.add(result_list.get(i));
            }
        }
        return "CartPage"; 
    }
    
    public String CreatePo(){
        
        ArrayList<Products> bySupplierName = new ArrayList<Products>();
         MYJDBC data=new MYJDBC();
        //data.loadMySQLDriver();
        java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
        String query ="";
         String query1 ="";
          String count="select count(*) from supplier";
           Connection c2;
           int poid=0;
        try {
            c2 = data.getConnection();
            Statement s = c2.createStatement();
                 ResultSet rs=s.executeQuery(count);
                  while(rs.next())
                  {
                      poid = rs.getInt(1);
                  }
             } catch (SQLException ex) {
            Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < addpo.size(); i++) {
               
              query= "INSERT INTO purchase_order ( PO_Supplier, PO_Buyer, PO_gen_date, PO_app_date,PO_status,PO_description) "
                    + "VALUES ('" + this.addpo.get(i).getSupplier_name()
                    + "','" + "user"
                    + "','" + date
                    + "','" + date
                    + "','" + "Pending"
                    + "','" + "PO Order created"
                    + "');";
                query1= "INSERT INTO po_lineitem (po_id,PO_Supplier, Prod_Serv_id, P_Quantity, P_Price,P_total,PO_createdby) "
                    + "VALUES (" + poid++
                    + ",'" + this.addpo.get(i).getSupplier_name()
                    + "','" + addpo.get(i).getItem_id()
                    + "','" + addpo.get(i).getQuantity()
                    + "','" + addpo.get(i).getItem_price()
                    + "','" + 0
                    + "','" + "user"
                    + "');";
              
                Connection c;
                try {
                    c = data.getConnection();
                    Statement s = c.createStatement();
                    Statement s1 = c.createStatement();
                          s.executeUpdate(query);
                          s1.executeUpdate(query1);
                     } catch (SQLException ex) {
                    Logger.getLogger(Order.class.getName()).log(Level.SEVERE, null, ex);
                }           
        }
       return "BuyerPage";
       
    }
    
    public void selectall(){
        selectalllist.clear();
        if (this.isSelectall() ==true) {
            for (int i = 0; i < result_list.size(); i++) {
                result_list.get(i).setChecked(true);
            }
        }
        else{
             for (int i = 0; i < result_list.size(); i++) {
                result_list.get(i).setChecked(false);
            }
        }
        
    }
}
