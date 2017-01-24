
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SwathiReddy
 */
public class Invoice {
    
    private String poNum;
    private String sender;
    private String receiver;
    private String createDate;
    private String  invnum;
    private Boolean generate;

    
 
    public Invoice() {
    }
    
    

    

     public Invoice(String invnum,String poNum, String sender, String receiver, String createDate) 
     {
        this.poNum = poNum;
        this.sender = sender;
        this.receiver = receiver;
        this.createDate = createDate;
        this.invnum = invnum;
    }

    public String getInvnum() {
        return invnum;
    }

    public void setInvnum(String invnum) {
        this.invnum = invnum;
    }
     

    public String getPoNum() {
        return poNum;
    }

    public void setPoNum(String poNum) {
        this.poNum = poNum;
    }

    public Boolean getGenerate() {
        return generate;
    }

    public void setGenerate(Boolean generate) {
        this.generate = generate;
    }

   

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


}
