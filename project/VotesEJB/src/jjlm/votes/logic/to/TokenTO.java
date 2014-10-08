/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.votes.logic.to;



public class TokenTO extends AbstractEntityTO {
    private static final long serialVersionUID = -4370022270160263849L;
    
    private String signature;
    private ParticipantTO participant;
    private PollTO poll;
    
    private boolean invalid;
    
    
    public TokenTO(){
        
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public ParticipantTO getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantTO participant) {
        this.participant = participant;
    }

    public PollTO getPoll() {
        return poll;
    }

    public void setPoll(PollTO poll) {
        this.poll = poll;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }
}
