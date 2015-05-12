/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author ranju
 */
public class CreditNote extends Model{
    private Long id;
    private Vendor vendor;
    private double totalAmount;
    private String remarks;
    private Date creationDate;
    private List<CreditNoteDetail> creditNoteDetails;
    /**
     * @return the vendor
     */
    public Vendor getVendor() {
        return vendor;
    }

    /**
     * @param vendor the vendor to set
     */
    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    /**
     * @return the totalAmount
     */
    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the creditNoteDetails
     */
    public List<CreditNoteDetail> getCreditNoteDetails() {
        return creditNoteDetails;
    }

    /**
     * @param creditNoteDetails the creditNoteDetails to set
     */
    public void setCreditNoteDetails(List<CreditNoteDetail> creditNoteDetails) {
        this.creditNoteDetails = creditNoteDetails;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    
    
}
