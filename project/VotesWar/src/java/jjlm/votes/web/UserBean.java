/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * UserBean is a data provider bean for presentation logic
 * @author maxmeffert
 */
@SessionScoped
@Named
public class UserBean  implements Serializable {

    private boolean isLoggedIn = true;
    private boolean isOrganizer = false;
    private boolean isParticipant = false;
    private String name = "Max Meffert";
    private String email;
    private String password;

    public boolean getIsLoggedIn() {
        return isLoggedIn;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login () {
        
        this.isLoggedIn = true;
        
    }
    
    public void logout () {
        
        this.isLoggedIn = false;
        
    }
    
    
}
