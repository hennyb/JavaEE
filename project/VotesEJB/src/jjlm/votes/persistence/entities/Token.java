/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import jjlm.votes.logic.to.TokenTO;

/**
 *
 * @author henny
 */
@Entity
public class Token extends AbstractEntity<Token,TokenTO>{
    private static final long serialVersionUID = 4046280168952966572L;
    
    private String signature;
    private Participant participant;
    private Poll poll;
    
    private boolean invalid;
    
    public Token(){
    }

    @ManyToOne
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Column(unique = true)
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    @OneToOne
    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
    
    @Override
    public TokenTO createTO() {
        TokenTO to = new TokenTO();
        to.setId(id);
        to.setParticipant(participant.createTO());
        to.setPoll(poll.createTO());
        to.setSignature(signature);
        to.setInvalid(invalid);
        
        return to;
    }
    
}
