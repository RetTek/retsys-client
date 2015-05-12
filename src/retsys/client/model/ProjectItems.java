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
public class ProjectItems extends Model{

    private Item item;
    private DeliveryChallan challan;
    private int quantity;

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
    }

    /**
     * @return the challan
     */
    public DeliveryChallan getChallan() {
        return challan;
    }

    /**
     * @param challan the challan to set
     */
    public void setChallan(DeliveryChallan challan) {
        this.challan = challan;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
