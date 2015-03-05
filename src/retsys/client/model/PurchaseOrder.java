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
public class PurchaseOrder extends Model{

    private Date date;
    private Vendor vendor;
    private Client client;
    private String deliveryAddress;
    private List<PurchaseOrderDetail> purchaseOrderDetail;

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

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
     * @return the client
     */
    public Client getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * @return the deliveryAddress
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * @param deliveryAddress the deliveryAddress to set
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * @return the purchaseOrderDetail
     */
    public List<PurchaseOrderDetail> getPurchaseOrderDetail() {
        return purchaseOrderDetail;
    }

    /**
     * @param purchaseOrderDetail the purchaseOrderDetail to set
     */
    public void setPurchaseOrderDetail(List<PurchaseOrderDetail> purchaseOrderDetail) {
        this.purchaseOrderDetail = purchaseOrderDetail;
    }

}
