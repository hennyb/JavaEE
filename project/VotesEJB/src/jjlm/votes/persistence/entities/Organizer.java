/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import jjlm.votes.logic.to.OrganizerTO;

/**
 *
 * @author henny
 */
@Entity
public class Organizer extends NamedEntity<Organizer, OrganizerTO> {

    private static final long serialVersionUID = 7704608960481160103L;

    private List<Poll> polls;
    private String username;
    private String realname;
    private String email;
    private String encryptedPassword;

    public Organizer() {
        polls = new ArrayList<>();
    }

    @ManyToMany
    @JoinTable(name = "ORGANIZER_POLL")
    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }
    
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public OrganizerTO createTO() {
        OrganizerTO to = new OrganizerTO();
        to.setId(id);
        to.setPolls(createTransferList(getPolls()));
        to.setEmail(email);
        to.setEncryptedPassword(encryptedPassword);
        to.setRealname(realname);
        to.setUsername(username);
        
        System.out.println(to.getUsername());
        
        return to;
    }

}
