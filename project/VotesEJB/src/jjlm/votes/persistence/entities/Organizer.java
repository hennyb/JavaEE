/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import jjlm.votes.logic.to.OrganizerTO;

/**
 *
 * @author henny
 */
@Entity
public class Organizer extends AbstractEntity<Organizer, OrganizerTO> {

    private static final long serialVersionUID = 7704608960481160103L;

    private Set<Poll> polls;
    private Set<ParticipantList> participantLists;
    
    private String username;
    private String realname;
    private String email;
    private String encryptedPassword;
    private String passwordSalt;


    public Organizer() {
        polls = new HashSet<>();
    }

    @OneToMany(mappedBy="organizer", cascade = CascadeType.REMOVE)
    public Set<Poll> getPolls() {
        return polls;
    }

    public void setPolls(Set<Poll> polls) {
        this.polls = polls;
    }

    @OneToMany(mappedBy="organizer", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    public Set<ParticipantList> getParticipantLists() {
        return participantLists;
    }

    public void setParticipantLists(Set<ParticipantList> participantLists) {
        this.participantLists = participantLists;
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
    
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String generatePasswordSalt() {
        byte[] b = new byte[20];
        new Random().nextBytes(b);
        return new String(b);
    }

    @Override
    public OrganizerTO createTO() {
       
        OrganizerTO to = new OrganizerTO();
        
        to.setId(getId());
        to.setEmail(email);
        to.setEncryptedPassword(encryptedPassword);
        to.setPasswordSalt(passwordSalt);
        to.setRealname(realname);
        to.setUsername(username);
        
        return to;
    }

}
