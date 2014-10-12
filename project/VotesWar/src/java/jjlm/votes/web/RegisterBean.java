package jjlm.votes.web;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Organizer;
import jjlm.votes.persistence.entities.Poll;
import jjlm.votes.web.logic.HashGenerator;

/**
 * BackingBean to register new Users
 * 
 */
@Named
@RequestScoped
public class RegisterBean {
    
    private String username;
    private String realname;
    private String password1;
    private String password2;
    private String email;
    
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
    
    public String register () {
        
        try {
            
            String pwdHash = (new HashGenerator()).generateHash(password1);
            
            Organizer organizer = new Organizer();
            organizer.setEmail(email);
            organizer.setEncryptedPassword(pwdHash);
            organizer.setRealname(realname);
            organizer.setUsername(username);

            //logic.storeOrganizer(organizer);
            logic.storeOrganizer(organizer.createTO());
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RegisterBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return "index?faces-redirect=true";
        
    }
    
}