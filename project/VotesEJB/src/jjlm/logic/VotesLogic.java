/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic;

import javax.ejb.Remote;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;

@Remote
public interface VotesLogic {
    
    OrganizerTO getOrganizer(String email);
    
    OrganizerTO findFirst();
    
    void storeOrganizer(OrganizerTO to);
    
    PollTO storePoll(PollTO to);
    
    String getPlainString();
    
    OrganizerTO lookupUser(String uid);
    
    OrganizerTO getUser(String uid);
    
}
