/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import jjlm.votes.logic.to.PollTO;

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

    private String startPoll;
    private String endPoll;

    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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
            setPollName(pollTO.getName());

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

        } catch (Exception e) {
            System.err.println(e);
            pollTO = new PollTO();
        }

        return "edit-poll";
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

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String edit() {
        pollTO.setName(pollName);
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

        setPollName("");
        setPollDescription("");
        setStartPoll("");
        setEndPoll("");
        return ("my-polls");
    }

}
