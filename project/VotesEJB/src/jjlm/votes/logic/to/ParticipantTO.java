/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;


/**
 *
 * @author henny
 */
public class ParticipantTO extends AbstractEntityTO{
    private static final long serialVersionUID = 7757146310902670505L;
    
    private String email;
    private boolean hasVoted;
    
    private PollTO poll;
    
    public ParticipantTO(){
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }
    
    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }
    
}
