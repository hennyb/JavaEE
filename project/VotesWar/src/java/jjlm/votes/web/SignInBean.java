/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;
/**
 *
 * @author darjeeling
 */

@Named
@RequestScoped
public class SignInBean {
    
    private String username;
    private String realname;
    private String password1;
    private String password2;
    private String email;
    private String passwordSalt;

    
    @EJB
    private VotesLogic logic;
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String setPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String salt) {
        this.passwordSalt = salt;
    }
    
    public String signin () throws NoSuchAlgorithmException {
        
        OrganizerTO organizerTo = new OrganizerTO();
        
        Organizer organizer = new Organizer();
        organizer.setEmail(email);
        
        
        String salt = organizer.generatePasswordSalt();
        String encryptedPassword = organizerTo.encryptPassword(password1, salt);
        organizer.setPasswordSalt(salt);
        organizer.setEncryptedPassword(encryptedPassword);
        organizer.setRealname(realname);
        organizer.setUsername(username);
        
        System.out.println(encryptedPassword);
        
        //logic.storeOrganizer(organizer);
        logic.storeOrganizer(organizer.createTO());
        return "index?faces-redirect=true";
        
    }
    
}
