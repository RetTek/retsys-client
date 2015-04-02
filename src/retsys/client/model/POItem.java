package retsys.client.model;

import java.time.LocalDate;
import java.util.Date;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import retsys.client.helper.DateUtils;

public class POItem {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty location = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty brand = new SimpleStringProperty();
    private SimpleStringProperty model = new SimpleStringProperty();
    private SimpleDoubleProperty quantity = new SimpleDoubleProperty();
    private SimpleBooleanProperty confirm = new SimpleBooleanProperty();
    private SimpleObjectProperty<LocalDate> receivedDate = new SimpleObjectProperty<LocalDate>();
    private SimpleStringProperty billNo = new SimpleStringProperty();
    private SimpleStringProperty supervisor = new SimpleStringProperty();

    public POItem(int id, String location, String name, String brand, String model, double quantity, boolean confirm, Date receivedDate) {
        this.id.set(id);
        this.location.set(location);
        this.name.set(name);
        this.brand.set(brand);
        this.model.set(model);
        this.quantity.set(quantity);
        this.confirm.set(confirm);
        this.receivedDate.set(DateUtils.asLocalDate(receivedDate));
    }

    public StringProperty locationProperty() {
        if (getLocation() == null) {
            setLocation(new SimpleStringProperty(this, "location"));
        }

        return getLocation();
    }

    public StringProperty nameProperty() {
        if (getName() == null) {
            setName(new SimpleStringProperty(this, "name"));
        }

        return getName();
    }

    public StringProperty brandProperty() {
        if (getBrand() == null) {
            setBrand(new SimpleStringProperty(this, "brand"));
        }

        return getBrand();
    }

    public StringProperty modelProperty() {
        if (getModel() == null) {
            setModel(new SimpleStringProperty(this, "model"));
        }

        return getModel();
    }

    public SimpleDoubleProperty quantityProperty() {
        if (getQuantity() == null) {
            setQuantity(new SimpleDoubleProperty(this, "quantity"));
        }

        return getQuantity();
    }

    public SimpleBooleanProperty confirmProperty() {
        if (getConfirm() == null) {
            setConfirm(new SimpleBooleanProperty(this, "confirm"));
        }

        return this.confirm;
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
     * @return the location
     */
    public SimpleStringProperty getLocation() {
        return location;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(SimpleStringProperty location) {
        this.location = location;
    }

    /**
     * @return the name
     */
    public SimpleStringProperty getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(SimpleStringProperty name) {
        this.name = name;
    }

    /**
     * @return the brand
     */
    public SimpleStringProperty getBrand() {
        return brand;
    }

    /**
     * @param brand the brand to set
     */
    public void setBrand(SimpleStringProperty brand) {
        this.brand = brand;
    }

    /**
     * @return the model
     */
    public SimpleStringProperty getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(SimpleStringProperty model) {
        this.model = model;
    }

    /**
     * @return the quantity
     */
    public SimpleDoubleProperty getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(SimpleDoubleProperty quantity) {
        this.quantity = quantity;
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

    public ObjectProperty<LocalDate> receivedDateProperty() {
        if (getReceivedDate() == null) {
            setReceivedDate(new SimpleObjectProperty<LocalDate>(this, "receivedDate"));
        }

        return getReceivedDate();
    }

    /**
     * @return the receivedDate
     */
    public SimpleObjectProperty<LocalDate> getReceivedDate() {
        return receivedDate;
    }

    /**
     * @param receivedDate the receivedDate to set
     */
    public void setReceivedDate(SimpleObjectProperty<LocalDate> receivedDate) {
        this.receivedDate = receivedDate;
    }

    /**
     * @return the name
     */
    public SimpleStringProperty getBillNo() {
        return billNo;
    }

    /**
     * @param billNo the billNo to set
     */
    public void setBillNo(SimpleStringProperty billNo) {
        this.billNo = billNo;
    }

    /**
     * @return the supervisor
     */
    public SimpleStringProperty getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(SimpleStringProperty supervisor) {
        this.supervisor = supervisor;
    }

    public StringProperty billNoProperty() {
        if (getBillNo() == null) {
            setBillNo(new SimpleStringProperty(this, "billNo"));
        }

        return getBillNo();
    }

    public StringProperty supervisorProperty() {
        if (getSupervisor() == null) {
            setSupervisor(new SimpleStringProperty(this, "supervisor"));
        }

        return getSupervisor();
    }
}
