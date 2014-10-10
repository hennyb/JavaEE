/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.web;

import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.ParticipantTO;
import jjlm.votes.logic.to.PollTO;
import jjlm.votes.logic.to.TokenTO;
import jjlm.votes.web.help.RequestParameters;
import jjlm.votes.web.organizer.OrganizerBean;

/**
 *
 * @author lukas
 */
@Named
@RequestScoped
public class MailerBean extends OrganizerBean {

    private PollTO poll;
    private int paramID;
    private String paramString;
    private TokenTO token;
        

    public void init() {
        paramString = RequestParameters.get("id");
        try {
            this.paramID = Integer.parseInt(paramString);
            this.poll = logic.getPoll(this.paramID);
            
            List<ParticipantTO> participants = logic.getParticipantsOfPoll(this.paramID);
            
            for (ParticipantTO participant: participants) {
               participant.setToken(logic.getTokensOfPollAndParticipant(paramID, participant.getId()));
            }
                 
            this.poll.setParticipants(participants);
        } catch (Exception e) {
            System.err.println(e);
        }

    }
    
    
    public void sendMails() {
        
    }
    
    public void sendMail(ParticipantTO participant) {
        
    }
    
    public String getMailBody(ParticipantTO participant) {
        String result =  "Hi,\n";
        result += "Here is your Link to the Poll:  poll.xhtml?id=" + this.paramID;
        result += "Your Token is: " + participant.getToken().getSignature();
        return result;
    }
    
    public PollTO getPoll() {
        return this.poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

}
