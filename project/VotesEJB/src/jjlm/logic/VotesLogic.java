/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jjlm.logic;

import jjlm.votes.persistence.entities.PollState;
import java.util.List;
import javax.ejb.Remote;
import jjlm.votes.logic.to.ItemTO;
import jjlm.votes.logic.to.OrganizerTO;
import jjlm.votes.logic.to.PollTO;

@Remote
public interface VotesLogic {
    
    OrganizerTO getOrganizer(String email);
    
    OrganizerTO findFirst();
    
    OrganizerTO storeOrganizer(OrganizerTO to);
    
    PollTO storePoll(PollTO to);
    
    PollTO addOrganizerToPoll(int organizerId, int pollId);
    
    String getPlainString();
    
    List<PollTO> getPollsfromOrganizer(OrganizerTO to);
    
    List<PollTO> getPollsfromOrganizer(int organizerID);
    
    List<PollTO> getPollsfromOrganizer(int organizerID, int from, int to);
    
    List<PollTO> getAllPolls();
    
    PollTO getPoll(String name, String description);
    
    PollTO getPoll(int pollId);
    
    PollState getStateOfPoll(int pollId);
    
    List<ItemTO> getItemsOfPoll(int poollId);
    
    ItemTO storeItem(ItemTO to);
    
}
