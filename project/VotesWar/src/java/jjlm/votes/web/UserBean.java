/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;

/**
 * UserBean is a data provider bean for presentation logic
 *
 * @author maxmeffert
 */
@SessionScoped
@Named
public class UserBean implements Serializable {

    private boolean isLoggedIn = false;
    private String name;
    private String email;
    private String password;

    @EJB
    private VotesLogic logic;

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

    public void login() {

        this.isLoggedIn = false;

        try {
            if (email != null) {
                OrganizerTO o = logic.getOrganizer(email);
                if (o != null) {
                    if (o.getEncryptedPassword() != null && o.getEncryptedPassword().equals(password)) {
                        name = o.getRealname();
                        isLoggedIn = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logout() {

        this.isLoggedIn = false;

    }

}
