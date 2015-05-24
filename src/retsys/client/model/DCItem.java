/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Fahad
 */
public class DCItem {

        private SimpleIntegerProperty id = new SimpleIntegerProperty();

    
        private SimpleStringProperty name = new SimpleStringProperty();
        private SimpleStringProperty brand = new SimpleStringProperty();
        private SimpleStringProperty model = new SimpleStringProperty();
        private SimpleIntegerProperty quantity = new SimpleIntegerProperty();
        private SimpleIntegerProperty rate = new SimpleIntegerProperty();

        private SimpleStringProperty units = new SimpleStringProperty();
        private SimpleIntegerProperty amount = new SimpleIntegerProperty();

        public DCItem(int id, String name, String brand, String model, int rate, int quantity, String units, int amount) {
            this.id.set(id);
            this.name.set(name);
            this.brand.set(brand);
            this.model.set(model);
            this.rate.set(rate);
            this.quantity.set(quantity);
            this.units.set(units);
            this.amount.set(amount);
        }


        public StringProperty nameProperty() {
            if (name == null) {
                name = new SimpleStringProperty(this, "name");
            }

            return name;
        }

        public StringProperty brandProperty() {
            if (brand == null) {
                brand = new SimpleStringProperty(this, "brand");
            }

            return brand;
        }

        public StringProperty modelProperty() {
            if (model == null) {
                model = new SimpleStringProperty(this, "model");
            }

            return model;
        }

        public IntegerProperty quantityProperty() {
            if (quantity == null) {
                quantity = new SimpleIntegerProperty(this, "quantity");
            }

            return quantity;
        }
        
        public StringProperty unitsProperty() {
            if (units == null) {
                units = new SimpleStringProperty(this, "units");
            }

            return units;
        }
        
        public IntegerProperty amountProperty() {
            if (amount == null) {
                amount = new SimpleIntegerProperty(this, "amount");
            }

            return amount;
        }
        
    public SimpleIntegerProperty getId() {
            return id;
        }

        public void setId(SimpleIntegerProperty id) {
            this.id = id;
        }

        public SimpleStringProperty getName() {
            return name;
        }

        public void setName(SimpleStringProperty name) {
            this.name = name;
        }

        public SimpleStringProperty getBrand() {
            return brand;
        }

        public void setBrand(SimpleStringProperty brand) {
            this.brand = brand;
        }

        public SimpleStringProperty getModel() {
            return model;
        }

        public void setModel(SimpleStringProperty model) {
            this.model = model;
        }

        public SimpleIntegerProperty getQuantity() {
            return quantity;
        }

        public void setQuantity(SimpleIntegerProperty quantity) {
            this.quantity = quantity;
        }

        public SimpleStringProperty getUnits() {
            return units;
        }

        public void setUnits(SimpleStringProperty units) {
            this.units = units;
        }

        public SimpleIntegerProperty getAmount() {
            return amount;
        }

        public void setAmount(SimpleIntegerProperty amount) {
            this.amount = amount;
        }        
        public SimpleIntegerProperty getRate() {
            return rate;
        }

        public void setRate(SimpleIntegerProperty rate) {
            this.rate = rate;
        }        
    }