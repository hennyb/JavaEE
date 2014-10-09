/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
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

    private String pollTitle;
    private String pollDescription;

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public String getPollDescription() {
        return this.pollDescription;
    }

    public void validateTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.uniquePollTitle(title)) {
            FacesMessage message = new FacesMessage("The title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

    public String save() {

        PollTO pollTO = new PollTO();

        pollTO.setTitle(pollTitle);
        pollTO.setDescription(pollDescription);

        pollTO = logic.storePoll(pollTO);

        OrganizerTO o = logic.getOrganizer(user.getEmail());
        logic.addOrganizerToPoll(o.getId(), pollTO.getId());

        setPollTitle("");
        setPollDescription("");

        return "edit-poll?faces-redirect=true&id=" + pollTO.getId();

    }

}
