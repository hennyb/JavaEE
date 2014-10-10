/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.persistence.entities.PollState;
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.organizer.OrganizerBean;

/**
 *
 * @author lukas
 */
@Named
@SessionScoped
public class ResultPollBean extends OrganizerBean {

    private PollTO poll;
    private int paramID;
    private String paramString;

    public void init() {
        paramString = RequestParameters.get("id");
        try {
            this.paramID = Integer.parseInt(paramString);
            this.poll = logic.getPoll(this.paramID);

            List<ItemTO> items = logic.getItemsOfPoll(this.paramID);
            for (ItemTO item : items) {
                item.setOptions(logic.getOptionsOfItem(item.getId()));
            }

            this.poll.setItems(items);

        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public boolean showResult() {

        boolean result = poll.getPollState() == PollState.FINISHED;
        result &= logic.getParticipation(paramID) >= 3;

        return result;
    }

    public PollTO getPoll() {
        return this.poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

}
