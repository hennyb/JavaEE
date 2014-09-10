/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import jjlm.votes.logic.to.TokenTO;

/**
 *
 * @author henny
 */
@Entity
public class Token extends NamedEntity<Token,TokenTO>{
    private static final long serialVersionUID = 4046280168952966572L;
    
    private String value;
    private Participant participant;
    private Poll poll;
    
    public Token(){
        
    }

    @ManyToOne
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
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

    @Override
    public TokenTO createTO() {
        TokenTO to = new TokenTO();
        return to;
    }
    
    
    
}
