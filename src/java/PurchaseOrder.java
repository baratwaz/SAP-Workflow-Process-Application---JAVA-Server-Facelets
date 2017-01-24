/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SwathiReddy
 */
public class PurchaseOrder
{
    private String POID;
    private String supplierName;
    private String buyerName;
    private String Status;
    private String createdDate;
    private Boolean Process;
    private Boolean generate;
    

    public PurchaseOrder() {
    } 

    public PurchaseOrder(String POID, String supplierName, String buyerName, String Status, String createdDate) {
        this.POID = POID;
        this.supplierName = supplierName;
        this.buyerName = buyerName;
        this.Status = Status;
        this.createdDate = createdDate;
    }

    public Boolean getGenerate() {
        return generate;
    }

    public void setGenerate(Boolean generate) {
        this.generate = generate;
    }
    
    public String getPOID() {
        return POID;
    }

    
    public void setPOID(String POID) {
        this.POID = POID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
     
    public Boolean getProcess() {
        return Process;
    }

    public void setProcess(Boolean Process) {
        this.Process = Process;
    }

    
  
}
