/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.web.help.RequestParameters;

/**
 *
 * @author maxmeffert
 */
@Named
@SessionScoped
public class EditPollBean extends OrganizerBean {

    private PollTO pollTO;
    private int paramID;

    private String pollDescription;
    private String pollName;

    public void init () {
        
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> sessionMap = facesContext.getExternalContext().getRequestParameterMap();
        setParamID(sessionMap.get("id"));
        
    }
    
    public String setParamID(String paramID) {
        try {
            this.paramID = Integer.parseInt(paramID);
            pollTO = logic.getPoll(this.paramID);
            System.out.println("pollID: "+pollTO.getId());

            setPollDescription(pollTO.getDescription());
            setPollName(pollTO.getName());
        } catch (Exception e) {
            pollTO = null;
        }

        return "edit-poll";
    }

    public String getPollDescription() {
        return pollDescription;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String edit() {

        pollTO.setName(pollName);
        pollTO.setDescription(pollDescription);

        logic.storePoll(pollTO);

        return ("my-polls");

    }

}
