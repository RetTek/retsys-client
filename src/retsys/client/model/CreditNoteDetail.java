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
public class CreditNoteDetail extends Model{

    private Item item;
    private double returnQuantity;
    private double amount;
    private boolean confirm;

    public CreditNoteDetail(){
        
    }
    
    public CreditNoteDetail(Integer id, Item item, double returnQuantity, double amount, boolean confirm) {
        this.id=id;
        this.item = item;
        this.returnQuantity = returnQuantity;
        this.amount = amount;
        this.confirm = confirm;
    }

    public CreditNoteDetail(Item item, double returnQuantity, double amount, boolean confirm) {
        this.item = item;
        this.returnQuantity = returnQuantity;
        this.amount = amount;
        this.confirm = confirm;
    }
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
     * @return the returnQuantity
     */
    public double getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * @param returnQuantity the returnQuantity to set
     */
    public void setReturnQuantity(double returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    /**
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * @return the confirm
     */
    public boolean isConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

}
