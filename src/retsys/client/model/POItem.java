package retsys.client.model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class POItem {

    private SimpleIntegerProperty id = new SimpleIntegerProperty();
    private SimpleStringProperty location = new SimpleStringProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleStringProperty brand = new SimpleStringProperty();
    private SimpleStringProperty model = new SimpleStringProperty();
    private SimpleDoubleProperty quantity = new SimpleDoubleProperty();
    private SimpleBooleanProperty confirm = new SimpleBooleanProperty();

    public POItem(int id, String location, String name, String brand, String model, double quantity, boolean confirm) {
        this.id.set(id);
        this.location.set(location);
        this.name.set(name);
        this.brand.set(brand);
        this.model.set(model);
        this.quantity.set(quantity);
        this.confirm.set(confirm);
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
        if (getConfirm()== null) {
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
}
