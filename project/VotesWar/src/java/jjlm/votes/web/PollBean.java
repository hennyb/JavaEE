package jjlm.votes.web;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author darjeeling
 */
@Named(value = "pollBean")
@Dependent
@ViewScoped
public class PollBean {

    private String pollId;
    
    /**
     * Creates a new instance of PollBean
     */
    public PollBean() {
    }

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
    
    
    
}
