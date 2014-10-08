/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.organizer.OrganizerBean;

/**
 *
 * @author lukas
 */
@Named
@SessionScoped
public class ResultPollBean extends OrganizerBean {

    private PollTO pollTO;
    private int paramID;
    private String paramString;

    public void init() {
        paramString = RequestParameters.get("id");
        try {
            this.paramID = Integer.parseInt(paramString);
            pollTO = logic.getPoll(this.paramID);
        } catch (Exception e) {
            System.err.println(e);
        }

    }

}
