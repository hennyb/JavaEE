/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;

/**
 *
 * @author henny
 */
@Entity
public class Organizer extends NamedEntity<Organizer, OrganizerTO> {

    private static final long serialVersionUID = 7704608960481160103L;

    private Set<Poll> polls;
    private String username;
    private String realname;
    private String email;
    private String encryptedPassword;

    public Organizer() {
        polls = new HashSet<>();
    }

    @ManyToMany(mappedBy="organizer")
    public Set<Poll> getPolls() {
        return polls;
    }

    public void setPolls(Set<Poll> polls) {
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
        
        if(getPolls()==null)
            to.setPolls(new ArrayList<PollTO>());
        else
            to.setPolls(createTransferList(getPolls()));
        to.setEmail(email);
        to.setEncryptedPassword(encryptedPassword);
        to.setRealname(realname);
        to.setUsername(username);
        
        return to;
    }

}
