/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.model;

import java.util.Date;

/**
 *
 * @author ranju
 */
public class ClientChallan {

    private Integer id;
    private Project project;
    private Date challanDate;
    private boolean isDelivery;

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
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the challanDate
     */
    public Date getChallanDate() {
        return challanDate;
    }

    /**
     * @param challanDate the challanDate to set
     */
    public void setChallanDate(Date challanDate) {
        this.challanDate = challanDate;
    }

    /**
     * @return the isDelivery
     */
    public boolean isIsDelivery() {
        return isDelivery;
    }

    /**
     * @param isDelivery the isDelivery to set
     */
    public void setIsDelivery(boolean isDelivery) {
        this.isDelivery = isDelivery;
    }
    
}
