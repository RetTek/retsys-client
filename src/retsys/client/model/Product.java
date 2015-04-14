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
public class Product extends Model {

    private String remarks;
    private String productDesc;

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
    
     public String productDesc() {
        return productDesc;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setDesc(String productDesc) {
        this.productDesc = productDesc;
    }

}
