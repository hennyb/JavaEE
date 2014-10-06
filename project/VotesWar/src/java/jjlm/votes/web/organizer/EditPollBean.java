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
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.ItemType;

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

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

    private List<ItemTO> pollItems;

    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> sessionMap = facesContext.getExternalContext().getRequestParameterMap();
        setParamID(sessionMap.get("id"));

    }

    public String setParamID(String paramID) {
        try {
            this.paramID = Integer.parseInt(paramID);
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
                setStartPoll("");
            }
            setItemTitle("");
            setItemType("");
        } catch (Exception e) {
            System.err.println(e);
            pollTO = new PollTO();
        }

        return "edit-poll";
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

    public String edit() {
        pollTO.setDescription(pollDescription);

        Date startDate = null;
        try {
            startDate = sdf.parse(getStartPoll());
        } catch (ParseException ex) {
            System.err.println(ex);
        }
        pollTO.setStartPoll(startDate);

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
        return ("my-polls");
    }

    public String addItem() {

        ItemTO itemTO = new ItemTO();

        itemTO.setPoll(pollTO);
        itemTO.setTitle(itemTitle);
        itemTO.setItemType(ItemType.values()[Integer.parseInt(itemType)]);

        itemTO = logic.storeItem(itemTO);

        return "edit-item?faces-redirect=true&id=" + itemTO.getId();
    }

}
