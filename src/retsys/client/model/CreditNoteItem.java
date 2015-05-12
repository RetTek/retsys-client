package retsys.client.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CreditNoteItem {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty itemName = new SimpleStringProperty();
    private SimpleDoubleProperty itemAmount = new SimpleDoubleProperty();
    private SimpleDoubleProperty returnQuantity = new SimpleDoubleProperty();
    private SimpleBooleanProperty confirm = new SimpleBooleanProperty();

    public CreditNoteItem(Integer id, String itemName, double itemAmount, double returnQuantity, boolean confirm) {
        this.id.set(id);
        this.itemName.set(itemName);
        this.itemAmount.set(itemAmount);
        this.returnQuantity.set(returnQuantity);
        this.confirm.set(confirm);
    }

    public CreditNoteItem(String itemName, double itemAmount, double returnQuantity, boolean confirm) {
        this.itemName.set(itemName);
        this.itemAmount.set(itemAmount);
        this.returnQuantity.set(returnQuantity);
        this.confirm.set(confirm);
    }

    public SimpleStringProperty itemNameProperty() {
        if (getItemName() == null) {
            setItemName(new SimpleStringProperty(this, "itemName"));
        }

        return getItemName();
    }

    public SimpleDoubleProperty itemAmountProperty() {
        if (getItemAmount() == null) {
            setItemAmount(new SimpleDoubleProperty(this, "itemAmount"));
        }

        return getItemAmount();
    }

    public SimpleDoubleProperty returnQuantityProperty() {
        if (getReturnQuantity() == null) {
            setReturnQuantity(new SimpleDoubleProperty(this, "returnQuantity"));
        }

        return getReturnQuantity();
    }

    public SimpleBooleanProperty confirmProperty() {
        if (getConfirm()== null) {
            setConfirm(new SimpleBooleanProperty(this, "confirm"));
        }

        return this.getConfirm();
    }

    /**
     * @return the id
     */
    public SimpleIntegerProperty getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    /**
     * @return the itemName
     */
    public SimpleStringProperty getItemName() {
        return itemName;
    }

    /**
     * @param itemName the itemName to set
     */
    public void setItemName(SimpleStringProperty itemName) {
        this.itemName = itemName;
    }

    /**
     * @return the itemAmount
     */
    public SimpleDoubleProperty getItemAmount() {
        return itemAmount;
    }

    /**
     * @param itemAmount the itemAmount to set
     */
    public void setItemAmount(SimpleDoubleProperty itemAmount) {
        this.itemAmount = itemAmount;
    }

    /**
     * @return the returnQuantity
     */
    public SimpleDoubleProperty getReturnQuantity() {
        return returnQuantity;
    }

    /**
     * @param returnQuantity the returnQuantity to set
     */
    public void setReturnQuantity(SimpleDoubleProperty returnQuantity) {
        this.returnQuantity = returnQuantity;
    }

    /**
     * @return the confirm
     */
    public SimpleBooleanProperty getConfirm() {
        return confirm;
    }

    /**
     * @param confirm the confirm to set
     */
    public void setConfirm(SimpleBooleanProperty confirm) {
        this.confirm = confirm;
    }

}
