/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author SwathiReddy
 */
@Named(value = "searchBean")
@ManagedBean
@SessionScoped
public class SearchBean implements Serializable {

    private String supplier;
    private String buyer;
    private String ponumber;
    private String podate;
    private String status;
    private Boolean singleselect;
    private Boolean invgenerate;
    private String sortValue;
    private int count = 0;
    private ArrayList<PurchaseOrder> polist = new ArrayList<>();
    private ArrayList<Invoice> invlist = new ArrayList<>();
    MYJDBC myjdbc = new MYJDBC();

    public SearchBean(String supplier, String buyer, String ponumber, String podate, String status) {
        this.supplier = supplier;
        this.buyer = buyer;
        this.ponumber = ponumber;
        this.podate = podate;
        this.status = status;
    }

    public SearchBean() {
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getPonumber() {
        return ponumber;
    }

    public void setPonumber(String ponumber) {
        this.ponumber = ponumber;
    }

    public String getSortValue() {
        return sortValue;
    }

    public void setSortValue(String sortValue) {
        this.sortValue = sortValue;
    }

    public String getPodate() {
        return podate;
    }

    public void setPodate(String podate) {
        this.podate = podate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<PurchaseOrder> getPolist() {
        return polist;
    }

    public void setPolist(ArrayList<PurchaseOrder> polist) {
        this.polist = polist;
    }

    public Boolean getSingleselect() {
        return singleselect;
    }

    public void setSingleselect(Boolean singleselect) {
        this.singleselect = singleselect;
    }

    public ArrayList<Invoice> getInvlist() {
        return invlist;
    }

    public void setInvlist(ArrayList<Invoice> invlist) {
        this.invlist = invlist;
    }

    public Boolean getInvgenerate() {
        return invgenerate;
    }

    public void setInvgenerate(Boolean invgenerate) {
        this.invgenerate = invgenerate;
    }

    public void sortByDate() throws ParseException {
        
        Date date1, date2;
        this.count++;

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");

        if (count == 0 || count % 2 == 0) {
            for (int i = 0; i < this.polist.size(); i++) {
                System.out.println("sd.parse(sortList.get(j).getCreatedDate());" + sd.parse(this.polist.get(i).getCreatedDate()));
                for (int j = 0; j < this.polist.size() - 1; j++) {
                    date1 = sd.parse(this.polist.get(j).getCreatedDate());
                    date2 = sd.parse(this.polist.get(j + 1).getCreatedDate());
                    if (date2.before(date1)) {

                        PurchaseOrder temp = this.polist.get(j);
                        this.polist.set(j, this.polist.get(j + 1));
                        this.polist.set(j + 1, temp);
                    }

                }
            }
        } else {
            for (int i = 0; i < this.polist.size(); i++) {

                for (int j = 0; j < this.polist.size() - 1; j++) {
                    date1 = sd.parse(this.polist.get(j).getCreatedDate());
                    date2 = sd.parse(this.polist.get(j + 1).getCreatedDate());
                    if (date1.after(date2)) {

                        PurchaseOrder temp = this.polist.get(j + 1);
                        this.polist.set(j + 1, this.polist.get(j));
                        this.polist.set(j, temp);
                    }

                }
            }
        }

    }

    public void processSinglePO(String purchaseorderNumber) 
    {
        Boolean singleflag = true;
        for (int i = 0; i < polist.size(); i++)
        {
            System.out.println("PO: " + this.polist.get(i).getProcess());
            if (this.polist.get(i).getPOID().equals(purchaseorderNumber))
            {
                polist.get(i).setProcess(singleflag);

            }

        }
        
        
    }

    public void createSingleInvoice(String purchaseorderNumber)
    {   
        for (int i = 0; i < polist.size(); i++) {

            if (this.polist.get(i).getPOID().equals(purchaseorderNumber)) {
                this.polist.get(i).setGenerate(true);

            }

        }
        
    }


       

    public void loadAllPending() {
        this.polist = myjdbc.getAllPending();
    }

    public void getAllinvoice() {
        this.invlist = myjdbc.getAllInvoice();
    }

    public void SearchPurchaseOrders() {

        if (this.status == null) {
            this.status = "";
        }

        if (this.supplier.isEmpty() && this.buyer.isEmpty() && this.status.isEmpty() && this.podate.isEmpty()) {
            this.polist = myjdbc.searchPO(String.valueOf(this.ponumber), "ponumber");
        } else if (this.ponumber.isEmpty() && this.buyer.isEmpty() && this.status.isEmpty() && this.podate.isEmpty()) {
            this.polist = myjdbc.searchPO(this.supplier, "supplier");

        } else if (this.ponumber.isEmpty() && this.supplier.isEmpty() && this.status.isEmpty() && this.podate.isEmpty()) {
            this.polist = myjdbc.searchPO(this.buyer, "buyer");

        } else if (this.ponumber.isEmpty() && this.buyer.isEmpty() && this.supplier.isEmpty() && this.podate.isEmpty()) {
            this.polist = myjdbc.searchPO(this.status, "status");
            this.status = "";

        } else if (this.ponumber.isEmpty() && this.buyer.isEmpty() && this.status.isEmpty() && this.supplier.isEmpty()) {
            this.polist = myjdbc.searchPO(this.podate, "podate");

        }

       
    }

    public String processSelectedPO() {

        for (int i = 0; i < polist.size(); i++) {
            if (this.polist.get(i).getProcess() == true) {
                myjdbc.updateSinglePurchaseOrder(this.polist.get(i).getPOID());

            }
        }
        return "displayallpo";

    }

    public String processAllPO() {
        Boolean flag = true;
        ArrayList<Invoice> inv = new ArrayList<>();
        System.out.println("polist : " + this.polist.size());
        if (this.polist.size() > 0) {
            for (int i = 0; i < this.polist.size(); i++) {
                this.polist.get(i).setProcess(flag);
            }
            myjdbc.updateAllPurchaseOrder();//update all pos to approved
        }
        return "displayallpo";
    }

    public void displayAllApprovedPO() {
        this.polist = myjdbc.getAllApprovedPO();
        System.out.println("this size" + this.polist.size());
    }

    public String generateInvoice(String poid) 
    {

        for (int i = 0; i < this.polist.size(); i++) {
            if (this.polist.get(i).getGenerate() == true) {
                this.invlist.add(myjdbc.insertToInvoice(this.polist.get(i).getPOID()));
            }

        }

        return "invoice";
    }

    public String generateAllInvoice()
    {
        for (int i = 0; i < this.polist.size(); i++) 
        {
            this.invlist.add(myjdbc.insertToInvoice(this.polist.get(i).getPOID()));
        }
        //this.invlist = (myjdbc.insertAllInvoice());
        return "invoice";
    }

    public Boolean validateCheckbox() {
        for (int i = 0; i < this.polist.size(); i++) {
            if (this.polist.get(i).getStatus().toUpperCase() == "PENDING") {
                return true;
            } else {
                return false;
            }

        }

        return true;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "Login";
    }

    public String home() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index";
    }

}
