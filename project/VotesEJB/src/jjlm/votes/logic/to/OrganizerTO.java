/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import java.util.ArrayList;
import java.util.List;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
public class OrganizerTO extends AbstractEntityTO{
    private static final long serialVersionUID = 6430614517398735844L;
    
     
    private List<PollTO> polls;
    private String username ;
    private String realname;
    private String email;
    private String encryptedPassword;
    
    public OrganizerTO(){
        polls = new ArrayList<>();
    }

    public List<PollTO> getPolls() {
        return polls;
    }

    public void setPolls(List<PollTO> polls) {
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
    
    
    
}
