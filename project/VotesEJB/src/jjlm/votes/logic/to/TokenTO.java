/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;

import jjlm.votes.persistence.entities.Participant;
import jjlm.votes.persistence.entities.Poll;

/**
 *
 * @author henny
 */
public class TokenTO extends NamedEntityTO {
    private static final long serialVersionUID = -4370022270160263849L;
    
    private String value;
    private Participant participant;
    private Poll poll;
    
    
    public TokenTO(){
        
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
    
}
