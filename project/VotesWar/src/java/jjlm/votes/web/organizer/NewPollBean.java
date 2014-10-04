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

    private int paramID;

    public String setParamID(String paramID) {
        this.paramID = Integer.parseInt(paramID);
        return "edit-poll";
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollName() {
        return logic.getPoll(paramID).getName();
    }

    public String getPollDescription() {
        return logic.getPoll(paramID).getDescription();
    }

    public String save() {

        Poll poll = new Poll();

        poll.setName(pollName);
        poll.setDescription(pollDescription);

        logic.storePoll(poll.createTO());

        OrganizerTO o = logic.getOrganizer(user.getEmail());

        PollTO pto = logic.getPoll(pollName, pollDescription);
        System.out.println("name:" + o.getEmail());
        System.out.println(o.getId());
        System.out.println(pto.getId());

        logic.addOrganizerToPoll(o.getId(), pto.getId());

        return "edit-poll";

    }

}
