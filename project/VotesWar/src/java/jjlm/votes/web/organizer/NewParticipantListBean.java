/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jjlm.votes.web.organizer;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import jjlm.votes.web.logic.ParticipantListParser;

/**
 *
 * @author darjeeling
 */
@Named
@RequestScoped
public class NewParticipantListBean {

    private String participantListText;

    public String getParticipantListText() {
        return participantListText;
    }

    public void setParticipantListText(String participantListText) {
        this.participantListText = participantListText;
    }
    
    
    
    public void save () {
        
        ParticipantListParser parser = new ParticipantListParser();
        
        System.out.println(parser.parse(this.participantListText));
        
    }
    
    
}
