/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import javax.ejb.Stateless;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author darjeeling
 */
@Named
@SessionScoped
public class NewPollBean extends OrganizerBean {
    
    private String pollName;
    private String pollDescription;

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }
    
    public String save () {
        
        
        return "edit-poll";
        
    }
    
}
