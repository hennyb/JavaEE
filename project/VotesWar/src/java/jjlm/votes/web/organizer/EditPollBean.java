/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.web.help.RequestParameters;

/**
 *
 * @author maxmeffert
 */

@Named
@SessionScoped
public class EditPollBean extends OrganizerBean {

    private final String PARAMETERNAME_ID = "id";

    public String getPARAMETERNAME_ID() {
        return PARAMETERNAME_ID;
    }

    
    
    
    public EditPollBean () {
        
    }
    
    private String pollId;

    
    public String getPollId() {
        return RequestParameters.get(PARAMETERNAME_ID);
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
    
    public void doStuff () {
        
        System.out.println(RequestParameters.get("page"));
        
    }
    
}
