/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.model;

/**
 *
 * @author ranju
 */
public class Item {

    private Integer id;
    private String name;
    private double rate;
    private String brand;
    private String color;
    private String unit;
    private String size;
    private String billno;
    private String site;
    private String remarks;
    private double quantity;
    private String transportmode;
    private double transportcharge;
    private String supervisor;
    private Vendor vendor;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * @param rate the rate to set
     */
    public void setRate(double rate) {
        this.rate = rate;
    }

    /**
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the size
     */
    public String getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * @return the billno
     */
    public String getBillno() {
        return billno;
    }

    /**
     * @param billno the billno to set
     */
    public void setBillno(String billno) {
        this.billno = billno;
    }

    /**
     * @return the site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site the site to set
     */
    public void setSite(String site) {
        this.site = site;
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
     * @return the quantity
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * @return the transportmode
     */
    public String getTransportmode() {
        return transportmode;
    }

    /**
     * @param transportmode the transportmode to set
     */
    public void setTransportmode(String transportmode) {
        this.transportmode = transportmode;
    }

    /**
     * @return the transportcharge
     */
    public double getTransportcharge() {
        return transportcharge;
    }

    /**
     * @param transportcharge the transportcharge to set
     */
    public void setTransportcharge(double transportcharge) {
        this.transportcharge = transportcharge;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
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
    
}
