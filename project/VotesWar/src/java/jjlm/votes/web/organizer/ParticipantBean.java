/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web.organizer;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.web.help.RequestParameters;

@Named
@SessionScoped
public class ParticipantBean extends OrganizerBean {

    private ParticipantTO participant;
    private String paramString;

    public void init() {

        paramString = RequestParameters.get("id");

        try {
            int id = Integer.parseInt(paramString);
            participant = logic.getParticipant(id);

        } catch (Exception e) {

        }

    }

    public ParticipantTO getParticipant() {
        return participant;
    }

    public long getNumberOfParticipants() {
        return logic.getNumberOfParticipants(participant.getPoll().getId());
    }

}
