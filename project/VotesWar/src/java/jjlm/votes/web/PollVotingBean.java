/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.logic.VotesLogic;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.PollTO;

@Named
@RequestScoped
public class PollVotingBean implements Serializable {

    @EJB
    protected VotesLogic logic;

    private PollTO poll;

    public PollVotingBean() {

    }


    private void setPoll() {
        poll = logic.getPoll(1);
        poll.setItems(logic.getItemsOfPoll(poll.getId()));
        for (ItemTO item : poll.getItems()) {
            item.setOptions(logic.getOptionsOfItem(item.getId()));
        }
    }

    public PollTO getPoll() {
        if (poll == null) {
            setPoll();
        }

        return poll;
    }

    public String bla() {
        return "joooo";
    }

}
