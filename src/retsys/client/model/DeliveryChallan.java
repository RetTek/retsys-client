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
public class DeliveryChallan extends Model{

    private Project project;
    private Date challanDate;
    private boolean isDelivery;
    private List<DeliveryChallanDetail> deliveryChallanDetail;
    private DeliveryChallan originalDeliveryChallan;
    private String deliveryMode;
    private String concernPerson;

    public DeliveryChallan getOriginalDeliveryChallan() {
        return originalDeliveryChallan;
    }

    public void setOriginalDeliveryChallan(DeliveryChallan originalDeliveryChallan) {
        this.originalDeliveryChallan = originalDeliveryChallan;
    }
    
    public List<DeliveryChallanDetail> getDeliveryChallanDetail() {
        return deliveryChallanDetail;
    }

    public void setDeliveryChallanDetail(List<DeliveryChallanDetail> deliveryChallanDetail) {
        this.deliveryChallanDetail = deliveryChallanDetail;
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

    public String getDeliveryMode() {
        return deliveryMode;
    }

    public void setDeliveryMode(String deliveryMode) {
        this.deliveryMode = deliveryMode;
    }

    public String getConcernPerson() {
        return concernPerson;
    }

    public void setConcernPerson(String concernPerson) {
        this.concernPerson = concernPerson;
    }

}
