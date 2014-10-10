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
import javax.ejb.EJB;
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
import jjlm.votes.web.MailerBean;
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.logic.ParticipantListParser;

/**
 *
 * @author maxmeffert
 */
@Named
@SessionScoped
public class EditPollBean extends OrganizerBean {
    @EJB
    private MailerBean mailerBean;

    private PollTO poll;
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

        poll = new PollTO();
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
            poll = logic.getPoll(this.paramID);

            setPollDescription(poll.getDescription());
            setPollTitle(poll.getTitle());

            pollItems = (poll.getId() != null
                    ? logic.getItemsOfPoll(poll.getId())
                    : new ArrayList<ItemTO>());

            poll.setItems(pollItems);

            if (poll.getStartPoll() != null) {
                setStartPoll(sdf.format(poll.getStartPoll()));
            } else {
                setStartPoll("");
            }
            if (poll.getEndPoll() != null) {
                setEndPoll(sdf.format(poll.getEndPoll()));
            } else {
                setEndPoll("");
            }
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public PollTO getPollTO() {
        return poll;
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
        return this.poll.getItems();
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
        return this.poll.getPollState().toString();
    }

    public boolean isStarted() {
        return poll.getPollState() == PollState.STARTED;
    }

    public boolean isPrepared() {

        return poll.getPollState() == PollState.PREPARED;

    }

    public boolean isVoting() {

        return poll.getPollState() == PollState.VOTING;

    }

    public boolean isFinished() {

        return poll.getPollState() == PollState.FINISHED;

    }

    public String addParticipants() {

        if (getParticipantsText() != null) {

            ParticipantListParser plp = new ParticipantListParser();

            List<String> emails = plp.parse(getParticipantsText());
            List<ParticipantTO> participants = logic.getParticipantsOfPoll(poll.getId());

            for (ParticipantTO participant : participants) {

                int i;
                while ((i = emails.indexOf(participant.getEmail())) != -1) {

                    emails.remove(i);

                }

            }

            for (String email : emails) {

                ParticipantTO participantTO = new ParticipantTO();
                participantTO.setEmail(email);
                participantTO.setPoll(poll);
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
        
        // send mails to participants
        mailerBean.init(paramID);
        mailerBean.sendMails();
   
        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String stop() {

        logic.resetPoll(paramID);

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String edit() {
        poll.setDescription(pollDescription);
        poll.setTitle(pollTitle);

        boolean valid = poll.getItems().size() > 0;

        for (ItemTO item : poll.getItems()) {

            valid = valid && item.isValid();

        }

        poll.setValid(valid);

        Date endDate = null;
        try {
            endDate = sdf.parse(getEndPoll());
        } catch (ParseException ex) {
            System.err.println(ex);
        }
        poll.setEndPoll(endDate);

        logic.storePoll(poll);

        poll = null;

        setPollTitle("");
        setPollDescription("");
        setStartPoll("");
        setEndPoll("");

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    public String addItem() {

        ItemTO itemTO = new ItemTO();

        itemTO.setPoll(poll);
        itemTO.setTitle(itemTitle);
        itemTO.setItemType(ItemType.values()[Integer.parseInt(itemType)]);

        itemTO = logic.storeItem(itemTO);

        if (itemTO.getItemType() == ItemType.YES_NO) {
            ItemOptionTO yes = new ItemOptionTO();
            yes.setVotes(0);
            
            yes.setTitle("Yes");
            yes.setItem(itemTO);
            yes.setDescription("");

            logic.storeItemOption(yes);

            ItemOptionTO no = new ItemOptionTO();
            no.setVotes(0);
            no.setTitle("No");
            no.setItem(itemTO);
            yes.setDescription("");
            
            logic.storeItemOption(no);
            
            
            itemTO.setM(1);
            itemTO = logic.storeItem(itemTO);
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

        if (!logic.uniquePollTitle(title, poll.getId())) {
            FacesMessage message = new FacesMessage("The title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

    public void validateItemTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.isItemTitleUnique(paramID, title) && !title.equals("")) {
            FacesMessage message = new FacesMessage("The item-title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }
    
    public void validateEndDate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String endDate = (String) value;

        if (!logic.isValidEndDate(paramID, endDate)) {
            FacesMessage message = new FacesMessage("The End-Date is not valid. Please choose another.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

}
