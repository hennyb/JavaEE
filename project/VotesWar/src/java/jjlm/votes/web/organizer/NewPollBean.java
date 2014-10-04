/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author darjeeling
 */
@Named
@SessionScoped
public class NewPollBean extends OrganizerBean {

    private String pollName;
    private String pollDescription;
    private PollTO pollTO;

    private int paramID;

    public String setParamID(String paramID) {
        try {
            this.paramID = Integer.parseInt(paramID);
            pollTO = logic.getPoll(this.paramID);

            setPollDescription(pollTO.getDescription());
            setPollName(pollTO.getName());
        } catch (Exception e) {
            pollTO = null;
        }

        return "edit-poll";
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollName() {
        return this.pollName;
    }

    public String getPollDescription() {
        return this.pollDescription;
    }

    public String save() {

        if (pollTO == null) {
            pollTO = new PollTO();
        }

        pollTO.setName(pollName);
        pollTO.setDescription(pollDescription);

        pollTO = logic.storePoll(pollTO);

        System.out.println("pollTO id " + pollTO.getId());

        OrganizerTO o = logic.getOrganizer(user.getEmail());

        logic.addOrganizerToPoll(o.getId(), pollTO.getId());

        return "edit-poll";

    }

}
