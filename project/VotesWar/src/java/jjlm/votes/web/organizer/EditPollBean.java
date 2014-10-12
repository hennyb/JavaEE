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
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.logic.ParticipantListParser;

/**
 * BackingBean (Controller) for the edit-poll page
 *
 */
@Named
@SessionScoped
public class EditPollBean extends OrganizerBean {

    /**
     * Current poll to edit
     */
    private PollTO poll;
    private int paramID;

    private String pollDescription;
    private String pollTitle;

    private String startPoll;
    private String endPoll;

    private String itemTitle;
    private String itemType;

    private String participantsText;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private List<ItemTO> pollItems;

    private String paramString;

    private String notificationText;

    private String pollState;
    
    private boolean tracking =false;
    
    

    public PollTO getPollTO() {
        return poll;
    }

    public boolean isTracking() {
        return tracking;
    }

    public void setTracking(boolean tracking) {
        this.tracking = tracking;
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
    
    public void toogleTracking(){
        tracking = !tracking;
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

    public boolean showPoll() {

        return poll.getOrganizer().getId() == this.getOrganizer().getId();

    }

    /**
     * Initializes EditPollBean on request (redirect)
     */
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
                setStartPoll(simpleDateFormat.format(poll.getStartPoll()));
            } else {
                setStartPoll("");
            }
            if (poll.getEndPoll() != null) {
                setEndPoll(simpleDateFormat.format(poll.getEndPoll()));
            } else {
                setEndPoll("");
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public boolean isPollOfOrganizer() {
        return logic.getPollIdsOfOrganizer(logic.getOrganizer(user.getEmail()).getId()).contains(paramID);
    }
    

    /**
     * Adds a new Item to the current poll
     *
     * @return
     */
    public String addItem() {

        save();

        poll = logic.getPoll(this.paramID);

        ItemTO itemTO = new ItemTO();

        itemTO.setPoll(poll);
        itemTO.setTitle(itemTitle);
        itemTO.setItemType(ItemType.values()[Integer.parseInt(itemType)]);

        itemTO = logic.storeItem(itemTO);

        poll = null;

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

    /**
     * Adds new participants to the current poll
     *
     * @return
     */
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

    /**
     * Deletes participant from current poll
     *
     * @param participantId
     * @return
     */
    public String deleteParticipant(int participantId) {

        logic.deleteParticipant(participantId);

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    /**
     * Starts current poll
     *
     * @return
     */
    public String start() {
        save();
        logic.startPoll(paramID, notificationText);


        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    /**
     * Resets current poll
     *
     * @return
     */
    public String reset() {

        logic.resetPoll(paramID);

        return "edit-poll?faces-redirect=true&id=" + paramString;
    }

    /**
     * Saves changes made to the current poll
     *
     * @return
     */
    public String save() {
        poll.setDescription(pollDescription);
        poll.setTitle(pollTitle);

        boolean valid = poll.getItems().size() > 0;

        for (ItemTO item : poll.getItems()) {
            valid = valid && item.isValid();
        }

        poll.setValid(valid);

        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(getEndPoll());
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

    /**
     * Deletes current poll
     *
     * @return
     */
    public String delete() {
        logic.deletePoll(paramID);
        return "my-polls";
    }

    /**
     * Validates current poll title to be unique system wide
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validateTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.uniquePollTitle(title, poll.getId())) {
            FacesMessage message = new FacesMessage("The title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

    /**
     * Validates new item title to be unique poll wide
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validateItemTitle(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String title = (String) value;

        if (!logic.isItemTitleUnique(paramID, title) && !title.equals("")) {
            FacesMessage message = new FacesMessage("The item-title is not unique. Please choose another one");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }

    /**
     * Validates poll end date to be in the future
     *
     * @param context
     * @param component
     * @param value
     * @throws ValidatorException
     */
    public void validateEndDate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

        String endDate = (String) value;

        if (!logic.isValidEndDate(paramID, endDate)) {
            FacesMessage message = new FacesMessage("The End-Date is not valid. Please choose another.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(component.getClientId(), message);
            throw new ValidatorException(message);
        }

    }
    
    public boolean isItemValid(int itemId){
        return logic.isItemValid(itemId);
    }

}
