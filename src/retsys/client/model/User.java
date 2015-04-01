/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package retsys.client.model;

/**
 *
 * @author Fahad
 */
public class User extends Model{
    private String password;
    private String usertype;
    
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the userType
     */
    public String getUsertype() {
        return usertype;
    }

    /**
     * @param userType the userType to set
     */
    public void setUsertype(String userType) {
        this.usertype = userType;
    }

    
    
}
