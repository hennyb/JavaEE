/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import jjlm.votes.logic.to.ItemOptionTO;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.ItemType;
import jjlm.votes.persistence.entities.PollState;
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.logic.ParticipantListParser;

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
    private String pollTitle;

    private String startPoll;
    private String endPoll;

    private String itemTitle;
    private String itemType;

    private String participantsText;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private List<ItemTO> pollItems;

    private String paramString;

    private String notificationText;
    
    private String pollState;

    public void init() {

        pollTO = new PollTO();
        setPollDescription("");
        setPollTitle("");
        setStartPoll("");
        setEndPoll("");
        setItemTitle("");
        setItemType("");
        pollItems = new ArrayList<>();

        paramString = RequestParameters.get("id");

        try {
            this.paramID = Integer.parseInt(paramString);
            pollTO = logic.getPoll(this.paramID);

            setPollDescription(pollTO.getDescription());
            setPollTitle(pollTO.getTitle());

            pollItems = (pollTO.getId() != null
                    ? logic.getItemsOfPoll(pollTO.getId())
                    : new ArrayList<ItemTO>());

            if (pollTO.getStartPoll() != null) {
                setStartPoll(sdf.format(pollTO.getStartPoll()));
            } else {
                setStartPoll("");
            }
            if (pollTO.getEndPoll() != null) {
                setEndPoll(sdf.format(pollTO.getEndPoll()));
            } else {
                setEndPoll("");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public List<ItemType> getItemTypes() {

        List<ItemType> values = Arrays.asList(ItemType.values());

        return values;

    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public List<ItemTO> getPollItems() {
        return this.pollItems;
    }

    public void setPollItems(List<ItemTO> pollItems) {
        this.pollItems = pollItems;
    }

    public String getStartPoll() {
        return startPoll;
    }

    public void setStartPoll(String startPoll) {
        this.startPoll = startPoll;
    }

    public String getEndPoll() {
        return endPoll;
    }

    public void setEndPoll(String endPoll) {
        this.endPoll = endPoll;
    }

    public String getPollDescription() {
        return pollDescription;
    }

    public void setPollDescription(String pollDescription) {
        this.pollDescription = pollDescription;
    }

    public String getPollTitle() {
        return pollTitle;
    }

    public void setPollTitle(String pollTitle) {
        this.pollTitle = pollTitle;
    }

    public String getParticipantsText() {
        return participantsText;
    }

    public void setParticipantsText(String participantsText) {
        this.participantsText = participantsText;
    }

    public List<ParticipantTO> getParticipants() {
        return logic.getParticipantsOfPoll(paramID);
    }

    public String getPollState() {
        return this.pollTO.getPollState().toString();
    }

    
    public boolean isStarted () {
        return pollTO.getPollState() == PollState.STARTED;
    }
    
    public boolean isPrepared () {
        
        return pollTO.getPollState() == PollState.PREPARED;
        
    }
    
    
    public boolean isVoting () {
        
        return pollTO.getPollState() == PollState.VOTING;
        
    }
    public boolean isFinished () {
        
        return pollTO.getPollState() == PollState.FINISHED;
        
    }
    
    public String addParticipants() {

        if (getParticipantsText() != null) {

            ParticipantListParser plp = new ParticipantListParser();

            ArrayList<String> emails = (ArrayList<String>) plp.parse(getParticipantsText());

            for (String email : emails) {

                ParticipantTO participantTO = new ParticipantTO();
                participantTO.setEmail(email);
                participantTO.setPoll(pollTO);
                participantTO.setHasVoted(false);

                logic.storeParticipant(participantTO);

            }
        }

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String deleteParticipant(int participantId) {

        logic.deleteParticipant(participantId);

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String start() {
        edit();
        logic.startPoll(paramID);
        

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }
    
    public String stop() {
        
        logic.resetPoll(paramID);
        

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String edit() {
        pollTO.setDescription(pollDescription);
        pollTO.setTitle(pollTitle);

        Date endDate = null;
        try {
            endDate = sdf.parse(getEndPoll());
        } catch (ParseException ex) {
            System.err.println(ex);
        }
        pollTO.setEndPoll(endDate);

        logic.storePoll(pollTO);

        pollTO = null;

        setPollTitle("");
        setPollDescription("");
        setStartPoll("");
        setEndPoll("");
        return "my-polls";
    }

    public String addItem() {

        ItemTO itemTO = new ItemTO();

        itemTO.setPoll(pollTO);
        itemTO.setTitle(itemTitle);
        itemTO.setItemType(ItemType.values()[Integer.parseInt(itemType)]);

        itemTO = logic.storeItem(itemTO);

        if (itemTO.getItemType() == ItemType.YES_NO) {
            ItemOptionTO yes = new ItemOptionTO();
            yes.setCount(0);
            yes.setTitle("Yes");
            yes.setItem(itemTO);
            yes.setDescription("");

            logic.storeItemOption(yes);

            ItemOptionTO no = new ItemOptionTO();
            no.setCount(0);
            no.setTitle("No");
            no.setItem(itemTO);
            yes.setDescription("");

            logic.storeItemOption(no);
            return "edit-poll?faces-redirect=true&id=" + paramString;
        }

        return "edit-item?faces-redirect=true&id=" + itemTO.getId();
    }

    public String delete() {
        logic.deletePoll(paramID);
        return "my-polls";
    }

    public String save() {
        return "";
    }

    public void validateTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.uniquePollTitle(title, pollTO.getId())) {
            FacesMessage message = new FacesMessage("The title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

}
